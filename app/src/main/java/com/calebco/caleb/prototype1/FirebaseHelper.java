package com.calebco.caleb.prototype1;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;




    public class FirebaseHelper {

        DatabaseReference db;
        Boolean saved=null;
        ArrayList<String> videoList=new ArrayList<>();

        public FirebaseHelper(DatabaseReference db) {
            this.db = db;
        }

        //WRITE
        public Boolean save(VideoCardClass videoItem)
        {
            if(videoItem==null)
            {
                saved=false;
            }else
            {
                try
                {
                    db.child("Users-Uploads").push().setValue(videoItem);
                    saved=true;

                }catch (DatabaseException e)
                {
                    e.printStackTrace();
                    saved=false;
                }
            }

            return saved;
        }

        //READ
        public ArrayList<String> retrieve()
        {
            //testing
            Log.d("FLAG 4", "IN RETRIEVE");
            /*
            db.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    fetchData(dataSnapshot);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    fetchData(dataSnapshot);

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            */

            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //testing
                    Log.d("FLAG 5", "ONDATACHANGE FETCHING SNAPSHOT");
                    fetchData(dataSnapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w("FLAG 2 ", "loadPost:onCancelled", databaseError.toException());
                }
            });

            return videoList;
        }

        private void fetchData(DataSnapshot dataSnapshot)
        {
            videoList.clear();


            Log.d("FLAG 6", "EXECUTING LOOP");
            for (DataSnapshot ds : dataSnapshot.getChildren())
            {
                String title=ds.getValue(VideoCardClass.class).getTitle();
                String recipient=ds.getValue(VideoCardClass.class).getRecipient();
                String date=ds.getValue(VideoCardClass.class).getDate();
                videoList.add(title);
                videoList.add(recipient);
                videoList.add(date);

                //testing data
                Log.d("FLAG CONTENT", "TITLE: " + videoList.get(0));
                Log.d("FLAG CONTENT", "RECIPIENT: " + videoList.get(1));
                Log.d("FLAG CONTENT", "DATE: " + videoList.get(2));
            }

        }
    }


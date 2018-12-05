package com.calebco.caleb.prototype1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ManageUploads extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private String currentUserId;


    DatabaseReference db;

    FirebaseRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_manage);
        toolbar = findViewById(R.id.manageBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Uploads");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        //current user ID
        currentUserId = mAuth.getCurrentUser().getUid();


        db = FirebaseDatabase.getInstance().getReference().child("Users/" + "/" + currentUserId + "/" + "Uploads/");

        //query the database for user uploads
        Query query = db.limitToLast(10);
        FirebaseRecyclerOptions<VideoCardClass> options = new FirebaseRecyclerOptions.Builder<VideoCardClass>().setQuery(query, VideoCardClass.class).setLifecycleOwner(this).build();

        recyclerView = findViewById(R.id.ManageVideoView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FirebaseRecyclerAdapter<VideoCardClass, CardViewHolder>(options) {

            //inflate cards
            @Override
            public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                Log.d("FLAG 2", "IN ON CREATE VIEW HOLDER");

                //LayoutInflater inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_card_layout, parent, false);
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());

                return new CardViewHolder(inflater.inflate(R.layout.video_card_layout, parent, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull CardViewHolder holder, int position, @NonNull VideoCardClass model) {
                Log.d("FLAG 3", "IN ON BIND VIEW HOLDER");


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //go to video detail with intent
                    }
                });


                holder.bind(model);
                //testing
                Log.d("FLAG 4", "BINDING HOLDER" + holder.titleText);

            }


        };
        recyclerView.setAdapter(adapter);

    }
}











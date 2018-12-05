package com.calebco.caleb.prototype1;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 *
 * Download and display users uploads from db
 */

public class VideosFragment extends Fragment {


    public VideosFragment() {
        // Required empty public constructor
    }

    private static final String TAG = VideosFragment.class.getSimpleName();
    FirebaseRecyclerAdapter adapter;

    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private String currentUserId;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_videos, container, false);
        mAuth = FirebaseAuth.getInstance();

        //current user ID
        currentUserId = mAuth.getCurrentUser().getUid();

        recyclerView = view.findViewById(R.id.videoRecyclerView);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        Query query = FirebaseDatabase.getInstance().getReference().child("Users/" + "/" + currentUserId + "/" + "Uploads/").limitToLast(10);

        FirebaseRecyclerOptions<VideoCardClass> options = new FirebaseRecyclerOptions.Builder<VideoCardClass>().setQuery(query, VideoCardClass.class).setLifecycleOwner(this).build();

        adapter = new FirebaseRecyclerAdapter<VideoCardClass, CardViewHolder>(options) {

            @Override
            public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                LayoutInflater inflater = LayoutInflater.from(parent.getContext());

                return new CardViewHolder(inflater.inflate(R.layout.video_card_layout, parent, false));
            }


            protected void onBindViewHolder(@NonNull CardViewHolder holder, int position, @NonNull VideoCardClass model) {
                final DatabaseReference cardRef = getRef(position);
                VideoCardClass videoCardClass = new VideoCardClass();
                final String cardKey = cardRef.getKey();
                holder.titleText.setText(videoCardClass.getTitle());
                holder.recipientText.setText(videoCardClass.getRecipient());
                holder.dateText.setText(videoCardClass.getDate());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //go to video detail with intent
                    }
                });


                holder.bind(model);

            }

        };
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        if(adapter != null) {
            adapter.startListening();
        } else {
            Log.d("ON START***", "NULL ADAPTER");
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if(adapter != null){
            adapter.stopListening();
        } else {
            Log.d("ON STOP***", "NULL ADAPTER");
        }

    }
}








/*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.videoRecyclerView);
        //recyclerView.setHasFixedSize(true);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users Uploads");
        databaseReference.keepSynced(true);
        /*
        videoAdapter = new VideoAdapter(VideoCardClass.class,R.layout.video_card_layout, CardViewHolder.class,databaseReference, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(videoAdapter);
        */
//    }




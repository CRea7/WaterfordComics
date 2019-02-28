package com.example.waterfordcomicsapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.waterfordcomicsapp.R;
import com.example.waterfordcomicsapp.adapters.MyAdapter;
import com.example.waterfordcomicsapp.models.Comic;
import com.example.waterfordcomicsapp.models.FavouriteComics;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyComics extends Base {

    private DatabaseReference mDatabase;
    private FirebaseDatabase db;
    //private ArrayList<Comic> MyComicsList;
    RequestQueue requestQueue;
    public RecyclerView mRecyclerView;
    public FirebaseRecyclerAdapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comics);

        db = FirebaseDatabase.getInstance();
        mDatabase = db.getReference().child("Comic");

        final ArrayList<FavouriteComics> MyComicsList = new ArrayList<>();


        Log.i("Fire", String.valueOf(MyComicsList.size()));

        mRecyclerView = findViewById(R.id.Comic_Recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        final Query query;
        query= FirebaseDatabase.getInstance().getReference().child("Comic");
        FirebaseRecyclerOptions<Comic> options = new FirebaseRecyclerOptions.Builder<Comic>()
                .setQuery(query,Comic.class).build();

        mAdapter = new FirebaseRecyclerAdapter<Comic,recyclerViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull recyclerViewHolder holder, int position, @NonNull final Comic comic) {
                holder.addImage.setVisibility(View.GONE);
                holder.deleteButton.setVisibility(View.VISIBLE);
                holder.comicName.setText(comic.getComicTitle());
                holder.comicIssueNum.setText(comic.getIssueNumber());
                if (comic.getImage().isEmpty()) {
                    Picasso.get().load("http://via.placeholder.com/150x225").fit().into(holder.comicImage);
                } else {
                    Picasso.get().load(comic.getImage()).fit().into(holder.comicImage);
                }


                holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDatabase.child(comic.getComicId()).removeValue();
                    }
                });
            }

            @NonNull
            @Override
            public recyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_layout, viewGroup, false);

                return new recyclerViewHolder(v);
            }
        };
        mAdapter.startListening();
        mRecyclerView.setAdapter(mAdapter);
    }

    public void GoHome(View v){
        startActivity (new Intent(this, MainActivity.class));
    }


 class recyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView comicName;
        public TextView comicIssueNum;
        public ImageView comicImage;
        public ImageView addImage;
        public ImageView deleteButton;
        public recyclerViewHolder(View v) {
            super(v);
            comicName = v.findViewById(R.id.comic_name);
            comicIssueNum = v.findViewById(R.id.comic_issueNum);
            comicImage = v.findViewById(R.id.comic_image);
            addImage = v.findViewById(R.id.comic_add);
            deleteButton = v. findViewById(R.id.comic_delete);
        }
    }
}

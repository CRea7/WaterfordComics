package com.example.waterfordcomicsapp.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.waterfordcomicsapp.R;
import com.example.waterfordcomicsapp.models.Comic;
import com.example.waterfordcomicsapp.models.FavouriteComics;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyComics extends NavDrawerActivity {

    private DatabaseReference mDatabase;
    private FirebaseDatabase db;
    //private ArrayList<Comic> MyComicsList;
    RequestQueue requestQueue;
    public RecyclerView mRecyclerView;
    public FirebaseRecyclerAdapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;
    private Context mContext;
    private FirebaseAuth mAuth;

    //Firebase adapter to display list as recycler view wasn't working
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_my_comics, null, false);
        drawer.addView(contentView,0);


        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            userEmail = currentUser.getEmail();
        }
        Log.i("UserEmail", userEmail);

        mContext = this;

        db = FirebaseDatabase.getInstance();
        mDatabase = db.getReference().child("Comic");

        final ArrayList<FavouriteComics> MyComicsList = new ArrayList<>();


        Log.i("Fire", String.valueOf(MyComicsList.size()));

        mRecyclerView = findViewById(R.id.Comic_Recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        final Query query;
        query= FirebaseDatabase.getInstance().getReference().child("Comic").orderByChild("userEmail").equalTo(userEmail);
        FirebaseRecyclerOptions<Comic> options = new FirebaseRecyclerOptions.Builder<Comic>()
                .setQuery(query,Comic.class).build();

        mAdapter = new FirebaseRecyclerAdapter<Comic,recyclerViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull recyclerViewHolder holder, int position, @NonNull final Comic comic) {
                holder.addImage.setVisibility(View.GONE);
                holder.deleteButton.setVisibility(View.VISIBLE);
                holder.comicName.setText(comic.getComicTitle());
                holder.comicIssueNum.setText(comic.getIssueNumber());
                final String comicImageUrl;

                if (comic.getImage().isEmpty()) {
                    Picasso.get().load("http://via.placeholder.com/150x225").fit().into(holder.comicImage);
                    comicImageUrl = "http://via.placeholder.com/150x225";
                } else {
                    Picasso.get().load(comic.getImage()).fit().into(holder.comicImage);
                    comicImageUrl = comic.getImage();
                }

                //Same as add but on delete
                holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDatabase.child(comic.getComicId()).removeValue();
                    }
                });

                holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, SavedComicsActivity.class);
                        intent.putExtra("image_url", comicImageUrl);
                        intent.putExtra("comic_name", comic.getComicTitle());
                        intent.putExtra("comic_id", comic.getComicId());
                        mContext.startActivity(intent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);



        //This makes it crash on startup not sure why
        //SearchView searchView = (SearchView) menuItem.getActionView();
        //searchView.setOnQueryTextListener(this);

        return true;
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
        ConstraintLayout parentLayout;

        public recyclerViewHolder(View v) {
            super(v);
            comicName = v.findViewById(R.id.comic_name);
            comicIssueNum = v.findViewById(R.id.comic_issueNum);
            comicImage = v.findViewById(R.id.my_comic_image);
            addImage = v.findViewById(R.id.comic_add);
            deleteButton = v. findViewById(R.id.comic_delete);
            parentLayout = v.findViewById(R.id.parent_layout);
        }
    }
}

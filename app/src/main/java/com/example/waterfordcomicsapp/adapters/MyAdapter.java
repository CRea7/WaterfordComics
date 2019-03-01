package com.example.waterfordcomicsapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.waterfordcomicsapp.R;
import com.example.waterfordcomicsapp.activities.MainActivity;
import com.example.waterfordcomicsapp.models.Comic;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Comic> mDataset;
    private DatabaseReference mDatabase;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView comicName;
        public TextView comicIssueNum;
        public ImageView comicImage;
        public ImageView addImage;
        public MyViewHolder(View v) {
            super(v);
            comicName = v.findViewById(R.id.comic_name);
            comicIssueNum = v.findViewById(R.id.comic_issueNum);
            comicImage = v.findViewById(R.id.comic_image);
            addImage = v.findViewById(R.id.comic_add);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Comic> comicList) {
        this.mDataset=comicList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Comic comic = mDataset.get(position);
            holder.comicName.setText(comic.getComicTitle());
            holder.comicIssueNum.setText(comic.getIssueNumber());

            //Some comics dont have images so assign placeholder to those
            if (comic.getImage().isEmpty()) {
                Picasso.get().load("http://via.placeholder.com/150x225").fit().into(holder.comicImage);
            } else {
                Picasso.get().load(comic.getImage()).fit().into(holder.comicImage);
            }
        //FirebaseApp.initializeApp();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Comic");

        holder.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(comic.getComicId()).setValue(comic);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    //search filter which is not working
    public void getListUpdate(List<Comic> newList){
        mDataset = new ArrayList<>();
        mDataset.addAll(newList);
        notifyDataSetChanged();
    }


}

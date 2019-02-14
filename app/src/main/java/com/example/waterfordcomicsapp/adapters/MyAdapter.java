package com.example.waterfordcomicsapp.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.waterfordcomicsapp.R;
import com.example.waterfordcomicsapp.models.Comic;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Comic> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView comicName;
        public TextView comicIssueNum;
        public ImageView comicImage;
        public MyViewHolder(View v) {
            super(v);
            comicName = v.findViewById(R.id.comic_name);
            comicIssueNum = v.findViewById(R.id.comic_issueNum);
            comicImage = v.findViewById(R.id.comic_image);
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

        Comic comic = mDataset.get(position);
        if(comic.getImage().isEmpty())
        {
            //do nothing
        }
        else {
            holder.comicName.setText(comic.getComicTitle());
            holder.comicIssueNum.setText(comic.getIssueNumber());
            if (comic.getImage().isEmpty()) {

            } else {
                Picasso.get().load(comic.getImage()).fit().into(holder.comicImage);
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}

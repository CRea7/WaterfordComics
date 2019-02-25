package com.example.waterfordcomicsapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
            if (comic.getImage().isEmpty()) {

            } else {
                Picasso.get().load(comic.getImage()).fit().into(holder.comicImage);
            }
        //FirebaseApp.initializeApp();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Comic");

        holder.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = comic.getComicTitle();
                String issue = comic.getIssueNumber();
                String id = comic.getComicId();
                String image = comic.getImage();
                String price = comic.getPrice();
                String extension = comic.getExtension();
                String storeDate = comic.getStoreDate();

                HashMap<String, String> dataMap = new HashMap<String, String>();
                dataMap.put("ComicTitle", title);
                dataMap.put("ComicIssueNum", issue);
                dataMap.put("ComicId", id);
                dataMap.put("ComicImage", image);
                dataMap.put("ComicPrice", price);
                dataMap.put("ComicExtension", extension);
                dataMap.put("ComicStoreDate", storeDate);

                mDatabase.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    //this lets the user know if the data has been added correctly.
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i("Add", "Add Successful");
                        } else {
                            Log.i("Add", "Add Failed");
                        }
                    }
                });
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}

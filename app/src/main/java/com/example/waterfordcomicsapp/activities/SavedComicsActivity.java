package com.example.waterfordcomicsapp.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.waterfordcomicsapp.R;
import com.example.waterfordcomicsapp.models.Comic;
import com.example.waterfordcomicsapp.models.FavouriteComics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SavedComicsActivity extends Base{

    private DatabaseReference mDatabase;
    private FirebaseDatabase db;
    private String comicId = "";
    private String comicNote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savedcomics);
        Log.i("Saved", "onCreate: Started.");

        db = FirebaseDatabase.getInstance();
        mDatabase = db.getReference().child("Comic");



        final ArrayList<FavouriteComics> MyComicsList = new ArrayList<>();


        getIncomingIntent();
    }

    private void getIncomingIntent(){

        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("comic_name") && getIntent().hasExtra("comic_id") ){

            String imageUrl = getIntent().getStringExtra("image_url");
            String comicName = getIntent().getStringExtra("comic_name");
            comicId = getIntent().getStringExtra("comic_id");

            Log.i("ComicId", comicId);
            firebaseStuff();
            setScreen(imageUrl,comicName);
        }

    }

    private void setScreen(String imageUrl, String comicName) {

        TextView textView = findViewById(R.id.my_comic_name);
        textView.setText(comicName);

        EditText editText = findViewById(R.id.descriptionEditText);
        editText.setText(comicNote, TextView.BufferType.EDITABLE);

        ImageView imageView = findViewById(R.id.my_comic_image);
        Picasso.get().load(imageUrl).fit().into(imageView);

    }

    private void firebaseStuff()
    {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren())
                {
                    Comic comic = childSnapshot.getValue(Comic.class);

                    Log.i("ID", comic.comicId);
                    Log.i("SotrID", comicId);
                    if(comic.comicId.equals(comicId))
                    {
                        comicNote = comic.note;
                        Log.i("ComicNote", comicNote);
                    }

                    String comics = comic.toString();
                    Log.i("POST", comics);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

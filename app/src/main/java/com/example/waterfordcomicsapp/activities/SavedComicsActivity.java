package com.example.waterfordcomicsapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.waterfordcomicsapp.R;
import com.squareup.picasso.Picasso;

public class SavedComicsActivity extends Base{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savedcomics);
        Log.i("Saved", "onCreate: Started.");

        getIncomingIntent();
    }

    private void getIncomingIntent(){

        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("comic_name")){

            String imageUrl = getIntent().getStringExtra("image_url");
            String comicName = getIntent().getStringExtra("comic_name");

            setScreen(imageUrl,comicName);
        }

    }

    private void setScreen(String imageUrl, String comicName) {

        TextView textView = findViewById(R.id.my_comic_name);
        textView.setText(comicName);

        ImageView imageView = findViewById(R.id.my_comic_image);
        Picasso.get().load(imageUrl).fit().into(imageView);

    }

}

package com.example.waterfordcomicsapp.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.waterfordcomicsapp.R;
import com.example.waterfordcomicsapp.models.Comic;
import com.example.waterfordcomicsapp.models.FavouriteComics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class  SavedComicsActivity extends NavDrawerActivity {

    private DatabaseReference mDatabase;
    private FirebaseDatabase db;
    private String comicId = "";
    private String comicNote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_savedcomics, null, false);
        drawer.addView(contentView,0);

        getSupportActionBar().setTitle("Saved Comics");
        //navigationView.setCheckedItem(R.id.nav_home);

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        Log.i("Saved", "onCreate: Started.");

        db = FirebaseDatabase.getInstance();
        mDatabase = db.getReference().child("Comic");

        Button updateButton = findViewById(R.id.buttonUpdate);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("update", "it ran updateNotes()");
                updateNotes();
            }
        });

        final ArrayList<FavouriteComics> MyComicsList = new ArrayList<>();


        getIncomingIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }

    private void getIncomingIntent(){

        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("comic_name") && getIntent().hasExtra("comic_id") ){

            String imageUrl = getIntent().getStringExtra("image_url");
            String comicName = getIntent().getStringExtra("comic_name");
            comicId = getIntent().getStringExtra("comic_id");

            Log.i("ComicId", comicId);
            firebaseStuff(imageUrl,comicName);
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

    private void firebaseStuff(final String imageUrl, final String comicName)
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
                        setScreen(imageUrl,comicName);
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

    private void updateNotes()
    {
        EditText editText = findViewById(R.id.descriptionEditText);
        String newNote = editText.getText().toString();

        DatabaseReference taskRef = mDatabase.child(comicId);

        Map<String,Object> taskMap = new HashMap<String,Object>();
        taskMap.put("note", newNote);
        taskRef.updateChildren(taskMap);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

}

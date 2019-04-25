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

public class  SavedComicsActivity extends NavDrawerActivity implements NavigationView.OnNavigationItemSelectedListener {

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

    public static class SignUpInActivity extends Base {

        private FirebaseAuth mAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up_in);
            //initialize the thing to log in as this may stop crashing
            mAuth = FirebaseAuth.getInstance();

            final Button SignIn = findViewById(R.id.SignInButton);
            SignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SignIn();
                }
            });

            FirebaseUser currentUser = mAuth.getCurrentUser();
        }

        private void SignIn()
        {
            EditText emailInput = findViewById(R.id.SignInEmail);
            String email = emailInput.getText().toString();
            EditText passwordInput = findViewById(R.id.SignInPassword);
            String password = passwordInput.getText().toString();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.i("LogIn", "user logged in sucessfully");
                        GoHome();
                        userEmail = user.getEmail();
                    } else {
                        Log.i("LogIn","User log in failed");
                        Toast.makeText(SignUpInActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        public void GoHome(){
            startActivity (new Intent(this, MainActivity.class));
        }
    }
}

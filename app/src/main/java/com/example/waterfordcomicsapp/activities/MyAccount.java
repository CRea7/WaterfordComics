package com.example.waterfordcomicsapp.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.waterfordcomicsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.PrivateKey;

public class MyAccount extends NavDrawerActivity {

    private FirebaseAuth mAuth;
    private Button logOut;
    private TextView Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_my_account, null, false);
        drawer.addView(contentView,0);

        getSupportActionBar().setTitle("My Account");

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            userEmail = currentUser.getEmail();
        }

        setScreen();

        logOut = findViewById(R.id.account_log);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    public void setScreen()
    {
        Email = findViewById(R.id.account_Email);
        Email.setText(userEmail);
    }

    public void logOut()
    {
        FirebaseAuth.getInstance().signOut();
    }

    public void GoHome(View v){
        startActivity (new Intent(this, MainActivity.class));
    }

}

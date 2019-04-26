package com.example.waterfordcomicsapp.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.waterfordcomicsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccountActivity extends NavDrawerActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_create_account, null, false);
        drawer.addView(contentView,0);

        mAuth = FirebaseAuth.getInstance();

        Button createAccount = findViewById(R.id.CreatedButton);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }

    private void CreateAccount()
    {

        EditText emailInput = findViewById(R.id.CreateEmail);
        String email = emailInput.getText().toString();
        EditText passwordInput = findViewById(R.id.CreatedPassword);
        String password = passwordInput.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // if create account was successful
                            FirebaseUser user = mAuth.getCurrentUser();
                            userEmail = user.getEmail();
                            GoHome();
                        } else {
                            // If create account fails
                            Toast.makeText(CreateAccountActivity.this, "create user failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    public void GoHome(){
        startActivity (new Intent(this, MainActivity.class));
    }
}

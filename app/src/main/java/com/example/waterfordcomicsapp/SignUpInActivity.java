package com.example.waterfordcomicsapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.waterfordcomicsapp.activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpInActivity extends AppCompatActivity {

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

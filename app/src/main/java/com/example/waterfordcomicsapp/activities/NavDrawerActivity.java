package com.example.waterfordcomicsapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.waterfordcomicsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public String userEmail = "";

    NavigationView navigationView;
    DrawerLayout drawer;
    private FirebaseAuth mAuth;
    MenuItem logIn;
    MenuItem logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu nav_Menu = navigationView.getMenu();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            userEmail = currentUser.getEmail();
            nav_Menu.findItem(R.id.nav_signIn).setVisible(false);
            nav_Menu.findItem(R.id.nav_logOut).setVisible(true);
            nav_Menu.findItem(R.id.nav_create).setVisible(false);
            //nav_Menu.findItem(R.id.nav_Email).setTitle(userEmail);
        }
        else{
            nav_Menu.findItem(R.id.nav_signIn).setVisible(true);
            nav_Menu.findItem(R.id.nav_logOut).setVisible(false);
            nav_Menu.findItem(R.id.nav_create).setVisible(true);
            //nav_Menu.findItem(R.id.nav_Email).setTitle("Sign In to display information");
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        //UpdateUI(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity (new Intent(this, MainActivity.class));
            //getSupportActionBar().setTitle("Featured");
        } else if (id == R.id.nav_saved) {
            if(userEmail != "")
            {
                startActivity (new Intent(this, MyComics.class));
            }
            else
            {
                startActivity (new Intent(this, SignUpInActivity.class));
            }
        } else if (id == R.id.nav_signIn) {
            startActivity (new Intent(this, SignUpInActivity.class));
        } else if (id == R.id.nav_create) {
            startActivity (new Intent(this, CreateAccountActivity.class));
        } else if (id == R.id.nav_logOut) {
            FirebaseAuth.getInstance().signOut();
            startActivity (new Intent(this, MainActivity.class));
        }  else if (id == R.id.nav_maps) {
            startActivity (new Intent(this, MapsActivity.class));
        } else if (id == R.id.nav_account){
            startActivity(new Intent(this, MyAccount.class));
            //getSupportActionBar().setTitle("My Account");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    public void UpdateUI(Menu menu)
//    {
//        logIn = menu.findItem(R.id.nav_signIn);
//        logOut = menu.findItem(R.id.nav_logOut);
//
//        if(userEmail == "") {
//            logIn.setVisible(true);
//            logOut.setVisible(true);
//        }else{
//            logIn.setVisible(true);
//            logOut.setVisible(true);
//        }
//    }
}

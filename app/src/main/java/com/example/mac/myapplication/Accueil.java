package com.example.mac.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.FragmentManager;
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
import android.widget.ImageView;
import android.widget.TextView;


import com.example.mac.myapplication.layout.Panda;

import static com.example.mac.myapplication.R.id.imageView;

public class Accueil extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static String id;
    android.support.v4.app.Fragment fragment = null;
    Class fragmentClass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Bundle extras = getIntent().getExtras();
        String firstName,lastName,uri;

         firstName = extras.getString("firstname");
         lastName = extras.getString("lastname");
         uri =extras.getString("uri");
         id=extras.getString("iduserfb");

        Log.e("Name:", String.valueOf(firstName));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        TextView firstname=(TextView) header.findViewById(R.id.FirstName);
        TextView lastname=(TextView) header.findViewById(R.id.LastName);
        ImageView imgUri=(ImageView) header.findViewById(imageView);


            firstname.setText(firstName);
            lastname.setText(lastName);
            new ImageLoadTask(uri, imgUri).execute();
        Log.e("uri",uri);

        fragmentClass = Panda.class;
        try {
            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent,fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
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
        getMenuInflater().inflate(R.menu.accueil, menu);
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
        if (id == R.id.nav_camera) {
            fragmentClass = Panda.class;

        } else if (id == R.id.nav_gallery) {

            fragmentClass = Gifts.class;

        } else if (id == R.id.nav_slideshow) {
            fragmentClass= BlankFragment.class;
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        try {
            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent,fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    static String getId(){

        return id;
    }
}

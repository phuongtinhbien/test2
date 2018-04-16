package com.example.vuphu.test2;


import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(getSupportFragmentManager().findFragmentById(R.id.container) != null) {
            getSupportFragmentManager()
                    .beginTransaction().
                    remove(getSupportFragmentManager().findFragmentById(R.id.content)).commit();
        }
        setTitle("Rooms");
        transaction.replace(R.id.content, HeatFragment.newInstance()).commit();
        navigationView.setCheckedItem(R.id.nav_heating);
        sharedPreferences = getSharedPreferences("mode",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        setData();


    }

    public void setData(){
        Gson gson = new Gson();
        if (sharedPreferences.getAll().isEmpty()) {

            List<mode> modes = new ArrayList<>();

            modes.add(new mode("Comfort", "C", R.color.colorAccent, new int[]{12, 4, 52}));
            modes.add(new mode("Night", "N", android.R.color.darker_gray, new int[]{12, 4, 52}));
            modes.add(new mode("Comfort+", "C+", R.color.color3, new int[]{12, 4, 52}));
            modes.add(new mode("Eco", "E", R.color.color1, new int[]{12, 4, 52}));


            for (mode item : modes) {
                String json = gson.toJson(item);
                editor.putString(item.getName(), json);
                editor.commit();
            }
        }
        SharedPreferences sharedPreferences = getSharedPreferences("room",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.getAll().isEmpty()) {
            List<room> rooms = new ArrayList<>();
            rooms.add(new room("Living room", 15, new String[]{"Comfort", "Night", "Comfort+"}));
            rooms.add(new room("Bed room", 15, new String[]{"Comfort", "Night", "Comfort+"}));
            rooms.add(new room("Kitchen", 15, new String[]{"Comfort", "Night", "Comfort+"}));

            for (room item : rooms) {
                String json = gson.toJson(item);
                editor.putString(item.getName(), json);
                editor.commit();
            }
        }
        SharedPreferences sharedPreference = getSharedPreferences("special_mode",MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreference.edit();
        if (sharedPreference.getAll().isEmpty()) {
            edit.putInt("away", 20);
            edit.putInt("cooling", 12);
            edit.commit();
        }

        SharedPreferences sharedPreference1 = getSharedPreferences("schedule",MODE_PRIVATE);
        SharedPreferences.Editor edit1 = sharedPreference1.edit();
    if (sharedPreference1.getAll().isEmpty()) {
            List<schedule> schedules = new ArrayList<>();
            schedules.add(new schedule("Morning", "6:00","14:00",R.drawable.sun,1));
            schedules.add(new schedule("Afternoon", "14:01","18:00",R.drawable.afternoon,2));
            schedules.add(new schedule("Night", "18:01","5:59",R.drawable.night,3));
            for (schedule item : schedules) {
                String json = gson.toJson(item);
                edit1.putString(item.getName(), json);
                edit1.commit();
            }
            Log.i("check", "ok");
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment temp = null;
        if (id == R.id.nav_energy) {
            temp = EnergyFragment.newInstance();
            setTitle("Energy usage");
            // Handle the camera action
        } else if (id == R.id.nav_heating) {
            temp = HeatFragment.newInstance();
            setTitle("Rooms");
        } else if (id == R.id.nav_temperature) {
            temp = ModeFragment.newInstance();
            setTitle("Modify temperature");
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, temp).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

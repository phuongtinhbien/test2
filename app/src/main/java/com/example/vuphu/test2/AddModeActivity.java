package com.example.vuphu.test2;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class AddModeActivity extends AppCompatActivity implements ActivityCommunicator{


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private RoomApdater.adapter adapter;
    public  List<room> rooms;
    private RecyclerView list;
    private mode add;
    private EditText name_modify;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mode);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences("room", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        list = findViewById(R.id.list_room_add);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        list.setLayoutManager(linearLayoutManager);
        list.setNestedScrollingEnabled(false);
        list.setHasFixedSize(true);
        rooms = setData();
        FragmentManager fragmentManager = getFragmentManager();
        adapter = new RoomApdater.adapter(this, rooms);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);

        name_modify = findViewById(R.id.name_modify);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public List<room> setData() {
        List<room> modes = new ArrayList<>();
        Gson gson = new Gson();

        Map<String, ?> map = sharedPreferences.getAll();
        List<String> key = new ArrayList<String>((Collection<? extends String>) map.values());
        for (int i = 0; i < key.size(); i++) {
            modes.add(gson.fromJson(key.get(i).toString(), room.class));
        }
        return modes;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void saveMode(View view) {

        int[] a = new int[rooms.size()];
        String b = String.valueOf(name_modify.getText().charAt(0));
        for (int i=0;i<rooms.size();i++){
            a[i] = rooms.get(i).getTemp();
        }

        add = new mode(name_modify.getText().toString(),
                b,
                R.color.colorPrimary,
                a);

        SharedPreferences sharedPreferences = getSharedPreferences("mode",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        
        Gson gson =new Gson();
        String json = gson.toJson(add);
        editor.putString(add.getName(), json);
        editor.commit();
        Toast.makeText(this, "Add mode successfully", Toast.LENGTH_SHORT).show();

        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void passDataToActivity(int position, int temp) {
        Log.i("position",position+"");
        Log.i("temp",temp+"");

        rooms.get(position).setTemp(temp);
        adapter.notifyDataSetChanged();
    }
}

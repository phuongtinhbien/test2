package com.example.vuphu.test2;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ModifyTemperatureActivity extends AppCompatActivity implements ActivityCommunicator {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private mode mode;
    public List<room> rooms;
    private RecyclerView list;
    private RoomApdater.adapter adapter;
    private TextView name_modify, text_modify;
    private CircleImageView color_modify;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_temperature);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences("mode",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Intent intent = getIntent();
        mode = (com.example.vuphu.test2.mode) intent.getSerializableExtra("data");
        name_modify = findViewById(R.id.name_modify);
        text_modify = findViewById(R.id.text_modify);
        color_modify = findViewById(R.id.color_modify);

        name_modify.setText(mode.getName());
        text_modify.setText(mode.getText());
        color_modify.setImageResource(mode.getColor());
        list = findViewById(R.id.list_room_modify);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        list.setLayoutManager(linearLayoutManager);
        list.setNestedScrollingEnabled(false);
        list.setHasFixedSize(true);
        rooms = setData();
        adapter = new RoomApdater.adapter(this, rooms);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);
    }

    public List<room> setData() {
        SharedPreferences sharedPreferences = getSharedPreferences("room", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<room> modes = new ArrayList<>();
        Gson gson = new Gson();

        Map<String, ?> map = sharedPreferences.getAll();
        List<String> key = new ArrayList<String>((Collection<? extends String>) map.values());
        for (int i = 0; i < key.size(); i++) {
            modes.add(gson.fromJson(key.get(i).toString(), room.class));
            modes.get(i).setTemp(mode.getTemp()[i]);
        }
        return modes;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            editor.remove(mode.getName());
                            editor.commit();
                            onBackPressed();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();


        }

        if (id==android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void passDataToActivity(int position, int temp) {
        rooms.get(position).setTemp(temp);

        adapter.notifyDataSetChanged();
    }

    public void saveChange(View view) {
        int[] a = new int[rooms.size()];

        for (int i=0;i<rooms.size();i++){
            a[i] = rooms.get(i).getTemp();
        }
        mode.setTemp(a);
        Gson gson =new Gson();
        String json = gson.toJson(mode);
        editor.putString(mode.getName(), json);
        editor.commit();
        Toast.makeText(this, "Change mode successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}

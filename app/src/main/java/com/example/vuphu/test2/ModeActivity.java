package com.example.vuphu.test2;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ModeActivity extends AppCompatActivity {

    private RecyclerView list;
    private List<mode> modes;
    private ModeChooseApdater.adapter adapter;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        sharedPreferences = getSharedPreferences("mode",MODE_PRIVATE);
        list = findViewById(R.id.list_mode_choose);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        list.setLayoutManager(linearLayoutManager);
        list.setNestedScrollingEnabled(false);
        list.setHasFixedSize(true);
        modes = setData();

        adapter = new ModeChooseApdater.adapter(getApplicationContext(), modes);
        list.setAdapter(adapter);
       adapter.notifyDataSetChanged();
    }
    public List<mode> setData(){
        List<mode> modes = new ArrayList<>();
        Gson gson = new Gson();

        Map<String, ?> map = sharedPreferences.getAll();
        List<String> key = new ArrayList<String>((Collection<? extends String>) map.values());
        for (int i=0;i<key.size();i++){
            modes.add(gson.fromJson(key.get(i).toString(),mode.class));
        }
        return modes;
    }
}

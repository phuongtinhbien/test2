package com.example.vuphu.test2;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {

    private schedule schedule;
    private room room;

    private TextView timeStart, timeStop,name;
    private Button custom;
    private CircleImageView avatar;
    private RecyclerView list;
    private List<mode> modes;
    private ModeChooseApdater.adapter adapter;
    private SharedPreferences sharedPreferences;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    public static ScheduleFragment newInstance(schedule schedule) {
        
        Bundle args = new Bundle();
        
        ScheduleFragment fragment = new ScheduleFragment();
        args.putSerializable("schedule", schedule);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);

        timeStart = v.findViewById(R.id.tv_time_start);
        timeStop = v.findViewById(R.id.tv_time_stop);
        avatar = v.findViewById(R.id.time);
        custom = v.findViewById(R.id.btn_custom);
        name = v.findViewById(R.id.tv_name_time);
        name.setAllCaps(true);

        Intent intent = getActivity().getIntent();
        room = (com.example.vuphu.test2.room) intent.getSerializableExtra("room");
        sharedPreferences = getActivity().getSharedPreferences("mode",MODE_PRIVATE);
        list = v.findViewById(R.id.list_mode_choose);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(linearLayoutManager);
        list.setNestedScrollingEnabled(false);
        list.setHasFixedSize(true);
        modes = setData();

        adapter = new ModeChooseApdater.adapter(getContext(), modes);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        schedule = (com.example.vuphu.test2.schedule) getArguments().getSerializable("schedule");
        timeStart.setText("Time start: "+schedule.getTimeStart());
        timeStop.setText("Time stop: "+schedule.getTimeStop());
        avatar.setImageResource(schedule.getAvatar());
        name.setText(schedule.getName());
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (custom.getText().equals("SAVE")){

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(room.getName(),MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String data = adapter.getCheck();
                    if (!data.isEmpty()) {
                        list.setVisibility(View.GONE);
                        custom.setText("CUSTOM TEMPERATURE");
                        editor.putString(schedule.getName(), data);
                        editor.commit();
                        Toast.makeText(getContext(), "Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Choose one mode", Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    list.setVisibility(View.VISIBLE);
                    custom.setText("SAVE");
                }

            }
        });
        return v;
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

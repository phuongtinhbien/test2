package com.example.vuphu.test2;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;
import com.sdsmdg.harjot.crollerTest.Croller;
import com.sdsmdg.harjot.crollerTest.OnCrollerChangeListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class RoomFragment extends Fragment {

    private Croller croller_main;

    private room room;
    private Button btCreate;
    private TextView idDeg;
    private SingleSelectToggleGroup single;
    public RoomFragment() {
        // Required empty public constructor
    }

    public static RoomFragment newInstance(room rooms) {
        
        Bundle args = new Bundle();
        
        RoomFragment fragment = new RoomFragment( );
        args.putSerializable("room", rooms);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_room, container, false);
        single = (SingleSelectToggleGroup) v.findViewById(R.id.group_choices);
        croller_main  = v.findViewById(R.id.croller_main);
        croller_main.setMax(50);
        croller_main.setMin(0);
        croller_main.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            @Override
            public void onProgressChanged(Croller croller, int progress) {
                idDeg.setText(progress+"°C");
            }

            @Override
            public void onStartTrackingTouch(Croller croller) {

            }

            @Override
            public void onStopTrackingTouch(Croller croller) {

            }
        });
        room = (com.example.vuphu.test2.room) getArguments().getSerializable("room");
        idDeg = v.findViewById(R.id.idDeg);
        btCreate = v.findViewById(R.id.btCreate);

        if (single.getCheckedId()==R.id.choice_a){
            setTemp();
        }
        single.setOnCheckedChangeListener(new SingleSelectToggleGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SingleSelectToggleGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.choice_a:
                        croller_main.setMax(50);
                        setTemp();
                        break;
                    case R.id.choice_b:
                        croller_main.setMax(50);
                        croller_main.setProgress(25);
                        idDeg.setText("25°C");
                        croller_main.setLabel("OUTSIDE: 30°C");
                        break;
                    case R.id.choice_c:
                        croller_main.setMax(0);
                        croller_main.setProgress(0);
                        croller_main.setMin(0);
                        idDeg.setText("0°C");

                        croller_main.setLabel("OFF");
                        break;
                    case R.id.choice_cool:
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("special_mode",MODE_PRIVATE);
                        croller_main.setMax(50);
                        croller_main.setProgress(sharedPreferences.getInt("cooling",0));
                        idDeg.setText(sharedPreferences.getInt("cooling",0)+"");
                        croller_main.setLabel("Cooling");
                        break;
                    case R.id.choice_away:
                        SharedPreferences shared = getActivity().getSharedPreferences("special_mode",MODE_PRIVATE);
                        croller_main.setMax(50);
                        croller_main.setProgress(shared.getInt("away",0));
                        idDeg.setText(shared.getInt("away",0)+"");
                        croller_main.setLabel("Away");
                        break;
                }
            }
        });


        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CreateScheduleActivity.class).putExtra("room",room));
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTemp();
    }

    public void setTemp(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(room.getName(),Context.MODE_PRIVATE);
        SharedPreferences getmode = getActivity().getSharedPreferences("mode",MODE_PRIVATE);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String getCurrentTime = sdf.format(c.getTime());
        Gson gson =new Gson();

        if (getCurrentTime .compareTo("06:00") > 0 && getCurrentTime.compareTo("14:00") < 0){

            String name = sharedPreferences.getString("Morning","");
            mode mode = gson.fromJson(getmode.getString(name,""), com.example.vuphu.test2.mode.class);

            if (mode== null){
//                Toast.makeText(getContext(), "You have yet not create schedule", Toast.LENGTH_SHORT).show();
                croller_main.setProgress(room.getTemp());
                croller_main.setLabel("");
            }
            else {
                croller_main.setProgress(mode.getTemp()[position(room.getName())]);
                croller_main.setLabel("Morning");
            }
        }


        if (getCurrentTime .compareTo("14:01") > 0 && getCurrentTime.compareTo("18:00") < 0){
            String name = sharedPreferences.getString("Afternoon","");
            mode mode = gson.fromJson(getmode.getString(name,""), com.example.vuphu.test2.mode.class);
            if (mode== null){
//                Toast.makeText(getContext(), "You have yet not create schedule", Toast.LENGTH_SHORT).show();
                croller_main.setProgress(room.getTemp());
                croller_main.setLabel("");
            }
            else {
                croller_main.setProgress(mode.getTemp()[position(room.getName())]);
                croller_main.setLabel("Afternoon");
            }
        }
        if ((getCurrentTime .compareTo("18:01") > 0 && getCurrentTime.compareTo("23:59") < 0)
                ||
                (getCurrentTime .compareTo("00:00") > 0 && getCurrentTime.compareTo("05:59") < 0)){
            String name = sharedPreferences.getString("Night","");
            mode mode = gson.fromJson(getmode.getString(name,""), com.example.vuphu.test2.mode.class);
            if (mode == null){
//                Toast.makeText(getContext(), "You have yet not create schedule", Toast.LENGTH_SHORT).show();
                croller_main.setProgress(room.getTemp());
                croller_main.setLabel("");
            }
            else {
                croller_main.setProgress(mode.getTemp()[position(room.getName())]);
                croller_main.setLabel("Night");
            }
        }


    }

    public int position(String name){

            List<room> modes = new ArrayList<>();
            int x=-1;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("room",Context.MODE_PRIVATE);
            Gson gson = new Gson();
            Map<String, ?> map = sharedPreferences.getAll();
            List<String> key = new ArrayList<String>((Collection<? extends String>) map.values());
            for (int i = 0; i < key.size(); i++) {
                modes.add(gson.fromJson(key.get(i).toString(), room.class));
            }

            for (room item:modes){
                if (item.getName().equals(name)){
                    return modes.indexOf(item);
                }
            }
        return x;
    }

}

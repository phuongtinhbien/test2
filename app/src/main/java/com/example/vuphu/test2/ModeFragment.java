package com.example.vuphu.test2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class ModeFragment extends Fragment {


    private RecyclerView list;
    private List<mode> modes = new ArrayList<>();
    ModeApdater.adapter adapter;
    private Button add;
    private ImageButton away, cooling;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public ModeFragment() {
        // Required empty public constructor
    }

    public static ModeFragment newInstance() {
        ModeFragment fragment = new ModeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mode, container, false);



        sharedPreferences = getActivity().getSharedPreferences("mode",MODE_PRIVATE);
        list = v.findViewById(R.id.list_mode);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(linearLayoutManager);
        list.setNestedScrollingEnabled(false);
        list.setHasFixedSize(true);
        modes = setData(sharedPreferences);

        adapter = new ModeApdater.adapter(getContext(), modes);
        list.setAdapter(adapter);


        add = v.findViewById(R.id.btn_add);
        away = v.findViewById(R.id.btn_edit_away);
        cooling = v.findViewById(R.id.btn_edit_cooling);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddModeActivity.class));
            }
        });
        away.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemListDialogFragment bottomSheetDialogFragment = new ItemListDialogFragment();

                bottomSheetDialogFragment.newInstance(true,"away").show(getFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });
        cooling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemListDialogFragment bottomSheetDialogFragment = new ItemListDialogFragment();

                bottomSheetDialogFragment.newInstance(true, "cooling").show(getFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });

        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        update();


    }

    public void update(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mode",MODE_PRIVATE);
        modes = setData(sharedPreferences);
        ModeApdater.adapter adapter = new ModeApdater.adapter(getContext(),modes);

        list.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

    }

    public List<mode> setData(SharedPreferences sharedPreferences){

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

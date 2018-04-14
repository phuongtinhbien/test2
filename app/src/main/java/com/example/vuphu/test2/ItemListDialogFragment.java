package com.example.vuphu.test2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.harjot.crollerTest.Croller;
import com.sdsmdg.harjot.crollerTest.OnCrollerChangeListener;

import java.util.List;


public class ItemListDialogFragment extends BottomSheetDialogFragment{



    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Croller croller;
    private TextView temp;
    private Button save;

    public ActivityCommunicator activityCommunicator;

    public static ItemListDialogFragment newInstance(Boolean special_mode, String mode) {

        Bundle args = new Bundle();

        ItemListDialogFragment fragment = new ItemListDialogFragment();
        if (special_mode) {
            args.putBoolean("true", special_mode);
            args.putString("special_mode", mode);
        }
        else
            args.putString("mode", mode);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_list_dialog, container, false);
        sharedPreferences = getActivity().getSharedPreferences("special_mode", Context.MODE_PRIVATE);
        croller = v.findViewById(R.id.croller_modify);

        editor =sharedPreferences.edit();
        temp = v.findViewById(R.id.temp_modify);
        save = v.findViewById(R.id.btn_save_temp);
        if (getArguments().getBoolean("true")){
            croller.setProgress(sharedPreferences.getInt(getArguments().get("special_mode").toString(),0));
            croller.setOnCrollerChangeListener(new OnCrollerChangeListener() {
                @Override
                public void onProgressChanged(Croller croller, int progress) {
                    temp.setText(progress+"°C" );
                }

                @Override
                public void onStartTrackingTouch(Croller croller) {

                }

                @Override
                public void onStopTrackingTouch(Croller croller) {

                }
            });
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editor.putInt(getArguments().getString("special_mode"), croller.getProgress());
                    editor.commit();
                    Toast.makeText(getContext(), "Change successfully", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            });
        }
        else {
            croller.setOnCrollerChangeListener(new OnCrollerChangeListener() {
                @Override
                public void onProgressChanged(Croller croller, int progress) {
                    temp.setText(progress+"°C" );
                }

                @Override
                public void onStartTrackingTouch(Croller croller) {

                }

                @Override
                public void onStopTrackingTouch(Croller croller) {

                }
            });
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    viewreturnResult();
                   activityCommunicator.passDataToActivity(Integer.parseInt(getArguments().getString("mode")),croller.getProgress());
                    dismiss();
                }
            });
        }


        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    public void returnResult() {
        Intent data = new Intent();
        data.putExtra("position", Integer.parseInt(getArguments().getString("mode")));
        data.putExtra("temp", croller.getProgress());
        (( AddModeActivity )getActivity()).setResult(Activity.RESULT_OK, data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!getArguments().getBoolean("true"))
            activityCommunicator= (ActivityCommunicator) context;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_item_list_dialog, null);
        dialog.setContentView(contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if( behavior != null && behavior instanceof BottomSheetBehavior ) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }



}

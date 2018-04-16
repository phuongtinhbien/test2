package com.example.vuphu.test2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.luseen.verticalintrolibrary.VerticalIntro;
import com.luseen.verticalintrolibrary.VerticalIntroItem;

public class TutorialActivity extends VerticalIntro {


    @Override
    protected void init() {

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorPrimaryDark)
                .image(R.drawable.cool)
                .title("Thermostat")
                .text("1. Choose your room\n\n" +
                        "2. Adjust temperature\n\n" +
                        "3. Create a schedule in the morning, the afternoon, night\n\n")
                .textColor(android.R.color.white)
                .titleColor(android.R.color.white)
                .textSize(14) // in SP
                .titleSize(24) // in SP
                .nextTextColor(android.R.color.white)
                .build());

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.color7)
                .image(R.drawable.heat)
                .title("Modify temperature")
                .text("1. Choose a mode or create new one\n\n" +
                        "2. Adjust temperature\n\n" +
                        " 3. Save your customs\n\n")
                .textColor(android.R.color.white)
                .titleColor(android.R.color.white)
                .textSize(14) // in SP
                .titleSize(24) // in SP
                .nextTextColor(android.R.color.white)
                .build());
        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorPrimaryDark)
                .image(R.drawable.success)
                .title("Arr you ready?")
                .textColor(android.R.color.white)
                .titleColor(android.R.color.white)
                .textSize(14) // in SP
                .titleSize(24) // in SP
                .nextTextColor(android.R.color.white)
                .build());

        setSkipEnabled(true);
        setNextText("NEXT");
        setDoneText("LET'S START");

    }



    @Override
    protected Integer setLastItemBottomViewColor() {
        return R.color.colorAccent;
    }

    @Override
    protected void onSkipPressed(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    @Override
    protected void onFragmentChanged(int position) {

    }

    @Override
    protected void onDonePressed() {
        final ProgressDialog progressBar = new ProgressDialog(TutorialActivity.this);
        progressBar.setMessage("Loading...");
        progressBar.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                progressBar.hide();
                finish();
            }
        }, 3000);

    }


//    public void start(View view) {
//        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//    }
}

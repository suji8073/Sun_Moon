package com.example.sun_moon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ScrollView;


public class exercise_screen extends exerciseScreen {
    public exercise_screen() {
        this.layoutid = R.layout.exercise_screen;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        system_start();
    }

    @Override
    public void onBackPressed(){}

    public void system_start(){
        pb.setProgress(progressStatus);
        if (originY == 0) {
            scroll.post(() -> {
                scroll.fullScroll(ScrollView.FOCUS_DOWN);

                up.setY(image.getHeight()- view.getHeight());
                time_view.setY(image.getHeight()- view.getHeight() + 40);
                score_view.setY(image.getHeight()- view.getHeight() + 40);

                pb.setY(image.getHeight()- view.getHeight() + 600);
                tiger_progress.setY(image.getHeight()- view.getHeight() + 1900);
                tiger_exercise.setY(image.getHeight()- view.getHeight() + 2400); // 처음 1600
                tiger_100.setY(image.getHeight()- view.getHeight() + 1400);
            });
        }
    }

    @Override
    public void nextScreen(){
        Intent start_intent = new Intent(exercise_screen.this, rest.class);
        start_intent.putExtra("점수", score_text);
        start_intent.putExtra("호랑이", tiger_count);
        start_intent.putExtra("screen_up", move_num * score_text );
        startActivity(start_intent);
    }



}
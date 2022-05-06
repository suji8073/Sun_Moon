package com.example.sun_moon;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ScrollView;

public class exercise_screen_1 extends exerciseScreen {
    public exercise_screen_1() {
        this.layoutid = R.layout.exercise_screen_1;
    }

    @SuppressLint({"ClickableViewAccessibility", "UnsafeOptInUsageError"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent intent = getIntent();
        score_text_1 = intent.getIntExtra("점수", 0);
        tiger_count = intent.getIntExtra("호랑이", 0);
        screen_up = intent.getIntExtra("screen_up", 0);

        system_start();
        music_start();
    }

    public void system_start(){
        pb.setProgress(progressStatus);
        if (originY == 0) {
            scroll.post(() -> {
                scroll.fullScroll(ScrollView.FOCUS_DOWN);
                float up_location = image.getHeight()- view.getHeight();
                userNoBG.setY(image.getHeight()- view.getHeight() - screen_up);//조정 필요
                up.setY(image.getHeight()- view.getHeight() - screen_up);
                time_view.setY(image.getHeight()- view.getHeight() + 40 - screen_up);
                score_view.setY(image.getHeight()- view.getHeight() + 40 - screen_up);

                pb.setY(image.getHeight()- view.getHeight() + 600 - screen_up);
                tiger_progress.setY(image.getHeight()- view.getHeight() + 1900 - screen_up);
                tiger_exercise.setY(image.getHeight()- view.getHeight() + 2400 - screen_up);
                tiger_100.setY(image.getHeight()- view.getHeight() + 1400 - screen_up);

                ObjectAnimator.ofInt(scroll, "scrollY", Math.round(up_location), Math.round(up_location - screen_up)).setDuration(600).start();
            });
        }
    }

    @Override
    public void nextScreen(){
        Intent start_intent = new Intent(exercise_screen_1.this, scoreboard.class);
        start_intent.putExtra("점수_1", score_text_1);
        start_intent.putExtra("점수_2", score_text);
        start_intent.putExtra("호랑이", tiger_count);
        startActivity(start_intent);
    }
}



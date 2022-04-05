package com.example.sun_moon;

import static java.lang.Thread.sleep;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class exercise_screen extends exerciseScreen {
    public exercise_screen() {
        this.layoutid = R.layout.exercise_screen;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        system_start();
    }


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
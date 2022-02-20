package com.example.sun_moon;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

public class exercise_screen1 extends AppCompatActivity {
    Button skip, up, up1;
    ScrollView scroll;
    int originX, originY;
    float button_y;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_screen);

        LinearLayout view = findViewById(R.id.view);
        image= findViewById(R.id.image);

        scroll= findViewById(R.id.scrl);
        originX = scroll.getScrollX();
        originY = scroll.getScrollY();
        skip = findViewById(R.id.skip);


        scroll.setOnTouchListener( new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        if (originY == 0) {
            scroll.post(new Runnable() {
                @Override
                public void run() {
                    scroll.fullScroll(ScrollView.FOCUS_DOWN);
                    System.out.println("여기" + image.getHeight());
                    System.out.println("여기" + view.getHeight());
                    up.setY(image.getHeight()- view.getHeight());
                    skip.setY(image.getHeight()- view.getHeight());
                }
            });
        }



        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start_intent = new Intent(exercise_screen1.this, scoreboard.class);
                startActivity(start_intent);
            }
        });

        up = findViewById(R.id.up);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll.post(new Runnable() {
                    @Override
                    public void run() {
                        //scroll.smoothScrollBy(originX, originY - 100);
                        ObjectAnimator.ofInt(scroll, "scrollY", 500).setDuration(60000).start();
                        ObjectAnimator.ofFloat(up, "Y", 500).setDuration(60000).start();
                        ObjectAnimator.ofFloat(skip, "Y", 500).setDuration(60000).start();



                    }
                });
            }
        });

    }
    private void Scrolldown(){
        scroll.post(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private void Scrollup(int originX, int originY){


    }




    //private Animator.AnimatorListener

}
package com.example.sun_moon;

import static java.lang.Thread.sleep;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class exercise_screen1 extends AppCompatActivity {
    Button time, up;
    ScrollView scroll;
    int originX, originY;
    ImageView image;
    private int progressStatus = 0;
    private int timerStatus = 90;

    int move_num = 1000;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_screen1);

        LinearLayout view = findViewById(R.id.view);
        image= findViewById(R.id.image);

        scroll= findViewById(R.id.scrl);
        originX = scroll.getScrollX();
        originY = scroll.getScrollY();
        time = findViewById(R.id.time);

        ImageButton btn = findViewById(R.id.btn); // 호랑이
        TextView tv = findViewById(R.id.tv);
        ProgressBar pb = findViewById(R.id.pb);
        TextView time = findViewById(R.id.time);

        Timer(time);


        progressStatus = 0;
        pb.setProgress(progressStatus);
//        pb.setVisibility(View.INVISIBLE);

        // Start the lengthy operation in a background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressStatus < 100){
                    // Update the progress status
//                            if(운동하면){
//                                잠시 멈추고 progressStatus 저장,
//                                다시 안움직이면 다시 시작
//                            }
                    progressStatus +=1;
                    // Try to sleep the thread for 20 milliseconds
                    try{
                        sleep(300);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    // Update the progress
                    handler.post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            pb.setProgress(progressStatus);
                            // Show the progress on TextView
                            tv.setText(progressStatus+"");
                            // If task execution completed
                            if(progressStatus >= 80){
                                // Set a message of completion
                                tv.setText("곧 호랑이 ~!");
                                tv.setTextColor(0xAAef484a);

                                pb.setProgressBackgroundTintList(ColorStateList.valueOf(Color.RED));
                                //호랑이 쪽에 100이라고 알림
                            }
                            if(progressStatus == 100){
                                // Set a message of completion
                                tv.setText("어흥");
                                //호랑이 쪽에 100이라고 알림
                            }
                        }
                    });
                }
            }
        }).start(); // Start the operation

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(progressStatus<10){
                    progressStatus=0;
                }
                else{
                    progressStatus-=10;
                }
            }
        });



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
                    up.setY(image.getHeight()- view.getHeight());
                    time.setY(image.getHeight()- view.getHeight() + 50);
                    pb.setY(image.getHeight()- view.getHeight() + 600);
                    btn.setY(image.getHeight()- view.getHeight() + 1900);
                    tv.setY(image.getHeight()- view.getHeight() + 1500);
                }
            });
        }



        time.setOnClickListener(new View.OnClickListener() {
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
                        if(progressStatus<10){
                            progressStatus=0;
                        }
                        else{
                            progressStatus-=10;
                        }
                        //scroll.smoothScrollBy(originX, originY - 1000);
//                        ObjectAnimator.ofInt(scroll, "scrollY", view.getHeight()).setDuration(60000).start();
//                        ObjectAnimator.ofFloat(up, "Y", view.getHeight()).setDuration(60000).start();
//                        ObjectAnimator.ofFloat(time, "Y", view.getHeight()+50).setDuration(60000).start();
//                        ObjectAnimator.ofFloat(pb, "Y", view.getHeight()+600).setDuration(60000).start();
//                        ObjectAnimator.ofFloat(btn, "Y", view.getHeight()+1900).setDuration(60000).start();
//                        ObjectAnimator.ofFloat(tv, "Y", view.getHeight()+1500).setDuration(60000).start();

                        ObjectAnimator.ofInt(scroll, "scrollY", Math.round(up.getY()), Math.round(up.getY()-move_num)).setDuration(600).start();
                        ObjectAnimator.ofFloat(up, "Y", up.getY(), up.getY()-move_num).setDuration(600).start();
                        ObjectAnimator.ofFloat(time, "Y", time.getY(), time.getY()-move_num).setDuration(600).start();
                        ObjectAnimator.ofFloat(pb, "Y", pb.getY(), pb.getY()-move_num).setDuration(600).start();
                        ObjectAnimator.ofFloat(btn, "Y", btn.getY(), btn.getY()-move_num).setDuration(600).start();
                        ObjectAnimator.ofFloat(tv, "Y", tv.getY(), tv.getY()-move_num).setDuration(600).start();



                    }
                });
            }
        });

    }
    public void Timer(TextView view) {
        // Start the lengthy operation in a background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(timerStatus > 0 ){
                    // Update the progress status
//                            if(운동하면){
//                                잠시 멈추고 progressStatus 저장,
//                                다시 안움직이면 다시 시작
//                            }
                    timerStatus -=1;
                    // Try to sleep the thread for 20 milliseconds
                    try{
                        Thread.sleep(400);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    // Update the progress
                    handler.post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            if(timerStatus>=60){
                                if((timerStatus-60)<=9){
                                    view.setText("1:0"+(timerStatus-60));
                                }
                                else {
                                    view.setText("1:"+(timerStatus-60));
                                }
                            }
                            else if((timerStatus<=9)){
                                view.setText("0:0"+(timerStatus));
                            }
                            else{
                                view.setText("0:"+(timerStatus));
                            }

                            // If task execution completed
                            if(timerStatus <=30){
                                // Set a message of completion


                                view.setTextColor(0xAAef484a);
                            }

                        }
                    });
                }
            }
        }).start(); // Start the operation

    }
}
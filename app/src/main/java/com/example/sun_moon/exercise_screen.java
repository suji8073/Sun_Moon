package com.example.sun_moon;

import static java.lang.Thread.sleep;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class exercise_screen extends AppCompatActivity {
    Button time, up;
    ScrollView scroll;
    int originX, originY;
    ImageView image;
    private int progressStatus = 0;
    private int timerStatus = 90;
    private int score_text = 0;
    public int tiger_count=0;

    private SoundPool soundPool;
    private int sound;

    int move_num = 1000;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_screen);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes=new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool=new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }else{
            soundPool=new SoundPool(6,AudioManager.STREAM_MUSIC,0);
        }
        sound=soundPool.load(this,R.raw.growl,1);

        LinearLayout view = findViewById(R.id.view);
        image= findViewById(R.id.image);

        scroll= findViewById(R.id.scrl);
        originX = scroll.getScrollX();
        originY = scroll.getScrollY();
        time = findViewById(R.id.time);

        ImageButton btn = findViewById(R.id.btn); // 호랑이
        TextView tv = findViewById(R.id.tv);
        ProgressBar pb = findViewById(R.id.pb);
        Button time = findViewById(R.id.time);
        TextView time_title = findViewById(R.id.time_title);
        Button score = findViewById(R.id.score);
        TextView score_title = findViewById(R.id.score_title);

        time_title.bringToFront();
        score_title.bringToFront();

        Timer(time);


        progressStatus = 0;
        pb.setProgress(progressStatus);
//        pb.setVisibility(View.INVISIBLE);

        // Start the lengthy operation in a background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressStatus < 100) {
                    // Update the progress status
//                            if(운동하면){
//                                잠시 멈추고 progressStatus 저장,
//                                다시 안움직이면 다시 시작
//                            }
                    progressStatus += 1;
                    // Try to sleep the thread for 20 milliseconds
                    try {
                        sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // Update the progress
                    handler.post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            pb.setProgress(progressStatus);
                            // Show the progress on TextView
                            tv.setText(progressStatus + "");
                            // If task execution completed
                            if (progressStatus >= 80) {
                                // Set a message of completion
                                tv.setText("곧 호랑이 ~!");
                                tv.setTextColor(0xAAef484a);

                                //pb.setProgressBackgroundTintList(ColorStateList.valueOf(Color.RED));
                                //호랑이 쪽에 100이라고 알림
                            }
                            if (progressStatus == 100) {
                                // Set a message of completion
                                tv.setText("어흥");
                                //호랑이 쪽에 100이라고 알림
                                tiger_count+=1;

                                soundPool.play(sound,1,1,0,0,1);

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
                    time_title.setY(image.getHeight()- view.getHeight() + 40);
                    score_title.setY(image.getHeight()- view.getHeight() + 40);
                    time.setY(image.getHeight()- view.getHeight() + 50);
                    score.setY(image.getHeight()- view.getHeight() + 50);
                    pb.setY(image.getHeight()- view.getHeight() + 600);
                    btn.setY(image.getHeight()- view.getHeight() + 1900);
                    tv.setY(image.getHeight()- view.getHeight() + 1500);
                }
            });
        }



        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start_intent = new Intent(exercise_screen.this, scoreboard.class);
                startActivity(start_intent);
            }
        });

        up = findViewById(R.id.up);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (up.getY() - move_num >0) {
                    scroll.post(new Runnable() {
                        @Override
                        public void run() {
                            if (progressStatus < 10) {
                                progressStatus = 0;
                            } else {
                                progressStatus -= 10;
                            }

                            Score(score);


                            //scroll.smoothScrollBy(originX, originY - 1000);
                            //                        ObjectAnimator.ofInt(scroll, "scrollY", view.getHeight()).setDuration(60000).start();
                            //                        ObjectAnimator.ofFloat(up, "Y", view.getHeight()).setDuration(60000).start();
                            //                        ObjectAnimator.ofFloat(time, "Y", view.getHeight()+50).setDuration(60000).start();
                            //                        ObjectAnimator.ofFloat(pb, "Y", view.getHeight()+600).setDuration(60000).start();
                            //                        ObjectAnimator.ofFloat(btn, "Y", view.getHeight()+1900).setDuration(60000).start();
                            //                        ObjectAnimator.ofFloat(tv, "Y", view.getHeight()+1500).setDuration(60000).start();

                            ObjectAnimator.ofInt(scroll, "scrollY", Math.round(up.getY()), Math.round(up.getY() - move_num)).setDuration(600).start();
                            ObjectAnimator.ofFloat(up, "Y", up.getY(), up.getY() - move_num).setDuration(600).start();
                            ObjectAnimator.ofFloat(time_title, "Y", time.getY(), time.getY() - move_num).setDuration(600).start();
                            ObjectAnimator.ofFloat(score_title, "Y", time.getY(), time.getY() - move_num).setDuration(600).start();
                            ObjectAnimator.ofFloat(time, "Y", time.getY(), time.getY() - move_num).setDuration(600).start();
                            ObjectAnimator.ofFloat(score, "Y", time.getY(), time.getY() - move_num).setDuration(600).start();
                            ObjectAnimator.ofFloat(pb, "Y", pb.getY(), pb.getY() - move_num).setDuration(600).start();
                            ObjectAnimator.ofFloat(btn, "Y", btn.getY(), btn.getY() - move_num).setDuration(600).start();
                            ObjectAnimator.ofFloat(tv, "Y", tv.getY(), tv.getY() - move_num).setDuration(600).start();

                            //score.setBackgroundResource(R.drawable.score);
                        }

                    });

                    score.setBackgroundResource(R.drawable.score);
                }
            }
        });



    }
    public void Timer(Button btn) {
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
                        Thread.sleep(1000);
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
                                    btn.setText("1:0"+(timerStatus-60));
                                }
                                else {
                                    btn.setText("1:"+(timerStatus-60));
                                }
                            }
                            else if((timerStatus<=9)){
                                btn.setText("0:0"+(timerStatus));
                            }
                            else{
                                btn.setText("0:"+(timerStatus));
                            }

                            // If task execution completed
                            if(timerStatus <=30){
                                // Set a message of completion
                                btn.setTextColor(0xAAef484a);
                            }
                            if(timerStatus ==0){
                                Intent start_intent = new Intent(exercise_screen.this, scoreboard.class);
                                start_intent.putExtra("점수", score_text);
                                start_intent.putExtra("호랑이", tiger_count);
                                startActivity(start_intent);
                            }

                        }
                    });
                }
            }
        }).start(); // Start the operation

    }


    public void Score(Button btn) {
        score_text+=1;
        Animation startAnimation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink_animation);
        btn.startAnimation(startAnimation);
        btn.setText(String.valueOf(score_text)+"점");
        btn.setBackgroundResource(R.drawable.score_plus);
//        try{
//            Thread.sleep(1000);
//        }catch(InterruptedException e){
//            e.printStackTrace();
//        }
//        btn.setBackgroundResource(R.drawable.score);
    }

}



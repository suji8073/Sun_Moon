package com.example.sun_moon;

import static java.lang.Thread.sleep;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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

public class exercise_screen_1 extends AppCompatActivity {
    Button up;
    ScrollView scroll;
    ProgressBar pb;
    TextView time, tv, score, rest_timer;
    int originX, originY;
    ImageView image, tiger_exercise, tiger_progress;
    private int progressStatus = 0;
    private int timerStatus = 90;
    MediaPlayer mediaPlayer;
    LinearLayout view, score_view, time_view, rest_view;
    int rest_time = 31;
    float alpha = 1;
    private int score_text = 0;
    public int tiger_count=0;

    private SoundPool soundPool;
    private int sound, sound1;

    int move_num = 100;
    View full_view_rest;
    int score_text_1, screen_up;


    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_screen_1);

        Intent intent = getIntent(); //전달할 데이터를 받을 Intent
        score_text_1 = intent.getIntExtra("점수", 0);
        tiger_count = intent.getIntExtra("호랑이", 0);
        screen_up = intent.getIntExtra("screen_up", 5000);


        mediaPlayer = MediaPlayer.create(this, R.raw.background);
        //mediaPlayer.setLooping(true); //무한재생
        mediaPlayer.start();
        rest_view = findViewById(R.id.rest_view);
        rest_timer = findViewById(R.id.rest_timer);
        full_view_rest = findViewById(R.id.full_view_rest);
        full_view_rest.setVisibility(View.INVISIBLE);
        rest_view.setVisibility(View.INVISIBLE);


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
        sound1=soundPool.load(this,R.raw.plus,1);

        view = findViewById(R.id.view);
        image= findViewById(R.id.image);

        score_view = findViewById(R.id.score_view);
        time_view= findViewById(R.id.time_view);

        scroll= findViewById(R.id.scrl);
        originX = scroll.getScrollX();
        originY = scroll.getScrollY();

        time = findViewById(R.id.time);

        tv = findViewById(R.id.tv);
        pb = findViewById(R.id.pb);
        time = findViewById(R.id.time);
        score = findViewById(R.id.score);

        tiger_progress = findViewById(R.id.tiger_progress); // 호랑이
        tiger_exercise = findViewById(R.id.tiger_exercise); // 호랑이
        tiger_exercise.setVisibility(View.INVISIBLE);

        //progressStatus = 0;
        pb.setProgress(progressStatus);




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
                    System.out.println(scroll.getY());
                    //scroll.scrollTo(0,-5000);
                    System.out.println(scroll.getY());
                    float up_location = image.getHeight()- view.getHeight();

                    up.setY(image.getHeight()- view.getHeight() - screen_up);

                    time_view.setY(image.getHeight()- view.getHeight() + 40 - screen_up);
                    score_view.setY(image.getHeight()- view.getHeight() + 40 - screen_up);
                    rest_view.setY(image.getHeight()- view.getHeight() + 800 - screen_up);
                    //rest_timer.setY(image.getHeight()- view.getHeight() + 1000);

                    pb.setY(image.getHeight()- view.getHeight() + 600 - screen_up);
                    tiger_progress.setY(image.getHeight()- view.getHeight() + 1900 - screen_up);
                    tv.setY(image.getHeight()- view.getHeight() + 1500 - screen_up);
                    tiger_exercise.setY(image.getHeight()- view.getHeight() + 1600 - screen_up);
                    up.setEnabled(false);

                    ObjectAnimator.ofInt(scroll, "scrollY", Math.round(up_location), Math.round(up_location - screen_up)).setDuration(600).start();
                }
            });
        }


        up = findViewById(R.id.up);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiger_exercise.setVisibility(View.INVISIBLE);
                if (progressStatus <= 10) {
                    progressStatus = 0;
                }
                else if(progressStatus>=100) {
                    progressStatus = 90;
                }
                else{
                    progressStatus -= 10;
                }

                Score(score);

                if (up.getY() - move_num >0) {
                    scroll.post(new Runnable() {
                        @Override
                        public void run() {
                            ObjectAnimator.ofInt(scroll, "scrollY", Math.round(up.getY()), Math.round(up.getY() - move_num)).setDuration(600).start();
                            ObjectAnimator.ofFloat(up, "Y", up.getY(), up.getY() - move_num).setDuration(600).start();
                            /*ObjectAnimator.ofFloat(time_title, "Y", time.getY(), time.getY() - move_num).setDuration(600).start();
                            ObjectAnimator.ofFloat(score_title, "Y", time.getY(), time.getY() - move_num).setDuration(600).start();
                            ObjectAnimator.ofFloat(time, "Y", time.getY(), time.getY() - move_num).setDuration(600).start();
                            ObjectAnimator.ofFloat(score, "Y", time.getY(), time.getY() - move_num).setDuration(600).start();*/

                            ObjectAnimator.ofFloat(time_view, "Y", time_view.getY(), time_view.getY() - move_num).setDuration(600).start();
                            ObjectAnimator.ofFloat(score_view, "Y", time_view.getY(), time_view.getY() - move_num).setDuration(600).start();


                            ObjectAnimator.ofFloat(pb, "Y", pb.getY(), pb.getY() - move_num).setDuration(600).start();
                            ObjectAnimator.ofFloat(tiger_progress, "Y", tiger_progress.getY(), tiger_progress.getY() - move_num).setDuration(600).start();
                            ObjectAnimator.ofFloat(tiger_exercise, "Y", tiger_exercise.getY(), tiger_exercise.getY() - move_num).setDuration(600).start();
                            ObjectAnimator.ofFloat(tv, "Y", tv.getY(), tv.getY() - move_num).setDuration(600).start();

                        }

                    });

                    score_view.setBackgroundResource(R.drawable.score);
                }
            }
        });
        rest_timer();



    }

    public void tiget_timer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
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
                            if(progressStatus < 100){
                                //tv.setText(" ");
                            }
                            // If task execution completed
                            if ((progressStatus >= 80)&(progressStatus<100)) {
                                // Set a message of completion
                                //tv.setText("곧 호랑이 ~!");
                                tv.setTextColor(0xAAef484a);

                                //pb.setProgressBackgroundTintList(ColorStateList.valueOf(Color.RED));
                                //호랑이 쪽에 100이라고 알림
                            }
                            if (progressStatus == 100) {
                                // Set a message of completion
                                //tv.setText("어흥");
                                //호랑이 쪽에 100이라고 알림
                                tiger_count+=1;
                                tiger_exercise.setVisibility(View.VISIBLE);

                                soundPool.play(sound,1,1,0,0,1);

                            }
                            if(progressStatus >100){
                                progressStatus=101;
                            }
                        }
                    });

                }
            }
        }).start(); // Start the operation
    }

    public void Timer() {
        // Start the lengthy operation in a background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(timerStatus > 0 ){
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
                                    time.setText("1:0"+(timerStatus-60));
                                }
                                else {
                                    time.setText("1:"+(timerStatus-60));
                                }
                            }
                            else if((timerStatus<=9)){
                                time.setText("0:0"+(timerStatus));
                            }
                            else{
                                time.setText("0:"+(timerStatus));
                            }

                            // If task execution completed
                            if(timerStatus <=30){
                                // Set a message of completion
                                time.setTextColor(0xAAef484a);
                            }

                        }
                    });
                }
                if(timerStatus ==0){
                    Intent start_intent = new Intent(exercise_screen_1.this, scoreboard.class);
                    start_intent.putExtra("점수_1", score_text_1);
                    start_intent.putExtra("점수_2", score_text);
                    start_intent.putExtra("호랑이", tiger_count);
                    startActivity(start_intent);
                }
            }
        }).start(); // Start the operation

    }


    public void Score(TextView btn) {
        score_text+=1;
        Animation startAnimation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink_animation);
        score_view.startAnimation(startAnimation);
        btn.setText(String.valueOf(score_text)+"점");
        score_view.setBackgroundResource(R.drawable.score_plus);

        soundPool.play(sound1,1,1,0,0,1);

    }
    private void rest_timer() {
        new Thread() {
            public void run() {
                rest_time = 21; // 초기화
                alpha = 1;
                float one_set = alpha / rest_time;
                rest_view.setVisibility(View.VISIBLE);
                full_view_rest.setVisibility(View.VISIBLE);
                while (rest_time >= 0 ) {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                rest_time--;
                                System.out.println(rest_time);
                                rest_timer.setText(String.valueOf(rest_time));
                                full_view_rest.setAlpha(alpha);

                                if (rest_time == 0) {
                                    rest_time = -1;
                                    rest_view.setVisibility(View.INVISIBLE);
                                    up.setEnabled(true);
                                    Timer();
                                    tiget_timer();
                                }

                                alpha = alpha - one_set;

                            }
                        });
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

}



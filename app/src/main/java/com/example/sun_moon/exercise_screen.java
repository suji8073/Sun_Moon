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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class exercise_screen extends AppCompatActivity {
    Button up;
    ScrollView scroll;
    ProgressBar pb;
    TextView time, tv, score;
    int originX, originY;
    ImageView image;
    private int progressStatus = 0;
    private int timerStatus = 10;
    private int score_text = 0;
    public int tiger_count=0;
    ImageView tiger_exercise;
    ImageView tiger_progress;
    MediaPlayer mediaPlayer;
    LinearLayout view, score_view, time_view;


    private SoundPool soundPool;
    private int sound, sound1;

    int move_num = 100;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_screen);
        mediaPlayer = MediaPlayer.create(this, R.raw.background);
        //mediaPlayer.setLooping(true); //무한재생
        mediaPlayer.start();

        AudioAttributes audioAttributes=new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool=new SoundPool.Builder()
                .setMaxStreams(6)
                .setAudioAttributes(audioAttributes)
                .build();
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

        final Animation animTransAlpha=AnimationUtils.loadAnimation(this, R.anim.scale);

        Timer(time);


        //progressStatus = 0;
        pb.setProgress(progressStatus);

        // Start the lengthy operation in a background thread
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
                                tiger_exercise.startAnimation(animTransAlpha);


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
                   /* time_title.setY(image.getHeight()- view.getHeight() + 40);
                    score_title.setY(image.getHeight()- view.getHeight() + 40);
                    time.setY(image.getHeight()- view.getHeight() + 50);
                    score.setY(image.getHeight()- view.getHeight() + 50);*/

                    time_view.setY(image.getHeight()- view.getHeight() + 40);
                    score_view.setY(image.getHeight()- view.getHeight() + 40);


                    pb.setY(image.getHeight()- view.getHeight() + 600);
                    tiger_progress.setY(image.getHeight()- view.getHeight() + 1900);
                    tv.setY(image.getHeight()- view.getHeight() + 1500);
                    tiger_exercise.setY(image.getHeight()- view.getHeight() + 1600);
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



    }
    public void Timer(TextView btn) {
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

                        }
                    });
                }
                if(timerStatus ==0){
                    progressStatus = 101;

                    Intent start_intent = new Intent(exercise_screen.this, rest.class);

                    mediaPlayer.stop();
                    start_intent.putExtra("점수", score_text);
                    start_intent.putExtra("호랑이", tiger_count);
                    start_intent.putExtra("screen_up", move_num * score_text );
                    startActivity(start_intent);
                }
            }
        }).start(); // Start the operation

    }


    public void Score(TextView btn) {
        score_text+=1;
        Animation startAnimation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink_animation);
        score_view.startAnimation(startAnimation);
        btn.setText(score_text +"점");
        score_view.setBackgroundResource(R.drawable.score_plus);

        soundPool.play(sound1,1,1,0,0,1);

    }

}



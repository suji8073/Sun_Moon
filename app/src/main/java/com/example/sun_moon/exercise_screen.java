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


public class exercise_screen extends AppCompatActivity {
    Button up;
    ScrollView scroll;
    ProgressBar pb;
    TextView time, score;
    int originX, originY;
    private int timerStatus = 90, score_text = 0, tiger_count = 0, progressStatus = 0, move_num = 100 ;
    ImageView image, tiger_exercise, tiger_100, tiger_progress;
    MediaPlayer mediaPlayer;
    LinearLayout view, score_view, time_view;

    private SoundPool soundPool;
    private int sound, sound1;
    private final Handler handler = new Handler();

    int up_count = 0;
    int tiger_up_num = 30;
    String tiger_up_check = "f";

    TimerThread timerThread = new TimerThread();
    progressThread progressThread = new progressThread();
    Thread tthread = new Thread(timerThread);
    Thread pthread = new Thread(progressThread);


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_screen);
        mediaPlayer = MediaPlayer.create(this, R.raw.background);
        mediaPlayer.start();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        view = findViewById(R.id.view);
        image= findViewById(R.id.image);
        display.getRealSize(size);
        Bitmap background = BitmapFactory.decodeResource(getResources(),R.drawable.background);
        Bitmap resizedbg= Bitmap.createScaledBitmap(background,size.x,8744,true);
        Log.v("size",resizedbg.getHeight()+" 넓"+resizedbg.getWidth());
        image.setImageBitmap(resizedbg);


        score_view = findViewById(R.id.score_view);
        time_view= findViewById(R.id.time_view);

        scroll= findViewById(R.id.scrl);
        scroll.setOnTouchListener((v, event) -> {
            scroll.requestDisallowInterceptTouchEvent(false);
            return true;
        });

        originX = scroll.getScrollX();
        originY = scroll.getScrollY();
        time = findViewById(R.id.time);

        pb = findViewById(R.id.pb);
        time = findViewById(R.id.time);
        score = findViewById(R.id.score);

        tiger_progress = findViewById(R.id.tiger_progress); // 호랑이
        tiger_exercise = findViewById(R.id.tiger_exercise); // 호랑이
        tiger_100 = findViewById(R.id.tiger_100);// 호랑이 100일 때 나오는 호랑이

        tiger_exercise.setVisibility(View.INVISIBLE); // 호랑이 안 보이게
        tiger_100.setVisibility(View.INVISIBLE); // 호랑이 안 보이게


        tthread.start();
        pthread.start();
        system_start();
        music_start();

        up = findViewById(R.id.up);
        up.setOnClickListener(view -> up_action());

    }


    @Override
    protected void onPause(){
        super.onPause();
        Log.v("로그","퍼즈");
        soundPool.autoPause();
        mediaPlayer.pause();
        tthread.interrupt();
        pthread.interrupt();
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.v("로그","리섬");
        soundPool.autoResume();
        mediaPlayer.start();
        if(tthread.isInterrupted()){
            tthread.start();
        }
        if(pthread.isInterrupted()){
            pthread.start();
        }
    }
    @Override
    protected void onStop(){
        super.onStop();
        tthread.interrupt();
        pthread.interrupt();
    }



    public void music_start(){
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(6)
                .setAudioAttributes(audioAttributes)
                .build();

        sound = soundPool.load(this,R.raw.growl,1);
        sound1 = soundPool.load(this,R.raw.plus,1);
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

    public void up_action(){
        tiger_100.setVisibility(View.INVISIBLE);
        tiger_up_check = "f";

        if (progressStatus <= 10) progressStatus = 0;
        else if (( progressStatus >= 50 )&( progressStatus < 100 )) progressStatus -= 15;
        else if (progressStatus > 100) progressStatus = 48;
        else progressStatus -= 15;

        Score();

        if (up.getY() - move_num >0) {
            scroll.post(() -> {
                ObjectAnimator.ofInt(scroll, "scrollY", Math.round(up.getY()), Math.round(up.getY() - move_num)).setDuration(600).start();
                ObjectAnimator.ofFloat(up, "Y", up.getY(), up.getY() - move_num).setDuration(600).start();
                ObjectAnimator.ofFloat(time_view, "Y", time_view.getY(), time_view.getY() - move_num).setDuration(600).start();
                ObjectAnimator.ofFloat(score_view, "Y", time_view.getY(), time_view.getY() - move_num).setDuration(600).start();
                ObjectAnimator.ofFloat(pb, "Y", pb.getY(), pb.getY() - move_num).setDuration(600).start();
                ObjectAnimator.ofFloat(tiger_progress, "Y", tiger_progress.getY(), tiger_progress.getY() - move_num).setDuration(600).start();
                ObjectAnimator.ofFloat(tiger_100, "Y", tiger_100.getY(), tiger_100.getY() - move_num).setDuration(600).start();
            });
            score_view.setBackgroundResource(R.drawable.score);
        }
    }
    class progressThread implements Runnable {
        public void run() {
            try {
                tiger_up_check = "t";
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            pb.setProgress(progressStatus);
                            if (progressStatus < 50) {
                                tiger_exercise.setVisibility(View.INVISIBLE);
                            } else if (progressStatus == 50) {
                                tiger_exercise.setY(tiger_100.getY() + 800);
                            } else if (progressStatus < 100) {
                                up_count++;
                                tiger_exercise.setVisibility(View.VISIBLE);
                                if (tiger_up_check.equals("t")) { // 올라가기
                                    System.out.println("올라감");
                                    ObjectAnimator.ofFloat(tiger_exercise, "Y", tiger_exercise.getY(), tiger_exercise.getY() - tiger_up_num).setDuration(600).start();
                                } else { // 내려가기 up 버튼을 눌렀을 때
                                    System.out.println("내려가야함");
                                    ObjectAnimator.ofFloat(tiger_exercise, "Y", tiger_exercise.getY(), tiger_exercise.getY() + 3 * tiger_up_num).setDuration(600).start();
                                    tiger_up_check = "t";
                                }
                            } else if (progressStatus == 100) { // 호랑이 등장
                                System.out.println("up_count : " + up_count);
                                tiger_count += 1;
                                tiger_exercise.setVisibility(View.INVISIBLE);
                                tiger_100.setVisibility(View.VISIBLE);
                                soundPool.play(sound, 1, 1, 0, 0, 1); //호랑이 등장 소리
                            } else progressStatus = 101;
                        }
                    });
                    progressStatus += 1;
                }

            } catch (Exception ignored) {
            }
        }
    }

    class TimerThread implements Runnable {
        public void run() {
            try {
                    while (!Thread.currentThread().isInterrupted()&&timerStatus > 0) {
                        timerStatus -= 1;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.post(() -> {
                            if (timerStatus >= 60) {
                                if ((timerStatus - 60) <= 9)
                                    time.setText("1:0" + (timerStatus - 60));
                                else time.setText("1:" + (timerStatus - 60));
                            } else if (timerStatus <= 9)
                                time.setText("0:0" + (timerStatus));
                            else time.setText("0:" + (timerStatus));

                            if (timerStatus <= 30) {
                                time.setTextColor(0xAAef484a);
                            }
                        });
                    }
                    if (timerStatus == 0&&!Thread.currentThread().isInterrupted()){
                        Log.v("타이머 탈출","타이머 탈출");
                        mediaPlayer.stop();
                        progressStatus = 101;
                        Intent start_intent = new Intent(exercise_screen.this, rest.class);
                        start_intent.putExtra("점수", score_text);
                        start_intent.putExtra("호랑이", tiger_count);
                        start_intent.putExtra("screen_up", move_num * score_text );
                        startActivity(start_intent);
                    }


            } catch (Exception ignored) {


            }
        }

    }


    public void Score() {
        score_text += 1;
        Animation startAnimation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink_animation);
        score_view.startAnimation(startAnimation);
        score.setText(score_text +"점");
        score_view.setBackgroundResource(R.drawable.score_plus);

        soundPool.play(sound1,1,1,0,0,1);
    }





}



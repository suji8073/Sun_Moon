package com.example.sun_moon;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class exercise_intro extends AppCompatActivity {
    TextView txt;
    //private CountDownTimer countDownTimer;
    //private boolean TimerRunning;
    private int timerStatus=10;
    private final Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercies_intro);
        txt=findViewById(R.id.txt);
        Button skip = findViewById(R.id.skip);
        final VideoView videoView=(VideoView) findViewById(R.id.video);
        MediaController mc=new MediaController(this);
        videoView.setMediaController(mc);
        videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.im));
        videoView.start();

        Timer(txt);



//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
//                    public void onTick(long millisUntilFinished) {
//                        int num = (int) (millisUntilFinished / 1000);
//                        txt.setText(Integer.toString(num));
//                    }
//
//
//                    @Override
//                    public void onFinish() {
//                        handler.removeCallbacksAndMessages(null);
//                        txt.setText("시작");
//                        Intent start_intent = new Intent(exercise_intro.this, exercise_screen.class);
//                        startActivity(start_intent);
//
//                    }
//                }.start();
//
//            }
//        },600);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //handler.removeCallbacksAndMessages(null);
                timerStatus=-1;
                Intent start_intent = new Intent(exercise_intro.this, exercise_screen.class);
                startActivity(start_intent);

                //위의 타이머 중단해야됨
                //pauseTimer();
            }
        });


        Button back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerStatus=-1;
                Intent start_intent = new Intent(exercise_intro.this, list.class);
                startActivity(start_intent);
            }
        });



    }
//    private final Timer mTimer=new Timer();
//    private TimerTask mTimerTask;
//    private void pauseTimer(){
//        countDownTimer.cancel();
//        TimerRunning=false;
//
//    }
    public void Timer(TextView txt){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(timerStatus > 0 ){
                    timerStatus -=1;
                    txt.setText(String.valueOf(timerStatus));
                    try{
                        Thread.sleep(1000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
                if(timerStatus == 0){
                    txt.setText("시작");
                    Intent start_intent = new Intent(exercise_intro.this, exercise_screen.class);
                    startActivity(start_intent);
                }
            }
        }).start(); // Start the operation
    }


}
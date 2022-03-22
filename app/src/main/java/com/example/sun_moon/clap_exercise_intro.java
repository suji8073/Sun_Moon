package com.example.sun_moon;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class clap_exercise_intro extends AppCompatActivity {
    TextView txt, back;
    public int timerStatus = 30;
    private final Handler handler = new Handler();
    Button skip;
    VideoView videoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clap_exercies_intro);
        txt=findViewById(R.id.txt);
        skip = findViewById(R.id.skip);
        videoView= findViewById(R.id.video);
        MediaController mc=new MediaController(this);
        videoView.setMediaController(mc);
        videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.intro));
        videoView.start();


        runThread(txt);



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

        skip.setOnClickListener(new View.OnClickListener() { // 운동 화면으로 넘어가기
            @Override
            public void onClick(View view) {
                //handler.removeCallbacksAndMessages(null);
                timerStatus=-1;
                Intent start_intent = new Intent(clap_exercise_intro.this, clap_exercise_screen.class);
                startActivity(start_intent);
                
            }
        });


        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerStatus=-1;
                Intent start_intent = new Intent(clap_exercise_intro.this, list.class);
                startActivity(start_intent);
            }
        });



    }

    private Activity getActivity() {
        return null;
    }

    //    private final Timer mTimer=new Timer();
//    private TimerTask mTimerTask;
//    private void pauseTimer(){
//        countDownTimer.cancel();
//        TimerRunning=false;
//
//    }


    private void runThread(TextView txt) {

        new Thread() {
            public void run() {
                while (timerStatus >= 0 ) {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timerStatus -=1;
                                txt.setText(String.valueOf(timerStatus));

                                if (timerStatus == 0)
                                {
                                    txt.setText("시작");
                                    Intent start_intent = new Intent(clap_exercise_intro.this, sun_exercise_screen.class);
                                    startActivity(start_intent);
                                }
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
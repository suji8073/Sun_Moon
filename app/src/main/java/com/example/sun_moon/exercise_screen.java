package com.example.sun_moon;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class exercise_screen extends Activity {
    private int progressStatus = 0;
    private int timerStatus = 90;

    private final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_screen);
        // Get the widgets reference from XML layout
        RelativeLayout rl = findViewById(R.id.rl);
        ImageButton btn = findViewById(R.id.btn);
        TextView tv = findViewById(R.id.tv);
        ProgressBar pb = findViewById(R.id.pb);
        TextView time = findViewById(R.id.time);

        Timer(time);

        progressStatus = 0;
        pb.setProgress(progressStatus);
//        pb.setVisibility(View.INVISIBLE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the progress status zero on each button click
//                if(progressStatus==100){
//                    progressStatus = 0;
//                    pb.setProgress(progressStatus);
//                    pb.setProgressBackgroundTintList(ColorStateList.valueOf(0xCBFF7676));
//                }

//                pb.setVisibility(View.VISIBLE);

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
                                Thread.sleep(100);
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






package com.example.sun_moon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class clap_exercise_screen_1 extends AppCompatActivity {

    Button next;
    int score_text_1, tiger_count;
    int score_text = 1; // 2세트 점수
    TextView score, time;
    ProgressBar pb;
    private int timerStatus = 90;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clap_exercise_screen);
        score = findViewById(R.id.next);
        time = findViewById(R.id.time);
        pb = findViewById(R.id.pb);


        Intent intent = getIntent(); //전달할 데이터를 받을 Intent
        score_text_1 = intent.getIntExtra("점수", 0);
        tiger_count = intent.getIntExtra("호랑이", 0);

        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start_intent = new Intent(clap_exercise_screen_1.this, scoreboard.class);
                start_intent.putExtra("점수_1", score_text_1);
                start_intent.putExtra("점수_2", score_text);
                start_intent.putExtra("호랑이", tiger_count);
                startActivity(start_intent);
            }
        });
    }


        public void Timer() {
            // Start the lengthy operation in a background thread
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(timerStatus > 0 ){
                        timerStatus-=1;
                        try{
                            Thread.sleep(1000);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }
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
                    if(timerStatus == 0){
                        Intent start_intent = new Intent(clap_exercise_screen_1.this, scoreboard.class);
                        start_intent.putExtra("점수_1", score_text_1);
                        start_intent.putExtra("점수_2", score_text);
                        start_intent.putExtra("호랑이", tiger_count);
                        start_intent.putExtra("game_name", "곶감과 호랑이");
                        startActivity(start_intent);
                    }
                }
            }).start(); // Start the operation

        }


    }

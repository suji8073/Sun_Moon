package com.example.sun_moon;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class clap_exercise_screen extends AppCompatActivity {

    Button next;
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

        Timer();

        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerStatus-=1;
                Intent start_intent = new Intent(clap_exercise_screen.this, clap_rest.class);
                start_intent.putExtra("점수", 0);
                start_intent.putExtra("호랑이", 0);
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
                        Intent start_intent = new Intent(clap_exercise_screen.this, clap_rest.class);
                        start_intent.putExtra("점수", 0);
                        start_intent.putExtra("호랑이", 0);
                        startActivity(start_intent);
                    }
                }
            }).start(); // Start the operation

        }


}
package com.example.sun_moon;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class clap_rest extends AppCompatActivity {
    int rest_time = 31;
    TextView rest_timer;

    int score_text_1, screen_up, tiger_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clap_rest);

        rest_timer = findViewById(R.id.rest_timer);

        Intent intent = getIntent(); //전달할 데이터를 받을 Intent
        score_text_1 = intent.getIntExtra("점수", 0);
        tiger_count = intent.getIntExtra("호랑이", 0);
        screen_up = intent.getIntExtra("screen_up", 0);

        rest_timer();
    }

    private void rest_timer() {
        new Thread() {
            public void run() {
                while (rest_time >= 0 ) {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                rest_time--;
                                System.out.println(rest_time);
                                rest_timer.setText(String.valueOf(rest_time));

                                if (rest_time == 0) {
                                    rest_time = -1;
                                    Intent start_intent = new Intent(clap_rest.this, clap_exercise_screen_1.class);
                                    start_intent.putExtra("점수", score_text_1);
                                    start_intent.putExtra("호랑이", tiger_count);
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
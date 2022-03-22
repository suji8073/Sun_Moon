package com.example.sun_moon;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class sun_rest extends AppCompatActivity {
    int rest_time = 31;
    float alpha = 1;
    ImageView full_view_rest;
    TextView rest_timer;

    int score_text_1, screen_up, tiger_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sun_rest);

        full_view_rest = findViewById(R.id.full_view_rest);
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
                float one_set = alpha / rest_time;
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
                                    Intent start_intent = new Intent(sun_rest.this, sun_exercise_screen_1.class);
                                    start_intent.putExtra("점수", score_text_1);
                                    start_intent.putExtra("호랑이", tiger_count);
                                    start_intent.putExtra("screen_up", screen_up );
                                    startActivity(start_intent);
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
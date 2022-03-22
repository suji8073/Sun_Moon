package com.example.sun_moon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class clap_exercise_screen_1 extends AppCompatActivity {

    Button next;
    int score_text_1, tiger_count;
    int score_text; // 2세트 점수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clap_exercise_screen);


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


}}
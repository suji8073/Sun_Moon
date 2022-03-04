package com.example.sun_moon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class scoreboard extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard);

        Intent secondIntent = getIntent();
        int score_text = secondIntent.getIntExtra("점수",0);
        int tiger = secondIntent.getIntExtra("호랑이",0);

        ImageButton home = findViewById(R.id.home);
        ImageButton person = findViewById(R.id.person);
        TextView name = findViewById(R.id.name);
        TextView final_score = findViewById(R.id.final_score);
        TextView diff_score = findViewById(R.id.diff_score);
        TextView tiger_score = findViewById(R.id.tiger_score);
        TextView set1 = findViewById(R.id.set1);
        TextView set2 = findViewById(R.id.set2);
        TextView tiger_count = findViewById(R.id.tiger_count);
        Button final_btn = findViewById(R.id.final_btn);

        final_score.setText(score_text+"점");
        tiger_score.setText(tiger+"번");
        tiger_count.setText(tiger+"번");

        int set1_score=80;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (set1.getWidth(), set1.getHeight() * (set1.getHeight())/set1_score);
        set1.setLayoutParams(params);
        set1.setText(set1_score+"점");

        int set2_score= score_text+15;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams
                (set2.getWidth(), set2.getHeight() * (set2.getHeight())/set2_score);
        set2.setLayoutParams(params2);
        set2.setText(score_text+"점");

        int diff;
        if((set1_score - score_text)>0){
            diff=set1_score - score_text;
        }
        else{
            diff=score_text - set1_score;
        }

        diff_score.setText(diff+"점");


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start_intent = new Intent(scoreboard.this, list.class);
                startActivity(start_intent);
            }
        });

        final_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start_intent = new Intent(scoreboard.this, list.class);
                startActivity(start_intent);
            }
        });

    }

}
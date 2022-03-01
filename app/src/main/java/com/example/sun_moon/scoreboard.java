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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard);

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

        person.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                int final_score_t = 95;
                final_score.setText(final_score_t+"점");

                int set1_score=80;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                        (set1.getWidth(), (int) (set1.getHeight() * (set1.getHeight())/set1_score));
                set1.setLayoutParams(params);
                set1.setText(set1_score+"점");

//                int set2_score= Integer.parseInt((String) final_score.getText());
                int set2_score= 95+15;
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams
                        (set2.getWidth(), (int) (set2.getHeight() * (set2.getHeight())/set2_score));
                set2.setLayoutParams(params2);
                set2.setText(((set2_score)-15)+"점");

//                if((set1_score - set2_score)>0){
//                    diff=set1_score - (set2_score-15);
//                }
//                else{
//                    diff=(set2_score-15) - set1_score;
//                }

                int diff=(set2_score-15) - set1_score;
                diff_score.setText(diff+"점");

            }
        });

    }

}
package com.example.sun_moon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class scoreboard extends AppCompatActivity {

    View view_1, view_2;

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
        TextView set2 = findViewById(R.id.set2);
        TextView tiger_count = findViewById(R.id.tiger_count);
        Button final_btn = findViewById(R.id.final_btn);

        final_score.setText(score_text+"점");
        tiger_score.setText(tiger+"번");
        tiger_count.setText(tiger+"번");

        final TextView set1 = findViewById(R.id.set1);

        set1.post(new Runnable() {
            @Override
            public void run() {
                set1.getHeight(); //height is ready
            }
        });

        //layout세로 - pb세로*(점수/100)
        int set1_score=80;

        float zero_h = getResources().getDimension(R.dimen.zero_h);
        float content_w = getResources().getDimension(R.dimen.content_w);
        float content_h= getResources().getDimension(R.dimen.content_h);
        float layout_w = getResources().getDimension(R.dimen.layout_w);
        float layout_h= getResources().getDimension(R.dimen.layout_h);

        LinearLayout set2_layout = findViewById(R.id.set2_layout);

        //int new_h = (int)(content_h*((layout_h-content_h)*(50/100)));
        //int new_h = (int)(content_h + content_h*0.00249*score_text);
        int new_h = 0;
        if(score_text==0){
            new_h = (int)(zero_h);
        }
        else if((score_text>0)&(score_text<4)){
            new_h = (int)(content_h);
        }
        else if(score_text>=4){
            new_h = (int)(content_h + content_h*0.025*score_text);
        }


//        ProgressBar pb = findViewById(R.id.pb5);
//        int pb5_height = pb.getLayoutParams().height;
//        int view_new_h = pb5_height / 100 * score_text;
//
//        view_2 = findViewById(R.id.view_2);
//        view_2.setLayoutParams(new LinearLayout.LayoutParams((int) layout_w, view_new_h));

        set2_layout.setLayoutParams(new LinearLayout.LayoutParams((int) layout_w, new_h));

        set1.setText(set1_score+"점");
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





//int pb5 = set1_layout.getLay() - set1.getHeight();
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
//                (set1_layout.getWidth(), (int)(set1_layout.getHeight() - pb5*(set1_score/100)));
//        set1.setLayoutParams(params);
//        int set2_score= score_text+15;
//        int pb6 = set2_layout.getHeight() - set2.getHeight();
//        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams
//                (set2.getWidth(), (int) (set2.getHeight() * 10));
//        //(set2.getHeight())/set2_score
//        set2.setLayoutParams(params2);
//        set2.setText(score_text+"점");
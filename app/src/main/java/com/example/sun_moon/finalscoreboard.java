package com.example.sun_moon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class finalscoreboard extends AppCompatActivity {

    View view_1, view_2;
    ImageView home;
    TextView name, diff_score, final_score, tiger_score, set1, set2, tiger_count, which_score;
    Button final_btn;
    ProgressBar pb5, pb6;
    int diff = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalscoreboard);

        Intent secondIntent = getIntent();
        int score_text = secondIntent.getIntExtra("점수",50);
        int tiger = secondIntent.getIntExtra("호랑이",0);

        home = findViewById(R.id.home);
        name = findViewById(R.id.name); // 사용자 이름
        //사용자 이름 수정 코드 추가해야 함.

        final_score = findViewById(R.id.final_score);
        diff_score = findViewById(R.id.diff_score);
        tiger_score = findViewById(R.id.tiger_score);
        set2 = findViewById(R.id.set2);
        tiger_count = findViewById(R.id.tiger_count);
        final_btn = findViewById(R.id.final_btn);

        final_score.setText(score_text+"점"); // 사용자 총 점수
        tiger_score.setText(tiger+"번"); //호랑이 등장 횟수
        tiger_count.setText(tiger+"번");//호랑이 등장 횟수

        set1 = findViewById(R.id.set1); // 사용자 전 세트 점수

        int set1_score = 40; //사용자의 전 세트 점수
        set1.setText(set1_score+"점");
        set2.setText(score_text+"점"); // 사용자의 현재 점수

        //사용자 전 세트와 현재 점수 차이 보여주기
        which_score = findViewById(R.id.which_score);
        pb5 = findViewById(R.id.pb5); //1세트
        pb6 = findViewById(R.id.pb6); //2세트
        view_1 = findViewById(R.id.view_1);
        view_2 = findViewById(R.id.view_2);

        if((set1_score - score_text) > 0)
        {
            diff = set1_score - score_text;
            which_score.setText("1");

            pb5.getLayoutParams().height = 440;
            view_1.getLayoutParams().height = 0;

            float user_one_point_dp = pb5.getLayoutParams().height / set1_score;
            System.out.println(user_one_point_dp);

            view_2.getLayoutParams().height = (int) (diff * user_one_point_dp);
            pb6.getLayoutParams().height =  440 - (int) (score_text * user_one_point_dp);
        }

        else
        {
            diff = score_text - set1_score;
            which_score.setText("2");

            pb6.getLayoutParams().height = 440;
            view_2.getLayoutParams().height = 0;

            float user_one_point_dp = 440 / score_text;
            System.out.println(user_one_point_dp);

            view_1.getLayoutParams().height = (int) (diff * user_one_point_dp);
            pb5.getLayoutParams().height = 440 - (int) (diff * user_one_point_dp);
        }
        diff_score.setText(diff+"점");


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start_intent = new Intent(finalscoreboard.this, list.class);
                startActivity(start_intent);
            }
        });

        final_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start_intent = new Intent(finalscoreboard.this, list.class);
                startActivity(start_intent);
            }
        });
    }
}
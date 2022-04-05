package com.example.sun_moon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class scoreboard extends AppCompatActivity {

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
        setContentView(R.layout.scoreboard);

        Intent secondIntent = getIntent();
        int set1_score = secondIntent.getIntExtra("점수_1",0);
        int score_text = secondIntent.getIntExtra("점수_2",0);
        int tiger = secondIntent.getIntExtra("호랑이",0);

        SharedPreferences pref;
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        String name_text = pref.getString("name",null);

        home = findViewById(R.id.home);
        name = findViewById(R.id.name); // 사용자 이름
        name.setText(name_text);

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

        set1.setText(set1_score+"점");
        set2.setText(score_text+"점"); // 사용자의 현재 점수

        //사용자 전 세트와 현재 점수 차이 보여주기
        pb5 = findViewById(R.id.pb5); //1세트
        pb6 = findViewById(R.id.pb6); //2세트
        view_1 = findViewById(R.id.view_1);
        view_2 = findViewById(R.id.view_2);

        float sum = set1_score + score_text;
        diff_score.setText((sum / 2) + "점");

        if((set1_score - score_text) > 0)
        {
            diff = set1_score - score_text;

            pb5.getLayoutParams().height = 440;
            view_1.getLayoutParams().height = 0;

            float user_one_point_dp = set1_score == 0 ? pb5.getLayoutParams().height : pb5.getLayoutParams().height / set1_score; // 여기 0점 하면 0나누기 에러 발생해서 임의로 고쳤는데 0일떄 그래프 풀로 나타남.
            System.out.println(user_one_point_dp);

            if (score_text == 0) {
                view_2.getLayoutParams().height = 440;
                pb6.getLayoutParams().height = 0;
            }
            else {
                view_2.getLayoutParams().height = (int) (diff * user_one_point_dp);
                pb6.getLayoutParams().height =  440 - (int) (diff * user_one_point_dp);
            }

        }
        else if (set1_score == 0 && score_text == 0) { // 둘 다 0 일 때
            pb5.getLayoutParams().height = 0;
            pb6.getLayoutParams().height = 0;
            view_1.getLayoutParams().height = 440;
            view_2.getLayoutParams().height = 440;
        }
        else {
            diff = score_text - set1_score;

            pb6.getLayoutParams().height = 440;
            view_2.getLayoutParams().height = 0;

            float user_one_point_dp = score_text == 0 ? 440: 440 / score_text; //여기도
            System.out.println(user_one_point_dp);

            if (set1_score == 0){
                view_1.getLayoutParams().height = 440;
                pb5.getLayoutParams().height = 0;
            }
            else {
                view_1.getLayoutParams().height = (int) (diff * user_one_point_dp);
                pb5.getLayoutParams().height = 440 - (int) (diff * user_one_point_dp);
            }
        }




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
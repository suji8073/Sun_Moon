package com.example.sun_moon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class list extends AppCompatActivity {
    TextView name, rank_more;
    Button logout;
    String name_text;
    SharedPreferences pref;
    LinearLayout start, rank_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        pref = getSharedPreferences("pref", MODE_PRIVATE);
        name_text = pref.getString("name",null);

        name = findViewById(R.id.name);
        name.setText(name_text);

        start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start_intent = new Intent(list.this, camerax.class);
                startActivity(start_intent);
            }
        });

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout_dialog();
            }
        });

        rank_click = findViewById(R.id.rank_click);
        rank_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rank_popup();
            }
        });

        rank_more = findViewById(R.id.rank_more);
        rank_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rank_popup();
            }
        });
    }

    void logout_dialog(){
        logout octDialog = new logout(list.this, new CustomDialogClickListener1() {
            @Override
            public void onPositiveClick() { //로그아웃 취소
                Log.i("Dialog", "확인");
            }

            @Override
            public void onNegativeClick() { //로그아웃
                Intent start_intent = new Intent(list.this, login.class);
                startActivity(start_intent);
            }
        });
        octDialog.setCanceledOnTouchOutside(true);
        octDialog.setCancelable(true);
        octDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        octDialog.show();
    }

    void rank_popup(){
        popup_dialog1 octDialog = new popup_dialog1(list.this, new CustomDialogClickListener() {
            @Override
            public void onPositiveClick() { //확인 버튼
                Log.i("Dialog", "확인");
            }
        });
        octDialog.setCanceledOnTouchOutside(true);
        octDialog.setCancelable(true);
        octDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        octDialog.show();
    }
}
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
    TextView name;
    Button logout;
    String name_text;
    SharedPreferences pref;
    LinearLayout start;

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
}
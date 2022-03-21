package com.example.sun_moon;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class list extends AppCompatActivity {
    Button name;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        //Intent secondIntent = getIntent();
        //String name_text = secondIntent.getStringExtra("이름");


        SharedPreferences pref;
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        String name_text = pref.getString("name",null);

        Button name_btn = findViewById(R.id.name);
        name_btn.setText(name_text);

        LinearLayout start = (LinearLayout) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start_intent = new Intent(list.this, exercise_camera.class);
                startActivity(start_intent);
            }
        });

        logout= findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(list.this);
                builder.setMessage("로그아웃 하시겠습니까?");
                builder.setTitle("로그아웃 알림창")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent start_intent = new Intent(list.this, login.class);
                                startActivity(start_intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alert=builder.create();
                alert.setTitle("종료 알림창");
                alert.show();
            }
        });

    }



}
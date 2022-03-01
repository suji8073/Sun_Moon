package com.example.sun_moon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class list extends AppCompatActivity {
    Button move1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        move1 = findViewById(R.id.move1);

        move1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start_intent = new Intent(list.this,exercise_screen.class);
                //exercise_intro
                startActivity(start_intent);
            }
        });

    }

}
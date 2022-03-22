package com.example.sun_moon;

import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class exercise_camera extends AppCompatActivity {
    TextView name, back;

    Button go;
    SurfaceView surfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_camera);
        go = findViewById(R.id.go);
        name = findViewById(R.id.name);
        //runThread(txt);

        Intent secondIntent = getIntent();
        String game_name = secondIntent.getStringExtra("game_name");
        name.setText(game_name);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (game_name.equals("어깨내리기")) {
                    Intent start_intent = new Intent(exercise_camera.this, sun_exercise_intro.class);
                    startActivity(start_intent);
                }
                else if (game_name.equals("박수치기")) {
                    Intent start_intent = new Intent(exercise_camera.this, clap_exercise_intro.class);
                    startActivity(start_intent);
                }

            }
        });

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent start_intent = new Intent(exercise_camera.this, list.class);
                startActivity(start_intent);
            }
        });
    }

}
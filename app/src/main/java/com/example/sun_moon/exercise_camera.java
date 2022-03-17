package com.example.sun_moon;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class exercise_camera extends AppCompatActivity {
    TextView txt, back;

    Button go;
    SurfaceView surfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_camera);
        go = findViewById(R.id.go);
        //runThread(txt);


        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent start_intent = new Intent(exercise_camera.this, exercise_intro.class);
                startActivity(start_intent);

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
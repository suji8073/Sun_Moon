package com.example.sun_moon;

import android.app.ActionBar;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class exercise_intro extends AppCompatActivity {
    Button skip, back;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercies_intro);
        txt=findViewById(R.id.txt);
        final VideoView videoView=(VideoView) findViewById(R.id.video);
        MediaController mc=new MediaController(this);
        videoView.setMediaController(mc);
        videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.im));
        videoView.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CountDownTimer countDownTimer=new CountDownTimer(15000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int num=(int) (millisUntilFinished/1000);
                        txt.setText(Integer.toString(num+1));

                    }

                    @Override
                    public void onFinish() {
                        txt.setText("시작");
                        Intent start_intent = new Intent(exercise_intro.this, exercise_screen.class);
                        startActivity(start_intent);

                    }
                }.start();

            }
        },6000);




        skip = findViewById(R.id.skip);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start_intent = new Intent(exercise_intro.this, exercise_screen.class);
                startActivity(start_intent);
            }
        });

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start_intent = new Intent(exercise_intro.this, list.class);
                startActivity(start_intent);
            }
        });

    }

}
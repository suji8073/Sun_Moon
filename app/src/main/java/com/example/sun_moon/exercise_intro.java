package com.example.sun_moon;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;


public class exercise_intro extends AppCompatActivity {
    TextView txt, back;
    public int timerStatus = 30;
    private final Handler handler = new Handler();
    Button skip;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercies_intro);
        txt=findViewById(R.id.txt);
        skip = findViewById(R.id.skip);
        videoView= findViewById(R.id.video);
        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);
        videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.intro));
        videoView.start();
        videoView.setOnCompletionListener(completionListener);


        runThread(txt);


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerStatus=-1;
                Intent start_intent = new Intent(exercise_intro.this, exercise_screen.class);
                startActivity(start_intent);
            }
        });


        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerStatus=-1;
                Intent start_intent = new Intent(exercise_intro.this, camerax.class);
                startActivity(start_intent);
            }
        });

    }

    MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            videoView.start();
        }
    };

    private void runThread(TextView txt) {
        new Thread() {
            public void run() {
                while (timerStatus >= 0 ) {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timerStatus -=1;
                                txt.setText(String.valueOf(timerStatus));
                                if (timerStatus == 0)
                                {
                                    txt.setText("시작");
                                    Intent start_intent = new Intent(exercise_intro.this, exercise_screen.class);
                                    startActivity(start_intent);
                                }
                            }
                        });
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
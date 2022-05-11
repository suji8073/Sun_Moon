package com.example.sun_moon;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

public class scroll extends AppCompatActivity {
    Button skip, up;
    ScrollView scroll;
    int originX, originY;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll);

        LinearLayout view = findViewById(R.id.view);
        image= findViewById(R.id.image);

        scroll= findViewById(R.id.scrl);


        originX = scroll.getScrollX();
        originY = scroll.getScrollY();
        skip = findViewById(R.id.skip);

        if (originY == 0) {
            scroll.post(() -> {
                scroll.fullScroll(ScrollView.FOCUS_DOWN);
            });
        }
        scroll.fullScroll(ScrollView.FOCUS_DOWN);


        up = findViewById(R.id.up);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll.post(new Runnable() {
                    @Override
                    public void run() {
                        ObjectAnimator.ofInt(scroll, "scrollY", 500).setDuration(10000).start();
                        ObjectAnimator.ofFloat(up, "Y", 500).setDuration(10000).start();
                        ObjectAnimator.ofFloat(skip, "Y", 500).setDuration(10000).start();

                    }
                });
            }
        });

    }
}



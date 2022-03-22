package com.example.sun_moon;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;

public class exercise_camera extends AppCompatActivity implements SurfaceHolder.Callback {
    final private static String TAG = "GILBOMI";
    Button btn_photo;
    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    boolean previewing=false;
    LayoutInflater controlInflater=null;
    final static int TAKE_PICTURE = 1;
    String mCurrentPhotoPath;
    final static int REQUEST_TAKE_PHOTO = 1;

    TextView txt, back;

    Button on;
    Button go;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.exercise_camera);
        go = findViewById(R.id.go);
        on=findViewById(R.id.on);
        surfaceView = findViewById(R.id.surfaceview);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceView=(SurfaceView)findViewById(R.id.surfaceview);
        surfaceHolder=surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


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

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        camera= Camera.open();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int height, int width) {
        if(previewing){
            camera.stopPreview();
            previewing=false;
        }
        if(camera !=null){
            try{
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                previewing=true;
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        camera.stopPreview();
        camera.release();
        camera=null;
        previewing=false;
    }
}
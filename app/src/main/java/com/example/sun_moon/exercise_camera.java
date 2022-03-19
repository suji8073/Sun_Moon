package com.example.sun_moon;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class exercise_camera extends AppCompatActivity {
    final private static String TAG = "GILBOMI";
    Button btn_photo;
    ImageView camera;
    final static int TAKE_PICTURE = 1;
    String mCurrentPhotoPath;
    final static int REQUEST_TAKE_PHOTO = 1;

    TextView txt, back;

    Button on;
    Button go;
    SurfaceView surfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_camera);
        go = findViewById(R.id.go);
        on=findViewById(R.id.on);
        camera = findViewById(R.id.camera);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED&& checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                Log.d(TAG,"권한 설정 완료");

            }
            else{
                Log.d(TAG,"권한 설정 요청");
                ActivityCompat.requestPermissions(exercise_camera.this, new
                    String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE },1);
            }
        }

        //runThread(txt);
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.on:
                        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });


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
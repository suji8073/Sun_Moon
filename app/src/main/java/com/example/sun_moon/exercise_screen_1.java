package com.example.sun_moon;

import static com.google.mlkit.vision.pose.PoseDetectorOptionsBase.CPU_GPU;
import static java.lang.Thread.sleep;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.common.internal.ImageConvertUtils;
import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseDetection;
import com.google.mlkit.vision.pose.PoseDetector;
import com.google.mlkit.vision.pose.PoseLandmark;
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions;
import com.google.mlkit.vision.segmentation.Segmentation;
import com.google.mlkit.vision.segmentation.SegmentationMask;
import com.google.mlkit.vision.segmentation.Segmenter;
import com.google.mlkit.vision.segmentation.selfie.SelfieSegmenterOptions;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class exercise_screen_1 extends exerciseScreen {
    public exercise_screen_1() {
        this.layoutid = R.layout.exercise_screen_1;
    }


    @SuppressLint({"ClickableViewAccessibility", "UnsafeOptInUsageError"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        score_text_1 = intent.getIntExtra("점수", 0);
        tiger_count = intent.getIntExtra("호랑이", 0);
        screen_up = intent.getIntExtra("screen_up", 0);
        system_start();
        music_start();
    }

    public void system_start(){
        pb.setProgress(progressStatus);
        if (originY == 0) {
            scroll.post(() -> {
                scroll.fullScroll(ScrollView.FOCUS_DOWN);
                float up_location = image.getHeight()- view.getHeight();
                userNoBG.setY(image.getHeight()- view.getHeight() - screen_up);//조정 필요
                up.setY(image.getHeight()- view.getHeight() - screen_up);
                time_view.setY(image.getHeight()- view.getHeight() + 40 - screen_up);
                score_view.setY(image.getHeight()- view.getHeight() + 40 - screen_up);

                pb.setY(image.getHeight()- view.getHeight() + 600 - screen_up);
                tiger_progress.setY(image.getHeight()- view.getHeight() + 1900 - screen_up);
                tiger_exercise.setY(image.getHeight()- view.getHeight() + 2400 - screen_up);
                tiger_100.setY(image.getHeight()- view.getHeight() + 1400 - screen_up);

                ObjectAnimator.ofInt(scroll, "scrollY", Math.round(up_location), Math.round(up_location - screen_up)).setDuration(600).start();
            });
        }
    }


    @Override
    public void nextScreen(){
        Intent start_intent = new Intent(exercise_screen_1.this, scoreboard.class);
        start_intent.putExtra("점수_1", score_text_1);
        start_intent.putExtra("점수_2", score_text);
        start_intent.putExtra("호랑이", tiger_count);
        startActivity(start_intent);
    }



}



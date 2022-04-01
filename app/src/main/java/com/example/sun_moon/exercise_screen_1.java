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

public class exercise_screen_1 extends AppCompatActivity {
    Button up;
    ScrollView scroll;
    ProgressBar pb;
    TextView time, score;
    int originX, originY;
    private int timerStatus = 90, score_text = 0, tiger_count = 0, progressStatus = 0, move_num = 100 ;
    ImageView image, tiger_exercise, tiger_100, tiger_progress;
    MediaPlayer mediaPlayer;
    LinearLayout view, score_view, time_view;

    private SoundPool soundPool;
    private int sound, sound1;
    private final Handler handler = new Handler();
    int count1 =0;
    int score_text_1, screen_up;
    int up_count = 0;
    int tiger_up_num = 30;
    String tiger_up_check = "f";
    TimerThread timerThread = new TimerThread();
    progressThread progressThread = new progressThread();
    Thread tthread = new Thread(timerThread);
    Thread pthread = new Thread(progressThread);

    protected ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    ImageView userNoBG;
    PoseLandmark leftWrist;
    private final Executor classificationExecutor;
    private final Executor segmentExecutor;
    public boolean leftWristDown= false;
    public int count =0;

    private PoseDetector detector;
    private Segmenter segmenter;

    public exercise_screen_1() {
        classificationExecutor = Executors.newSingleThreadExecutor();
        segmentExecutor= Executors.newSingleThreadExecutor();
    }


    @SuppressLint({"ClickableViewAccessibility", "UnsafeOptInUsageError"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_screen_1);
        userNoBG=findViewById(R.id.userwithnobg);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        mediaPlayer = MediaPlayer.create(this, R.raw.background);
        mediaPlayer.start();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        Intent intent = getIntent(); //전달할 데이터를 받을 Intent
        score_text_1 = intent.getIntExtra("점수", 0);
        tiger_count = intent.getIntExtra("호랑이", 0);
        screen_up = intent.getIntExtra("screen_up", 0);

        view = findViewById(R.id.view);
        image= findViewById(R.id.image);
        Bitmap background = BitmapFactory.decodeResource(getResources(),R.drawable.background);
        Bitmap resizedbg= Bitmap.createScaledBitmap(background,size.x,8744,true);
        image.setImageBitmap(resizedbg);

        score_view = findViewById(R.id.score_view);
        time_view= findViewById(R.id.time_view);

        scroll = findViewById(R.id.scrl);
        scroll.setOnTouchListener((v, event) -> {
            scroll.requestDisallowInterceptTouchEvent(false);
            return true;
        });
        originX = scroll.getScrollX();
        originY = scroll.getScrollY();
        time = findViewById(R.id.time);

        pb = findViewById(R.id.pb);
        time = findViewById(R.id.time);
        score = findViewById(R.id.score);

        tiger_progress = findViewById(R.id.tiger_progress); // 호랑이
        tiger_exercise = findViewById(R.id.tiger_exercise); // 호랑이
        tiger_100 = findViewById(R.id.tiger_100);// 호랑이 100일 때 나오는 호랑이

        tiger_exercise.setVisibility(View.INVISIBLE); // 호랑이 안 보이게
        tiger_100.setVisibility(View.INVISIBLE); // 호랑이 안 보이게

        up = findViewById(R.id.up);
        tthread.start();
        pthread.start();
        system_start();
        music_start();

        PoseDetectorOptions options =
                new PoseDetectorOptions.Builder()
                        .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
                        .setPreferredHardwareConfigs(CPU_GPU)
                        .build();
        detector = PoseDetection.getClient(options);
        SelfieSegmenterOptions options2 =
                new SelfieSegmenterOptions.Builder()
                        .setDetectorMode(SelfieSegmenterOptions.STREAM_MODE)
                        //.enableRawSizeMask()
                        .build();
        segmenter = Segmentation.getClient(options2);
        startCamera();
    }

    @Override
    protected void onPause(){
        super.onPause();
        soundPool.autoPause();
        mediaPlayer.pause();


    }
    @Override
    protected void onResume(){
        super.onResume();
        soundPool.autoResume();
        mediaPlayer.start();



    }

    @Override
    protected void onStop(){
        super.onStop();
        tthread.interrupt();
        pthread.interrupt();
    }

    public void music_start(){
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(6)
                .setAudioAttributes(audioAttributes)
                .build();

        sound = soundPool.load(this,R.raw.growl,1);
        sound1 = soundPool.load(this,R.raw.plus,1);
    }

    public void system_start(){
        pb.setProgress(progressStatus);
        if (originY == 0) {
            scroll.post(() -> {
                scroll.fullScroll(ScrollView.FOCUS_DOWN);
                float up_location = image.getHeight()- view.getHeight();

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

    public void up_action(){
        tiger_100.setVisibility(View.INVISIBLE);
        tiger_up_check = "f";

        if (progressStatus <= 10) progressStatus = 0;
        else if (( progressStatus >= 50 )&( progressStatus < 100 )) progressStatus -= 15;
        else if (progressStatus > 100) progressStatus = 48;
        else progressStatus -= 15;

        Score();

        if (up.getY() - move_num > 0) {
            scroll.post(new Runnable() {
                @Override
                public void run() {
                    ObjectAnimator.ofInt(scroll, "scrollY", Math.round(up.getY()), Math.round(up.getY() - move_num)).setDuration(600).start();
                    ObjectAnimator.ofFloat(up, "Y", up.getY(), up.getY() - move_num).setDuration(600).start();
                    ObjectAnimator.ofFloat(time_view, "Y", time_view.getY(), time_view.getY() - move_num).setDuration(600).start();
                    ObjectAnimator.ofFloat(score_view, "Y", time_view.getY(), time_view.getY() - move_num).setDuration(600).start();
                    ObjectAnimator.ofFloat(pb, "Y", pb.getY(), pb.getY() - move_num).setDuration(600).start();
                    ObjectAnimator.ofFloat(tiger_progress, "Y", tiger_progress.getY(), tiger_progress.getY() - move_num).setDuration(600).start();
                    ObjectAnimator.ofFloat(tiger_100, "Y", tiger_100.getY(), tiger_100.getY() - move_num).setDuration(600).start();
                }
            });
            score_view.setBackgroundResource(R.drawable.score);
        }
    }

    class progressThread implements Runnable {
        public void run() {
            try {
                tiger_up_check = "t";
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            pb.setProgress(progressStatus);
                            if (progressStatus < 50) {
                                tiger_exercise.setVisibility(View.INVISIBLE);
                            } else if (progressStatus == 50) {
                                tiger_exercise.setY(tiger_100.getY() + 800);
                            } else if (progressStatus < 100) {
                                up_count++;
                                tiger_exercise.setVisibility(View.VISIBLE);
                                if (tiger_up_check.equals("t")) { // 올라가기
                                    ObjectAnimator.ofFloat(tiger_exercise, "Y", tiger_exercise.getY(), tiger_exercise.getY() - tiger_up_num).setDuration(600).start();
                                } else { // 내려가기 up 버튼을 눌렀을 때

                                    ObjectAnimator.ofFloat(tiger_exercise, "Y", tiger_exercise.getY(), tiger_exercise.getY() + 3 * tiger_up_num).setDuration(600).start();
                                    tiger_up_check = "t";
                                }
                            } else if (progressStatus == 100) { // 호랑이 등장
                                tiger_count += 1;
                                tiger_exercise.setVisibility(View.INVISIBLE);
                                tiger_100.setVisibility(View.VISIBLE);
                                soundPool.play(sound, 1, 1, 0, 0, 1); //호랑이 등장 소리
                            } else progressStatus = 101;
                        }
                    });
                    progressStatus += 1;
                }

            } catch (Exception ignored) {
            }
        }
    }

    class TimerThread implements Runnable {
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()&&timerStatus > 0) {
                    timerStatus -= 1;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            if (timerStatus >= 60) {
                                if ((timerStatus - 60) <= 9)
                                    time.setText("1:0" + (timerStatus - 60));
                                else time.setText("1:" + (timerStatus - 60));
                            } else if (timerStatus <= 9)
                                time.setText("0:0" + (timerStatus));
                            else time.setText("0:" + (timerStatus));

                            if (timerStatus <= 30) {
                                time.setTextColor(0xAAef484a);
                            }
                        }
                    });
                }
                if (timerStatus == 0) {
                    mediaPlayer.stop();
                    progressStatus = 101;

                    Intent start_intent = new Intent(exercise_screen_1.this, scoreboard.class);
                    start_intent.putExtra("점수_1", score_text_1);
                    start_intent.putExtra("점수_2", score_text);
                    start_intent.putExtra("호랑이", tiger_count);
                    startActivity(start_intent);
                }


            } catch (Exception ignored) {


            }
        }

    }


    public void Score() {
        score_text += 1;
        Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_animation);
        score_view.startAnimation(startAnimation);
        score.setText(String.valueOf(score_text) + "점");
        score_view.setBackgroundResource(R.drawable.score_plus);

        soundPool.play(sound1, 1, 1, 0, 0, 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @ExperimentalGetImage
    private void startCamera(){
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                        .build();
                ImageAnalysis imageAnalysis =
                        new ImageAnalysis.Builder()
                                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                                //.setTargetResolution(new Size(640,480))
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build();

                imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this),
                        imageProxy -> {

                            Image mediaImage = imageProxy.getImage();
                            if (mediaImage != null) {
                                Log.v("size", "inputimage "+mediaImage.getHeight()+" we :" +mediaImage.getWidth());
                                InputImage image = InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
                                posework(image).addOnCompleteListener(result->segwork(image).addOnCompleteListener(results -> imageProxy.close()));
                            }
                        });

                cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageAnalysis);
            } catch (ExecutionException | InterruptedException ignored) {
            }
        }, ContextCompat.getMainExecutor(this));
    }


    private Task<Object> segwork(InputImage image) {
        return segmenter
                .process(image)
                .continueWith(segmentExecutor,
                        task -> {
                            SegmentationMask segmentationMask  = task.getResult();
                            ByteBuffer mask = segmentationMask.getBuffer();
                            int maskWidth = segmentationMask.getWidth();
                            int maskHeight = segmentationMask.getHeight();
                            Bitmap inputimg = ImageConvertUtils.getInstance().getUpRightBitmap(image);
                            int[] pixels = new int[inputimg.getHeight()*inputimg.getWidth()];
                            inputimg.getPixels(pixels, 0, inputimg.getWidth(), 0, 0, inputimg.getWidth(), inputimg.getHeight());
                            for (int i = 0; i < maskWidth * maskHeight; i++) { //픽셀을 하나하나 처리하면 너무 느려지고 배열로 한번에 처리한후 배열째로 처리(갱신)해야함
                                if (mask.getFloat() < 0.1){ //사람
                                    pixels[i]= Color.TRANSPARENT;
                                }
                            }
                            inputimg.setPixels(pixels, 0, inputimg.getWidth(), 0, 0, inputimg.getWidth(), inputimg.getHeight());
                            new Handler(Looper.getMainLooper()).post(() -> userNoBG.setImageBitmap(inputimg));

                            return task;
                        }
                );
    }


    private Task<Object> posework(InputImage image){
        return detector
                .process(image)
                .continueWith(
                        classificationExecutor,
                        task -> {
                            Pose pose = task.getResult();
                            leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST);
                            if (leftWrist.getPosition().y<200&leftWristDown){
                                up_action();
                                leftWristDown=false;
                            }
                            else if(leftWrist.getPosition().y>450&!leftWristDown){
                                leftWristDown=true;
                            }

                            return task;
                        }
                );

    }

}



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
import android.media.Image;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
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
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class exerciseScreen extends AppCompatActivity {

    Button up;
    ScrollView scroll;
    ProgressBar pb;
    TextView time, score;
    int originX, originY;
    int timerStatus = 90;
    int score_text = 0;
    int tiger_count = 0;
    int progressStatus = 0;
    final int move_num = 100 ;
    int speed_count = 0;
    ImageView image, tiger_exercise, tiger_100, tiger_progress;
    MediaPlayer mediaPlayer;
    LinearLayout view, score_view, time_view;

    int layoutid;

    private SoundPool soundPool;
    private int sound, sound1;
    private final Handler handler = new Handler();

    int score_text_1, screen_up;
    int up_count = 0;
    int tiger_up_num = 30;
    String tiger_up_check = "f";
    progressThread progressThread = new progressThread();
    Timer timer = new Timer();
    Thread pthread = new Thread(progressThread);

    protected ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    ImageView userNoBG;
    PoseLandmark leftWrist;
    private final Executor classificationExecutor;
    private final Executor segmentExecutor;
    public boolean leftWristDown= false;

    private PoseDetector detector;
    private Segmenter segmenter;
    private Bitmap background, resized_bg;

    boolean isActivityForeground= false;
    ArrayList move_time_save = new ArrayList();

    public exerciseScreen() { //생성자는 나중에 처리
        classificationExecutor = Executors.newSingleThreadExecutor();
        segmentExecutor= Executors.newSingleThreadExecutor();
    }

    @SuppressLint({"ClickableViewAccessibility", "UnsafeOptInUsageError"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutid);
        userNoBG=findViewById(R.id.userwithnobg);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        mediaPlayer = MediaPlayer.create(this, R.raw.background); // 배경음악
        mediaPlayer.start();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);

        view = findViewById(R.id.view);
        image= findViewById(R.id.image);
        background = BitmapFactory.decodeResource(getResources(),R.drawable.background);
        resized_bg= Bitmap.createScaledBitmap(background,size.x,8744,true);
        image.setImageBitmap(resized_bg);

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
        TimerTask timerTask = new timerFunc();
        timer.schedule(timerTask,0,1000);
        pthread.start();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        music_start();
        move_time_save.add(timerStatus);

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
        isActivityForeground=false;
        soundPool.autoPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        isActivityForeground=true;
        soundPool.autoResume();
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy()
    {
        isActivityForeground=false;
        soundPool.release();
        timer.cancel();
        super.onDestroy();
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

        sound = soundPool.load(this,R.raw.growl,1); // 호랑이 등장 소리
        sound1 = soundPool.load(this,R.raw.plus,1);

    }

    public void system_start(){}

    public void up_action(){
        tiger_100.setVisibility(View.INVISIBLE);
        tiger_up_check = "f";

        if (progressStatus <= 10) progressStatus = 0;
        else if (( progressStatus >= 50 )&( progressStatus < 100 )) progressStatus -= 15;
        else if (progressStatus > 100) progressStatus = 48;
        else progressStatus -= 15;

        Score();
        move_time_save.add(timerStatus);
        Speed_count();

        if (up.getY() - move_num > 0) {
            scroll.post(() -> {
                ObjectAnimator.ofFloat(userNoBG, "Y", up.getY(), up.getY() - move_num).setDuration(600).start();
                ObjectAnimator.ofInt(scroll, "scrollY", Math.round(up.getY()), Math.round(up.getY() - move_num)).setDuration(600).start();
                ObjectAnimator.ofFloat(up, "Y", up.getY(), up.getY() - move_num).setDuration(600).start();
                ObjectAnimator.ofFloat(time_view, "Y", time_view.getY(), time_view.getY() - move_num).setDuration(600).start();
                ObjectAnimator.ofFloat(score_view, "Y", time_view.getY(), time_view.getY() - move_num).setDuration(600).start();
                ObjectAnimator.ofFloat(pb, "Y", pb.getY(), pb.getY() - move_num).setDuration(600).start();
                ObjectAnimator.ofFloat(tiger_progress, "Y", tiger_progress.getY(), tiger_progress.getY() - move_num).setDuration(600).start();
                ObjectAnimator.ofFloat(tiger_100, "Y", tiger_100.getY(), tiger_100.getY() - move_num).setDuration(600).start();
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
                    handler.post(() -> {
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
                            }
                            else { // 내려가기 up 버튼을 눌렀을 때
                                ObjectAnimator.ofFloat(tiger_exercise, "Y", tiger_exercise.getY(), tiger_exercise.getY() + 3 * tiger_up_num).setDuration(600).start();
                                tiger_up_check = "t";
                            }
                        } else if (progressStatus == 100) { // 호랑이 등장
                            tiger_count += 1;
                            tiger_exercise.setVisibility(View.INVISIBLE);
                            tiger_100.setVisibility(View.VISIBLE);
                            if(isActivityForeground){
                                soundPool.play(sound, 1, 1, 0, 0, 1); //호랑이 등장 소리
                            }
                        } else progressStatus = 101;
                    });
                    progressStatus += 1;
                }

            } catch (Exception ignored) {
            }
        }
    }

    class timerFunc extends TimerTask{
        @Override
        public void run(){
            if(timerStatus!=0){
                timerStatus -= 1;
            }else {
                timer.cancel();
                mediaPlayer.stop();
                progressStatus = 101;
                nextScreen();
            }
            handler.post(()->time.setText(getString(R.string.caltime,timerStatus/60,timerStatus%60/10,timerStatus%60%10)));

            if (timerStatus <= 30) {
                handler.post(()->time.setTextColor(0xAAef484a));
            }
        }
    }

    public void nextScreen(){}

    public void Score() {
        score_text += 1;
        Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_animation);
        score_view.startAnimation(startAnimation);
        score.setText(getString(R.string.score,score_text));
        score_view.setBackgroundResource(R.drawable.score_plus);

        soundPool.play(sound1, 1, 1, 0, 0, 1);
    }

    public void Speed_count(){ // 3초 이내에 운동 한번씩 * 5번 진행 -> 배속
        int array_size = move_time_save.size();
        int last_time = (int) move_time_save.get(array_size - 2);
        int now_time = (int) move_time_save.get(array_size - 1);

        if (last_time - now_time <= 3) { // 3초 이내이면
            speed_count++;
        } else {
            speed_count = 0; // 초기화
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(1.0f));
            }
        }

        if (speed_count >= 3) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(1.3f));
            }
            speed_count = 0; // 초기화
        }
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
                                InputImage image = InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
                                posework(image).addOnCompleteListener(result->segwork(image).addOnCompleteListener(results -> imageProxy.close()));
                            }
                        });

                cameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis);
            } catch (ExecutionException | InterruptedException ignored) {
            }
        }, ContextCompat.getMainExecutor(this));
    }


    private Task<Object> segwork(InputImage image) {
        return segmenter.process(image).continueWith(segmentExecutor, task -> {
            SegmentationMask segmentationMask  = task.getResult();
            ByteBuffer mask = segmentationMask.getBuffer();
            int maskWidth = segmentationMask.getWidth();
            int maskHeight = segmentationMask.getHeight();
            Bitmap input_img = ImageConvertUtils.getInstance().getUpRightBitmap(image);
            int[] pixels = new int[input_img.getHeight() * input_img.getWidth()];
            input_img.getPixels(pixels, 0, input_img.getWidth(), 0, 0, input_img.getWidth(), input_img.getHeight());
            for (int i = 0; i < maskWidth * maskHeight; i++) {
                if (mask.getFloat() < 0.1){ //사람
                    pixels[i]= Color.TRANSPARENT;
                }
            }
            input_img.setPixels(pixels, 0, input_img.getWidth(), 0, 0, input_img.getWidth(), input_img.getHeight());
            Bitmap resize= Bitmap.createScaledBitmap(input_img,1600,2560,true);
            new Handler(Looper.getMainLooper()).post(() -> userNoBG.setImageBitmap(input_img));

            return task;
        });
    }

    private Task<Object> posework(InputImage image){
        return detector.process(image).continueWith( classificationExecutor, task -> {
            Pose pose = task.getResult();
            leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST);
            if (leftWrist.getPosition().y<150&leftWristDown){
                up_action();
                leftWristDown=false;
            }
            else if(leftWrist.getPosition().y>250&!leftWristDown){
                leftWristDown=true;
            }
            return task;
        });
    }
}

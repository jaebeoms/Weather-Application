package com.example.teamproject;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.teamproject.plantGame.PlantGame;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity2 extends GPS implements SensorEventListener {

    SensorManager sensorManager;
    Sensor stepCountSensor;
    TextView stepCountView;
    Button resetButton;
    ImageView iv, iv_bground;
    Button btn, btnGame;
    ProgressBar bar;
    String timeInfo = "";
    String weatherInfo="";
    Date date;
    Intent fIntent, sIntent;
    int nowValue = 0, maxValue;
    int currentSteps = 0;   // 현재 걸음 수

    private Weather weather = new Weather();

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout2);

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnGame = findViewById(R.id.btnGame);
        btnGame.setOnClickListener(gameStartLtn);
        iv = findViewById(R.id.iv);
        iv_bground = findViewById(R.id.iv_bground);
        Glide.with(this).load(R.drawable.run).into(iv);
        stepCountView = findViewById(R.id.stepCountView);
        resetButton = findViewById(R.id.resetButton);
        bar = findViewById(R.id.progressBar);
        // 리셋 버튼 추가 - 리셋 기능
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 걸음수 초기화
                currentSteps = 0;
                stepCountView.setText(String.valueOf(currentSteps));
                bar.setProgress(currentSteps);
            }
        });

        fIntent = getIntent();
        weatherInfo = fIntent.getStringExtra("weather");
        setBackGround(); // 배경이미지 설정
        //활동 퍼미션 체크
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }
        // 걸음 센서 연결
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        // 디바이스에 걸음 센서의 존재 여부 체크
        if (stepCountSensor == null) {
            Toast.makeText(this, "No Step Sensor", Toast.LENGTH_SHORT).show();
        }
    }

    //배경화면 설정
    void setBackGround() {
        // 18시 이전, 화창한 경우 화창한낮 배경설정
        SimpleDateFormat mFormat = new SimpleDateFormat("HH");
        date = new Date(System.currentTimeMillis());
        timeInfo = mFormat.format(date);
        if(Integer.parseInt(timeInfo)<18 && weatherInfo.equals("Sunny")){
            iv_bground.setBackgroundResource(R.drawable.sunnyday);
            //18시 이후, 화창한 경우 화창한밤 배경설정
        }else if(Integer.parseInt(timeInfo)>=18 && weatherInfo.equals("Sunny")){
            iv_bground.setBackgroundResource(R.drawable.sunnynight);
            //18시 이전, 비오는 경우 비오는낮 배경설정
        }else if(Integer.parseInt(timeInfo)<18 && weatherInfo.equals("Rain")){
            //iv_bground.setBackgroundResource(R.drawable.rainyday);
            iv_bground.setBackgroundResource(R.drawable.sunnyday);
            Glide.with(this).load(R.drawable.rain).into(iv_bground);
            //gif파일 넣기(비오는 화면)
            //18시 이후, 비오는 경우 비오는밤 배경설정
        }else if(Integer.parseInt(timeInfo)>=18 && weatherInfo.equals("Rain")){
            //iv_bground.setBackgroundResource(R.drawable.rainynight);
            iv_bground.setBackgroundResource(R.drawable.sunnynight);
            Glide.with(this).load(R.drawable.rain).into(iv_bground);
            //gif파일 넣기(비오는 화면)
            //18시 이전, 구름낀경우 구름낀 배경설정
        }else if(Integer.parseInt(timeInfo)<18 && weatherInfo.equals("Clouds")){
            iv_bground.setBackgroundResource(R.drawable.sunnyday);
            //gif파일 넣기 (구름낀 화면)
            //18시 이후, 구름낀 경우 구름낀 배경설정
        }else if(Integer.parseInt(timeInfo)>=18 && weatherInfo.equals("Clouds")){
            iv_bground.setBackgroundResource(R.drawable.sunnynight);
            //gif파일 넣기 (구름낀 화면)
        }
    }

    public void onStart() {
        super.onStart();
        if(stepCountSensor !=null) {
            // 센서 속도 설정
            // * 옵션
            // - SENSOR_DELAY_NORMAL: 20,000 초 딜레이
            // - SENSOR_DELAY_UI: 6,000 초 딜레이
            // - SENSOR_DELAY_GAME: 20,000 초 딜레이
            // - SENSOR_DELAY_FASTEST: 딜레이 없음
            //
            sensorManager.registerListener(this,stepCountSensor,SensorManager.SENSOR_DELAY_FASTEST);
            nowValue = 0;
            maxValue = bar.getMax();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // 걸음 센서 이벤트 발생시
        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            if(event.values[0]==1.0f){
                // 센서 이벤트가 발생할때 마다 걸음수 증가
                stepCountView.setText(nowValue+"");
                maxValue = bar.getMax();
                bar.setProgress(nowValue);
                Log.i("khr", nowValue + "");
                if (maxValue == nowValue) {
                    nowValue = 0;
                } else {
                    nowValue += 1;
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    //plantGame 액티비티 전환
    View.OnClickListener gameStartLtn =  new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sIntent = new Intent(MainActivity2.this, PlantGame.class);
            sIntent.putExtra("weather", weatherInfo);
            startActivity(sIntent);
        }
    };
}
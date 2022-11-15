package com.example.teamproject.plantGame;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.teamproject.R;
import com.example.teamproject.Weather;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlantGame extends AppCompatActivity {

    //필요한 변수 선언
    Intent intent;
    String weatherInfo = "";
    String timeInfo = "";
    Date date;
    Button btnWater, btnMusic, btnBack;
    ProgressBar progressBar;
    Weather weather;
    MediaPlayer mediaPlayer;
    SoundPool soundPoolWater, soundPoolSuccess;
    ImageView iv_background;
    int soundIDWater, soundIDSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_game);

        //findViewById
        progressBar = findViewById(R.id.progressBar);
        btnMusic = findViewById(R.id.btnMusic);
        btnMusic.setOnClickListener(musicLtn);
        btnWater = findViewById(R.id.btnWater);
        btnWater.setOnClickListener(waterLtn);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(goBackLtn);
        iv_background = findViewById(R.id.iv_background);

        //Weather 클래스의 인스턴스변수 weather 선언
        weather = new Weather();
        intent = getIntent();
        weatherInfo = intent.getStringExtra("weather");
//        weatherInfo = "Rain";
        //초기화
        init();
    }

    //초기화 함수
    void init() {
        //현재 시간 불러오기
        SimpleDateFormat mFormat = new SimpleDateFormat("HH");
        date = new Date(System.currentTimeMillis());
        timeInfo = mFormat.format(date);
        //효과음 설정
        soundPoolWater = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        soundPoolSuccess = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundIDWater = soundPoolWater.load(this,R.raw.blop,1);
        soundIDSuccess = soundPoolSuccess.load(this, R.raw.success ,1);
        setBackground();    //배경사진 설정
        setRainSound();     //배경음악 설정
    }

    //배경사진 설정
    void setBackground() {
        //비 & 낮
        if(weatherInfo.equals("Rain") && Integer.parseInt(timeInfo) < 18){
            iv_background.setBackgroundResource(R.drawable.plantsunny);
            Glide.with(this).load(R.drawable.rain).into(iv_background);
        }
        //비 & 밤
        else if (weatherInfo.equals("Rain") && Integer.parseInt(timeInfo) >= 18){
            iv_background.setBackgroundResource(R.drawable.plantnight);
            Glide.with(this).load(R.drawable.rain).into(iv_background);
        }
        //비X & 낮
        else if(weatherInfo.equals("Rain") == false && Integer.parseInt(timeInfo) < 18) {
            iv_background.setBackgroundResource(R.drawable.plantsunny);
        }
        //비x & 밤
        else if (weatherInfo.equals("Rain") == false && Integer.parseInt(timeInfo) >= 18) {
            iv_background.setBackgroundResource(R.drawable.plantnight);
        }
        //다 아니라면 오류.
        else {
            Toast.makeText(PlantGame.this, "오류입니다.", Toast.LENGTH_SHORT).show();
        }
    }

    //배경음악 설정
    void setRainSound() {
        //비올때
        if(weatherInfo.equals("Rain")){
            mediaPlayer = MediaPlayer.create(PlantGame.this, R.raw.rainsound);
            mediaPlayer.start();
            mediaPlayer.setVolume(0.05f, 0.05f);
            mediaPlayer.setLooping(true);
        }
        //비 안올때
        else if(weatherInfo.equals("Rain") == false){
            mediaPlayer = MediaPlayer.create(PlantGame.this, R.raw.sunny);
            mediaPlayer.start();
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.setLooping(true);
        }
    }

    //물주기 버튼
    View.OnClickListener waterLtn = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            //물 효과음 재생
            soundPoolWater.play(soundIDWater,0.5f,0.5f,0,0,1f);

            int nowValue = progressBar.getProgress();
            int maxValue = progressBar.getMax();

            //progressbar가 꽉 찼을 때
            if(maxValue == nowValue) {
                //팡파레 재생
                soundPoolSuccess.play(soundIDSuccess,0.25f,0.25f,0,0,1f);
                nowValue = 0;
            }
            //progressbar가 꽉 차지 않았을 때
            else {
                //nowValue값 5씩 증가
                nowValue += 5;
            }
            //progressBar nowValue값으로 setting.
            progressBar.setProgress(nowValue);
        }
    };

    //음악 일시정지, 재생 버튼
    View.OnClickListener musicLtn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //재생중일때
            if(mediaPlayer.isPlaying()){
                //일시정지 후 버튼 이미지 바꾸기
                mediaPlayer.pause();
                btnMusic.setBackgroundResource(R.drawable.play);
            }
            //정지상태였을 때
            else {
                //재생 후 버튼 이미지 바꾸기
                mediaPlayer.start();
                btnMusic.setBackgroundResource(R.drawable.pause);
            }
        }
    };

    //만보기 액티비티로 돌아가는 버튼
    View.OnClickListener goBackLtn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //액티비티 종료
            finish();
        }
    };

    //액티비티가 종료될 때
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // MediaPlayer 해지
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

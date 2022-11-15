package com.example.teamproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class MainActivity extends GPS {
    ArrayList<getWeather> all = new ArrayList<getWeather>();
    private GPS gps = new GPS();
    private Weather weather = new Weather();
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter mRecyclerAdapter;
    private ArrayList<FriendItem> mfriendItems = new ArrayList<FriendItem>();
    FriendItem ffr;
    TextView tv_area;
    TextView tv_temp;
    ImageView iv_weather;
    ImageView iv_bground;
    TextView tv_umbrella;
    Button btn;

    Handler mylander = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            getWeather gw = (getWeather)msg.obj;
            // what 값이 마지막이 아니라면//
            if(msg.what !=3){
                all.add(gw);
                // what 최종값은 cnt 갯수랑 비례해요!!//
            }else if(msg.what == 3){
                all.add(gw);
                for(int i=0;i<all.size();i++){
                    if(all.get(i).weather().equals("Rain")){
                        ffr = new FriendItem(R.drawable.rainmark,all.get(i).temp(),all.get(i).forecastTime()+"시");
                        mfriendItems.add(ffr);
                    }else if(all.get(i).weather().equals("Sunny")){
                        ffr = new FriendItem(R.drawable.sunnymark,all.get(i).temp(),all.get(i).forecastTime()+"시");
                        mfriendItems.add(ffr);
                    }else if(all.get(i).weather().equals("Clouds")){
                        ffr = new FriendItem(R.drawable.cloudmark,all.get(i).temp(),all.get(i).forecastTime()+"시");
                        mfriendItems.add(ffr);
                    }
                }
                updateView();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        weather.weather(mylander);
        tv_area = findViewById(R.id.tv_area);
        tv_temp = findViewById(R.id.tv_temp);
        iv_weather = findViewById(R.id.iv_weather);
        iv_bground = findViewById(R.id.iv_bground);
        tv_umbrella = findViewById(R.id.tv_umbrella);
        btn = findViewById(R.id.btn);

    }
    void updateView(){

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
                intent.putExtra("weather", all.get(0).weather());
                startActivity(intent);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        /* initiate adapter */
        mRecyclerAdapter = new MyRecyclerAdapter();

        /* initiate recyclerview */
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));

        /* adapt data */
        mRecyclerAdapter.setFriendList(mfriendItems);

        RecyclerViewDecoration spaceDecoration=new RecyclerViewDecoration(30);
        mRecyclerView.addItemDecoration(spaceDecoration);

        tv_area.setText(gpsArea());
        tv_area.setTextColor(Color.WHITE);

        // 배경이미지 설정
        // 18시 이전, 화창한 경우 화창한낮 배경설정
        if(all.get(0).forecastTime()<18 && all.get(0).weather().equals("Sunny")){
            iv_bground.setBackgroundResource(R.drawable.sunnyday);
            //18시 이후, 화창한 경우 화창한밤 배경설정
        }else if(all.get(0).forecastTime()>=18 && all.get(0).weather().equals("Sunny")){
            iv_bground.setBackgroundResource(R.drawable.sunnynight);
            //18시 이전, 비오는 경우 비오는낮 배경설정
        }else if(all.get(0).forecastTime()<18 && all.get(0).weather().equals("Rain")){
            //iv_bground.setBackgroundResource(R.drawable.rainyday);
            iv_bground.setBackgroundResource(R.drawable.sunnyday);
            Glide.with(this).load(R.drawable.rain).into(iv_bground);
            //gif파일 넣기(비오는 화면)
            //18시 이후, 비오는 경우 비오는밤 배경설정
        }else if(all.get(0).forecastTime()>=18 && all.get(0).weather().equals("Rain")){
            //iv_bground.setBackgroundResource(R.drawable.rainynight);
            iv_bground.setBackgroundResource(R.drawable.sunnynight);
            Glide.with(this).load(R.drawable.rain).into(iv_bground);
            //gif파일 넣기(비오는 화면)
            //18시 이전, 구름낀경우 구름낀 배경설정
        }else if(all.get(0).forecastTime()<18 && all.get(0).weather().equals("Clouds")){
            iv_bground.setBackgroundResource(R.drawable.sunnyday);
            Glide.with(this).load(R.drawable.cloud).into(iv_bground);
            //gif파일 넣기 (구름낀 화면)
            //18시 이후, 구름낀 경우 구름낀 배경설정
        }else if(all.get(0).forecastTime()>=18 && all.get(0).weather().equals("Clouds")){
            iv_bground.setBackgroundResource(R.drawable.sunnynight);
            Glide.with(this).load(R.drawable.cloud).into(iv_bground);
            //gif파일 넣기 (구름낀 화면)
        }
        //마크 이미지 설정
        if(all.get(0).weather().equals("Rain")){
            iv_weather.setBackgroundResource(R.drawable.rainmark);
        }else if(all.get(0).weather().equals("Sunny")){
            iv_weather.setBackgroundResource(R.drawable.sunnymark);
            //화창한 날씨마크 이미지 소스
        }else if(all.get(0).weather().equals("Clouds")){
            iv_weather.setBackgroundResource(R.drawable.cloudmark);
            //구름낀 날씨마크 이미지 소스
        }

        // 우산을 챙겨야 해요! visibility 설정
        if(all.get(0).weather().equals("Rain")){
            tv_umbrella.setVisibility(View.VISIBLE);
            tv_umbrella.setTextColor(Color.WHITE);
        }else if(all.get(0).weather().equals("Clouds")){
            tv_umbrella.setVisibility(View.VISIBLE);
            tv_umbrella.setText("우산을 챙겨야 할지도?");
            tv_umbrella.setTextColor(Color.WHITE);
        }else{
            tv_umbrella.setVisibility(View.INVISIBLE);
        }

        // 온도값 설정
        tv_temp.setText(String.valueOf(all.get(0).temp()+"\u00B0C"));
        tv_temp.setTextColor(Color.WHITE);
    }
}
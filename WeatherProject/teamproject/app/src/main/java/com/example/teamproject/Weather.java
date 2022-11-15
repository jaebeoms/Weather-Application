package com.example.teamproject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Weather {
    Message msg;
    ArrayList<getWeather> weatherArray = new ArrayList<getWeather>();
    getWeather getweather;
    GPS gps;
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/").addConverterFactory(GsonConverterFactory.create()).build();
    WeatherApi weatherApi;
    private final static String appKey = "6236d803b5111857a6fd9f8c518e240a";
    final String[] json = {""};
    Context context;

    public void weather(Handler handler){

        Thread th = new Thread(){

            @Override
            public void run() {


                weatherApi = retrofit.create(WeatherApi.class);

             Call<Object> getWeather = weatherApi.getWeather(36.809356,127.146623,"metric","3",appKey);

                try{
                    json[0] = new Gson().toJson(getWeather.execute().body());

//                    String result = json[0];
                    String result = json[0];

//                    Message msg = myHandler.obtainMessage(1, result);

//                    myHandler.sendMessage(msg);
                    JSONParser jsonParser = new JSONParser();

                    try {

                        JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
                        JSONArray jsonArray = (JSONArray)jsonObject.get("list");



                        for(int i = 0; i<jsonArray.size(); i++){
                            JSONObject jsobj = (JSONObject)jsonArray.get(i);
                            JSONArray jsobj2 = (JSONArray) jsobj.get("weather");
                            JSONObject jsobj3 = (JSONObject)jsobj.get("main");
                            JSONObject aa21 = (JSONObject) jsobj2.get(0);
                            String weathermain = (String)aa21.get("main");
                            String dttxt = (String)jsobj.get("dt_txt");
                            String[] aaa = dttxt.split(" ");
                            aaa = aaa[1].split(":");
                            int datenum = Integer.valueOf(aaa[0]);
                            double temp= (double)jsobj3.get("temp");
                            getweather = new getWeather(weathermain, temp, datenum);

                            msg = new Message();
                            msg.what =i+1;
                            msg.obj = getweather;
                            handler.sendMessage(msg);
//                            weatherArray.add(getweather);

                        }

                        msg = new Message();
                        msg.what =2;
                        msg.obj = getweather;
                        handler.sendMessage(msg);

//

                    } catch (ParseException e) {
                        e.printStackTrace();

                    }

                }catch (IOException e){
                    e.printStackTrace();
                }


            }
        };
        th.start();

    }
    ArrayList<getWeather> getWeathers(){
        return weatherArray;
    }


}
package com.example.teamproject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    public static final String API_URL = "https://api.openweathermap.org/"; //저랑 같은 날씨 API를
    //쓰면 같아요. 이게 BASE_URL 이 될 수 도 있어요.

    @GET("data/2.5/forecast") //BASE_URL과 get괄호 안에를 합치면 설명서에 나와있는 주소가 돼요.
    Call<Object> getWeather(@Query("lat") double lat,@Query("lon") double lon,@Query("units") String units,@Query("cnt") String cnt, @Query("appid") String appid);
}


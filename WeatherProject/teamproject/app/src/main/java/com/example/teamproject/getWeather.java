package com.example.teamproject;

public class getWeather {
    private String weather;
    private double temp;
    private int date;

    //여기서 변경
    public getWeather(String w, double t, int d){
        this.weather = w;
        this.temp = t;
        this. date = d;
    }

    public String weather(){

        return weather;
    }
    public double temp(){

        return temp;
    }
    public int forecastTime(){

        return date;
    }

}

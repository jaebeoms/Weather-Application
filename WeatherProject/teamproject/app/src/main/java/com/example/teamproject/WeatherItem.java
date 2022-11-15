package com.example.teamproject;

public class WeatherItem {
    private static String area;
    private static String temp;
    private static String weather;

    public WeatherItem(String area,String temp,String weather){
        this.area=area;
        this.temp=temp;
        this.weather=weather;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public static String getArea() {
        return area;
    }

    public static String getTemp() {
        return temp;
    }

    public static void setWeather(String weather) {
        WeatherItem.weather = weather;
    }

    public static String getWeather() {
        return weather;
    }
}

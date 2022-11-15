package com.example.teamproject;
public class FriendItem {
    double temp;
    String hour;
    int resourceId;

    public FriendItem(int resourceId, double temp, String hour) {
        this.temp = temp;
        this.hour=hour;
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getHour() {
        return hour;
    }

    public double getTemp() {
        return temp;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}

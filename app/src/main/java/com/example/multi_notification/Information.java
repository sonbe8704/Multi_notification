package com.example.multi_notification;


import com.google.firebase.Timestamp;

public class Information {
    int hour;
    int minute;
    String name;
    Boolean onoff;
    String Id;

    public Information() {
    }

    public Information(int hour, int minute, String name, Boolean onoff, String id) {
        this.hour = hour;
        this.minute = minute;
        this.name = name;
        this.onoff = onoff;
        Id = id;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOnoff() {
        return onoff;
    }

    public void setOnoff(Boolean onoff) {
        this.onoff = onoff;
    }
}

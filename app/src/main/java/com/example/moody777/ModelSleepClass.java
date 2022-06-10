package com.example.moody777;

public class ModelSleepClass {
    int hours;
    String date;
    String mood;

    public ModelSleepClass(int hours, String date, String mood) {
        this.hours = hours;
        this.date = date;
        this.mood = mood;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }
}

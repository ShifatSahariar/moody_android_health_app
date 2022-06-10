package com.example.moody777.Calender;

import java.util.ArrayList;

public class HomeCollection {
    public String date="";
    public String TOTAL_HOURS="";
    public String SLEEP_TYPE="";

    public String AWAKE_NIGHT_TIME="";
    public String SLEEP_AWAKE_TYPE="";
    public String MOOD_YESTERDAY_WAS="";
    public String EXERCISE_TIME_YESTERDAY_WAS="";
    public String EXERCISE_TYPE_YESTERDAY_WAS="";




    public static ArrayList<HomeCollection> date_collection_arr;
    public HomeCollection(String date, String totalHours, String sleepType,
                          String awakeNightTime, String awakeType, String moodWas, String exTime ,String exType){

        this.date = date;
        this.TOTAL_HOURS = totalHours;
        this.SLEEP_TYPE = sleepType;
        this.AWAKE_NIGHT_TIME = awakeNightTime;
        this.SLEEP_AWAKE_TYPE = awakeType;
        this.MOOD_YESTERDAY_WAS = moodWas;
        this.EXERCISE_TIME_YESTERDAY_WAS = exTime;
        this.EXERCISE_TYPE_YESTERDAY_WAS = exType;


    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTOTAL_HOURS() {
        return TOTAL_HOURS;
    }

    public void setTOTAL_HOURS(String TOTAL_HOURS) {
        this.TOTAL_HOURS = TOTAL_HOURS;
    }

    public String getSLEEP_TYPE() {
        return SLEEP_TYPE;
    }

    public void setSLEEP_TYPE(String SLEEP_TYPE) {
        this.SLEEP_TYPE = SLEEP_TYPE;
    }

    public String getAWAKE_NIGHT_TIME() {
        return AWAKE_NIGHT_TIME;
    }

    public void setAWAKE_NIGHT_TIME(String AWAKE_NIGHT_TIME) {
        this.AWAKE_NIGHT_TIME = AWAKE_NIGHT_TIME;
    }

    public String getSLEEP_AWAKE_TYPE() {
        return SLEEP_AWAKE_TYPE;
    }

    public void setSLEEP_AWAKE_TYPE(String SLEEP_AWAKE_TYPE) {
        this.SLEEP_AWAKE_TYPE = SLEEP_AWAKE_TYPE;
    }

    public String getMOOD_YESTERDAY_WAS() {
        return MOOD_YESTERDAY_WAS;
    }

    public void setMOOD_YESTERDAY_WAS(String MOOD_YESTERDAY_WAS) {
        this.MOOD_YESTERDAY_WAS = MOOD_YESTERDAY_WAS;
    }

    public String getEXERCISE_TIME_YESTERDAY_WAS() {
        return EXERCISE_TIME_YESTERDAY_WAS;
    }

    public void setEXERCISE_TIME_YESTERDAY_WAS(String EXERCISE_TIME_YESTERDAY_WAS) {
        this.EXERCISE_TIME_YESTERDAY_WAS = EXERCISE_TIME_YESTERDAY_WAS;
    }

    public String getEXERCISE_TYPE_YESTERDAY_WAS() {
        return EXERCISE_TYPE_YESTERDAY_WAS;
    }

    public void setEXERCISE_TYPE_YESTERDAY_WAS(String EXERCISE_TYPE_YESTERDAY_WAS) {
        this.EXERCISE_TYPE_YESTERDAY_WAS = EXERCISE_TYPE_YESTERDAY_WAS;
    }
}

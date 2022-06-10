package com.example.moody777;

import android.provider.BaseColumns;

import java.util.ArrayList;

public class Sleep {
    String id, total_hour, date ,sleep_type ,awake_night_time,sleep_awake_type,exercise_time_was,exercise_type_was,mood_yesterday_was;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotal_hour() {
        return total_hour;
    }

    public void setTotal_hour(String total_hour) {
        this.total_hour = total_hour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSleep_type() {
        return sleep_type;
    }

    public void setSleep_type(String sleep_type) {
        this.sleep_type = sleep_type;
    }

    public String getAwake_night_time() {
        return awake_night_time;
    }

    public void setAwake_night_time(String awake_night_time) {
        this.awake_night_time = awake_night_time;
    }

    public String getSleep_awake_type() {
        return sleep_awake_type;
    }

    public void setSleep_awake_type(String sleep_awake_type) {
        this.sleep_awake_type = sleep_awake_type;
    }

    public String getExercise_time_was() {
        return exercise_time_was;
    }

    public void setExercise_time_was(String exercise_time_was) {
        this.exercise_time_was = exercise_time_was;
    }

    public String getMood_yesterday_was() {
        return mood_yesterday_was;
    }

    public void setMood_yesterday_was(String mood_yesterday_was) {
        this.mood_yesterday_was = mood_yesterday_was;
    }

    public String getExercise_type_was() {
        return exercise_type_was;
    }

    public void setExercise_type_was(String exercise_type_was) {
        this.exercise_type_was = exercise_type_was;
    }
}

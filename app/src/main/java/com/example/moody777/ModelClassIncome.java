package com.example.moody777;

public class ModelClassIncome {
    //int id;
    String name;
    int value;
    String time;




    public ModelClassIncome(String name, int value, String time) {

        this.name = name;
        this.value = value;
        this.time = time;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

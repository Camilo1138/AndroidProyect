package com.example.myapplication;

public class ActividadFisica {
    private String date;
    private String activityType;
    private int duration;
    private String timeOfDay;
//7
    public ActividadFisica(String date, String activityType, int duration, String timeOfDay) {
        this.date = date;
        this.activityType = activityType;
        this.duration = duration;
        this.timeOfDay = timeOfDay;
    }

    public String getDate() { return date; }
    public String getActivityType() { return activityType; }
    public int getDuration() { return duration; }
    public String getTimeOfDay() { return timeOfDay; }
}


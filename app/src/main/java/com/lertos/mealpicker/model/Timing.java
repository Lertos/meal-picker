package com.lertos.mealpicker.model;

public class Timing {

    private int timeInMinutes;

    public Timing(int timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

    public int getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(int timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

    public String getDisplayTimeBasedOnSetting() {
        return "";
    }

    public String getDisplayTimeInMinutes() {
        return "";
    }

    public String getDisplayTimeInHoursAndMinutes() {
        return "";
    }
}

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
        //TODO: Need to check the setting for how the time should be displayed once it's implemented on Settings page
        return getDisplayTimeInMinutes();
    }

    public String getDisplayTimeInMinutes() {
        StringBuilder sb = new StringBuilder();

        sb.append(timeInMinutes);
        sb.append(" ");
        sb.append("mins");

        return sb.toString();
    }

    public String getDisplayTimeInHoursAndMinutes() {
        StringBuilder sb = new StringBuilder();

        int minutes = timeInMinutes;
        int hours = (int) Math.floor(timeInMinutes / 60);

        minutes = minutes - (hours * 60);

        if (hours > 0) {
            sb.append(hours);
            sb.append(" ");
            sb.append("hrs");
        }

        sb.append(minutes);
        sb.append(" ");
        sb.append("mins");

        return sb.toString();
    }
}

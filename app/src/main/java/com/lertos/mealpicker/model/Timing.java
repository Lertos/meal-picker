package com.lertos.mealpicker.model;

import java.io.Serializable;

public class Timing implements Serializable {

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
        if (DataManager.getInstance().getSettings().useMinutesOnly())
            return getDisplayTimeInMinutes();
        return getDisplayTimeInHoursAndMinutes();
    }

    public String getDisplayTimeInMinutes() {
        StringBuilder sb = new StringBuilder();

        sb.append(timeInMinutes);
        sb.append(" ");

        if (timeInMinutes == 1)
            sb.append("min");
        else
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

            if (hours == 1)
                sb.append("hr");
            else
                sb.append("hrs");
        }

        if (minutes == 0)
            return sb.toString();

        sb.append(" ");
        sb.append(minutes);
        sb.append(" ");

        if (minutes == 1)
            sb.append("min");
        else
            sb.append("mins");

        return sb.toString();
    }
}

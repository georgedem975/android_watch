package com.example.watcher.clock;

/**
 * the Time class stores the current time
 * @author georgedem975
 */

public class Time {
    private int hour;

    private int minute;

    private int second;

    public Time() { }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public int getSecond() {
        return this.second;
    }

    public void setHour(int hour) {
        if (hour > 12) {
            hour -= 12;
        }
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}

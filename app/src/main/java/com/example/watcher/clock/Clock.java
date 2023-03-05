package com.example.watcher.clock;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * the class was created to get the current time
 *
 * @author georgedem975
 */

public class Clock {
    private Time time;

    public Clock() { }

    /**
     * the method determines the current time and returns it as an object of the Time class
     *
     * @param calendar
     * @return time
     */
    public Time getHourMinuteSecond(Calendar calendar) {
        if (time == null) {
            time = new Time();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
        String currentTime = simpleDateFormat.format(calendar.getTime());

        this.time.setHour(Integer.parseInt(currentTime.substring(0, 2)));
        this.time.setMinute(Integer.parseInt(currentTime.substring(3, 5)));
        this.time.setSecond(Integer.parseInt(currentTime.substring(6, 8)));

        return time;
    }
}

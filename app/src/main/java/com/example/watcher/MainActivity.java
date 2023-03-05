package com.example.watcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.watcher.clock.Clock;
import com.example.watcher.clock.Time;
import com.example.watcher.view.RegularHoursView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private RegularHoursView regularHoursView;

    private Clock clock;

    private Time time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        regularHoursView = findViewById(R.id.rhv);

        clock = new Clock();

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Calendar calendar = Calendar.getInstance();
                                time = clock.getHourMinuteSecond(calendar);
                                regularHoursView.setTurningTheSecondHandAnimated(-time.getSecond() * 6);
                                regularHoursView.setTurningTheMinuteHandAnimated(-time.getMinute() * 6);
                                regularHoursView.setClockwiseRotationAnimated(-time.getHour() * 6 * 5 - regularHoursView.getTimeInterval() * 6 * 5 - time.getMinute()/2);
                            }
                        });
                    }
                }
                catch (Exception e) {

                }
            }
        };

        thread.start();
    }
}
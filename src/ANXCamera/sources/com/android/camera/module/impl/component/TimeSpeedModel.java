package com.android.camera.module.impl.component;

import java.util.List;

public class TimeSpeedModel {
    long duration;
    double speed;

    public TimeSpeedModel(long j, double d) {
        this.duration = j;
        this.speed = d;
    }

    public static int calculateRealTime(int i, double d) {
        return (int) ((1.0d * ((double) i)) / d);
    }

    public static long calculateRealTime(long j, double d) {
        return (long) ((1.0d * ((double) j)) / d);
    }

    public static long calculateRealTime(List<TimeSpeedModel> list) {
        if (list == null || list.size() <= 0) {
            return 0;
        }
        int i = 0;
        for (TimeSpeedModel next : list) {
            i = (int) (((long) i) + calculateRealTime(next.duration, next.speed));
        }
        return (long) i;
    }

    public long getDuration() {
        return this.duration;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setDuration(int i) {
        this.duration = (long) i;
    }

    public void setSpeed(float f) {
        this.speed = (double) f;
    }
}

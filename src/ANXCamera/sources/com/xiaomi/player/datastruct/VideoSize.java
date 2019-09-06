package com.xiaomi.player.datastruct;

public class VideoSize {
    public float video_height;
    public float video_width;

    public VideoSize(float f2, float f3) {
        this.video_height = f2;
        this.video_width = f3;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("video_height=");
        sb.append(this.video_height);
        sb.append(",");
        sb.append("video_width=");
        sb.append(this.video_width);
        return sb.toString();
    }
}

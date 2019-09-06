package com.xiaomi.rendermanager;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import com.xiaomi.rendermanager.videoRender.VideoStreamsView;
import com.xiaomi.rendermanager.videoRender.VideoStreamsView.RenderModel;

public class RenderManager {
    private static final String TAG = "RenderManager";
    private Context mContext;

    private native void constructRenderManagerJni(Context context);

    private native void destructRenderManagerJni();

    private native VideoStreamsView getRenderJni(String str);

    private native boolean setResolutionJni(String str, int i, int i2);

    public boolean bindRenderWithStream(VideoStreamsView videoStreamsView, String str, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("binding render with user:");
        sb.append(str);
        String sb2 = sb.toString();
        String str2 = TAG;
        Log.i(str2, sb2);
        if (this.mContext == null) {
            Log.e(str2, "bindRenderWithStream error, please init the engine first");
            return false;
        } else if (!videoStreamsView.bindRenderWithStream(str, z)) {
            Log.e(str2, "bind render failed");
            return false;
        } else {
            Log.i(str2, "bind render succeeded");
            return true;
        }
    }

    public boolean constructRenderManager(Context context) {
        String str = TAG;
        Log.d(str, "construct RenderManager...");
        if (this.mContext != null) {
            Log.d(str, "construct error, please destroy the render manager first");
            return false;
        }
        this.mContext = context;
        constructRenderManagerJni(context);
        return true;
    }

    public VideoStreamsView createRender(Point point) {
        StringBuilder sb = new StringBuilder();
        sb.append("creating render with display size:");
        sb.append(point);
        String sb2 = sb.toString();
        String str = TAG;
        Log.i(str, sb2);
        Context context = this.mContext;
        if (context != null) {
            return new VideoStreamsView(context, point);
        }
        Log.e(str, "createRender error, please init the engine first");
        return null;
    }

    public void destroyRender(VideoStreamsView videoStreamsView) {
        String str = TAG;
        Log.i(str, "destorying render...");
        if (this.mContext == null) {
            Log.e(str, "destroyRender error, please init the engine first");
            return;
        }
        videoStreamsView.destoryNativeRender();
        Log.i(str, "destory render done");
    }

    public boolean destructRenderManager() {
        String str = TAG;
        Log.d(str, "destruct RenderManager...");
        if (this.mContext == null) {
            Log.d(str, "destruct error, please create the engine first");
            return false;
        }
        this.mContext = null;
        destructRenderManagerJni();
        return true;
    }

    public VideoStreamsView getRender(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("getting render for user:");
        sb.append(str);
        String sb2 = sb.toString();
        String str2 = TAG;
        Log.i(str2, sb2);
        if (this.mContext != null) {
            return getRenderJni(str);
        }
        Log.e(str2, "getRender error, please init the engine first");
        return null;
    }

    public void setRenderModel(VideoStreamsView videoStreamsView, RenderModel renderModel) {
        StringBuilder sb = new StringBuilder();
        sb.append("setting render model:");
        sb.append(renderModel);
        String sb2 = sb.toString();
        String str = TAG;
        Log.i(str, sb2);
        if (this.mContext == null) {
            Log.e(str, "setRenderModel error, please init the engine first");
            return;
        }
        videoStreamsView.setRenderModel(renderModel);
        Log.i(str, "set render model done");
    }

    public boolean setResolution(String str, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append("set resolution for user:");
        sb.append(str);
        sb.append(",height:");
        sb.append(i2);
        sb.append(",width:");
        sb.append(i);
        String sb2 = sb.toString();
        String str2 = TAG;
        Log.i(str2, sb2);
        if (this.mContext == null) {
            Log.e(str2, "setResolution error, please init the engine first");
            return false;
        } else if (!setResolutionJni(str, i, i2)) {
            Log.e(str2, "set resolution failed");
            return false;
        } else {
            Log.i(str2, "set resolution suceeded");
            return true;
        }
    }

    public void setShiftUp(VideoStreamsView videoStreamsView, float f2, float f3, float f4, float f5, float f6) {
        String str = TAG;
        Log.i(str, "Set the render shiftup value.");
        if (this.mContext == null) {
            Log.e(str, "set the render shiftup error, please init the engine first");
            return;
        }
        videoStreamsView.setShiftUpInternal(f2, f3, f4, f5, f6);
        Log.i(str, "Set the render shiftup succeeded");
    }

    public boolean unbindRenderWithStream(VideoStreamsView videoStreamsView) {
        String str = TAG;
        Log.i(str, "unbinding render...");
        if (this.mContext == null) {
            Log.e(str, "unbindRenderWithStream error, please init the engine first");
            return false;
        } else if (!videoStreamsView.unbindRenderWithStream()) {
            Log.e(str, "unbind render failed");
            return false;
        } else {
            Log.i(str, "unbind render succeeded");
            return true;
        }
    }
}

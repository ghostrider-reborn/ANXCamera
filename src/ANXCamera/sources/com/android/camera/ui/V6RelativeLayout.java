package com.android.camera.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class V6RelativeLayout extends RelativeLayout implements Rotatable, V6FunctionUI, V6Animation {
    public V6RelativeLayout(Context context) {
        super(context);
    }

    public V6RelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void animateIn(Runnable runnable) {
    }

    public void animateIn(Runnable runnable, int i) {
        animateIn(runnable);
    }

    public void animateIn(Runnable runnable, int i, boolean z) {
    }

    public void animateOut(Runnable runnable) {
    }

    public void animateOut(Runnable runnable, int i) {
        animateOut(runnable);
    }

    public void animateOut(Runnable runnable, int i, boolean z) {
    }

    public void enableControls(boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof V6FunctionUI) {
                ((V6FunctionUI) childAt).enableControls(z);
            }
        }
    }

    public View findChildrenById(int i) {
        return findViewById(i);
    }

    public void onCameraOpen() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof V6FunctionUI) {
                ((V6FunctionUI) childAt).onCameraOpen();
            }
        }
    }

    public void onCreate() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof V6FunctionUI) {
                ((V6FunctionUI) childAt).onCreate();
            }
        }
    }

    public void onPause() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof V6FunctionUI) {
                ((V6FunctionUI) childAt).onPause();
            }
        }
    }

    public void onResume() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof V6FunctionUI) {
                ((V6FunctionUI) childAt).onResume();
            }
        }
    }

    public void setOrientation(int i, boolean z) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt instanceof Rotatable) {
                ((Rotatable) childAt).setOrientation(i, z);
            }
        }
    }
}

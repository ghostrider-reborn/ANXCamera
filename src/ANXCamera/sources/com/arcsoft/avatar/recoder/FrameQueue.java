package com.arcsoft.avatar.recoder;

import android.opengl.GLES30;
import com.arcsoft.avatar.gl.GLFramebuffer;
import com.arcsoft.avatar.util.CodecLog;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FrameQueue {

    /* renamed from: a  reason: collision with root package name */
    private static final String f34a = FrameQueue.class.getSimpleName();

    /* renamed from: b  reason: collision with root package name */
    private FrameItem f35b = null;
    private FrameItem c = null;
    private List<FrameItem> d = new ArrayList();
    private Queue<FrameItem> e = new LinkedList();
    private boolean f;

    public void addEmptyFrameForConsumer() {
        if (this.c != null) {
            this.d.add(this.c);
            this.c = null;
        }
    }

    public void addFrameForProducer() {
        if (this.f35b != null) {
            this.e.offer(this.f35b);
            this.f35b = null;
        }
    }

    public void deleteSync(FrameItem frameItem) {
        try {
            if (0 != frameItem.f33a) {
                String str = f34a;
                CodecLog.d(str, "deleteSync delete_a_sync : " + frameItem.f33a);
                GLES30.glDeleteSync(frameItem.f33a);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            String str2 = f34a;
            CodecLog.e(str2, "deleteSync meet error : " + e2.getMessage());
        } catch (Throwable th) {
            frameItem.f33a = 0;
            throw th;
        }
        frameItem.f33a = 0;
    }

    public FrameItem getFrameForConsumer() {
        if (this.c != null) {
            return this.c;
        }
        if (this.e.isEmpty()) {
            return null;
        }
        this.c = this.e.poll();
        return this.c;
    }

    public FrameItem getFrameForProducer() {
        if (this.f35b != null) {
            return this.f35b;
        }
        if (!this.d.isEmpty()) {
            this.f35b = this.d.remove(0);
        } else if (this.e.isEmpty()) {
            return null;
        } else {
            this.f35b = this.e.poll();
        }
        return this.f35b;
    }

    public void init(int i, int i2, int i3, boolean z) {
        unInit();
        for (int i4 = 0; i4 < i; i4++) {
            FrameItem frameItem = new FrameItem();
            frameItem.mIsEmpty = true;
            frameItem.mIsInited = true;
            frameItem.mFrameIndex = i4;
            frameItem.mFramebuffer = new GLFramebuffer();
            frameItem.mFramebuffer.init(i2, i3, z);
            this.d.add(frameItem);
        }
        this.f = true;
    }

    public boolean isIsInited() {
        return this.f;
    }

    public int queueSize() {
        return this.e.size();
    }

    public void unInit() {
        if (!(this.f35b == null || this.f35b.mFramebuffer == null)) {
            this.f35b.mFramebuffer.unInit();
            deleteSync(this.f35b);
            this.f35b.mFramebuffer = null;
            this.f35b = null;
        }
        if (!(this.c == null || this.c.mFramebuffer == null)) {
            this.c.mFramebuffer.unInit();
            deleteSync(this.c);
            this.c.mFramebuffer = null;
            this.c = null;
        }
        if (!this.d.isEmpty()) {
            for (FrameItem next : this.d) {
                if (next.mFramebuffer != null) {
                    next.mFramebuffer.unInit();
                    deleteSync(next);
                    next.mFramebuffer = null;
                }
            }
        }
        this.d.clear();
        while (!this.e.isEmpty()) {
            FrameItem poll = this.e.poll();
            if (!(poll == null || poll.mFramebuffer == null)) {
                poll.mFramebuffer.unInit();
                deleteSync(poll);
                poll.mFramebuffer = null;
            }
        }
        this.e.clear();
        this.f = false;
    }
}

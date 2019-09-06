package com.arcsoft.avatar.recoder;

import android.opengl.GLES30;
import com.arcsoft.avatar.gl.GLFramebuffer;
import com.arcsoft.avatar.util.CodecLog;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FrameQueue {

    /* renamed from: a reason: collision with root package name */
    private static final String f103a = "FrameQueue";

    /* renamed from: b reason: collision with root package name */
    private FrameItem f104b = null;

    /* renamed from: c reason: collision with root package name */
    private FrameItem f105c = null;

    /* renamed from: d reason: collision with root package name */
    private List<FrameItem> f106d = new ArrayList();

    /* renamed from: e reason: collision with root package name */
    private Queue<FrameItem> f107e = new LinkedList();

    /* renamed from: f reason: collision with root package name */
    private boolean f108f;

    public void addEmptyFrameForConsumer() {
        FrameItem frameItem = this.f105c;
        if (frameItem != null) {
            this.f106d.add(frameItem);
            this.f105c = null;
        }
    }

    public void addFrameForProducer() {
        FrameItem frameItem = this.f104b;
        if (frameItem != null) {
            this.f107e.offer(frameItem);
            this.f104b = null;
        }
    }

    public void deleteSync(FrameItem frameItem) {
        try {
            if (0 != frameItem.f102a) {
                String str = f103a;
                StringBuilder sb = new StringBuilder();
                sb.append("deleteSync delete_a_sync : ");
                sb.append(frameItem.f102a);
                CodecLog.d(str, sb.toString());
                GLES30.glDeleteSync(frameItem.f102a);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            String str2 = f103a;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("deleteSync meet error : ");
            sb2.append(e2.getMessage());
            CodecLog.e(str2, sb2.toString());
        } catch (Throwable th) {
            frameItem.f102a = 0;
            throw th;
        }
        frameItem.f102a = 0;
    }

    public FrameItem getFrameForConsumer() {
        FrameItem frameItem = this.f105c;
        if (frameItem != null) {
            return frameItem;
        }
        if (this.f107e.isEmpty()) {
            return null;
        }
        this.f105c = (FrameItem) this.f107e.poll();
        return this.f105c;
    }

    public FrameItem getFrameForProducer() {
        FrameItem frameItem = this.f104b;
        if (frameItem != null) {
            return frameItem;
        }
        if (!this.f106d.isEmpty()) {
            this.f104b = (FrameItem) this.f106d.remove(0);
        } else if (this.f107e.isEmpty()) {
            return null;
        } else {
            this.f104b = (FrameItem) this.f107e.poll();
        }
        return this.f104b;
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
            this.f106d.add(frameItem);
        }
        this.f108f = true;
    }

    public boolean isIsInited() {
        return this.f108f;
    }

    public int queueSize() {
        return this.f107e.size();
    }

    public void unInit() {
        FrameItem frameItem = this.f104b;
        if (frameItem != null) {
            GLFramebuffer gLFramebuffer = frameItem.mFramebuffer;
            if (gLFramebuffer != null) {
                gLFramebuffer.unInit();
                deleteSync(this.f104b);
                this.f104b.mFramebuffer = null;
                this.f104b = null;
            }
        }
        FrameItem frameItem2 = this.f105c;
        if (frameItem2 != null) {
            GLFramebuffer gLFramebuffer2 = frameItem2.mFramebuffer;
            if (gLFramebuffer2 != null) {
                gLFramebuffer2.unInit();
                deleteSync(this.f105c);
                this.f105c.mFramebuffer = null;
                this.f105c = null;
            }
        }
        if (!this.f106d.isEmpty()) {
            for (FrameItem frameItem3 : this.f106d) {
                GLFramebuffer gLFramebuffer3 = frameItem3.mFramebuffer;
                if (gLFramebuffer3 != null) {
                    gLFramebuffer3.unInit();
                    deleteSync(frameItem3);
                    frameItem3.mFramebuffer = null;
                }
            }
        }
        this.f106d.clear();
        while (!this.f107e.isEmpty()) {
            FrameItem frameItem4 = (FrameItem) this.f107e.poll();
            if (frameItem4 != null) {
                GLFramebuffer gLFramebuffer4 = frameItem4.mFramebuffer;
                if (gLFramebuffer4 != null) {
                    gLFramebuffer4.unInit();
                    deleteSync(frameItem4);
                    frameItem4.mFramebuffer = null;
                }
            }
        }
        this.f107e.clear();
        this.f108f = false;
    }
}

package com.miui.extravideo.common;

import android.media.MediaCodec.BufferInfo;
import android.media.MediaExtractor;
import android.media.MediaMuxer;
import java.io.IOException;
import java.nio.ByteBuffer;

public class MediaUtils {
    private static void addVideoToTrack(MediaExtractor mediaExtractor, MediaMuxer mediaMuxer, int i) {
        ByteBuffer allocate = ByteBuffer.allocate(mediaExtractor.getTrackFormat(mediaExtractor.getSampleTrackIndex()).getInteger("max-input-size"));
        allocate.position(0);
        BufferInfo bufferInfo = new BufferInfo();
        readBufferByExtractor(allocate, bufferInfo, mediaExtractor);
        while (bufferInfo.size > 0) {
            allocate.position(0);
            mediaMuxer.writeSampleData(i, allocate, bufferInfo);
            if ((bufferInfo.flags & 4) == 0) {
                mediaExtractor.advance();
                allocate.position(0);
                readBufferByExtractor(allocate, bufferInfo, mediaExtractor);
            } else {
                return;
            }
        }
    }

    public static long computePresentationTime(int i, int i2) {
        return (long) (((i * 1000000) / i2) + 132);
    }

    private static MediaExtractor generateExtractor(String str) throws IOException {
        MediaExtractor mediaExtractor = new MediaExtractor();
        mediaExtractor.setDataSource(str);
        int i = 0;
        while (true) {
            if (i >= mediaExtractor.getTrackCount()) {
                break;
            } else if (mediaExtractor.getTrackFormat(i).getString("mime").startsWith("video/")) {
                mediaExtractor.selectTrack(i);
                break;
            } else {
                i++;
            }
        }
        return mediaExtractor;
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r1v0, types: [android.media.MediaMuxer] */
    /* JADX WARNING: type inference failed for: r0v1, types: [android.media.MediaExtractor] */
    /* JADX WARNING: type inference failed for: r5v1, types: [android.media.MediaExtractor] */
    /* JADX WARNING: type inference failed for: r0v2, types: [android.media.MediaMuxer] */
    /* JADX WARNING: type inference failed for: r1v1 */
    /* JADX WARNING: type inference failed for: r0v3 */
    /* JADX WARNING: type inference failed for: r5v3 */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r1v3, types: [android.media.MediaMuxer] */
    /* JADX WARNING: type inference failed for: r5v4 */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r5v5 */
    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r5v6 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r0v7, types: [android.media.MediaExtractor] */
    /* JADX WARNING: type inference failed for: r0v8 */
    /* JADX WARNING: type inference failed for: r0v9 */
    /* JADX WARNING: type inference failed for: r0v10 */
    /* JADX WARNING: type inference failed for: r0v11 */
    /* JADX WARNING: type inference failed for: r0v12 */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r5v10 */
    /* JADX WARNING: type inference failed for: r0v13 */
    /* JADX WARNING: type inference failed for: r0v14 */
    /* JADX WARNING: type inference failed for: r0v15 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r5v4
  assigns: []
  uses: []
  mth insns count: 69
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0066  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0075  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0082  */
    /* JADX WARNING: Unknown variable types count: 10 */
    public static int mixVideo(String str, String str2, String str3, int i) {
        MediaExtractor mediaExtractor;
        ? r1;
        ? r0;
        ? r5;
        ? r02;
        ? r52;
        ? r03;
        ? r04;
        ? r05 = 0;
        try {
            ? mediaMuxer = new MediaMuxer(str, 0);
            try {
                mediaMuxer.setOrientationHint(i);
                mediaExtractor = generateExtractor(str2);
            } catch (Exception e2) {
                e = e2;
                mediaExtractor = null;
                r52 = 0;
                r02 = mediaMuxer;
                r5 = r52;
                try {
                    e.printStackTrace();
                    if (r02 != 0) {
                        r02.stop();
                        r02.release();
                    }
                    if (mediaExtractor != null) {
                        mediaExtractor.release();
                    }
                    if (r5 != 0) {
                        r5.release();
                    }
                    return -1;
                } catch (Throwable th) {
                    th = th;
                    r1 = r02;
                    r0 = r5;
                    if (r1 != 0) {
                        r1.stop();
                        r1.release();
                    }
                    if (mediaExtractor != null) {
                        mediaExtractor.release();
                    }
                    if (r0 != 0) {
                        r0.release();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                mediaExtractor = null;
                r0 = r05;
                r1 = mediaMuxer;
                if (r1 != 0) {
                }
                if (mediaExtractor != null) {
                }
                if (r0 != 0) {
                }
                throw th;
            }
            try {
                r03 = r05;
                r04 = r05;
                ? generateExtractor = generateExtractor(str3);
                int addTrack = mediaMuxer.addTrack(mediaExtractor.getTrackFormat(mediaExtractor.getSampleTrackIndex()));
                int addTrack2 = mediaMuxer.addTrack(generateExtractor.getTrackFormat(generateExtractor.getSampleTrackIndex()));
                mediaMuxer.start();
                addVideoToTrack(mediaExtractor, mediaMuxer, addTrack);
                addVideoToTrack(generateExtractor, mediaMuxer, addTrack2);
                r03 = generateExtractor;
                r04 = generateExtractor;
                mediaMuxer.stop();
                mediaMuxer.release();
                if (mediaExtractor != null) {
                    mediaExtractor.release();
                }
                if (generateExtractor == 0) {
                    return addTrack2;
                }
                generateExtractor.release();
                return addTrack2;
            } catch (Exception e3) {
                e = e3;
                r52 = r03;
                r02 = mediaMuxer;
                r5 = r52;
                e.printStackTrace();
                if (r02 != 0) {
                }
                if (mediaExtractor != null) {
                }
                if (r5 != 0) {
                }
                return -1;
            } catch (Throwable th3) {
                th = th3;
                r1 = mediaMuxer;
                r0 = r04;
                if (r1 != 0) {
                }
                if (mediaExtractor != null) {
                }
                if (r0 != 0) {
                }
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            mediaExtractor = null;
            r5 = 0;
            r02 = r05;
            e.printStackTrace();
            if (r02 != 0) {
            }
            if (mediaExtractor != null) {
            }
            if (r5 != 0) {
            }
            return -1;
        } catch (Throwable th4) {
            th = th4;
            mediaExtractor = null;
            r1 = 0;
            r0 = r05;
            if (r1 != 0) {
            }
            if (mediaExtractor != null) {
            }
            if (r0 != 0) {
            }
            throw th;
        }
    }

    private static void readBufferByExtractor(ByteBuffer byteBuffer, BufferInfo bufferInfo, MediaExtractor mediaExtractor) {
        bufferInfo.size = mediaExtractor.readSampleData(byteBuffer, 0);
        bufferInfo.presentationTimeUs = mediaExtractor.getSampleTime();
        bufferInfo.flags = mediaExtractor.getSampleFlags();
        bufferInfo.offset = 0;
    }
}

package com.miui.extravideo.common;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaMuxer;
import java.io.IOException;
import java.nio.ByteBuffer;

public class MediaUtils {
    private static void addVideoToTrack(MediaExtractor mediaExtractor, MediaMuxer mediaMuxer, int i) {
        ByteBuffer allocate = ByteBuffer.allocate(mediaExtractor.getTrackFormat(mediaExtractor.getSampleTrackIndex()).getInteger("max-input-size"));
        allocate.position(0);
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
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
        return (long) (132 + ((i * 1000000) / i2));
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

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0044, code lost:
        if (r4 != null) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0046, code lost:
        r4.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0076, code lost:
        if (r4 == null) goto L_0x0079;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0079, code lost:
        return r6;
     */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0073  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x007e  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0086  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x008b  */
    public static int mixVideo(String str, String str2, String str3, int i) {
        MediaExtractor mediaExtractor;
        MediaExtractor mediaExtractor2;
        MediaMuxer mediaMuxer;
        int i2;
        MediaMuxer mediaMuxer2 = null;
        try {
            mediaMuxer = new MediaMuxer(str, 0);
            try {
                mediaMuxer.setOrientationHint(i);
                mediaExtractor2 = generateExtractor(str2);
            } catch (Exception e) {
                e = e;
                mediaExtractor2 = null;
                mediaExtractor = null;
                mediaMuxer2 = mediaMuxer;
                i2 = -1;
                try {
                    e.printStackTrace();
                    if (mediaMuxer2 != null) {
                    }
                    if (mediaExtractor2 != null) {
                    }
                } catch (Throwable th) {
                    th = th;
                    mediaMuxer = mediaMuxer2;
                    if (mediaMuxer != null) {
                    }
                    if (mediaExtractor2 != null) {
                    }
                    if (mediaExtractor != null) {
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                mediaExtractor2 = null;
                mediaExtractor = null;
                if (mediaMuxer != null) {
                }
                if (mediaExtractor2 != null) {
                }
                if (mediaExtractor != null) {
                }
                throw th;
            }
            try {
                mediaExtractor = generateExtractor(str3);
                try {
                    int addTrack = mediaMuxer.addTrack(mediaExtractor2.getTrackFormat(mediaExtractor2.getSampleTrackIndex()));
                    i2 = mediaMuxer.addTrack(mediaExtractor.getTrackFormat(mediaExtractor.getSampleTrackIndex()));
                    mediaMuxer.start();
                    addVideoToTrack(mediaExtractor2, mediaMuxer, addTrack);
                    addVideoToTrack(mediaExtractor, mediaMuxer, i2);
                    mediaMuxer.stop();
                    mediaMuxer.release();
                    if (mediaExtractor2 != null) {
                        mediaExtractor2.release();
                    }
                } catch (Exception e2) {
                    e = e2;
                    mediaMuxer2 = mediaMuxer;
                    i2 = -1;
                    e.printStackTrace();
                    if (mediaMuxer2 != null) {
                    }
                    if (mediaExtractor2 != null) {
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (mediaMuxer != null) {
                    }
                    if (mediaExtractor2 != null) {
                    }
                    if (mediaExtractor != null) {
                    }
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                mediaExtractor = null;
                mediaMuxer2 = mediaMuxer;
                i2 = -1;
                e.printStackTrace();
                if (mediaMuxer2 != null) {
                }
                if (mediaExtractor2 != null) {
                }
            } catch (Throwable th4) {
                th = th4;
                mediaExtractor = null;
                if (mediaMuxer != null) {
                }
                if (mediaExtractor2 != null) {
                }
                if (mediaExtractor != null) {
                }
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            mediaExtractor2 = null;
            mediaExtractor = null;
            i2 = -1;
            e.printStackTrace();
            if (mediaMuxer2 != null) {
                mediaMuxer2.stop();
                mediaMuxer2.release();
            }
            if (mediaExtractor2 != null) {
                mediaExtractor2.release();
            }
        } catch (Throwable th5) {
            th = th5;
            mediaExtractor2 = null;
            mediaExtractor = null;
            mediaMuxer = null;
            if (mediaMuxer != null) {
                mediaMuxer.stop();
                mediaMuxer.release();
            }
            if (mediaExtractor2 != null) {
                mediaExtractor2.release();
            }
            if (mediaExtractor != null) {
                mediaExtractor.release();
            }
            throw th;
        }
    }

    private static void readBufferByExtractor(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo, MediaExtractor mediaExtractor) {
        bufferInfo.size = mediaExtractor.readSampleData(byteBuffer, 0);
        bufferInfo.presentationTimeUs = mediaExtractor.getSampleTime();
        bufferInfo.flags = mediaExtractor.getSampleFlags();
        bufferInfo.offset = 0;
    }
}

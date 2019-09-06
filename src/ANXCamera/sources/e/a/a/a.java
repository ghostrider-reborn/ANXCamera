package e.a.a;

import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import com.ss.android.medialib.audio.AudioDataProcessThread.OnProcessDataListener;

/* compiled from: AudioRecorderInterface */
public interface a extends OnProcessDataListener {
    int addPCMData(byte[] bArr, int i);

    int closeWavFile(boolean z);

    @RestrictTo({Scope.LIBRARY})
    int initAudioConfig(int i, int i2);

    int initWavFile(int i, int i2, double d2);

    void lackPermission();

    int onProcessData(byte[] bArr, int i);

    void recordStatus(boolean z);
}

package com.android.camera;

import android.util.Log;
import java.io.IOException;
import miui.app.backup.BackupMeta;
import miui.app.backup.FullBackupAgent;

public class CameraBackupAgent extends FullBackupAgent {
    private static final String TAG = "CameraBackupAgent";

    /* access modifiers changed from: protected */
    public int onRestoreEnd(BackupMeta backupMeta) throws IOException {
        Log.d(TAG, "onRestoreEnd: update watermark for vendor");
        Util.generateWatermark2File();
        return CameraBackupAgent.super.onRestoreEnd(backupMeta);
    }
}

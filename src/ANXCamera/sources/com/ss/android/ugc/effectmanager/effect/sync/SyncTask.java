package com.ss.android.ugc.effectmanager.effect.sync;

import android.support.annotation.NonNull;
import com.ss.android.ugc.effectmanager.common.task.ExceptionResult;

public class SyncTask<T> {
    private boolean mCanceled;
    private SyncTaskListener<T> mSyncTaskListener;

    public void cancel() {
        this.mCanceled = true;
    }

    public void execute() {
    }

    public boolean isCanceled() {
        return this.mCanceled;
    }

    public void onFailed(SyncTask<T> syncTask, ExceptionResult exceptionResult) {
        if (this.mSyncTaskListener != null) {
            this.mSyncTaskListener.onFailed(syncTask, exceptionResult);
        }
    }

    public void onFinally(SyncTask<T> syncTask) {
        if (this.mSyncTaskListener != null) {
            this.mSyncTaskListener.onFinally(syncTask);
        }
    }

    public void onResponse(SyncTask<T> syncTask, T t) {
        if (this.mSyncTaskListener != null) {
            this.mSyncTaskListener.onResponse(syncTask, t);
        }
    }

    public void onStart(SyncTask<T> syncTask) {
        if (this.mSyncTaskListener != null) {
            this.mSyncTaskListener.onStart(syncTask);
        }
    }

    public void setListener(@NonNull SyncTaskListener<T> syncTaskListener) {
        this.mSyncTaskListener = syncTaskListener;
    }
}

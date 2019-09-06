package com.android.camera.fragment.vv;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.camera.ActivityBase;
import com.android.camera.Camera;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.constant.GlobalConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.observeable.RxData.DataWrap;
import com.android.camera.data.observeable.VMProcessing;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.fragment.bottom.BottomAnimationConfig;
import com.android.camera.log.Log;
import com.android.camera.module.LiveModuleSubVV;
import com.android.camera.module.Module;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol.CameraAction;
import com.android.camera.protocol.ModeProtocol.HandleBackTrace;
import com.android.camera.protocol.ModeProtocol.LiveConfigVV;
import com.android.camera.protocol.ModeProtocol.LiveVVProcess;
import com.android.camera.protocol.ModeProtocol.ModeCoordinator;
import com.android.camera.statistic.CameraStat;
import com.android.camera.statistic.CameraStatUtil;
import com.android.camera.ui.CameraSnapView;
import com.android.camera.ui.CameraSnapView.SnapListener;
import com.android.camera.ui.TextureVideoView;
import com.android.camera.ui.TextureVideoView.MediaPlayerCallback;
import com.android.camera.ui.vv.VVProgressView;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FragmentVVProcess extends BaseFragment implements OnClickListener, HandleBackTrace, LiveVVProcess, SnapListener {
    private static final String TAG = "FragmentVVProcess";
    private ViewGroup mBottomActionView;
    private ViewGroup mBottomLayout;
    private CameraSnapView mCameraSnapView;
    private ProgressBar mCombineProgress;
    private ProgressBar mConcatProgress;
    private int mCurrentIndex;
    private List<Long> mDurationList;
    /* access modifiers changed from: private */
    public AlertDialog mExitDialog;
    private Handler mHandler;
    private boolean mIsIntentAction;
    private TextView mLandscapeHint;
    private boolean mPaused;
    private boolean mPendingShare;
    private ImageView mPreviewBack;
    private ImageView mPreviewCombine;
    private FrameLayout mPreviewLayout;
    private ImageView mPreviewNext;
    private ImageView mPreviewPrevious;
    private ImageView mPreviewShare;
    /* access modifiers changed from: private */
    public ImageView mPreviewStart;
    private VVProgressView mProgressView;
    /* access modifiers changed from: private */
    public AlertDialog mReverseDialog;
    private View mRootView;
    private ContentValues mSaveContentValues;
    private Uri mSavedUri;
    private ImageView mSegmentPreview;
    private ImageView mSegmentReverse;
    private View mShareCancel;
    private ViewGroup mShareLayout;
    private ProgressBar mShareProgress;
    private RecyclerView mShareRecyclerView;
    /* access modifiers changed from: private */
    public TextureVideoView mTextureVideoView;
    private TextView mTimeView;
    private boolean mVideoRecordingPaused;
    private boolean mVideoRecordingStarted;
    private VMProcessing mVmProcessing;
    /* access modifiers changed from: private */
    public boolean mWaitingResultSurfaceTexture;

    private void animateIn(View view) {
        if (view.getVisibility() != 0) {
            Completable.create(new AlphaInOnSubscribe(view)).subscribe();
        }
    }

    private void animateOut(View view) {
        if (view.getVisibility() == 0) {
            Completable.create(new AlphaOutOnSubscribe(view)).subscribe();
        }
    }

    private boolean checkAndShare() {
        if (this.mSavedUri == null) {
            return false;
        }
        startShare();
        return true;
    }

    private Intent getShareIntent() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.STREAM", this.mSavedUri);
        intent.setType(Util.convertOutputFormatToMimeType(2));
        intent.addFlags(1);
        return intent;
    }

    private void initHandler() {
        if (this.mHandler == null) {
            this.mHandler = new Handler() {
                public void handleMessage(Message message) {
                    super.handleMessage(message);
                }
            };
        }
    }

    private void initTextureView() {
        this.mTextureVideoView = new TextureVideoView(getContext());
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        Rect displayRect = Util.getDisplayRect(getActivity());
        layoutParams.topMargin = displayRect.top;
        layoutParams.height = displayRect.height();
        this.mPreviewLayout.removeAllViews();
        this.mPreviewLayout.setBackground(null);
        this.mPreviewLayout.addView(this.mTextureVideoView, layoutParams);
        this.mTextureVideoView.setClearSurface(true);
        this.mTextureVideoView.setScaleType(6);
        this.mTextureVideoView.setVisibility(4);
        this.mTextureVideoView.setMediaPlayerCallback(new MediaPlayerCallback() {
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
            }

            public void onCompletion(MediaPlayer mediaPlayer) {
                FragmentVVProcess.this.stopSegmentPreview();
            }

            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                StringBuilder sb = new StringBuilder();
                sb.append(i);
                sb.append(" | ");
                sb.append(i2);
                Log.e("onError:", sb.toString());
                return false;
            }

            public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
                StringBuilder sb = new StringBuilder();
                sb.append(i);
                sb.append(" | ");
                sb.append(i2);
                Log.e("onInfo:", sb.toString());
                return false;
            }

            public void onPrepared(MediaPlayer mediaPlayer) {
            }

            public void onSurfaceReady(Surface surface) {
                if (FragmentVVProcess.this.mWaitingResultSurfaceTexture) {
                    FragmentVVProcess.this.mWaitingResultSurfaceTexture = false;
                    FragmentVVProcess.this.mTextureVideoView.setVideoSpecifiedSize(1920, 1080);
                    FragmentVVProcess.this.startPlay(surface);
                }
            }

            public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
            }
        });
        this.mTextureVideoView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FragmentVVProcess.this.pausePlay(false);
            }
        });
    }

    private void intoResultPreview() {
        this.mVmProcessing.updateState(3);
        if (this.mTextureVideoView.getPreviewSurface() != null) {
            startPlay(this.mTextureVideoView.getPreviewSurface());
        } else {
            this.mWaitingResultSurfaceTexture = true;
        }
    }

    private boolean isFullSegmentsPlaying() {
        return this.mVmProcessing.getCurrentState() == 6;
    }

    private void onProcessingSateChanged(int i) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("newState: ");
        sb.append(i);
        Log.d(str, sb.toString());
        switch (i) {
            case 2:
                this.mPreviewStart.setVisibility(0);
                this.mConcatProgress.setVisibility(8);
                return;
            case 3:
                animateOut(this.mSegmentPreview);
                animateOut(this.mSegmentReverse);
                animateOut(this.mPreviewNext);
                animateIn(this.mPreviewBack);
                animateIn(this.mPreviewShare);
                animateIn(this.mPreviewCombine);
                animateIn(this.mConcatProgress);
                this.mPreviewLayout.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
                this.mTextureVideoView.setVisibility(0);
                return;
            case 4:
            case 5:
                this.mPreviewStart.setVisibility(0);
                this.mConcatProgress.setVisibility(8);
                return;
            case 6:
                this.mPreviewStart.setVisibility(8);
                this.mConcatProgress.setVisibility(8);
                return;
            case 7:
                if (this.mPendingShare) {
                    this.mPreviewShare.setVisibility(8);
                    this.mShareProgress.setVisibility(0);
                    return;
                }
                this.mPreviewStart.setVisibility(8);
                this.mPreviewCombine.setVisibility(8);
                this.mCombineProgress.setVisibility(0);
                return;
            case 8:
                onResultCombineFinished(true);
                return;
            case 9:
                onResultCombineFinished(false);
                return;
            default:
                return;
        }
    }

    private void onSegmentsChanged() {
        int i = 0;
        for (Long longValue : this.mDurationList) {
            if (longValue.longValue() > 0) {
                i++;
            }
        }
        animateIn(this.mProgressView);
        if (i == 0) {
            animateOut(this.mSegmentReverse);
            animateOut(this.mSegmentPreview);
            animateOut(this.mPreviewNext);
        } else if (i == this.mDurationList.size()) {
            animateIn(this.mSegmentReverse);
            animateIn(this.mSegmentPreview);
            animateIn(this.mPreviewNext);
        } else {
            animateIn(this.mSegmentReverse);
            animateIn(this.mSegmentPreview);
            animateOut(this.mPreviewNext);
        }
    }

    /* access modifiers changed from: private */
    public void pausePlay(boolean z) {
        TextureVideoView textureVideoView = this.mTextureVideoView;
        if (textureVideoView != null && textureVideoView.isPlaying()) {
            stopSegmentPreview();
        } else if (!isFullSegmentsPlaying()) {
            if (z && this.mVmProcessing.getCurrentState() == 5) {
                this.mVmProcessing.updateState(2);
            }
        } else {
            if (z) {
                this.mVmProcessing.updateState(2);
            } else {
                this.mVmProcessing.updateState(5);
            }
            LiveConfigVV liveConfigVV = (LiveConfigVV) ModeCoordinatorImpl.getInstance().getAttachProtocol(228);
            if (liveConfigVV != null) {
                liveConfigVV.pausePlay();
            }
        }
    }

    private void previewLastSegment() {
        int i = 0;
        for (int i2 = 0; i2 < this.mDurationList.size(); i2++) {
            if (((Long) this.mDurationList.get(i2)).longValue() > 0) {
                i = i2;
            }
        }
        this.mBottomActionView.setVisibility(4);
        this.mPreviewLayout.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        this.mTextureVideoView.setVisibility(0);
        this.mTextureVideoView.setVideoPath(((LiveConfigVV) ModeCoordinatorImpl.getInstance().getAttachProtocol(228)).getSegmentPath(i));
        this.mTextureVideoView.start();
    }

    private void resumePlay() {
        if (!isFullSegmentsPlaying()) {
            if (this.mVmProcessing.getCurrentState() != 5) {
                intoResultPreview();
                return;
            }
            this.mVmProcessing.updateState(6);
            ((LiveConfigVV) ModeCoordinatorImpl.getInstance().getAttachProtocol(228)).resumePlay();
        }
    }

    private void shareMore() {
        try {
            getContext().startActivity(Intent.createChooser(getShareIntent(), getString(R.string.live_edit_share_title)));
        } catch (ActivityNotFoundException e2) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("failed to share video ");
            sb.append(this.mSavedUri);
            Log.e(str, sb.toString(), e2);
        }
    }

    private void showReverseConfirmDialog() {
        if (this.mReverseDialog == null) {
            CameraStatUtil.trackLiveClick(CameraStat.PARAM_LIVE_CLICK_REVERSE);
            Builder builder = new Builder(getContext());
            builder.setMessage(R.string.live_reverse_message);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.live_reverse_confirm, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    FragmentVVProcess.this.mReverseDialog = null;
                    ((LiveModuleSubVV) ((ActivityBase) FragmentVVProcess.this.getContext()).getCurrentModule()).doReverse();
                }
            });
            builder.setNegativeButton(R.string.snap_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    FragmentVVProcess.this.mReverseDialog = null;
                }
            });
            this.mReverseDialog = builder.create();
            this.mReverseDialog.getWindow().setGravity(3);
            this.mReverseDialog.show();
        }
    }

    private void showShareSheet() {
        if (!this.mPaused) {
            shareMore();
        }
    }

    /* access modifiers changed from: private */
    public void startPlay(Surface surface) {
        if (!isFullSegmentsPlaying()) {
            this.mVmProcessing.updateState(6);
            LiveConfigVV liveConfigVV = (LiveConfigVV) ModeCoordinatorImpl.getInstance().getAttachProtocol(228);
            if (liveConfigVV != null) {
                liveConfigVV.startPlay(surface);
            }
        }
    }

    private void startSave() {
        this.mVmProcessing.updateState(7);
        final String asString = this.mSaveContentValues.getAsString("_data");
        Completable.create(new CompletableOnSubscribe() {
            public void subscribe(CompletableEmitter completableEmitter) throws Exception {
                LiveConfigVV liveConfigVV = (LiveConfigVV) ModeCoordinatorImpl.getInstance().getAttachProtocol(228);
                if (liveConfigVV != null) {
                    liveConfigVV.combineVideoAudio(asString);
                }
                completableEmitter.onComplete();
            }
        }).subscribeOn(GlobalConstant.sCameraSetupScheduler).subscribe();
    }

    private void startShare() {
        this.mPendingShare = false;
        showShareSheet();
    }

    /* access modifiers changed from: private */
    public void stopSegmentPreview() {
        this.mTextureVideoView.stop();
        this.mTextureVideoView.setVisibility(4);
        this.mBottomActionView.setVisibility(0);
        this.mPreviewLayout.setBackground(null);
    }

    public /* synthetic */ void a(DataWrap dataWrap) throws Exception {
        onProcessingSateChanged(((Integer) dataWrap.get()).intValue());
    }

    public boolean canSnap() {
        return true;
    }

    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_VV_PROCESS;
    }

    /* access modifiers changed from: protected */
    public int getLayoutResourceId() {
        return R.layout.fragment_vv_process;
    }

    public ContentValues getSaveContentValues() {
        return this.mSaveContentValues;
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        this.mRootView = view;
        this.mPreviewLayout = (FrameLayout) view.findViewById(R.id.vv_preview_layout);
        this.mConcatProgress = (ProgressBar) view.findViewById(R.id.vv_concat_progress);
        this.mCombineProgress = (ProgressBar) view.findViewById(R.id.vv_save_progress);
        this.mShareProgress = (ProgressBar) view.findViewById(R.id.vv_share_progress);
        this.mTimeView = (TextView) view.findViewById(R.id.vv_review_recording_time_view);
        this.mCameraSnapView = (CameraSnapView) view.findViewById(R.id.vv_preview_save_circle);
        this.mCameraSnapView.setSnapListener(this);
        this.mPreviewCombine = (ImageView) view.findViewById(R.id.vv_preview_save);
        this.mPreviewBack = (ImageView) view.findViewById(R.id.vv_preview_back);
        this.mPreviewShare = (ImageView) view.findViewById(R.id.vv_preview_share);
        this.mPreviewStart = (ImageView) view.findViewById(R.id.vv_preview_play);
        this.mPreviewNext = (ImageView) view.findViewById(R.id.vv_preview_next);
        this.mShareLayout = (ViewGroup) view.findViewById(R.id.vv_share_layout);
        this.mShareRecyclerView = (RecyclerView) this.mShareLayout.findViewById(R.id.vv_share_list);
        this.mShareCancel = this.mShareLayout.findViewById(R.id.vv_share_cancel);
        this.mProgressView = (VVProgressView) view.findViewById(R.id.vv_progress);
        this.mSegmentReverse = (ImageView) view.findViewById(R.id.vv_segment_reverse);
        this.mSegmentPreview = (ImageView) view.findViewById(R.id.vv_segment_preview);
        this.mLandscapeHint = (TextView) view.findViewById(R.id.vv_process_hint);
        ViewCompat.setRotation(this.mPreviewStart, 90.0f);
        ViewCompat.setRotation(this.mCameraSnapView, 90.0f);
        ViewCompat.setRotation(this.mPreviewCombine, 90.0f);
        ViewCompat.setRotation(this.mPreviewBack, 90.0f);
        ViewCompat.setRotation(this.mPreviewShare, 90.0f);
        ViewCompat.setRotation(this.mPreviewNext, 90.0f);
        ViewCompat.setRotation(this.mSegmentReverse, 90.0f);
        ViewCompat.setRotation(this.mSegmentPreview, 90.0f);
        ViewCompat.setRotation(this.mLandscapeHint, 90.0f);
        this.mShareCancel.setOnClickListener(this);
        this.mCameraSnapView.setOnClickListener(this);
        this.mPreviewCombine.setOnClickListener(this);
        this.mPreviewBack.setOnClickListener(this);
        this.mPreviewShare.setOnClickListener(this);
        this.mPreviewStart.setOnClickListener(this);
        this.mSegmentReverse.setOnClickListener(this);
        this.mSegmentPreview.setOnClickListener(this);
        this.mPreviewNext.setOnClickListener(this);
        this.mBottomActionView = (FrameLayout) view.findViewById(R.id.vv_preview_bottom_action);
        ((MarginLayoutParams) this.mBottomActionView.getLayoutParams()).height = Util.getBottomHeight(getResources());
        this.mBottomActionView.setOnClickListener(this);
        this.mBottomLayout = (RelativeLayout) view.findViewById(R.id.vv_preview_bottom_parent);
        ((MarginLayoutParams) this.mBottomLayout.getLayoutParams()).bottomMargin = getResources().getDimensionPixelSize(R.dimen.bottom_margin_bottom) + Util.sNavigationBarHeight;
        quit();
        this.mVmProcessing = (VMProcessing) DataRepository.dataItemObservable().get(VMProcessing.class);
        this.mVmProcessing.startObservable(this, new a(this));
    }

    public boolean onBackEvent(int i) {
        return false;
    }

    public void onClick(View view) {
        if (this.mConcatProgress.getVisibility() != 0 && this.mCombineProgress.getVisibility() != 0 && this.mShareProgress.getVisibility() != 0) {
            switch (view.getId()) {
                case R.id.vv_preview_back /*2131296623*/:
                    showExitConfirm();
                    return;
                case R.id.vv_preview_next /*2131296630*/:
                    intoResultPreview();
                    return;
                case R.id.vv_preview_play /*2131296631*/:
                    resumePlay();
                    return;
                case R.id.vv_preview_save /*2131296632*/:
                    if (this.mSavedUri != null) {
                        quitLiveRecordPreview(true);
                        return;
                    } else {
                        startSave();
                        return;
                    }
                case R.id.vv_preview_share /*2131296634*/:
                    if (!checkAndShare()) {
                        this.mPendingShare = true;
                        startSave();
                        return;
                    }
                    return;
                case R.id.vv_segment_preview /*2131296640*/:
                    previewLastSegment();
                    return;
                case R.id.vv_segment_reverse /*2131296641*/:
                    showReverseConfirmDialog();
                    return;
                default:
                    return;
            }
        }
    }

    public void onCombinePrepare(ContentValues contentValues) {
        this.mSavedUri = null;
        this.mSaveContentValues = contentValues;
        initHandler();
    }

    public void onLiveSaveToLocalFinished(Uri uri) {
        this.mSavedUri = uri;
        if (this.mPendingShare) {
            this.mPreviewShare.setVisibility(0);
            this.mShareProgress.setVisibility(8);
            startShare();
        }
    }

    public void onPause() {
        super.onPause();
        this.mPaused = true;
        pausePlay(true);
        AlertDialog alertDialog = this.mExitDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.mExitDialog = null;
        }
        AlertDialog alertDialog2 = this.mReverseDialog;
        if (alertDialog2 != null) {
            alertDialog2.dismiss();
            this.mReverseDialog = null;
        }
    }

    public void onRecordingFragmentUpdate(int i, long j) {
        this.mVideoRecordingStarted = false;
        this.mDurationList.set(i, Long.valueOf(j));
        this.mProgressView.updateDuration(i, j);
        onSegmentsChanged();
    }

    public void onRecordingNewFragmentStart(int i, long j) {
        this.mCurrentIndex = i;
    }

    public void onResultCombineFinished(boolean z) {
        Log.d(TAG, "combineSuccess");
        if (this.mPendingShare) {
            Log.d(TAG, "combineSuccess and share");
            ((LiveModuleSubVV) ((ActivityBase) getContext()).getCurrentModule()).startSaveToLocal();
            return;
        }
        Log.d(TAG, "combineSuccess and finish");
        this.mHandler.post(new Runnable() {
            public void run() {
                FragmentVVProcess.this.quitLiveRecordPreview(true);
            }
        });
    }

    public void onResultPreviewFinished(boolean z) {
        this.mHandler.post(new Runnable() {
            public void run() {
                FragmentVVProcess.this.mPreviewStart.setVisibility(0);
            }
        });
    }

    public void onResume() {
        super.onResume();
        this.mPaused = false;
    }

    public void onSnapClick() {
        if (!(this.mConcatProgress.getVisibility() == 0 || this.mCombineProgress.getVisibility() == 0 || this.mShareProgress.getVisibility() == 0)) {
            if (this.mPreviewNext.getVisibility() == 0) {
                this.mPreviewNext.performClick();
            } else if (this.mPreviewCombine.getVisibility() == 0) {
                this.mPreviewCombine.performClick();
            } else {
                Module currentModule = ((Camera) getContext()).getCurrentModule();
                if (currentModule == null || !currentModule.isIgnoreTouchEvent()) {
                    CameraAction cameraAction = (CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
                    if (cameraAction != null && this.mCurrentMode == 179 && !this.mVideoRecordingStarted) {
                        this.mVideoRecordingStarted = true;
                        cameraAction.onShutterButtonClick(10);
                    }
                }
            }
        }
    }

    public void onSnapLongPress() {
    }

    public void onSnapLongPressCancelIn() {
    }

    public void onSnapLongPressCancelOut() {
    }

    public void onSnapPrepare() {
    }

    public void onTrackSnapMissTaken(long j) {
    }

    public void onTrackSnapTaken(long j) {
    }

    public void prepare(VVItem vVItem) {
        this.mIsIntentAction = DataRepository.dataItemGlobal().isIntentAction();
        this.mRootView.setVisibility(0);
        this.mCurrentMode = 179;
        this.mCameraSnapView.setParameters(this.mCurrentMode, false, false);
        this.mCameraSnapView.hideRoundPaintItem();
        this.mCameraSnapView.showPaintCenterVVItem();
        this.mConcatProgress.setVisibility(8);
        this.mCombineProgress.setVisibility(8);
        this.mShareProgress.setVisibility(8);
        this.mPreviewCombine.setVisibility(8);
        this.mPreviewBack.setVisibility(8);
        this.mPreviewShare.setVisibility(8);
        this.mPreviewStart.setVisibility(8);
        this.mPreviewNext.setVisibility(8);
        this.mSegmentReverse.setVisibility(8);
        this.mSegmentPreview.setVisibility(8);
        this.mDurationList = new ArrayList(vVItem.durationList.size());
        for (Long longValue : vVItem.durationList) {
            this.mDurationList.add(Long.valueOf(-longValue.longValue()));
        }
        this.mProgressView.setDurationList(this.mDurationList);
        AlphaInOnSubscribe.directSetResult(this.mProgressView);
        initTextureView();
    }

    public void processingFinish() {
        this.mVideoRecordingStarted = false;
    }

    public void processingPause() {
        this.mCameraSnapView.triggerAnimation(BottomAnimationConfig.generate(false, this.mCurrentMode, false, false, false).configVariables());
    }

    public void processingPrepare() {
        this.mCameraSnapView.prepareRecording(BottomAnimationConfig.generate(false, this.mCurrentMode, true, false, false).configVariables());
    }

    public void processingResume() {
        long j;
        animateOut(this.mSegmentReverse);
        animateOut(this.mSegmentPreview);
        animateOut(this.mProgressView);
        animateOut(this.mLandscapeHint);
        BottomAnimationConfig configVariables = BottomAnimationConfig.generate(false, this.mCurrentMode, true, false, false).configVariables();
        Iterator it = this.mDurationList.iterator();
        while (true) {
            j = 0;
            if (!it.hasNext()) {
                break;
            }
            long longValue = ((Long) it.next()).longValue();
            if (longValue < 0) {
                j = Math.abs(longValue);
                break;
            }
        }
        configVariables.setSpecifiedDuration((int) j);
        this.mCameraSnapView.triggerAnimation(configVariables);
    }

    public void processingStart() {
        processingResume();
    }

    public void quit() {
        this.mRootView.setVisibility(8);
    }

    public void quitLiveRecordPreview(boolean z) {
        if (this.mConcatProgress.getVisibility() == 0) {
            this.mConcatProgress.setVisibility(8);
        }
        if (this.mCombineProgress.getVisibility() == 0) {
            this.mCombineProgress.setVisibility(8);
        }
        if (this.mShareProgress.getVisibility() == 0) {
            this.mShareProgress.setVisibility(8);
        }
        pausePlay(false);
        this.mVmProcessing.reset();
        CameraAction cameraAction = (CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction == null) {
            Log.d(TAG, "concat error, action null");
            return;
        }
        if (z) {
            cameraAction.onReviewDoneClicked();
        } else {
            cameraAction.onReviewCancelClicked();
        }
    }

    /* access modifiers changed from: protected */
    public void register(ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(230, this);
        registerBackStack(modeCoordinator, this);
    }

    public void showExitConfirm() {
        if (this.mExitDialog == null) {
            CameraStatUtil.trackLiveClick(CameraStat.PARAM_LIVE_CLICK_PLAY_EXIT);
            Builder builder = new Builder(getContext());
            builder.setMessage(R.string.live_edit_exit_message);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.live_edit_exit_confirm, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    CameraStatUtil.trackLiveClick(CameraStat.PARAM_LIVE_CLICK_PLAY_EXIT_CONFIRM);
                    FragmentVVProcess.this.mExitDialog = null;
                    FragmentVVProcess.this.quitLiveRecordPreview(false);
                }
            });
            builder.setNegativeButton(R.string.snap_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    FragmentVVProcess.this.mExitDialog = null;
                }
            });
            this.mExitDialog = builder.create();
            this.mExitDialog.getWindow().setGravity(80);
            this.mExitDialog.show();
        }
    }

    /* access modifiers changed from: protected */
    public void unRegister(ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(230, this);
        unRegisterBackStack(modeCoordinator, this);
    }

    public void updateRecordingTime(String str) {
        this.mCameraSnapView.setDurationText(str);
    }
}

package com.android.camera.fragment.fullscreen;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import com.android.camera.ActivityBase;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.animation.type.SlideInOnSubscribe;
import com.android.camera.animation.type.SlideOutOnSubscribe;
import com.android.camera.constant.ShareConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.fragment.RecyclerAdapterWrapper;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.fragment.mimoji.FragmentMimojiEdit;
import com.android.camera.log.Log;
import com.android.camera.module.LiveModule;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStat;
import com.android.camera.statistic.CameraStatUtil;
import com.android.camera.ui.CameraSnapView;
import com.ss.android.vesdk.TERecorder;
import com.ss.android.vesdk.VECommonCallback;
import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import miui.view.animation.CubicEaseInInterpolator;
import miui.view.animation.QuarticEaseInInterpolator;
import miui.view.animation.QuarticEaseOutInterpolator;

public class FragmentFullScreen extends BaseFragment implements View.OnClickListener, ModeProtocol.FullScreenProtocol, ModeProtocol.HandleBackTrace {
    public static final int FRAGMENT_INFO = 4086;
    private static final String TAG = "FullScreen";
    private ViewGroup mBottomActionView;
    private ViewGroup mBottomLayout;
    private CameraSnapView mCameraSnapView;
    private VECommonCallback mCombineListener;
    /* access modifiers changed from: private */
    public ProgressBar mCombineProgress;
    private boolean mCombineReady;
    private Disposable mConcatDisposable;
    private TERecorder.OnConcatFinishedListener mConcatListener;
    private ProgressBar mConcatProgress;
    private boolean mConcatReady;
    private VECommonCallback mErrorListener;
    /* access modifiers changed from: private */
    public AlertDialog mExitDialog;
    private FragmentMimojiEdit mFragmentMimojiEdit;
    private Handler mHandler;
    private boolean mIsIntentAction;
    /* access modifiers changed from: private */
    public boolean mIsPlaying;
    private View mLiveViewLayout;
    private ViewStub mLiveViewStub;
    private boolean mPaused;
    private boolean mPendingSaveFinish;
    private boolean mPendingShare;
    private ImageView mPreviewBack;
    private ImageView mPreviewCombine;
    private FrameLayout mPreviewLayout;
    private ImageView mPreviewShare;
    /* access modifiers changed from: private */
    public ImageView mPreviewStart;
    private TextureView mPreviewTextureView;
    private boolean mReady;
    private ContentValues mSaveContentValues;
    private Uri mSavedUri;
    /* access modifiers changed from: private */
    public View mScreenLightIndicator;
    /* access modifiers changed from: private */
    public View mScreenLightRootView;
    private ShareAdapter mShareAdapter;
    private View mShareCancel;
    private ViewGroup mShareLayout;
    private ProgressBar mShareProgress;
    private RecyclerView mShareRecyclerView;
    private TextView mTimeView;
    private ViewGroup mTopLayout;

    private void animateIn() {
    }

    private boolean checkAndShare() {
        if (this.mSavedUri == null) {
            return false;
        }
        startShare();
        return true;
    }

    /* access modifiers changed from: private */
    public void concatResult(Pair<String, String> pair) {
        if (canProvide()) {
            String str = (String) pair.first;
            String str2 = (String) pair.second;
            if (!str.isEmpty() || !str2.isEmpty()) {
                this.mConcatReady = true;
                Log.d(TAG, "concat: " + str + " | " + str2);
                initCombineListener();
                this.mPreviewTextureView = new TextureView(getContext());
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
                Rect displayRect = Util.getDisplayRect(getActivity());
                layoutParams.topMargin = displayRect.top;
                layoutParams.height = displayRect.height();
                this.mPreviewLayout.addView(this.mPreviewTextureView, layoutParams);
                if (!((ModeProtocol.LiveVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(209)).init(this.mPreviewTextureView, str, str2, this.mCombineListener, this.mErrorListener)) {
                    onCombineError();
                } else if (this.mConcatProgress.getVisibility() == 0) {
                    Log.d(TAG, "concat finish and start preview");
                    this.mConcatProgress.setVisibility(8);
                    startPlay();
                } else if (this.mCombineProgress.getVisibility() == 0) {
                    Log.d(TAG, "concat finish and start save");
                    this.mPreviewCombine.setVisibility(8);
                    setProgressBarVisible(0);
                    startCombine();
                } else if (this.mShareProgress.getVisibility() == 0) {
                    Log.d(TAG, "concat finish and pending share");
                    startCombine();
                }
            } else {
                onCombineError();
            }
        }
    }

    private FragmentMimojiEdit getFragmentMiMoji() {
        if (this.mFragmentMimojiEdit == null) {
            Log.d(TAG, "getFragmentMiMoji(): fragment is null");
            return null;
        } else if (this.mFragmentMimojiEdit.isAdded()) {
            return this.mFragmentMimojiEdit;
        } else {
            Log.d(TAG, "getFragmentMiMoji(): fragment is not added yet");
            return null;
        }
    }

    private Intent getShareIntent() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.STREAM", this.mSavedUri);
        intent.setType(Util.convertOutputFormatToMimeType(2));
        intent.addFlags(1);
        return intent;
    }

    private void hideShareSheet() {
        if (this.mShareLayout.getVisibility() != 4) {
            Completable.create(new SlideOutOnSubscribe(this.mShareLayout, 80).setInterpolator(new QuarticEaseInInterpolator()).setDurationTime(200)).subscribe();
        }
    }

    private void initCombineListener() {
        this.mCombineListener = new VECommonCallback() {
            public void onCallback(int i, int i2, float f, String str) {
                if (i == 4098) {
                    Log.d(FragmentFullScreen.TAG, "play finished and loop");
                } else if (i == 4101) {
                } else {
                    if (i == 4103) {
                        FragmentFullScreen.this.onCombineSuccess();
                    } else if (i == 4105) {
                    } else {
                        if (i != 4112) {
                            Log.d(FragmentFullScreen.TAG, "CombineCallback: " + i);
                            return;
                        }
                        Log.d(FragmentFullScreen.TAG, "CombineStart");
                    }
                }
            }
        };
        this.mErrorListener = new VECommonCallback() {
            public void onCallback(int i, int i2, float f, String str) {
                Log.e(FragmentFullScreen.TAG, "CombineError: " + i + " | " + i2 + " | " + f + " | " + str);
                FragmentFullScreen.this.onCombineError();
            }
        };
    }

    private void initConcatListener() {
        this.mConcatListener = new TERecorder.OnConcatFinishedListener() {
            public void onConcatFinished(int i) {
                Log.d(FragmentFullScreen.TAG, "onConcatFinished " + i);
                ModeProtocol.LiveConfigChanges liveConfigChanges = (ModeProtocol.LiveConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(201);
                if (liveConfigChanges != null) {
                    FragmentFullScreen.this.concatResult(liveConfigChanges.getConcatResult());
                }
            }
        };
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

    private void initLiveView(View view) {
        this.mTopLayout = (ViewGroup) view.findViewById(R.id.live_preview_top);
        ((ViewGroup.MarginLayoutParams) this.mTopLayout.getLayoutParams()).topMargin = Util.isNotchDevice ? Util.sStatusBarHeight : getResources().getDimensionPixelSize(R.dimen.top_control_panel_extra_margin_top);
        this.mPreviewLayout = (FrameLayout) view.findViewById(R.id.live_preview_layout);
        this.mConcatProgress = (ProgressBar) view.findViewById(R.id.live_concat_progress);
        this.mCombineProgress = (ProgressBar) view.findViewById(R.id.live_save_progress);
        this.mShareProgress = (ProgressBar) view.findViewById(R.id.live_share_progress);
        this.mTimeView = (TextView) view.findViewById(R.id.live_preview_recording_time_view);
        this.mCameraSnapView = (CameraSnapView) view.findViewById(R.id.live_preview_save_circle);
        this.mCameraSnapView.setParameters(this.mCurrentMode, false, false);
        this.mCameraSnapView.hideRoundPaintItem();
        this.mCameraSnapView.setSnapClickEnable(false);
        this.mPreviewCombine = (ImageView) view.findViewById(R.id.live_preview_save);
        this.mPreviewBack = (ImageView) view.findViewById(R.id.live_preview_back);
        this.mPreviewShare = (ImageView) view.findViewById(R.id.live_preview_share);
        this.mPreviewStart = (ImageView) view.findViewById(R.id.live_preview_play);
        this.mShareLayout = (ViewGroup) view.findViewById(R.id.live_share_layout);
        this.mShareRecyclerView = (RecyclerView) this.mShareLayout.findViewById(R.id.live_share_list);
        this.mShareCancel = this.mShareLayout.findViewById(R.id.live_share_cancel);
        this.mShareCancel.setOnClickListener(this);
        this.mPreviewLayout.setOnClickListener(this);
        this.mCameraSnapView.setOnClickListener(this);
        this.mPreviewCombine.setOnClickListener(this);
        this.mPreviewBack.setOnClickListener(this);
        this.mPreviewShare.setOnClickListener(this);
        this.mPreviewStart.setOnClickListener(this);
        this.mBottomActionView = (FrameLayout) view.findViewById(R.id.live_preview_bottom_action);
        ((ViewGroup.MarginLayoutParams) this.mBottomActionView.getLayoutParams()).height = Util.getBottomHeight(getResources());
        this.mBottomActionView.setOnClickListener(this);
        this.mBottomLayout = (RelativeLayout) view.findViewById(R.id.live_preview_bottom_parent);
        ((ViewGroup.MarginLayoutParams) this.mBottomLayout.getLayoutParams()).bottomMargin = getResources().getDimensionPixelSize(R.dimen.bottom_margin_bottom) + Util.sNavigationBarHeight;
        this.mBottomActionView.setBackgroundResource(R.color.fullscreen_background);
    }

    /* access modifiers changed from: private */
    public void onCombineError() {
        this.mHandler.post(new Runnable() {
            public void run() {
                FragmentFullScreen.this.quitLiveRecordPreview(false);
            }
        });
    }

    /* access modifiers changed from: private */
    public void onCombineSuccess() {
        Log.d(TAG, "combineSuccess");
        this.mCombineReady = true;
        if (this.mPaused) {
            this.mPendingShare = false;
        }
        if (this.mPendingShare) {
            Log.d(TAG, "combineSuccess and share");
            ((LiveModule) ((ActivityBase) getContext()).getCurrentModule()).startSaveToLocal();
            return;
        }
        Log.d(TAG, "combineSuccess and finish");
        this.mHandler.post(new Runnable() {
            public void run() {
                FragmentFullScreen.this.quitLiveRecordPreview(true);
            }
        });
    }

    private void onPlayCompleted() {
        this.mHandler.post(new Runnable() {
            public void run() {
                boolean unused = FragmentFullScreen.this.mIsPlaying = false;
                FragmentFullScreen.this.mPreviewStart.setVisibility(0);
            }
        });
    }

    private void pausePlay() {
        if (this.mConcatDisposable != null && !this.mConcatDisposable.isDisposed()) {
            this.mConcatDisposable.dispose();
            this.mConcatProgress.setVisibility(8);
            this.mPreviewStart.setVisibility(0);
        }
        if (this.mIsPlaying) {
            this.mIsPlaying = false;
            this.mPreviewStart.setVisibility(0);
            ModeProtocol.LiveVideoEditor liveVideoEditor = (ModeProtocol.LiveVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(209);
            if (liveVideoEditor != null) {
                liveVideoEditor.pausePlay();
            }
        }
    }

    private void resumePlay() {
        if (!this.mIsPlaying) {
            this.mIsPlaying = true;
            this.mPreviewStart.setVisibility(8);
            ModeProtocol.LiveVideoEditor liveVideoEditor = (ModeProtocol.LiveVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(209);
            if (liveVideoEditor != null) {
                liveVideoEditor.resumePlay();
            }
        }
    }

    private void setProgressBarVisible(int i) {
        if (this.mCombineProgress.getVisibility() != i) {
            if (i == 0) {
                this.mCombineProgress.setAlpha(0.0f);
                this.mCombineProgress.setVisibility(0);
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                ofFloat.setDuration(300);
                ofFloat.setStartDelay(160);
                ofFloat.setInterpolator(new PathInterpolator(0.25f, 0.1f, 0.25f, 1.0f));
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        Float f = (Float) valueAnimator.getAnimatedValue();
                        FragmentFullScreen.this.mCombineProgress.setAlpha(f.floatValue());
                        FragmentFullScreen.this.mCombineProgress.setScaleX((f.floatValue() * 0.1f) + 0.9f);
                        FragmentFullScreen.this.mCombineProgress.setScaleY(0.9f + (0.1f * f.floatValue()));
                    }
                });
                ofFloat.start();
                return;
            }
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
            ofFloat2.setDuration(300);
            ofFloat2.setInterpolator(new CubicEaseInInterpolator());
            ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    FragmentFullScreen.this.mCombineProgress.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
            ofFloat2.addListener(new Animator.AnimatorListener() {
                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationEnd(Animator animator) {
                    FragmentFullScreen.this.mCombineProgress.setVisibility(8);
                }

                public void onAnimationRepeat(Animator animator) {
                }

                public void onAnimationStart(Animator animator) {
                }
            });
            ofFloat2.start();
        }
    }

    private void shareMore() {
        try {
            getContext().startActivity(Intent.createChooser(getShareIntent(), getString(R.string.live_edit_share_title)));
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "failed to share video " + this.mSavedUri, e);
        }
    }

    private void showExitConfirm() {
        if (this.mExitDialog == null) {
            CameraStatUtil.trackLiveClick(CameraStat.PARAM_LIVE_CLICK_PLAY_EXIT);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(R.string.live_edit_exit_message);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.live_edit_exit_confirm, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    CameraStatUtil.trackLiveClick(CameraStat.PARAM_LIVE_CLICK_PLAY_EXIT_CONFIRM);
                    AlertDialog unused = FragmentFullScreen.this.mExitDialog = null;
                    FragmentFullScreen.this.quitLiveRecordPreview(false);
                }
            });
            builder.setNegativeButton(R.string.snap_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    AlertDialog unused = FragmentFullScreen.this.mExitDialog = null;
                }
            });
            this.mExitDialog = builder.show();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x009d  */
    private void showShareSheet() {
        boolean z;
        List<ResolveInfo> queryIntentActivities = getContext().getPackageManager().queryIntentActivities(getShareIntent(), 65536);
        if (queryIntentActivities == null || queryIntentActivities.isEmpty()) {
            Log.d(TAG, "no IntentActivities");
            return;
        }
        ArrayList arrayList = new ArrayList();
        int length = ShareConstant.DEFAULT_SHARE_LIST.length;
        for (ResolveInfo next : queryIntentActivities) {
            if (arrayList.size() == length) {
                break;
            }
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                } else if (ShareConstant.DEFAULT_SHARE_LIST[i].equals(next.activityInfo.name)) {
                    ShareInfo shareInfo = new ShareInfo(i, next.activityInfo.packageName, next.activityInfo.name, ShareConstant.DEFAULT_SHARE_LIST_ICON[i], ShareConstant.DEFAULT_SHARE_LIST_NAME[i]);
                    arrayList.add(shareInfo);
                    break;
                } else {
                    i++;
                }
            }
        }
        if (arrayList.isEmpty()) {
            Log.d(TAG, "no default share entry");
        } else if (((ShareInfo) arrayList.get(0)).index <= 1 || arrayList.size() >= 2) {
            z = false;
            if (!z) {
                shareMore();
                return;
            }
            ShareInfo shareInfo2 = new ShareInfo(length + 1, "com.android.camera", "more", R.drawable.ic_live_share_more, R.string.accessibility_more);
            arrayList.add(shareInfo2);
            Collections.sort(arrayList, new Comparator<ShareInfo>() {
                public int compare(ShareInfo shareInfo, ShareInfo shareInfo2) {
                    if (shareInfo.index == shareInfo2.index) {
                        return 0;
                    }
                    return shareInfo.index > shareInfo2.index ? 1 : -1;
                }
            });
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.live_share_item_margin);
            int max = Math.max((Util.sWindowWidth - (dimensionPixelSize * 2)) / arrayList.size(), (int) (((float) (Util.sWindowWidth - dimensionPixelSize)) / 5.5f));
            if (this.mShareAdapter == null || this.mShareAdapter.getItemCount() != arrayList.size()) {
                this.mShareAdapter = new ShareAdapter(arrayList, this, max);
                LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(getContext(), TAG);
                linearLayoutManagerWrapper.setOrientation(0);
                this.mShareRecyclerView.setLayoutManager(linearLayoutManagerWrapper);
                RecyclerAdapterWrapper recyclerAdapterWrapper = new RecyclerAdapterWrapper(this.mShareAdapter);
                Space space = new Space(getContext());
                space.setMinimumWidth(dimensionPixelSize);
                recyclerAdapterWrapper.addHeader(space);
                Space space2 = new Space(getContext());
                space2.setMinimumWidth(dimensionPixelSize);
                recyclerAdapterWrapper.addFooter(space2);
                this.mShareRecyclerView.setAdapter(recyclerAdapterWrapper);
            } else {
                this.mShareAdapter.setShareInfoList(arrayList);
                this.mShareAdapter.notifyDataSetChanged();
            }
            if (Util.sNavigationBarHeight > 0) {
                ((ViewGroup.MarginLayoutParams) this.mShareLayout.getLayoutParams()).bottomMargin = Util.sNavigationBarHeight;
            }
            Completable.create(new SlideInOnSubscribe(this.mShareLayout, 80).setInterpolator(new QuarticEaseOutInterpolator()).setDurationTime(200)).subscribe();
            return;
        } else {
            Log.d(TAG, "not match default share strategy");
        }
        z = true;
        if (!z) {
        }
    }

    private void startCombine() {
        this.mCombineReady = false;
        String asString = this.mSaveContentValues.getAsString("_data");
        ModeProtocol.LiveVideoEditor liveVideoEditor = (ModeProtocol.LiveVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(209);
        if (liveVideoEditor != null) {
            liveVideoEditor.combineVideoAudio(asString, this.mCombineListener, this.mErrorListener);
        }
    }

    private void startConcatVideoIfNeed() {
        if (this.mConcatDisposable == null || this.mConcatDisposable.isDisposed()) {
            Log.d(TAG, "startConcatVideo");
            boolean z = false;
            this.mConcatReady = false;
            this.mIsPlaying = false;
            initConcatListener();
            ModeProtocol.LiveConfigChanges liveConfigChanges = (ModeProtocol.LiveConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(201);
            if (liveConfigChanges != null && liveConfigChanges.onRecordConcat(this.mConcatListener)) {
                z = true;
            }
            if (!z) {
                Log.d(TAG, "concat failed");
                concatResult(new Pair("", ""));
            }
        }
    }

    private void startPlay() {
        if (!this.mPaused && !this.mIsPlaying) {
            ModeProtocol.LiveVideoEditor liveVideoEditor = (ModeProtocol.LiveVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(209);
            if (liveVideoEditor != null) {
                this.mIsPlaying = true;
                this.mPreviewStart.setVisibility(8);
                this.mPreviewLayout.setVisibility(0);
                liveVideoEditor.startPlay();
            }
        }
    }

    private void startShare() {
        this.mPendingShare = false;
        showShareSheet();
    }

    private void startShare(String str, String str2) {
        ComponentName componentName = new ComponentName(str, str2);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setComponent(componentName);
        intent.putExtra("android.intent.extra.STREAM", this.mSavedUri);
        intent.setType(Util.convertOutputFormatToMimeType(2));
        intent.addFlags(1);
        try {
            getContext().startActivity(Intent.createChooser(intent, getString(R.string.live_edit_share_title)));
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "failed to share video " + this.mSavedUri, e);
        }
    }

    public int getFragmentInto() {
        return 4086;
    }

    /* access modifiers changed from: protected */
    public int getLayoutResourceId() {
        return R.layout.fragment_full_screen;
    }

    public ContentValues getSaveContentValues() {
        return this.mSaveContentValues;
    }

    public void hideScreenLight() {
        if (this.mScreenLightIndicator.getVisibility() != 8) {
            Completable.create(new AlphaOutOnSubscribe(this.mScreenLightRootView).setDurationTime(200)).subscribe((Action) new Action() {
                public void run() throws Exception {
                    FragmentFullScreen.this.mScreenLightIndicator.setVisibility(8);
                    FragmentFullScreen.this.mScreenLightRootView.setVisibility(8);
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        this.mScreenLightRootView = view.findViewById(R.id.screen_light_root_view);
        this.mScreenLightIndicator = view.findViewById(R.id.screen_light_indicator);
        this.mLiveViewStub = (ViewStub) view.findViewById(R.id.live_record_preview);
    }

    public boolean isLiveRecordPreviewShown() {
        return this.mLiveViewLayout != null && this.mLiveViewLayout.getVisibility() == 0;
    }

    public boolean isLiveRecordSaving() {
        return this.mPendingSaveFinish || this.mPendingShare;
    }

    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        if (this.mFragmentMimojiEdit == null && DataRepository.dataItemFeature().gD()) {
            this.mFragmentMimojiEdit = new FragmentMimojiEdit();
            this.mFragmentMimojiEdit.registerProtocol();
            this.mFragmentMimojiEdit.setDegree(this.mDegree);
            FragmentUtils.addFragmentWithTag(getChildFragmentManager(), (int) R.id.full_screen_lift, (Fragment) this.mFragmentMimojiEdit, this.mFragmentMimojiEdit.getFragmentTag());
        }
    }

    public void notifyDataChanged(int i, int i2) {
        super.notifyDataChanged(i, i2);
        if (this.mLiveViewLayout != null && this.mLiveViewLayout.getVisibility() == 0 && this.mCombineReady) {
            if (this.mPendingSaveFinish) {
                onCombineSuccess();
            } else if (this.mPendingShare) {
                this.mPendingShare = false;
                this.mPreviewShare.setVisibility(0);
                this.mShareProgress.setVisibility(8);
            }
        }
        if (this.mFragmentMimojiEdit == null && DataRepository.dataItemFeature().gE()) {
            this.mFragmentMimojiEdit = new FragmentMimojiEdit();
            this.mFragmentMimojiEdit.registerProtocol();
            this.mFragmentMimojiEdit.setDegree(this.mDegree);
            FragmentUtils.addFragmentWithTag(getChildFragmentManager(), (int) R.id.full_screen_lift, (Fragment) this.mFragmentMimojiEdit, this.mFragmentMimojiEdit.getFragmentTag());
        }
        this.mReady = true;
    }

    public boolean onBackEvent(int i) {
        if (i != 1) {
            return false;
        }
        if (this.mLiveViewLayout == null || this.mLiveViewLayout.getVisibility() != 0) {
            FragmentMimojiEdit fragmentMiMoji = getFragmentMiMoji();
            return fragmentMiMoji != null && fragmentMiMoji.onBackEvent(i);
        }
        if (this.mShareLayout.getVisibility() == 0) {
            hideShareSheet();
        } else {
            showExitConfirm();
        }
        return true;
    }

    public void onClick(View view) {
        if (this.mConcatProgress.getVisibility() != 0 && this.mCombineProgress.getVisibility() != 0 && this.mShareProgress.getVisibility() != 0) {
            switch (view.getId()) {
                case R.id.live_preview_layout:
                    pausePlay();
                    return;
                case R.id.live_preview_play:
                    if (!this.mConcatReady) {
                        Log.d(TAG, "concat not finished, show play progress");
                        this.mPreviewStart.setVisibility(8);
                        this.mConcatProgress.setVisibility(0);
                        startConcatVideoIfNeed();
                        return;
                    }
                    hideShareSheet();
                    startPlay();
                    return;
                case R.id.live_preview_back:
                    showExitConfirm();
                    return;
                case R.id.live_preview_save_circle:
                case R.id.live_preview_save:
                    CameraStatUtil.trackLiveClick(CameraStat.PARAM_LIVE_CLICK_PLAY_SAVE);
                    if (this.mSavedUri != null) {
                        onCombineSuccess();
                        return;
                    } else if (!this.mConcatReady) {
                        Log.d(TAG, "concat not finished, show save progress and wait to save");
                        this.mPendingSaveFinish = true;
                        this.mPreviewCombine.setVisibility(8);
                        setProgressBarVisible(0);
                        startConcatVideoIfNeed();
                        return;
                    } else if (!this.mCombineReady) {
                        pausePlay();
                        this.mPreviewStart.setVisibility(8);
                        this.mPreviewCombine.setVisibility(8);
                        setProgressBarVisible(0);
                        this.mPendingSaveFinish = true;
                        startCombine();
                        return;
                    } else {
                        return;
                    }
                case R.id.live_preview_share:
                    CameraStatUtil.trackLiveClick(CameraStat.PARAM_LIVE_CLICK_PLAY_SHARE);
                    if (!checkAndShare()) {
                        this.mPendingShare = true;
                        this.mPreviewShare.setVisibility(8);
                        this.mShareProgress.setVisibility(0);
                        if (!this.mConcatReady) {
                            Log.d(TAG, "concat not finished, show share progress and wait to share");
                            startConcatVideoIfNeed();
                            return;
                        }
                        pausePlay();
                        startCombine();
                        return;
                    }
                    return;
                case R.id.live_share_cancel:
                    hideShareSheet();
                    return;
                case R.id.live_share_item:
                    ShareInfo shareInfo = (ShareInfo) view.getTag();
                    hideShareSheet();
                    CameraStatUtil.trackLiveClick("live预览分享_" + shareInfo.index);
                    if (shareInfo.className.equals("more")) {
                        shareMore();
                        return;
                    } else {
                        startShare(shareInfo.packageName, shareInfo.className);
                        return;
                    }
                default:
                    return;
            }
        }
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
        this.mReady = false;
        pausePlay();
    }

    public void onResume() {
        super.onResume();
        this.mPaused = false;
    }

    public void onStop() {
        super.onStop();
        hideScreenLight();
    }

    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        if (this.mLiveViewLayout != null && this.mLiveViewLayout.getVisibility() == 0) {
            if (i2 == 3) {
                if (this.mExitDialog != null) {
                    this.mExitDialog.dismiss();
                    this.mExitDialog = null;
                }
                this.mLiveViewLayout.setVisibility(8);
                return;
            }
            this.mConcatReady = false;
        }
    }

    /* access modifiers changed from: protected */
    public Animation provideEnterAnimation(int i) {
        return null;
    }

    /* access modifiers changed from: protected */
    public Animation provideExitAnimation(int i) {
        return null;
    }

    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        if (this.mLiveViewLayout != null && this.mLiveViewLayout.getVisibility() == 0) {
            list.add(this.mPreviewStart);
            list.add(this.mCameraSnapView);
            list.add(this.mPreviewCombine);
            list.add(this.mPreviewBack);
            list.add(this.mPreviewShare);
        }
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
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction == null) {
            Log.d(TAG, "concat error, action null");
            return;
        }
        this.mLiveViewLayout.setVisibility(8);
        this.mPendingSaveFinish = false;
        if (z) {
            cameraAction.onReviewDoneClicked();
        } else {
            cameraAction.onReviewCancelClicked();
        }
        ModeProtocol.LiveVideoEditor liveVideoEditor = (ModeProtocol.LiveVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(209);
        if (liveVideoEditor != null) {
            liveVideoEditor.onDestory();
        }
        this.mCombineListener = null;
        this.mErrorListener = null;
    }

    /* access modifiers changed from: protected */
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(196, this);
        registerBackStack(modeCoordinator, this);
    }

    public void setScreenLightColor(int i) {
        this.mScreenLightRootView.setBackgroundColor(i);
    }

    public boolean showScreenLight() {
        if (this.mScreenLightIndicator.getVisibility() == 0) {
            return false;
        }
        this.mScreenLightIndicator.setVisibility(0);
        Completable.create(new AlphaInOnSubscribe(this.mScreenLightRootView).setDurationTime(100)).subscribe();
        return true;
    }

    public void startLiveRecordPreview(ContentValues contentValues) {
        this.mSavedUri = null;
        this.mSaveContentValues = contentValues;
        initHandler();
        if (this.mLiveViewLayout == null) {
            this.mLiveViewLayout = this.mLiveViewStub.inflate();
            initLiveView(this.mLiveViewLayout);
        }
        this.mIsIntentAction = DataRepository.dataItemGlobal().isIntentAction();
        this.mPreviewLayout.setVisibility(8);
        this.mPreviewLayout.removeAllViews();
        this.mLiveViewLayout.setVisibility(0);
        this.mCombineProgress.setVisibility(8);
        this.mShareProgress.setVisibility(8);
        this.mShareLayout.setVisibility(4);
        ViewCompat.setRotation(this.mPreviewStart, (float) this.mDegree);
        ViewCompat.setRotation(this.mCameraSnapView, (float) this.mDegree);
        ViewCompat.setRotation(this.mPreviewCombine, (float) this.mDegree);
        ViewCompat.setRotation(this.mPreviewBack, (float) this.mDegree);
        ViewCompat.setRotation(this.mPreviewShare, (float) this.mDegree);
        Completable.create(new AlphaInOnSubscribe(this.mCameraSnapView)).subscribe();
        Completable.create(new AlphaInOnSubscribe(this.mPreviewCombine)).subscribe();
        Completable.create(new AlphaInOnSubscribe(this.mPreviewBack)).subscribe();
        Completable.create(new AlphaInOnSubscribe(this.mPreviewStart)).subscribe();
        if (((ActivityBase) getContext()).startFromKeyguard()) {
            this.mPreviewShare.setVisibility(8);
        } else {
            Completable.create(new AlphaInOnSubscribe(this.mPreviewShare)).subscribe();
        }
        this.mTimeView.setText(((ModeProtocol.LiveConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(201)).getTimeValue());
        this.mTimeView.setVisibility(0);
        this.mPreviewStart.setVisibility(8);
        this.mConcatProgress.setVisibility(0);
        this.mConcatReady = false;
        this.mCombineReady = false;
        this.mPendingShare = false;
        this.mPendingSaveFinish = false;
        this.mPendingShare = false;
        this.mReady = true;
        startConcatVideoIfNeed();
    }

    public void startLiveRecordSaving() {
        this.mCameraSnapView.performClick();
    }

    /* access modifiers changed from: protected */
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(196, this);
        unRegisterBackStack(modeCoordinator, this);
    }
}

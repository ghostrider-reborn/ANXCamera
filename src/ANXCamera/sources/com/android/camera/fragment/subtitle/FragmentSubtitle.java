package com.android.camera.fragment.subtitle;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;
import com.android.camera.Camera;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FragmentAnimationFactory;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.fragment.CtaNoticeFragment;
import com.android.camera.fragment.top.FragmentTopAlert;
import com.android.camera.module.loader.StartControl;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import java.util.List;

public class FragmentSubtitle extends BaseFragment implements CtaNoticeFragment.OnCtaNoticeClickListener, ModeProtocol.HandleBackTrace, ModeProtocol.SubtitleRecording {
    private static final int SOUND_WAVE_PLAY_ONCE_TIME = 2500;
    private static final int SUBTITLE_FLIP_ROTATE = Util.dpToPixel(32.0f);
    private static final int SUBTITLE_LANDSCAPE_OFFSET = Util.dpToPixel(67.0f);
    private static final int SUBTITLE_NO_SPEAK_TIP = 101;
    private static final int SUBTITLE_SHOW_TIME_END = 100;
    private static final int SUBTITLE_VERTICAL_OFFSET = Util.dpToPixel(33.0f);
    private static final String TAG = "FragmentSubtitle";
    /* access modifiers changed from: private */
    public boolean isReceiveVoice;
    private boolean isRecording;
    private CtaNoticeFragment mCtaNoticeFragment;
    /* access modifiers changed from: private */
    public TextView mFlipShow;
    /* access modifiers changed from: private */
    public TextView mLeftShow;
    /* access modifiers changed from: private */
    public RecognitionListener mRecognitionListener;
    /* access modifiers changed from: private */
    public TextView mRightShow;
    /* access modifiers changed from: private */
    public SoundWaveView mSwViewFilp;
    /* access modifiers changed from: private */
    public SoundWaveView mSwViewLeft;
    /* access modifiers changed from: private */
    public SoundWaveView mSwViewRight;
    /* access modifiers changed from: private */
    public SoundWaveView mSwViewVertical;
    /* access modifiers changed from: private */
    public TextView mVerticalShow;
    /* access modifiers changed from: private */
    public long startRecordingTime;
    /* access modifiers changed from: private */
    @SuppressLint({"HandlerLeak"})
    public Handler subtitleHandler = new Handler() {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 100:
                    if (FragmentSubtitle.this.mLeftShow != null && FragmentSubtitle.this.mRightShow != null && FragmentSubtitle.this.mVerticalShow != null && FragmentSubtitle.this.mFlipShow != null) {
                        FragmentSubtitle.this.mLeftShow.setText("");
                        FragmentSubtitle.this.mRightShow.setText("");
                        FragmentSubtitle.this.mVerticalShow.setText("");
                        FragmentSubtitle.this.mFlipShow.setText("");
                        return;
                    }
                    return;
                case 101:
                    FragmentSubtitle.this.mRecognitionListener.onRecognitionListener(FragmentSubtitle.this.getResources().getString(R.string.hint_subtitle_prompt));
                    return;
                default:
                    return;
            }
        }
    };
    private View subtitleView;
    private int top;
    private ModeProtocol.TopAlert topAlert;

    class RecognitionListener {
        RecognitionListener() {
        }

        /* access modifiers changed from: package-private */
        @RequiresApi(api = 21)
        public void onRecognitionListener(String str) {
            FragmentSubtitle.this.subtitleHandler.removeCallbacksAndMessages((Object) null);
            FragmentSubtitle.this.mLeftShow.setText(str);
            FragmentSubtitle.this.mRightShow.setText(str);
            FragmentSubtitle.this.mVerticalShow.setText(str);
            FragmentSubtitle.this.mFlipShow.setText(str);
            if (FragmentSubtitle.this.getResources().getString(R.string.hint_subtitle_prompt).equals(str)) {
                FragmentSubtitle.this.mLeftShow.setLetterSpacing(0.0f);
                FragmentSubtitle.this.mRightShow.setLetterSpacing(0.0f);
                FragmentSubtitle.this.mVerticalShow.setLetterSpacing(0.0f);
                FragmentSubtitle.this.mFlipShow.setLetterSpacing(0.0f);
                FragmentSubtitle.this.subtitleHandler.sendEmptyMessageDelayed(100, 5000);
                return;
            }
            FragmentSubtitle.this.mLeftShow.setLetterSpacing(0.1f);
            FragmentSubtitle.this.mRightShow.setLetterSpacing(0.1f);
            FragmentSubtitle.this.mVerticalShow.setLetterSpacing(0.1f);
            FragmentSubtitle.this.mFlipShow.setLetterSpacing(0.1f);
            FragmentSubtitle.this.subtitleHandler.sendEmptyMessageDelayed(100, FragmentTopAlert.HINT_DELAY_TIME);
        }

        /* access modifiers changed from: package-private */
        public void onVoiceVolume(int i) {
            if (System.currentTimeMillis() - FragmentSubtitle.this.startRecordingTime <= 2500 || i > 0) {
                if (!FragmentSubtitle.this.isReceiveVoice) {
                    long unused = FragmentSubtitle.this.startRecordingTime = System.currentTimeMillis();
                }
                boolean unused2 = FragmentSubtitle.this.isReceiveVoice = true;
                if (FragmentSubtitle.this.mSwViewLeft.getVisibility() == 0) {
                    FragmentSubtitle.this.mSwViewLeft.startAnimation();
                }
                if (FragmentSubtitle.this.mSwViewRight.getVisibility() == 0) {
                    FragmentSubtitle.this.mSwViewRight.startAnimation();
                }
                if (FragmentSubtitle.this.mSwViewVertical.getVisibility() == 0) {
                    FragmentSubtitle.this.mSwViewVertical.startAnimation();
                }
                if (FragmentSubtitle.this.mSwViewFilp.getVisibility() == 0) {
                    FragmentSubtitle.this.mSwViewFilp.startAnimation();
                    return;
                }
                return;
            }
            boolean unused3 = FragmentSubtitle.this.isReceiveVoice = false;
            FragmentSubtitle.this.mSwViewLeft.resetAnimation();
            FragmentSubtitle.this.mSwViewRight.resetAnimation();
            FragmentSubtitle.this.mSwViewVertical.resetAnimation();
            FragmentSubtitle.this.mSwViewFilp.resetAnimation();
        }
    }

    private void setAnyViewGone() {
        this.mRightShow.setVisibility(8);
        this.mLeftShow.setVisibility(8);
        this.mVerticalShow.setVisibility(8);
        this.mFlipShow.setVisibility(8);
        this.mSwViewLeft.setVisibility(8);
        this.mSwViewFilp.setVisibility(8);
        this.mSwViewVertical.setVisibility(8);
        this.mSwViewRight.setVisibility(8);
        this.mSwViewLeft.resetAnimation();
        this.mSwViewRight.resetAnimation();
        this.mSwViewVertical.resetAnimation();
        this.mSwViewFilp.resetAnimation();
    }

    private void updateLightingRelativeView() {
        setAnyViewGone();
        if (isLandScape()) {
            ((ViewGroup.MarginLayoutParams) this.subtitleView.getLayoutParams()).topMargin = this.top + SUBTITLE_LANDSCAPE_OFFSET;
            if (isLeftLandScape()) {
                this.mLeftShow.setVisibility(0);
                this.mSwViewLeft.setVisibility(0);
                this.mSwViewLeft.startAnimation();
            } else if (isRightLandScape()) {
                this.mRightShow.setVisibility(0);
                this.mSwViewRight.setVisibility(0);
                this.mSwViewRight.startAnimation();
            }
        } else if (isFlipRotate()) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.subtitleView.getLayoutParams();
            if (CameraSettings.isMacroModeEnabled(this.mCurrentMode)) {
                marginLayoutParams.topMargin = this.top + SUBTITLE_FLIP_ROTATE;
            } else {
                marginLayoutParams.topMargin = this.top;
            }
            this.mFlipShow.setVisibility(0);
            this.mSwViewFilp.setVisibility(0);
            this.mSwViewFilp.startAnimation();
        } else {
            ((ViewGroup.MarginLayoutParams) this.subtitleView.getLayoutParams()).topMargin = this.top + SUBTITLE_VERTICAL_OFFSET;
            this.mVerticalShow.setVisibility(0);
            this.mSwViewVertical.setVisibility(0);
            this.mSwViewVertical.startAnimation();
        }
    }

    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_SUBTITLE;
    }

    /* access modifiers changed from: protected */
    public int getLayoutResourceId() {
        return R.layout.fragment_subtitle;
    }

    @RequiresApi(api = 21)
    public void handleSubtitleRecordingPause() {
        this.isRecording = false;
        setAnyViewGone();
        this.subtitleView.setVisibility(8);
        this.mRecognitionListener.onRecognitionListener("");
    }

    public void handleSubtitleRecordingResume() {
        this.isRecording = true;
        this.subtitleView.setVisibility(0);
        updateLightingRelativeView();
    }

    public void handleSubtitleRecordingStart(String str) {
        this.topAlert.alertSubtitleHint(8, R.string.pref_video_subtitle);
        this.isRecording = true;
        this.isReceiveVoice = true;
        this.subtitleView.setVisibility(0);
        updateLightingRelativeView();
        this.subtitleHandler.sendEmptyMessageDelayed(101, FragmentTopAlert.HINT_DELAY_TIME);
        this.startRecordingTime = System.currentTimeMillis();
    }

    @RequiresApi(api = 21)
    public void handleSubtitleRecordingStop() {
        this.topAlert.alertSubtitleHint(0, R.string.pref_video_subtitle);
        this.isRecording = false;
        this.subtitleView.setVisibility(0);
        this.mRecognitionListener.onRecognitionListener("");
        setAnyViewGone();
    }

    public void initPermission() {
        if (!((DataItemGlobal) DataRepository.provider().dataGlobal()).getCTACanCollect()) {
            this.mCtaNoticeFragment = CtaNoticeFragment.showCta(getActivity().getFragmentManager(), false, this, 2);
        }
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        this.subtitleView = view.findViewById(R.id.subtitle_view_menu);
        Rect displayRect = Util.getDisplayRect(getContext(), DataRepository.dataItemGlobal().getDisplayMode() == 2 ? 1 : 0);
        this.top = displayRect.top;
        int height = displayRect.height();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.subtitleView.getLayoutParams();
        marginLayoutParams.topMargin = this.top;
        marginLayoutParams.height = height;
        this.mLeftShow = (TextView) view.findViewById(R.id.voice_value_left);
        this.mRightShow = (TextView) view.findViewById(R.id.voice_value_right);
        this.mFlipShow = (TextView) view.findViewById(R.id.voice_value_flip);
        this.mVerticalShow = (TextView) view.findViewById(R.id.voice_value_vertical);
        this.mSwViewVertical = (SoundWaveView) view.findViewById(R.id.speed_sw_view);
        this.mSwViewLeft = (SoundWaveView) view.findViewById(R.id.speed_sw_view_left);
        this.mSwViewRight = (SoundWaveView) view.findViewById(R.id.speed_sw_view_right);
        this.mSwViewFilp = (SoundWaveView) view.findViewById(R.id.speed_sw_view_flip);
        registerProtocol();
        this.topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        this.mRecognitionListener = new RecognitionListener();
    }

    public boolean onBackEvent(int i) {
        return false;
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mCtaNoticeFragment != null) {
            this.mCtaNoticeFragment.dismiss();
        }
    }

    public void onNegativeClick(DialogInterface dialogInterface, int i) {
        this.subtitleView.setVisibility(8);
        CameraSettings.setSubtitleEnabled(this.mCurrentMode, false);
        DataRepository.dataItemGlobal().setCurrentMode(162);
        ((Camera) getActivity()).onModeSelected(StartControl.create(162).setViewConfigType(1).setNeedBlurAnimation(true).setNeedReConfigureCamera(true));
    }

    public void onPositiveClick(DialogInterface dialogInterface, int i) {
    }

    public void onResume() {
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public Animation provideEnterAnimation(int i) {
        return FragmentAnimationFactory.wrapperAnimation(-1, -1);
    }

    /* access modifiers changed from: protected */
    public Animation provideExitAnimation() {
        return FragmentAnimationFactory.wrapperAnimation(-1, -1);
    }

    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        if (DataRepository.dataItemRunning().getComponentRunningSubtitle().isEnabled(this.mCurrentMode) && this.isRecording) {
            updateLightingRelativeView();
        }
    }

    /* access modifiers changed from: protected */
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(231, this);
        registerBackStack(modeCoordinator, this);
    }

    public void saveSubtitle() {
    }

    /* access modifiers changed from: protected */
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(231, this);
        unRegisterBackStack(modeCoordinator, this);
    }
}

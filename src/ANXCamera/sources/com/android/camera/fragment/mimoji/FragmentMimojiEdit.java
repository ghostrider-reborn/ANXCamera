package com.android.camera.fragment.mimoji;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.fragment.mimoji.MimojiTypeAdapter;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.FileUtils;
import com.android.camera.module.impl.component.MimojiStatusManager;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStat;
import com.android.camera.statistic.CameraStatUtil;
import com.android.camera.ui.MimojiEditGLTextureView;
import com.android.camera.ui.autoselectview.AutoSelectHorizontalView;
import com.arcsoft.avatar.AvatarConfig;
import com.arcsoft.avatar.AvatarEngine;
import io.reactivex.Completable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FragmentMimojiEdit extends BaseFragment implements View.OnClickListener, View.OnTouchListener, ModeProtocol.HandleBackTrace, ModeProtocol.MimojiEditor {
    private static final int EDIT_ABANDON = 4;
    private static final int EDIT_ABANDON_CAPTURE = 3;
    private static final int EDIT_BACK = 1;
    private static final int EDIT_CANCEL = 5;
    private static final int EDIT_RECAPTURE = 2;
    public static final int EDIT_STATE_STEP1 = 1;
    public static final int EDIT_STATE_STEP2_1 = 2;
    public static final int EDIT_STATE_STEP2_2 = 4;
    private static final int EDIT_STATE_STEP3 = 3;
    private static final int EDIT_STATE_STEP4 = 5;
    private static final int FRAGMENT_INFO = 65521;
    public static final int FROM_ALL_PROCESS = 105;
    public static final String TAG = FragmentMimojiEdit.class.getSimpleName();
    /* access modifiers changed from: private */
    public int fromTag;
    /* access modifiers changed from: private */
    public AvatarEngine mAvatar;
    private AvatarEngineManager mAvatarEngineManager;
    private TextView mBackTextView;
    private ClickCheck mClickCheck;
    private TextView mConfirmTextView;
    /* access modifiers changed from: private */
    public Context mContext;
    private AlertDialog mCurrentAlertDialog;
    /* access modifiers changed from: private */
    public String mCurrentConfigPath = "";
    /* access modifiers changed from: private */
    public int mCurrentTopPannelState = -1;
    /* access modifiers changed from: private */
    public EditLevelListAdapter mEditLevelListAdapter;
    /* access modifiers changed from: private */
    public boolean mEditState = false;
    private TextView mEditTextView;
    /* access modifiers changed from: private */
    public boolean mEnterFromMimoji = false;
    @SuppressLint({"HandlerLeak"})
    private Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 4:
                    Bitmap thumbnailBitmapFromData = MimojiHelper.getThumbnailBitmapFromData((byte[]) message.obj, 200, 200);
                    String format = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault()).format(new Date());
                    String str = MimojiHelper.CUSTOM_DIR + format + "/";
                    String str2 = str + format + "config.dat";
                    String str3 = str + format + "pic.png";
                    FileUtils.saveBitmap(thumbnailBitmapFromData, str3);
                    int saveConfig = FragmentMimojiEdit.this.mAvatar.saveConfig(str2);
                    FragmentMimojiEdit.this.mAvatar.loadConfig(str2);
                    Log.d(FragmentMimojiEdit.TAG, "res = " + saveConfig + "  save path : " + str2);
                    if (FragmentMimojiEdit.this.mCurrentTopPannelState == 4) {
                        FileUtils.deleteFile(FragmentMimojiEdit.this.mPopSaveDeletePath);
                    }
                    MimojiInfo mimojiInfo = new MimojiInfo();
                    mimojiInfo.mConfigPath = str2;
                    mimojiInfo.mAvatarTemplatePath = AvatarEngineManager.PersonTemplatePath;
                    mimojiInfo.mThumbnailUrl = str3;
                    DataRepository.dataItemLive().getMimojiStatusManager().setmCurrentMimojiInfo(mimojiInfo);
                    FragmentMimojiEdit.this.goBack(false, true);
                    return;
                case 5:
                    Bundle bundle = (Bundle) message.obj;
                    FragmentMimojiEdit.this.mEditLevelListAdapter.notifyThumbnailUpdate(bundle.getInt("TYPE"), bundle.getInt("OUTER"), bundle.getInt("INNER"));
                    return;
                case 6:
                    int selectType = AvatarEngineManager.getInstance().getSelectType();
                    boolean isColorSelected = AvatarEngineManager.getInstance().isColorSelected();
                    FragmentMimojiEdit.this.mEditLevelListAdapter.refreshData(AvatarEngineManager.getInstance().getSubConfigList(FragmentMimojiEdit.this.mContext, selectType), !AvatarEngineManager.getInstance().isNeedUpdate(selectType), isColorSelected);
                    if (AvatarEngineManager.getInstance().isNeedUpdate(selectType)) {
                        FragmentMimojiEdit.this.mRenderThread.draw(false);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    private boolean mIsSaveBtnClicked = false;
    /* access modifiers changed from: private */
    public boolean mIsShowDialog = false;
    /* access modifiers changed from: private */
    public boolean mIsStartEdit;
    /* access modifiers changed from: private */
    public RecyclerView mLevelRecyleView;
    /* access modifiers changed from: private */
    public MimojiEditGLTextureView mMimojiEditGLTextureView;
    /* access modifiers changed from: private */
    public View mMimojiEditViewLayout;
    private ViewStub mMimojiEditViewStub;
    /* access modifiers changed from: private */
    public MimojiPageChangeAnimManager mMimojiPageChangeAnimManager;
    private MimojiTypeAdapter mMimojiTypeAdapter;
    private AutoSelectHorizontalView mMimojiTypeSelectView;
    private LinearLayout mOperateSelectLayout;
    /* access modifiers changed from: private */
    public String mPopSaveDeletePath = "";
    private TextView mReCaptureTextView;
    /* access modifiers changed from: private */
    public MimojiThumbnailRenderThread mRenderThread;
    private LinearLayout mRlAllEditContent;
    private TextView mSaveTextView;
    private boolean mSetupCompleted = false;
    private Thread mSetupThread;

    /* access modifiers changed from: private */
    public void doSetup() {
        setupAvatar();
        this.mAvatar.saveConfig(AvatarEngineManager.TempOriginalConfigPath);
        this.mSetupCompleted = true;
    }

    /* access modifiers changed from: private */
    public void goBack(boolean z, boolean z2) {
        AvatarEngineManager.getInstance().clear();
        if (this.mMimojiEditGLTextureView != null) {
            this.mMimojiEditGLTextureView.setStopRender(true);
            this.mMimojiEditGLTextureView.queueEvent(new Runnable() {
                public void run() {
                    if (FragmentMimojiEdit.this.mAvatar != null) {
                        Log.d(FragmentMimojiEdit.TAG, "avatar releaseRender 2");
                        FragmentMimojiEdit.this.mAvatar.releaseRender();
                    }
                }
            });
        }
        ModeProtocol.MimojiAvatarEngine mimojiAvatarEngine = (ModeProtocol.MimojiAvatarEngine) ModeCoordinatorImpl.getInstance().getAttachProtocol(217);
        if (mimojiAvatarEngine != null) {
            mimojiAvatarEngine.backToPreview(z2, !z);
            if (z) {
                mimojiAvatarEngine.onMimojiCreate();
            }
        }
        if (z2) {
            ModeProtocol.MimojiAlert mimojiAlert = (ModeProtocol.MimojiAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(226);
            if (mimojiAlert != null) {
                CameraStatUtil.trackMimojiCount(Integer.toString(mimojiAlert.refreshMimojiList()));
            }
        }
        this.mEnterFromMimoji = false;
        this.mIsStartEdit = false;
        if (this.mMimojiEditViewLayout != null) {
            this.mMimojiEditViewLayout.setVisibility(8);
            this.mOperateSelectLayout.setVisibility(0);
            updateTitleState(1);
        }
        this.mMimojiEditGLTextureView.setVisibility(8);
        this.mRenderThread.quit();
        this.mSetupThread = null;
        FragmentUtils.removeFragmentByTag(getFragmentManager(), TAG);
    }

    private void initConfigList() {
        this.mRenderThread.initAvatar(this.mEnterFromMimoji ? this.mCurrentConfigPath : AvatarEngineManager.TempOriginalConfigPath);
        AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue = new AvatarConfig.ASAvatarConfigValue();
        this.mAvatar.getConfigValue(aSAvatarConfigValue);
        this.mAvatarEngineManager.setASAvatarConfigValue(aSAvatarConfigValue);
        this.mAvatarEngineManager.setConfigTypeList(this.mAvatar.getSupportConfigType(this.mAvatarEngineManager.getASAvatarConfigValue().gender));
        if (this.mLevelRecyleView.getAdapter() == null || this.mEditLevelListAdapter == null) {
            if (this.mEditLevelListAdapter == null) {
                this.mEditLevelListAdapter = new EditLevelListAdapter(this.mContext, new ItfGvOnItemClickListener() {
                    public void notifyUIChanged() {
                        boolean unused = FragmentMimojiEdit.this.mEditState = true;
                        if (FragmentMimojiEdit.this.fromTag == 105) {
                            FragmentMimojiEdit.this.updateTitleState(3);
                            FragmentMimojiEdit.this.mMimojiPageChangeAnimManager.resetLayoutPosition(4);
                            return;
                        }
                        FragmentMimojiEdit.this.updateTitleState(5);
                    }
                });
            }
            this.mLevelRecyleView.setAdapter(this.mEditLevelListAdapter);
        }
        this.mEditLevelListAdapter.setIsColorNeedNotify(true);
        if (this.mMimojiTypeAdapter == null) {
            this.mMimojiTypeAdapter = new MimojiTypeAdapter((ArrayList<MimojiTypeBean>) null);
            this.mMimojiTypeAdapter.setOnSelectListener(new MimojiTypeAdapter.OnSelectListener() {
                public void onSelectListener(AvatarConfig.ASAvatarConfigType aSAvatarConfigType, int i) {
                    String str = FragmentMimojiEdit.TAG;
                    Log.v(str, "onSelectListener position  : " + i);
                    FragmentMimojiEdit.this.mMimojiPageChangeAnimManager.updateLayoutPosition();
                    if (FragmentMimojiEdit.this.mEditLevelListAdapter != null) {
                        FragmentMimojiEdit.this.mEditLevelListAdapter.setIsColorNeedNotify(true);
                    }
                    ModeProtocol.MimojiEditor mimojiEditor = (ModeProtocol.MimojiEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(224);
                    if (!(mimojiEditor == null || aSAvatarConfigType == null)) {
                        mimojiEditor.onTypeConfigSelect(aSAvatarConfigType.configType);
                    }
                    FragmentMimojiEdit.this.mLevelRecyleView.scrollToPosition(0);
                }
            });
            this.mMimojiTypeSelectView.setAdapter(this.mMimojiTypeAdapter);
        }
        ArrayList<AvatarConfig.ASAvatarConfigType> configTypeList = AvatarEngineManager.getInstance().getConfigTypeList();
        ArrayList arrayList = new ArrayList();
        Iterator<AvatarConfig.ASAvatarConfigType> it = configTypeList.iterator();
        while (it.hasNext()) {
            AvatarConfig.ASAvatarConfigType next = it.next();
            ArrayList<AvatarConfig.ASAvatarConfigInfo> config = AvatarEngineManager.getInstance().queryAvatar().getConfig(next.configType, AvatarEngineManager.getInstance().getASAvatarConfigValue().gender);
            Log.i(TAG, "putConfigList:" + next.configTypeDesc + ":" + next.configType);
            AvatarEngineManager.getInstance().putConfigList(next.configType, config);
            if (!AvatarEngineManager.filterTypeTitle(next.configType)) {
                MimojiTypeBean mimojiTypeBean = new MimojiTypeBean();
                mimojiTypeBean.setAlpha(0);
                mimojiTypeBean.setASAvatarConfigType(next);
                arrayList.add(mimojiTypeBean);
            }
        }
        if (arrayList.size() > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                MimojiTypeBean mimojiTypeBean2 = (MimojiTypeBean) arrayList.get(i);
                if (mimojiTypeBean2.getAlpha() == 1) {
                    mimojiTypeBean2.setAlpha(0);
                }
            }
            this.mMimojiTypeSelectView.setAdapter(this.mMimojiTypeAdapter);
            this.mMimojiTypeAdapter.setDataList(arrayList);
            return;
        }
        Log.e(TAG, " initConfigList() size 0, repeat ");
        initConfigList();
    }

    private void initMimojiEdit(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            }
        });
        ((RelativeLayout) view.findViewById(R.id.rv_navigation_layout)).setOnClickListener(this);
        ((RelativeLayout) view.findViewById(R.id.rl_fragment_mimoji_edit_container)).setOnClickListener(this);
        this.mRlAllEditContent = (LinearLayout) view.findViewById(R.id.ll_bottom_editoperate_content);
        this.mReCaptureTextView = (TextView) view.findViewById(R.id.tv_recapture);
        this.mReCaptureTextView.setOnClickListener(this);
        this.mReCaptureTextView.setOnTouchListener(this);
        this.mEditTextView = (TextView) view.findViewById(R.id.tv_edit);
        this.mEditTextView.setOnClickListener(this);
        this.mEditTextView.setOnTouchListener(this);
        this.mSaveTextView = (TextView) view.findViewById(R.id.tv_save);
        this.mSaveTextView.setOnClickListener(this);
        this.mSaveTextView.setOnTouchListener(this);
        this.mBackTextView = (TextView) view.findViewById(R.id.tv_back);
        this.mBackTextView.setOnClickListener(this);
        this.mConfirmTextView = (TextView) view.findViewById(R.id.btn_confirm);
        this.mConfirmTextView.setOnClickListener(this);
        updateTitleState(1);
        this.mMimojiEditGLTextureView = (MimojiEditGLTextureView) view.findViewById(R.id.mimoji_edit_preview);
        this.mMimojiEditGLTextureView.setHandler(this.mHandler);
        this.mOperateSelectLayout = (LinearLayout) view.findViewById(R.id.operate_select_layout);
        this.mOperateSelectLayout.setVisibility(0);
        this.mMimojiTypeSelectView = (AutoSelectHorizontalView) view.findViewById(R.id.mimoji_type_view);
        this.mMimojiTypeSelectView.setItemViewCacheSize(10);
        this.mMimojiTypeSelectView.getItemAnimator().setChangeDuration(0);
        this.mMimojiTypeSelectView.setInitPosition(0);
        this.mLevelRecyleView = (RecyclerView) view.findViewById(R.id.color_level);
        if (this.mLevelRecyleView.getLayoutManager() == null) {
            LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(this.mContext, "color_level");
            linearLayoutManagerWrapper.setOrientation(1);
            this.mLevelRecyleView.setLayoutManager(linearLayoutManagerWrapper);
        }
        this.mEditLevelListAdapter = new EditLevelListAdapter(this.mContext, new ItfGvOnItemClickListener() {
            public void notifyUIChanged() {
                boolean unused = FragmentMimojiEdit.this.mEditState = true;
                if (FragmentMimojiEdit.this.fromTag == 105) {
                    FragmentMimojiEdit.this.updateTitleState(3);
                } else {
                    FragmentMimojiEdit.this.updateTitleState(5);
                }
            }
        });
        this.mClickCheck = new ClickCheck();
        this.mEditLevelListAdapter.setmClickCheck(this.mClickCheck);
        this.mLevelRecyleView.setAdapter(this.mEditLevelListAdapter);
        this.mMimojiPageChangeAnimManager = new MimojiPageChangeAnimManager();
        this.mMimojiPageChangeAnimManager.initView(this.mContext, this.mMimojiEditGLTextureView, this.mRlAllEditContent, 1);
    }

    private void resetData() {
        this.mHandler.removeMessages(6);
        this.mHandler.removeMessages(16);
        this.mAvatarEngineManager.resetData();
        this.mEditLevelListAdapter.setIsColorNeedNotify(true);
        this.mEditLevelListAdapter.setLevelDatas(AvatarEngineManager.getInstance().getSubConfigList(this.mContext, AvatarEngineManager.getInstance().getSelectType()));
        if (this.mRenderThread.getIsRendering()) {
            this.mRenderThread.setResetStopRender(true);
        } else {
            this.mRenderThread.draw(true);
        }
        this.mEditLevelListAdapter.notifyDataSetChanged();
        String str = TAG;
        Log.i(str, "resetData   mEnterFromMimoji :" + this.mEnterFromMimoji);
        this.mAvatar.loadConfig(this.mEnterFromMimoji ? this.mCurrentConfigPath : AvatarEngineManager.TempOriginalConfigPath);
    }

    private void setupAvatar() {
        Log.d(TAG, "setup avatar");
        this.mAvatarEngineManager = AvatarEngineManager.getInstance();
        this.mAvatar = this.mAvatarEngineManager.queryAvatar();
        this.mAvatar.loadColorValue(AvatarEngineManager.PersonTemplatePath);
        if (!this.mEnterFromMimoji) {
            this.mAvatar.setTemplatePath(AvatarEngineManager.PersonTemplatePath);
        }
        AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue = new AvatarConfig.ASAvatarConfigValue();
        this.mAvatar.getConfigValue(aSAvatarConfigValue);
        this.mAvatarEngineManager.setASAvatarConfigValue(aSAvatarConfigValue);
        this.mAvatarEngineManager.setASAvatarConfigValueDefault(aSAvatarConfigValue);
        this.mAvatar.setRenderScene(false, 0.85f);
        this.mMimojiEditGLTextureView.setStopRender(false);
        this.mRenderThread = new MimojiThumbnailRenderThread("MimojiEdit", 200, 200, this.mContext);
        this.mRenderThread.start();
        this.mRenderThread.waitUntilReady();
        this.mRenderThread.setUpdateHandler(this.mHandler);
        if (this.mEditLevelListAdapter != null) {
            this.mEditLevelListAdapter.setRenderThread(this.mRenderThread);
        }
        this.mAvatarEngineManager.initUpdatePara();
    }

    private void showAlertDialog(final int i) {
        int i2;
        if (!this.mIsShowDialog) {
            switch (i) {
                case 1:
                case 2:
                    i2 = R.string.mimoji_edit_cancel_alert;
                    break;
                case 3:
                    i2 = R.string.mimoji_edit_abandon_capture_alert;
                    break;
                case 4:
                case 5:
                    i2 = R.string.mimoji_edit_abandon_alert;
                    break;
                default:
                    i2 = -1;
                    break;
            }
            if (i2 != -1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(i2);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.mimoji_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean z = true;
                        if (!(i == 2 || i == 1)) {
                            z = false;
                        }
                        if (!z && FragmentMimojiEdit.this.mIsStartEdit) {
                            FragmentMimojiEdit.this.mAvatar.loadConfig(FragmentMimojiEdit.this.mEnterFromMimoji ? FragmentMimojiEdit.this.mCurrentConfigPath : AvatarEngineManager.TempOriginalConfigPath);
                        }
                        FragmentMimojiEdit.this.goBack(z, false);
                        switch (i) {
                            case 1:
                                CameraStatUtil.trackMimojiClick(CameraStat.PARAM_MIMOJI_CLICK_PREVIEW_MID_BACK);
                                break;
                            case 2:
                                CameraStatUtil.trackMimojiClick(CameraStat.PARAM_MIMOJI_CLICK_PREVIEW_MID_RECAPTURE);
                                break;
                            case 3:
                                CameraStatUtil.trackMimojiClick(CameraStat.PARAM_MIMOJI_CLICK_PREVIEW_MID_SOFT_BACK);
                                break;
                            case 4:
                                CameraStatUtil.trackMimojiClick(CameraStat.PARAM_MIMOJI_CLICK_EDIT_SOFT_BACK);
                                break;
                            case 5:
                                CameraStatUtil.trackMimojiClick(CameraStat.PARAM_MIMOJI_CLICK_EDIT_CANCEL);
                                break;
                        }
                        boolean unused = FragmentMimojiEdit.this.mIsShowDialog = false;
                    }
                });
                builder.setNegativeButton(R.string.mimoji_cancle, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean unused = FragmentMimojiEdit.this.mIsShowDialog = false;
                    }
                });
                this.mIsShowDialog = true;
                this.mCurrentAlertDialog = builder.show();
            }
        }
    }

    public void directlyEnterEditMode(MimojiInfo mimojiInfo, int i) {
        String str = TAG;
        Log.d(str, "configPath = " + this.mCurrentConfigPath);
        this.mPopSaveDeletePath = mimojiInfo.mPackPath;
        this.mCurrentConfigPath = mimojiInfo.mConfigPath;
        this.mEnterFromMimoji = true;
        this.mIsStartEdit = true;
        DataRepository.dataItemLive().getMimojiStatusManager().setMode(MimojiStatusManager.MIMOJI_EIDT);
        ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
        if (actionProcessing != null) {
            actionProcessing.forceSwitchFront();
        }
        startMimojiEdit(false, i);
        ModeProtocol.MimojiAvatarEngine mimojiAvatarEngine = (ModeProtocol.MimojiAvatarEngine) ModeCoordinatorImpl.getInstance().getAttachProtocol(217);
        if (mimojiAvatarEngine != null) {
            mimojiAvatarEngine.setDisableSingleTapUp(true);
        }
        ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).disableMenuItem(true, 197, 193);
        if (101 == i) {
            updateTitleState(4);
        } else {
            updateTitleState(2);
        }
        this.mOperateSelectLayout.setVisibility(8);
        initConfigList();
    }

    public int getFragmentInto() {
        return 65521;
    }

    /* access modifiers changed from: protected */
    public int getLayoutResourceId() {
        return R.layout.fragment_full_screen_mimoji;
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        this.mContext = getActivity();
        this.mMimojiEditViewStub = (ViewStub) view.findViewById(R.id.mimoji_edit);
    }

    public boolean isWorkForeground() {
        return this.mIsStartEdit;
    }

    public boolean onBackEvent(int i) {
        if (i != 1 || this.mIsSaveBtnClicked) {
            return false;
        }
        if (this.mIsStartEdit) {
            showAlertDialog(4);
            return true;
        } else if (this.mMimojiEditViewLayout == null || this.mMimojiEditViewLayout.getVisibility() == 8) {
            return false;
        } else {
            showAlertDialog(3);
            return true;
        }
    }

    public void onClick(View view) {
        if (this.mSetupCompleted) {
            switch (view.getId()) {
                case R.id.tv_edit:
                    updateTitleState(2);
                    this.mOperateSelectLayout.setVisibility(8);
                    this.mRlAllEditContent.setVisibility(0);
                    initConfigList();
                    this.mMimojiPageChangeAnimManager.updateOperateState(2);
                    DataRepository.dataItemLive().getMimojiStatusManager().setMode(MimojiStatusManager.MIMOJI_EIDT);
                    this.mIsStartEdit = true;
                    CameraStatUtil.trackMimojiClick(CameraStat.PARAM_MIMOJI_CLICK_PREVIEW_MID_EDIT);
                    return;
                case R.id.tv_back:
                    if (!this.mIsSaveBtnClicked) {
                        if (this.fromTag == 101) {
                            showAlertDialog(5);
                            return;
                        } else if (this.fromTag == 105 && this.mCurrentTopPannelState == 1) {
                            showAlertDialog(1);
                            return;
                        } else if (!this.mEditState) {
                            return;
                        } else {
                            if (this.mClickCheck == null || this.mClickCheck.checkClickable()) {
                                this.mEditState = false;
                                updateTitleState(2);
                                resetData();
                                CameraStatUtil.trackMimojiClick(CameraStat.PARAM_MIMOJI_CLICK_EDIT_RESET);
                                return;
                            }
                            return;
                        }
                    } else {
                        return;
                    }
                case R.id.btn_confirm:
                case R.id.tv_save:
                    if (!this.mIsSaveBtnClicked) {
                        this.mIsSaveBtnClicked = true;
                        this.mMimojiEditGLTextureView.setSaveConfigThum(true);
                        if (this.mIsStartEdit) {
                            AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue = new AvatarConfig.ASAvatarConfigValue();
                            this.mAvatar.getConfigValue(aSAvatarConfigValue);
                            Map<String, String> mimojiConfigValue = AvatarEngineManager.getMimojiConfigValue(aSAvatarConfigValue);
                            if (this.mEnterFromMimoji) {
                                CameraStatUtil.trackMimojiClick(CameraStat.PARAM_MIMOJI_CLICK_EDIT_SAVE_OLD);
                                CameraStatUtil.trackMimojiSavePara(CameraStat.PARAM_MIMOJI_CLICK_EDIT_SAVE_OLD, mimojiConfigValue);
                                return;
                            }
                            CameraStatUtil.trackMimojiClick(CameraStat.PARAM_MIMOJI_CLICK_EDIT_SAVE_NEW);
                            CameraStatUtil.trackMimojiSavePara(CameraStat.PARAM_MIMOJI_CLICK_EDIT_SAVE_NEW, mimojiConfigValue);
                            return;
                        }
                        CameraStatUtil.trackMimojiClick(CameraStat.PARAM_MIMOJI_CLICK_PREVIEW_MID_SAVE);
                        return;
                    }
                    return;
                case R.id.tv_recapture:
                    if (!this.mIsSaveBtnClicked) {
                        showAlertDialog(2);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onDeviceRotationChange(int i) {
        if (this.mMimojiEditGLTextureView != null) {
            this.mMimojiEditGLTextureView.onDeviceRotationChange(i);
        }
    }

    public void onResume() {
        super.onResume();
    }

    public void onStart() {
        super.onStart();
        if (this.mBackTextView != null && !this.mIsStartEdit) {
            this.mEditState = false;
            updateTitleState(1);
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int id = view.getId();
        if (id != R.id.tv_edit) {
            switch (id) {
                case R.id.tv_recapture:
                    if (motionEvent.getActionMasked() == 0) {
                        this.mReCaptureTextView.setBackground(getResources().getDrawable(R.drawable.shape_round_corner_selected));
                        this.mReCaptureTextView.setTextColor(getResources().getColor(R.color.white_alpha_cc));
                        return false;
                    } else if (motionEvent.getActionMasked() != 1) {
                        return false;
                    } else {
                        this.mReCaptureTextView.setBackground(getResources().getDrawable(R.drawable.shape_round_corner_default));
                        this.mReCaptureTextView.setTextColor(getResources().getColor(R.color.white));
                        return false;
                    }
                case R.id.tv_save:
                    if (motionEvent.getActionMasked() == 0) {
                        this.mSaveTextView.setBackground(getResources().getDrawable(R.drawable.shape_round_corner_save_selected));
                        this.mSaveTextView.setTextColor(getResources().getColor(R.color.white_alpha_cc));
                        return false;
                    } else if (motionEvent.getActionMasked() != 1) {
                        return false;
                    } else {
                        this.mSaveTextView.setBackground(getResources().getDrawable(R.drawable.shape_round_corner_save_default));
                        this.mSaveTextView.setTextColor(getResources().getColor(R.color.white));
                        return false;
                    }
                default:
                    return false;
            }
        } else if (motionEvent.getActionMasked() == 0) {
            this.mEditTextView.setBackground(getResources().getDrawable(R.drawable.shape_round_corner_selected));
            this.mEditTextView.setTextColor(getResources().getColor(R.color.white_alpha_cc));
            return false;
        } else if (motionEvent.getActionMasked() != 1) {
            return false;
        } else {
            this.mEditTextView.setBackground(getResources().getDrawable(R.drawable.shape_round_corner_default));
            this.mEditTextView.setTextColor(getResources().getColor(R.color.white));
            return false;
        }
    }

    public void onTypeConfigSelect(int i) {
        this.mAvatarEngineManager.setIsColorSelected(false);
        this.mAvatarEngineManager.setSelectType(i);
        if (!this.mRenderThread.getIsRendering()) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.what = 6;
            this.mHandler.sendMessage(obtainMessage);
            return;
        }
        this.mRenderThread.setStopRender(true);
    }

    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        String str = TAG;
        Log.d(str, "provideAnimateElement, animateInElements" + list + "resetType = " + i2);
        if (this.mMimojiEditViewLayout != null && this.mMimojiEditViewLayout.getVisibility() == 0 && i2 == 3) {
            Log.d(TAG, "mimoji edit timeout");
            goBack(false, false);
            DataRepository.dataItemLive().getMimojiStatusManager().reset();
            if (this.mCurrentAlertDialog != null) {
                this.mIsShowDialog = false;
                this.mCurrentAlertDialog.dismiss();
                this.mCurrentAlertDialog = null;
            }
            ((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).getAnimationComposite().remove(getFragmentInto());
        }
    }

    /* access modifiers changed from: protected */
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(224, this);
    }

    public void releaseRender() {
        this.mMimojiEditGLTextureView.queueEvent(new Runnable() {
            public void run() {
                if (FragmentMimojiEdit.this.mAvatar != null) {
                    Log.d(FragmentMimojiEdit.TAG, "avatar releaseRender 2");
                    FragmentMimojiEdit.this.mAvatar.releaseRender();
                }
            }
        });
    }

    public void requestRender() {
        if (this.mMimojiEditGLTextureView != null) {
            this.mMimojiEditGLTextureView.requestRender();
        }
    }

    public void resetConfig() {
        this.mAvatarEngineManager = AvatarEngineManager.getInstance();
        this.mAvatar = this.mAvatarEngineManager.queryAvatar();
        if (this.mMimojiEditGLTextureView == null) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    FragmentMimojiEdit.this.startMimojiEdit(true, 105);
                    FragmentMimojiEdit.this.mMimojiEditGLTextureView.setupAvatar();
                    FragmentMimojiEdit.this.mAvatar.loadConfig(FragmentMimojiEdit.this.mIsStartEdit ? AvatarEngineManager.TempEditConfigPath : AvatarEngineManager.TempOriginalConfigPath);
                }
            });
            return;
        }
        this.mMimojiEditGLTextureView.setupAvatar();
        this.mAvatar.loadConfig(this.mIsStartEdit ? AvatarEngineManager.TempEditConfigPath : AvatarEngineManager.TempOriginalConfigPath);
    }

    public void startMimojiEdit(boolean z, final int i) {
        String str = TAG;
        Log.d(str, "startMimojiEdit：" + i);
        this.mSetupCompleted = false;
        if (this.mMimojiEditViewLayout == null) {
            this.mMimojiEditViewLayout = this.mMimojiEditViewStub.inflate();
            initMimojiEdit(this.mMimojiEditViewLayout);
        }
        if (this.mLevelRecyleView != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mLevelRecyleView.getLayoutParams();
            if (layoutParams != null) {
                if (!Util.isFullScreenNavBarHidden(getContext())) {
                    layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.mimoji_edit_config_bottom);
                } else {
                    layoutParams.bottomMargin = 0;
                }
            }
        }
        this.mIsSaveBtnClicked = false;
        ((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).getAnimationComposite().put(getFragmentInto(), this);
        this.mMimojiEditViewLayout.setVisibility(0);
        this.mMimojiEditGLTextureView.setStopRender(true);
        this.mMimojiEditGLTextureView.setVisibility(4);
        this.fromTag = i;
        this.mMimojiEditViewLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                FragmentMimojiEdit.this.mMimojiEditViewLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                FragmentMimojiEdit.this.mMimojiEditGLTextureView.setVisibility(0);
                if (i == 101) {
                    FragmentMimojiEdit.this.mMimojiPageChangeAnimManager.resetLayoutPosition(4);
                } else {
                    FragmentMimojiEdit.this.mMimojiPageChangeAnimManager.resetLayoutPosition(1);
                }
            }
        });
        if (z) {
            this.mSetupThread = new Thread(new Runnable() {
                public void run() {
                    FragmentMimojiEdit.this.doSetup();
                }
            });
            this.mSetupThread.start();
            return;
        }
        doSetup();
    }

    /* access modifiers changed from: protected */
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        unRegisterBackStack(modeCoordinator, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(224, this);
        DataRepository.dataItemLive().getMimojiStatusManager().setMode(MimojiStatusManager.MIMOJI_NONE);
        this.mIsStartEdit = false;
    }

    public void updateTitleState(int i) {
        switch (i) {
            case 1:
                this.mCurrentTopPannelState = 1;
                this.mBackTextView.setText(getResources().getString(R.string.mimoji_back));
                this.mBackTextView.setTextColor(getResources().getColor(R.color.white));
                this.mBackTextView.setClickable(true);
                this.mBackTextView.setVisibility(0);
                this.mConfirmTextView.setVisibility(8);
                if (this.mRlAllEditContent != null && !this.mIsStartEdit) {
                    this.mRlAllEditContent.setVisibility(8);
                    return;
                }
                return;
            case 2:
                this.mCurrentTopPannelState = 2;
                this.mRlAllEditContent.setVisibility(0);
                this.mBackTextView.setVisibility(0);
                this.mConfirmTextView.setVisibility(0);
                this.mBackTextView.setTextColor(getResources().getColor(R.color.white_alpha_4d));
                this.mBackTextView.setClickable(false);
                this.mConfirmTextView.setText(getResources().getString(R.string.mimoji_save));
                this.mBackTextView.setText(getResources().getString(R.string.mimoji_reset));
                this.mConfirmTextView.setClickable(true);
                this.mConfirmTextView.setTextColor(getResources().getColor(R.color.white));
                return;
            case 3:
                this.mCurrentTopPannelState = 3;
                this.mRlAllEditContent.setVisibility(0);
                this.mBackTextView.setVisibility(0);
                this.mConfirmTextView.setVisibility(0);
                this.mBackTextView.setTextColor(getResources().getColor(R.color.white));
                this.mConfirmTextView.setTextColor(getResources().getColor(R.color.white));
                this.mConfirmTextView.setClickable(true);
                this.mBackTextView.setClickable(true);
                this.mConfirmTextView.setClickable(true);
                this.mConfirmTextView.setText(getResources().getString(R.string.mimoji_save));
                this.mBackTextView.setText(getResources().getString(R.string.mimoji_reset));
                return;
            case 4:
                this.mCurrentTopPannelState = 4;
                this.mRlAllEditContent.setVisibility(0);
                this.mBackTextView.setVisibility(0);
                this.mConfirmTextView.setVisibility(0);
                this.mBackTextView.setTextColor(getResources().getColor(R.color.white));
                this.mBackTextView.setClickable(true);
                this.mBackTextView.setText(getResources().getString(R.string.mimoji_cancle));
                this.mConfirmTextView.setText(getResources().getString(R.string.mimoji_save));
                this.mConfirmTextView.setTextColor(getResources().getColor(R.color.white_alpha_4d));
                this.mConfirmTextView.setClickable(false);
                return;
            case 5:
                this.mConfirmTextView.setTextColor(getResources().getColor(R.color.white));
                this.mConfirmTextView.setClickable(true);
                return;
            default:
                return;
        }
    }
}

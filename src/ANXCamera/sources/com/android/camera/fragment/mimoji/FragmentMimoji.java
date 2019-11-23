package com.android.camera.fragment.mimoji;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import com.android.camera.fragment.DefaultItemAnimator;
import com.android.camera.fragment.RecyclerAdapterWrapper;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.fragment.live.FragmentLiveBase;
import com.android.camera.fragment.music.RoundedCornersTransformation;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.FileUtils;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStat;
import com.android.camera.statistic.CameraStatUtil;
import com.bumptech.glide.c;
import com.bumptech.glide.load.i;
import com.bumptech.glide.request.f;
import com.mi.config.b;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentMimoji extends FragmentLiveBase implements View.OnClickListener, ModeProtocol.MimojiAlert {
    public static final String ADD_STATE = "add_state";
    public static final String CLOSE_STATE = "close_state";
    private static final int FRAGMENT_INFO = 4095;
    private static final String TAG = FragmentMimoji.class.getSimpleName();
    /* access modifiers changed from: private */
    public BubbleEditMimojiPresenter bubbleEditMimojiPresenter;
    private Context mContext;
    private int mItemWidth;
    private LinearLayoutManager mLayoutManager;
    private LinearLayout mLlProgress;
    /* access modifiers changed from: private */
    public List<MimojiInfo> mMimojiInfoList;
    /* access modifiers changed from: private */
    public MimojiInfo mMimojiInfoSelect;
    /* access modifiers changed from: private */
    public MimojiItemAdapter mMimojiItemAdapter;
    private RecyclerView mMimojiRecylerView;
    private View mNoneItemView;
    /* access modifiers changed from: private */
    public View mNoneSelectItemView;
    private int mSelectIndex;
    private String mSelectState = CLOSE_STATE;
    private int mTotalWidth;
    private RelativeLayout popContainer;
    private RelativeLayout popParent;

    public class MimojiItemAdapter extends RecyclerView.Adapter<MimojiItemHolder> {
        private String adapterSelectState;
        /* access modifiers changed from: private */
        public List<MimojiInfo> datas = new ArrayList();
        private Context mContext;
        LayoutInflater mLayoutInflater;
        private View mSelectItemView;
        private MimojiInfo mimojiInfoSelected;

        class MimojiItemHolder extends CommonRecyclerViewHolder implements View.OnClickListener {
            public MimojiItemHolder(View view) {
                super(view);
                view.setOnClickListener(this);
            }

            public void onClick(View view) {
                int adapterPosition = getAdapterPosition() - 1;
                if (adapterPosition != -2) {
                    FragmentMimoji.this.onItemSelected((MimojiInfo) MimojiItemAdapter.this.datas.get(adapterPosition), adapterPosition, view, false);
                }
            }
        }

        public MimojiItemAdapter(Context context, String str) {
            this.mContext = context;
            this.adapterSelectState = str;
            this.mLayoutInflater = LayoutInflater.from(context);
        }

        public int getItemCount() {
            return FragmentMimoji.this.mMimojiInfoList.size();
        }

        public MimojiInfo getMimojiInfoSelected() {
            return this.mimojiInfoSelected;
        }

        public void onBindViewHolder(MimojiItemHolder mimojiItemHolder, int i) {
            ImageView imageView = (ImageView) mimojiItemHolder.getView(R.id.mimoji_item_image);
            this.mSelectItemView = mimojiItemHolder.getView(R.id.mimoji_item_selected_indicator);
            View view = mimojiItemHolder.getView(R.id.mimoji_long_item_selected_indicator);
            MimojiInfo mimojiInfo = this.datas.get(i);
            mimojiItemHolder.itemView.setTag(mimojiInfo);
            if (mimojiInfo != null && mimojiInfo.mConfigPath != null) {
                if (FragmentMimoji.ADD_STATE.equals(mimojiInfo.mConfigPath)) {
                    imageView.setImageResource(R.drawable.mimoji_add);
                } else {
                    c.g(this.mContext).m(mimojiInfo.mThumbnailUrl).b(f.a((i<Bitmap>) new RoundedCornersTransformation(10, 1))).a(imageView);
                }
                if (mimojiInfo == null || TextUtils.isEmpty(this.adapterSelectState) || TextUtils.isEmpty(mimojiInfo.mConfigPath) || !this.adapterSelectState.equals(mimojiInfo.mConfigPath) || mimojiInfo.mConfigPath.equals(FragmentMimoji.ADD_STATE)) {
                    this.mSelectItemView.setVisibility(8);
                    view.setVisibility(8);
                    this.mimojiInfoSelected = null;
                    return;
                }
                this.mSelectItemView.setVisibility(0);
                if (AvatarEngineManager.isPrefabModel(mimojiInfo.mConfigPath)) {
                    view.setVisibility(8);
                    this.mSelectItemView.setBackground(FragmentMimoji.this.getResources().getDrawable(R.drawable.bg_mimoji_animal_selected));
                } else {
                    view.setVisibility(0);
                    this.mSelectItemView.setBackground(FragmentMimoji.this.getResources().getDrawable(R.drawable.bg_mimoji_selected));
                }
                this.mimojiInfoSelected = mimojiInfo;
            }
        }

        public MimojiItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new MimojiItemHolder(this.mLayoutInflater.inflate(R.layout.fragment_mimoji_item, viewGroup, false));
        }

        public void setMimojiInfoList(List<MimojiInfo> list) {
            this.datas.clear();
            this.datas.addAll(list);
            notifyDataSetChanged();
        }

        public void updateSelect() {
            this.adapterSelectState = DataRepository.dataItemLive().getMimojiStatusManager().getCurrentMimojiState();
            notifyDataSetChanged();
        }

        public void updateSelect(String str) {
            this.adapterSelectState = str;
            notifyDataSetChanged();
        }
    }

    private boolean scrollIfNeed(int i) {
        if (i == this.mLayoutManager.findFirstVisibleItemPosition() || i == this.mLayoutManager.findFirstCompletelyVisibleItemPosition()) {
            this.mLayoutManager.scrollToPosition(Math.max(0, i - 1));
            return true;
        } else if (i != this.mLayoutManager.findLastVisibleItemPosition() && i != this.mLayoutManager.findLastCompletelyVisibleItemPosition()) {
            return false;
        } else {
            this.mLayoutManager.scrollToPosition(Math.min(i + 1, this.mMimojiItemAdapter.getItemCount() - 1));
            return true;
        }
    }

    private void setItemInCenter(int i) {
        this.mLayoutManager.scrollToPositionWithOffset(i, (this.mTotalWidth / 2) - (this.mItemWidth / 2));
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.mimoji_delete_dialog_title);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.mimoji_delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if (FragmentMimoji.this.mMimojiInfoSelect != null && !TextUtils.isEmpty(FragmentMimoji.this.mMimojiInfoSelect.mPackPath)) {
                    FileUtils.deleteFile(FragmentMimoji.this.mMimojiInfoSelect.mPackPath);
                    FragmentMimoji.this.bubbleEditMimojiPresenter.processBubbleAni(-2, -2, (View) null);
                    DataRepository.dataItemLive().getMimojiStatusManager().setCurrentMimojiState(FragmentMimoji.CLOSE_STATE);
                    FragmentMimoji.this.mNoneSelectItemView.setVisibility(0);
                    FragmentMimoji.this.mMimojiItemAdapter.updateSelect();
                    FragmentMimoji.this.filelistToMinojiInfo();
                    DataRepository.dataItemLive().getMimojiStatusManager().setmCurrentMimojiInfo((MimojiInfo) null);
                    ModeProtocol.MimojiAvatarEngine mimojiAvatarEngine = (ModeProtocol.MimojiAvatarEngine) ModeCoordinatorImpl.getInstance().getAttachProtocol(217);
                    if (mimojiAvatarEngine != null) {
                        mimojiAvatarEngine.onMimojiDeleted();
                    }
                    CameraStatUtil.trackMimojiClick(CameraStat.PARAM_MIMOJI_CLICK_DELETE);
                    CameraStatUtil.trackMimojiCount(Integer.toString(FragmentMimoji.this.mMimojiInfoList.size() - 4));
                }
            }
        });
        builder.setNegativeButton(R.string.mimoji_cancle, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    public void filelistToMinojiInfo() {
        this.mMimojiInfoList = new ArrayList();
        MimojiInfo mimojiInfo = new MimojiInfo();
        mimojiInfo.mConfigPath = ADD_STATE;
        mimojiInfo.mDirectoryName = Long.MAX_VALUE;
        this.mMimojiInfoList.add(mimojiInfo);
        ArrayList arrayList = new ArrayList();
        try {
            File file = new File(MimojiHelper.CUSTOM_DIR);
            if (file.isDirectory()) {
                for (File file2 : file.listFiles()) {
                    MimojiInfo mimojiInfo2 = new MimojiInfo();
                    mimojiInfo2.mAvatarTemplatePath = AvatarEngineManager.PersonTemplatePath;
                    String name = file2.getName();
                    String absolutePath = file2.getAbsolutePath();
                    String str = name + "config.dat";
                    String str2 = name + "pic.png";
                    if (file2.isDirectory()) {
                        String str3 = MimojiHelper.CUSTOM_DIR + name + "/" + str;
                        String str4 = MimojiHelper.CUSTOM_DIR + name + "/" + str2;
                        if (!FileUtils.checkFileConsist(str3) || !FileUtils.checkFileConsist(str4)) {
                            arrayList.add(absolutePath);
                        } else {
                            mimojiInfo2.mConfigPath = str3;
                            mimojiInfo2.mThumbnailUrl = str4;
                            mimojiInfo2.mPackPath = absolutePath;
                            mimojiInfo2.mDirectoryName = Long.valueOf(name).longValue();
                            this.mMimojiInfoList.add(mimojiInfo2);
                        }
                    } else {
                        arrayList.add(absolutePath);
                    }
                }
                Collections.sort(this.mMimojiInfoList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        MimojiInfo mimojiInfo3 = new MimojiInfo();
        mimojiInfo3.mAvatarTemplatePath = AvatarEngineManager.PigTemplatePath;
        mimojiInfo3.mConfigPath = AvatarEngineManager.FAKE_PIG_CONFIGPATH;
        mimojiInfo3.mThumbnailUrl = MimojiHelper.DATA_DIR + "/pig.png";
        this.mMimojiInfoList.add(mimojiInfo3);
        if (b.sQ || b.sR || b.sS) {
            MimojiInfo mimojiInfo4 = new MimojiInfo();
            mimojiInfo4.mAvatarTemplatePath = AvatarEngineManager.RoyanTemplatePath;
            mimojiInfo4.mConfigPath = AvatarEngineManager.FAKE_ROYAN_CONFIGPATH;
            mimojiInfo4.mThumbnailUrl = MimojiHelper.DATA_DIR + "/royan.png";
            this.mMimojiInfoList.add(mimojiInfo4);
        }
        MimojiInfo mimojiInfo5 = new MimojiInfo();
        mimojiInfo5.mAvatarTemplatePath = AvatarEngineManager.BearTemplatePath;
        mimojiInfo5.mConfigPath = AvatarEngineManager.FAKE_BEAR_CONFIGPATH;
        mimojiInfo5.mThumbnailUrl = MimojiHelper.DATA_DIR + "/bear.png";
        this.mMimojiInfoList.add(mimojiInfo5);
        MimojiInfo mimojiInfo6 = new MimojiInfo();
        mimojiInfo6.mAvatarTemplatePath = AvatarEngineManager.RabbitTemplatePath;
        mimojiInfo6.mConfigPath = AvatarEngineManager.FAKE_RABBIT_CONFIGPATH;
        mimojiInfo6.mThumbnailUrl = MimojiHelper.DATA_DIR + "/rabbit.png";
        this.mMimojiInfoList.add(mimojiInfo6);
        this.mMimojiItemAdapter.setMimojiInfoList(this.mMimojiInfoList);
        this.mMimojiItemAdapter.notifyDataSetChanged();
        for (int i = 0; i < arrayList.size(); i++) {
            FileUtils.deleteFile((String) arrayList.get(i));
        }
    }

    public void firstProgressShow(boolean z) {
        if (getActivity() == null) {
            Log.e(TAG, "not attached to Activity , skip     firstProgressShow........");
            return;
        }
        if (this.mLlProgress == null) {
            initView(getView());
        }
        if (z) {
            this.mLlProgress.setVisibility(0);
            this.mMimojiRecylerView.setVisibility(8);
            return;
        }
        this.mLlProgress.setVisibility(8);
        this.mMimojiRecylerView.setVisibility(0);
    }

    public int getFragmentInto() {
        return 4095;
    }

    /* access modifiers changed from: protected */
    public int getLayoutResourceId() {
        return R.layout.fragment_mimoji;
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        this.mItemWidth = getResources().getDimensionPixelSize(R.dimen.live_sticker_item_size);
        this.mTotalWidth = getResources().getDisplayMetrics().widthPixels;
        this.mContext = getContext();
        this.mNoneItemView = view.findViewById(R.id.mimoji_none_item);
        this.mMimojiRecylerView = (RecyclerView) view.findViewById(R.id.mimoji_list);
        this.popContainer = (RelativeLayout) view.findViewById(R.id.ll_bubble_pop_occupation);
        this.popParent = (RelativeLayout) view.findViewById(R.id.rl_bubble_pop_parent);
        this.mLlProgress = (LinearLayout) view.findViewById(R.id.ll_updating);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setChangeDuration(150);
        defaultItemAnimator.setMoveDuration(150);
        defaultItemAnimator.setAddDuration(150);
        this.mMimojiRecylerView.setItemAnimator(defaultItemAnimator);
        this.mNoneSelectItemView = view.findViewById(R.id.mimoji_none_selected_indicator);
        this.bubbleEditMimojiPresenter = new BubbleEditMimojiPresenter(getContext(), this, this.popParent);
        this.mMimojiRecylerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                FragmentMimoji.this.bubbleEditMimojiPresenter.processBubbleAni(-2, -2, (View) null);
            }
        });
        this.mNoneItemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FragmentMimoji.this.bubbleEditMimojiPresenter.processBubbleAni(-2, -2, (View) null);
                DataRepository.dataItemLive().getMimojiStatusManager().setCurrentMimojiState(FragmentMimoji.CLOSE_STATE);
                FragmentMimoji.this.mNoneSelectItemView.setVisibility(0);
                ModeProtocol.MimojiAvatarEngine mimojiAvatarEngine = (ModeProtocol.MimojiAvatarEngine) ModeCoordinatorImpl.getInstance().getAttachProtocol(217);
                if (mimojiAvatarEngine != null) {
                    mimojiAvatarEngine.onMimojiSelect((MimojiInfo) null);
                }
                if (FragmentMimoji.this.mMimojiItemAdapter != null) {
                    FragmentMimoji.this.mMimojiItemAdapter.updateSelect();
                }
                CameraStatUtil.trackMimojiClick(CameraStat.PARAM_MIMOJI_CLICK_NULL);
            }
        });
        this.mMimojiItemAdapter = new MimojiItemAdapter(getContext(), this.mSelectState);
        firstProgressShow(DataRepository.dataItemLive().getMimojiStatusManager().IsLoading());
        filelistToMinojiInfo();
        this.mLayoutManager = new LinearLayoutManagerWrapper(getContext(), Util.ALGORITHM_NAME_MIMOJI_CAPTURE);
        this.mLayoutManager.setOrientation(0);
        this.mMimojiRecylerView.setLayoutManager(this.mLayoutManager);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.live_share_item_margin);
        RecyclerAdapterWrapper recyclerAdapterWrapper = new RecyclerAdapterWrapper(this.mMimojiItemAdapter);
        Space space = new Space(getContext());
        space.setMinimumWidth(dimensionPixelSize);
        recyclerAdapterWrapper.addHeader(space);
        Space space2 = new Space(getContext());
        space2.setMinimumWidth(dimensionPixelSize);
        recyclerAdapterWrapper.addFooter(space2);
        this.mMimojiRecylerView.setAdapter(recyclerAdapterWrapper);
        this.mSelectIndex = -1;
        String currentMimojiState = DataRepository.dataItemLive().getMimojiStatusManager().getCurrentMimojiState();
        int i = 1;
        while (true) {
            if (i < this.mMimojiInfoList.size()) {
                if (!TextUtils.isEmpty(this.mMimojiInfoList.get(i).mConfigPath) && currentMimojiState.equals(this.mMimojiInfoList.get(i).mConfigPath)) {
                    this.mSelectIndex = i;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        setItemInCenter(this.mSelectIndex);
        if (currentMimojiState.equals(CLOSE_STATE)) {
            this.mNoneSelectItemView.setVisibility(0);
        } else {
            this.mMimojiItemAdapter.updateSelect(currentMimojiState);
            MimojiInfo mimojiInfoSelected = this.mMimojiItemAdapter.getMimojiInfoSelected();
            if (mimojiInfoSelected != null) {
                onItemSelected(mimojiInfoSelected, -1, (View) null, true);
            }
        }
        DataRepository.dataItemLive().getMimojiStatusManager().setMimojiPannelState(true);
    }

    /* access modifiers changed from: protected */
    public void onAddItemSelected() {
        this.mIsNeedShowWhenExit = false;
        ModeProtocol.MimojiAvatarEngine mimojiAvatarEngine = (ModeProtocol.MimojiAvatarEngine) ModeCoordinatorImpl.getInstance().getAttachProtocol(217);
        if (mimojiAvatarEngine != null) {
            mimojiAvatarEngine.onMimojiCreate();
        }
        ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
        if (actionProcessing != null) {
            actionProcessing.forceSwitchFront();
        }
    }

    public boolean onBackEvent(int i) {
        String str = TAG;
        Log.d(str, "onBackEvent = " + i);
        if (DataRepository.dataItemLive().getMimojiStatusManager().IsInMimojiEdit() && i != 4) {
            return false;
        }
        DataRepository.dataItemLive().getMimojiStatusManager().setMimojiPannelState(false);
        return super.onBackEvent(i);
    }

    public void onClick(View view) {
        switch (((Integer) view.getTag()).intValue()) {
            case 101:
                ModeProtocol.MimojiAvatarEngine mimojiAvatarEngine = (ModeProtocol.MimojiAvatarEngine) ModeCoordinatorImpl.getInstance().getAttachProtocol(217);
                if (mimojiAvatarEngine != null) {
                    mimojiAvatarEngine.releaseRender();
                }
                ModeProtocol.MimojiEditor mimojiEditor = (ModeProtocol.MimojiEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(224);
                if (mimojiEditor != null) {
                    mimojiEditor.directlyEnterEditMode(this.mMimojiInfoSelect, 101);
                }
                CameraStatUtil.trackMimojiClick(CameraStat.PARAM_MIMOJI_CLICK_EDIT);
                this.bubbleEditMimojiPresenter.processBubbleAni(-2, -2, (View) null);
                return;
            case 102:
                showAlertDialog();
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void onItemSelected(MimojiInfo mimojiInfo, int i, View view, boolean z) {
        if (mimojiInfo != null && !TextUtils.isEmpty(mimojiInfo.mConfigPath)) {
            String str = mimojiInfo.mConfigPath;
            String currentMimojiState = DataRepository.dataItemLive().getMimojiStatusManager().getCurrentMimojiState();
            if (!str.equals(ADD_STATE)) {
                DataRepository.dataItemLive().getMimojiStatusManager().setmCurrentMimojiInfo(mimojiInfo);
            }
            String str2 = TAG;
            Log.i(str2, "click　currentState:" + str + " lastState:" + currentMimojiState);
            this.bubbleEditMimojiPresenter.processBubbleAni(-2, -2, (View) null);
            if (ADD_STATE.equals(mimojiInfo.mConfigPath)) {
                onAddItemSelected();
                CameraStatUtil.trackMimojiClick(CameraStat.PARAM_MIMOJI_CLICK_ADD);
            } else if (!z) {
                int findFirstVisibleItemPosition = this.mLayoutManager.findFirstVisibleItemPosition();
                int findFirstCompletelyVisibleItemPosition = this.mLayoutManager.findFirstCompletelyVisibleItemPosition();
                if (i == findFirstVisibleItemPosition || i == findFirstCompletelyVisibleItemPosition || i == findFirstCompletelyVisibleItemPosition - 2) {
                    this.mLayoutManager.scrollToPosition(Math.max(0, i - 1));
                } else if (i == this.mLayoutManager.findLastVisibleItemPosition() || i == this.mLayoutManager.findLastCompletelyVisibleItemPosition()) {
                    this.mLayoutManager.scrollToPosition(Math.min(i + 1, this.mMimojiItemAdapter.getItemCount() - 1));
                } else if (i == this.mLayoutManager.findLastVisibleItemPosition() - 1 || i == this.mLayoutManager.findLastCompletelyVisibleItemPosition() - 1) {
                    this.mLayoutManager.scrollToPosition(Math.min(i + 2, this.mMimojiItemAdapter.getItemCount()));
                } else {
                    processBubble(mimojiInfo, str, currentMimojiState, view, z);
                }
                setAvatarAndSelect(str, mimojiInfo);
            } else {
                processBubble(mimojiInfo, str, currentMimojiState, view, z);
                setAvatarAndSelect(str, mimojiInfo);
            }
        }
    }

    public void processBubble(MimojiInfo mimojiInfo, String str, String str2, View view, boolean z) {
        boolean isPrefabModel = AvatarEngineManager.isPrefabModel(mimojiInfo.mConfigPath);
        if (str.equals(str2) && !str2.equals(ADD_STATE) && !str2.equals(CLOSE_STATE) && !z && !isPrefabModel) {
            this.mMimojiInfoSelect = mimojiInfo;
            int width = view.getWidth();
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            int i = iArr[0];
            int height = this.popContainer.getHeight();
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.mimoji_edit_bubble_width) / 2;
            int i2 = height - dimensionPixelSize;
            String str3 = TAG;
            Log.i(str3, "coordinateY:" + i2);
            this.bubbleEditMimojiPresenter.processBubbleAni((i + (width / 2)) - dimensionPixelSize, i2, view);
        }
    }

    public int refreshMimojiList() {
        if (this.mMimojiItemAdapter == null) {
            return 0;
        }
        Log.d(TAG, "refreshMimojiList");
        filelistToMinojiInfo();
        this.mSelectIndex = -1;
        String currentMimojiState = DataRepository.dataItemLive().getMimojiStatusManager().getCurrentMimojiState();
        int i = 1;
        while (true) {
            if (i < this.mMimojiInfoList.size()) {
                if (!TextUtils.isEmpty(this.mMimojiInfoList.get(i).mConfigPath) && currentMimojiState.equals(this.mMimojiInfoList.get(i).mConfigPath)) {
                    this.mSelectIndex = i;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        setItemInCenter(this.mSelectIndex);
        this.mMimojiItemAdapter.updateSelect();
        return this.mMimojiInfoList.size() - 4;
    }

    /* access modifiers changed from: protected */
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        ModeCoordinatorImpl.getInstance().attachProtocol(226, this);
    }

    public void setAvatarAndSelect(String str, MimojiInfo mimojiInfo) {
        this.mNoneSelectItemView.setVisibility(8);
        DataRepository.dataItemLive().getMimojiStatusManager().setmCurrentMimojiInfo(mimojiInfo);
        this.mMimojiItemAdapter.updateSelect();
        ModeProtocol.MimojiAvatarEngine mimojiAvatarEngine = (ModeProtocol.MimojiAvatarEngine) ModeCoordinatorImpl.getInstance().getAttachProtocol(217);
        if (mimojiAvatarEngine != null) {
            mimojiAvatarEngine.onMimojiSelect(mimojiInfo);
        }
    }

    /* access modifiers changed from: protected */
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        this.bubbleEditMimojiPresenter.processBubbleAni(-2, -2, (View) null);
        ModeCoordinatorImpl.getInstance().detachProtocol(226, this);
        DataRepository.dataItemLive().getMimojiStatusManager().setMimojiPannelState(false);
    }
}

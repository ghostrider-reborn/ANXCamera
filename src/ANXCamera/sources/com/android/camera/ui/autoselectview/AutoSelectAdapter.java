package com.android.camera.ui.autoselectview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.android.camera.fragment.mimoji.MimojiTypeAdapter;
import com.android.camera.ui.autoselectview.SelectItemBean;
import java.util.ArrayList;

public abstract class AutoSelectAdapter<T extends SelectItemBean> extends RecyclerView.Adapter<AutoSelectViewHolder> implements OnPositionChangedListener {
    private ArrayList<T> mDdataList;
    private int mLastMiddlePosition = 0;
    private int mLastSelectPosition = -1;
    private RecyclerView recyclerView;

    public AutoSelectAdapter(ArrayList<T> arrayList) {
        this.mDdataList = arrayList;
    }

    public synchronized void addData(T t) {
        if (this.mDdataList != null) {
            this.mDdataList.add(t);
            notifyItemInserted(getItemCount());
        }
    }

    public ArrayList<T> getDataList() {
        return this.mDdataList;
    }

    public int getItemCount() {
        ArrayList<T> arrayList = this.mDdataList;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getLastSelectPosition() {
        return this.mLastSelectPosition;
    }

    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView2) {
        super.onAttachedToRecyclerView(recyclerView2);
        this.recyclerView = recyclerView2;
    }

    public void onBindViewHolder(@NonNull AutoSelectViewHolder autoSelectViewHolder, int i) {
        autoSelectViewHolder.setData((SelectItemBean) this.mDdataList.get(i), i);
    }

    public void onMoveMiddlePoisionChanged(int i, boolean z) {
        if (z) {
            int i2 = this.mLastSelectPosition;
            if (i2 != i) {
                updateDataAlpha(i2, 0);
                updateDataAlpha(i, 1);
            }
            int i3 = this.mLastMiddlePosition;
            if (i3 != i) {
                updateDataAlpha(i3, 0);
                updateDataAlpha(i, 1);
                this.mLastMiddlePosition = i;
                return;
            }
            return;
        }
        updateDataAlpha(this.mLastMiddlePosition, 0);
    }

    public void onSelectedPositionChanged(int i) {
        int i2 = this.mLastMiddlePosition;
        if (i2 != i) {
            updateDataAlpha(i2, 0);
            updateDataAlpha(i, 1);
        }
        int i3 = this.mLastSelectPosition;
        if (i3 != i) {
            updateDataAlpha(i3, 0);
            updateDataAlpha(i, 1);
            this.mLastSelectPosition = i;
            onSelectedPositionFinish(i);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001b, code lost:
        return;
     */
    public synchronized void removeData(int i) {
        if (this.mDdataList != null && i >= 0) {
            if (i <= getItemCount() - 1) {
                this.mDdataList.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    public synchronized void setDataList(ArrayList<T> arrayList) {
        this.mDdataList = arrayList;
        this.mLastSelectPosition = -1;
        this.mLastMiddlePosition = 0;
        notifyDataSetChanged();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001b, code lost:
        return;
     */
    public synchronized void updateData(int i, T t) {
        if (this.mDdataList != null && i >= 0) {
            if (i <= getItemCount() - 1) {
                this.mDdataList.set(i, t);
                notifyItemChanged(i);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0040, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0042, code lost:
        return;
     */
    public synchronized void updateDataAlpha(int i, int i2) {
        if (this.mDdataList != null && i >= 0) {
            boolean z = true;
            if (i <= getItemCount() - 1) {
                ((SelectItemBean) this.mDdataList.get(i)).setAlpha(i2);
                if (this.recyclerView != null) {
                    long j = (long) i;
                    if (this.recyclerView.findViewHolderForItemId(j) != null) {
                        MimojiTypeAdapter.TypeViewHolder typeViewHolder = (MimojiTypeAdapter.TypeViewHolder) this.recyclerView.findViewHolderForItemId(j);
                        TextView typeView = typeViewHolder.getTypeView();
                        if (i2 != 1) {
                            z = false;
                        }
                        typeViewHolder.setSelectState(typeView, z);
                    }
                }
                notifyItemChanged(i);
            }
        }
    }
}

package com.android.camera.ui.autoselectview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import java.util.concurrent.atomic.AtomicBoolean;

public class AutoSelectHorizontalView extends RecyclerView {
    private AtomicBoolean isClickMove = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public boolean isFirstPositionChanged = true;
    /* access modifiers changed from: private */
    public boolean isInit;
    private boolean isMoveFinished = true;
    private boolean isOverSpeed = false;
    /* access modifiers changed from: private */
    public AutoSelectAdapter mAutoSelectAdapter;
    private int mDeltaX;
    /* access modifiers changed from: private */
    public int mInitPosition = 0;
    private int mLastMoveX;
    /* access modifiers changed from: private */
    public LinearLayoutManagerWrapper mLinearLayoutManager;
    private Scroller mScroller;
    private int mSelectPosition = this.mInitPosition;
    private int mTempSelectPosition = this.mSelectPosition;
    /* access modifiers changed from: private */
    public WrapperAdapter mWrapAdapter;

    class WrapperAdapter extends RecyclerView.Adapter {
        private static final int HEADER_FOOTER_TYPE = -1;
        private RecyclerView.Adapter adapter;
        /* access modifiers changed from: private */
        public Context context;
        private int headerFooterWidth;
        private int itemPageCount = 5;
        private int itemWidth;

        class HeaderFooterViewHolder extends RecyclerView.ViewHolder {
            HeaderFooterViewHolder(View view) {
                super(view);
            }
        }

        public WrapperAdapter(RecyclerView.Adapter adapter2, Context context2) {
            this.adapter = adapter2;
            this.context = context2;
        }

        private boolean isHeaderOrFooter(int i) {
            return i == 0 || i == getItemCount() - 1;
        }

        public int getHeaderFooterWidth() {
            return this.headerFooterWidth;
        }

        public int getItemCount() {
            return this.adapter.getItemCount() + 2;
        }

        public long getItemId(int i) {
            if (i <= 0 || i > this.adapter.getItemCount()) {
                return -1;
            }
            return this.adapter.getItemId(i - 1);
        }

        public int getItemViewType(int i) {
            if (i == 0 || i == getItemCount() - 1) {
                return -1;
            }
            return this.adapter.getItemViewType(i - 1);
        }

        public int getItemWidth() {
            return this.itemWidth;
        }

        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            this.adapter.onAttachedToRecyclerView(recyclerView);
        }

        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            if (!isHeaderOrFooter(i)) {
                this.adapter.onBindViewHolder(viewHolder, i - 1);
            }
        }

        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            this.itemWidth = ((viewGroup.getMeasuredWidth() / this.itemPageCount) * 11) / 10;
            if (i == -1) {
                View view = new View(this.context);
                this.headerFooterWidth = (viewGroup.getMeasuredWidth() / 2) - (this.itemWidth / 2);
                view.setLayoutParams(new RecyclerView.LayoutParams(this.headerFooterWidth, -1));
                return new HeaderFooterViewHolder(view);
            }
            RecyclerView.ViewHolder onCreateViewHolder = this.adapter.onCreateViewHolder(viewGroup, i);
            ViewGroup.LayoutParams layoutParams = onCreateViewHolder.itemView.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = this.itemWidth;
                onCreateViewHolder.itemView.setLayoutParams(layoutParams);
            }
            return onCreateViewHolder;
        }

        public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
            super.onDetachedFromRecyclerView(recyclerView);
            this.adapter.onDetachedFromRecyclerView(recyclerView);
        }
    }

    public AutoSelectHorizontalView(Context context) {
        super(context);
        init();
    }

    public AutoSelectHorizontalView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public AutoSelectHorizontalView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void calculateSelectedPos() {
        int itemWidth = this.mWrapAdapter.getItemWidth();
        if (itemWidth != 0) {
            int i = this.mDeltaX / itemWidth;
            if (this.mDeltaX % itemWidth > itemWidth / 2) {
                this.mSelectPosition = i + 1;
            } else {
                this.mSelectPosition = i;
            }
        }
    }

    private void correctDeltax(RecyclerView.Adapter adapter) {
        if (adapter.getItemCount() <= this.mSelectPosition) {
            this.mDeltaX -= this.mWrapAdapter.getItemWidth() * ((this.mSelectPosition - adapter.getItemCount()) + 1);
        }
        calculateSelectedPos();
    }

    private void init() {
        this.mScroller = new Scroller(getContext(), new DecelerateInterpolator());
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (!AutoSelectHorizontalView.this.isInit) {
                    return;
                }
                if (AutoSelectHorizontalView.this.mAutoSelectAdapter == null) {
                    Log.e("View", "mAutoSelectAdapter  不能为空");
                    return;
                }
                if (AutoSelectHorizontalView.this.mInitPosition >= AutoSelectHorizontalView.this.mAutoSelectAdapter.getItemCount()) {
                    int unused = AutoSelectHorizontalView.this.mInitPosition = AutoSelectHorizontalView.this.mAutoSelectAdapter.getItemCount() - 1;
                }
                if (AutoSelectHorizontalView.this.mInitPosition < 0) {
                    int unused2 = AutoSelectHorizontalView.this.mInitPosition = 0;
                }
                if (AutoSelectHorizontalView.this.isFirstPositionChanged) {
                    AutoSelectHorizontalView.this.selectedPositionChanged(AutoSelectHorizontalView.this.mInitPosition);
                }
                AutoSelectHorizontalView.this.mLinearLayoutManager.scrollToPositionWithOffset(0, (-AutoSelectHorizontalView.this.mInitPosition) * AutoSelectHorizontalView.this.mWrapAdapter.getItemWidth());
                boolean unused3 = AutoSelectHorizontalView.this.isInit = false;
            }
        });
    }

    private void onDataAdd(int i) {
        selectedPositionChanged(this.mSelectPosition);
    }

    /* access modifiers changed from: private */
    public void onDataChanged() {
        selectedPositionChanged(this.mSelectPosition);
    }

    private void onDataRemove(int i) {
        if (i <= this.mSelectPosition) {
            correctDeltax(this.mAutoSelectAdapter);
            selectedPositionChanged(this.mSelectPosition);
            return;
        }
        correctDeltax(this.mAutoSelectAdapter);
    }

    public void computeScroll() {
        super.computeScroll();
        if (this.mScroller.computeScrollOffset()) {
            int currX = this.mScroller.getCurrX() - this.mLastMoveX;
            this.mLastMoveX += currX;
            scrollBy(currX, 0);
        } else if (this.mScroller.isFinished() && !this.isMoveFinished) {
            calculateSelectedPos();
            selectedPositionChanged(this.mSelectPosition);
            this.isMoveFinished = true;
            this.isClickMove.set(false);
        }
    }

    public boolean fling(int i, int i2) {
        smoothMoveToPosition(i);
        return false;
    }

    public void moveMiddlePositionChanged(int i, boolean z) {
        if (this.mAutoSelectAdapter != null) {
            this.mAutoSelectAdapter.onMoveMiddlePoisionChanged(i, z);
        }
    }

    public void moveToPosition(int i) {
        if (i < 0) {
            i = 0;
        }
        if (i >= this.mAutoSelectAdapter.getItemCount()) {
            i = this.mAutoSelectAdapter.getItemCount() - 1;
        }
        this.isClickMove.set(true);
        this.mLastMoveX = 0;
        this.isMoveFinished = false;
        int itemWidth = this.mWrapAdapter.getItemWidth();
        if (i != this.mSelectPosition) {
            this.mScroller.startScroll(getScrollX(), getScrollY(), (i - this.mSelectPosition) * itemWidth, 0);
            postInvalidate();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mAutoSelectAdapter != null) {
            this.mAutoSelectAdapter = null;
        }
        if (this.mWrapAdapter != null) {
            Context unused = this.mWrapAdapter.context = null;
            this.mWrapAdapter = null;
        }
    }

    public void onScrollStateChanged(int i) {
        super.onScrollStateChanged(i);
        if (i == 0 && this.mWrapAdapter != null) {
            int itemWidth = this.mWrapAdapter.getItemWidth();
            if (itemWidth != 0) {
                calculateSelectedPos();
                int i2 = this.mDeltaX % itemWidth;
                if (Math.abs(i2) == 0) {
                    selectedPositionChanged(this.mSelectPosition);
                    this.isOverSpeed = false;
                    this.isClickMove.set(false);
                } else if (Math.abs(i2) <= itemWidth / 2) {
                    smoothScrollBy(-i2, 0);
                } else if (i2 > 0) {
                    smoothScrollBy(itemWidth - i2, 0);
                } else {
                    smoothScrollBy(-(itemWidth + i2), 0);
                }
            }
        }
    }

    public void onScrolled(int i, int i2) {
        super.onScrolled(i, i2);
        this.mTempSelectPosition = this.mSelectPosition;
        this.mDeltaX += i;
        calculateSelectedPos();
        if (!this.isClickMove.get() && this.mTempSelectPosition != this.mSelectPosition) {
            if (this.isOverSpeed) {
                this.isClickMove.set(true);
                moveMiddlePositionChanged(this.mSelectPosition, false);
                return;
            }
            moveMiddlePositionChanged(this.mSelectPosition, true);
        }
    }

    public void selectedPositionChanged(int i) {
        if (this.mAutoSelectAdapter != null) {
            this.mAutoSelectAdapter.onSelectedPositionChanged(i);
        }
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (!(adapter instanceof AutoSelectAdapter)) {
            Log.e(getClass().getSimpleName(), "mAutoSelectAdapter must extends AutoSelectAdapter<T extends SelectItemBean> ");
            return;
        }
        this.mAutoSelectAdapter = (AutoSelectAdapter) adapter;
        this.mWrapAdapter = new WrapperAdapter(adapter, getContext());
        this.mWrapAdapter.setHasStableIds(true);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            public void onChanged() {
                super.onChanged();
                AutoSelectHorizontalView.this.mWrapAdapter.notifyDataSetChanged();
                AutoSelectHorizontalView.this.onDataChanged();
            }

            public void onItemRangeChanged(int i, int i2) {
                super.onItemRangeChanged(i, i2);
                AutoSelectHorizontalView.this.mWrapAdapter.notifyItemRangeChanged(i + 1, i2);
            }

            public void onItemRangeInserted(int i, int i2) {
                AutoSelectHorizontalView.this.mWrapAdapter.notifyDataSetChanged();
            }

            public void onItemRangeRemoved(int i, int i2) {
                AutoSelectHorizontalView.this.mWrapAdapter.notifyDataSetChanged();
            }
        });
        this.mDeltaX = 0;
        if (this.mLinearLayoutManager == null) {
            this.mLinearLayoutManager = new LinearLayoutManagerWrapper(getContext(), "autoselect");
        }
        this.mLinearLayoutManager.setOrientation(0);
        super.setLayoutManager(this.mLinearLayoutManager);
        super.setAdapter(this.mWrapAdapter);
        this.isInit = true;
    }

    public void setInitPosition(int i) {
        if (this.mAutoSelectAdapter != null) {
            Log.e("View", "This method should be called before setAdapter()!");
            return;
        }
        if (i < 0) {
            i = 0;
        }
        this.mInitPosition = i;
        this.mSelectPosition = i;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (!(layoutManager instanceof LinearLayoutManagerWrapper)) {
            Log.e("View", "The LayoutManager here must be LinearLayoutManager!");
        } else {
            this.mLinearLayoutManager = (LinearLayoutManagerWrapper) layoutManager;
        }
    }

    public void smoothMoveToPosition(int i) {
        int finalX;
        if (this.mAutoSelectAdapter != null && this.mWrapAdapter != null) {
            if (Math.abs(i) > 4000) {
                this.isOverSpeed = true;
                this.isClickMove.set(true);
                this.isMoveFinished = false;
            }
            int i2 = this.mSelectPosition + (i / 1000);
            if (i2 < 0) {
                i2 = 0;
            }
            if (i2 >= this.mAutoSelectAdapter.getItemCount()) {
                i2 = this.mAutoSelectAdapter.getItemCount() - 1;
            }
            Log.w("View", "fling  速度太快啦   position : " + i2);
            this.mLastMoveX = 0;
            int itemWidth = this.mWrapAdapter.getItemWidth();
            int itemCount = this.mAutoSelectAdapter.getItemCount() * itemWidth;
            if (Math.abs(i) < 9000) {
                this.mScroller.startScroll(getScrollX(), getScrollY(), ((i2 - (this.mDeltaX / itemWidth)) * itemWidth) - (this.mDeltaX % itemWidth), 0);
                Log.w("View", "fling 9000以内 finalX: " + finalX);
                this.mScroller.setFinalX(finalX);
            } else if (i < 0) {
                this.mScroller.startScroll(getScrollX(), getScrollY(), -this.mDeltaX, 0);
            } else {
                this.mScroller.startScroll(getScrollX(), getScrollY(), itemCount - this.mDeltaX, 0);
            }
            postInvalidate();
        }
    }
}

package com.karimsabitov.headmanlog.UI;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
/**
 * Created by User on 25.09.2018.
 */

public class RecyclerViewEmptySupport extends RecyclerView {

    private static final String TAG = "Test";
    private View mEmptyView;

    private AdapterDataObserver mObserver = new AdapterDataObserver() {

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            showEmptyView();
            Log.d(TAG, "onItemRangeInserted: ");
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            showEmptyView();
            Log.d(TAG, "onItemRangeRemoved: ");
        }

        @Override
        public void onChanged() {
            super.onChanged();
            showEmptyView();
            Log.d(TAG, "onChanged: ");
        }


    };

    private void showEmptyView() {
        Adapter<?> adapter = getAdapter();
        if (adapter != null && mEmptyView != null){
            if (adapter.getItemCount() == 0){
                mEmptyView.setVisibility(VISIBLE);
                RecyclerViewEmptySupport.this.setVisibility(GONE);
            }else{
                mEmptyView.setVisibility(GONE);
                RecyclerViewEmptySupport.this.setVisibility(VISIBLE);
            }
        }

    }


    public RecyclerViewEmptySupport(Context context) {
        super(context);
    }

    public RecyclerViewEmptySupport(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewEmptySupport(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if(adapter != null){
            Log.d(TAG, "setAdapter: ");
            //adapter.unregisterAdapterDataObserver(mObserver);
            adapter.registerAdapterDataObserver(mObserver);
            showEmptyView();
        }
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
        showEmptyView();
    }
}

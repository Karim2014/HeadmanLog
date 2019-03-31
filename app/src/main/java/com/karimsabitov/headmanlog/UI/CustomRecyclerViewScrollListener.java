package com.karimsabitov.headmanlog.UI;

import android.support.v7.widget.RecyclerView;

/**
 * Created by User on 19.07.2018.
 */

public abstract class CustomRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    int mScrollDist = 0;
    boolean mIsVisible = true;
    static final float MINIMUM = 20;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (mIsVisible && mScrollDist > MINIMUM){
            hide();
            mScrollDist = 0;
            mIsVisible = false;
        }else if (!mIsVisible && mScrollDist < -MINIMUM){
            show();
            mScrollDist = 0;
            mIsVisible = true;
        }
        if ((mIsVisible && dy > 0) || (!mIsVisible && dy < 0)){
            mScrollDist += dy;
        }

    }

    public abstract void show();

    public abstract void hide();
}

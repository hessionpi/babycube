package com.rjzd.baby.ui.adapter.recycleadapter;

import android.view.View;

/**
 * create time: 2018/5/25  15:05
 * create author: Hition
 * descriptions: EventDelegate
 */

public interface EventDelegate {
    void addData(int length);
    void clear();

    void stopLoadMore();
    void pauseLoadMore();
    void resumeLoadMore();

    void setMore(View view, XMBaseAdapter.OnLoadMoreListener listener);
    void setNoMore(View view);
    void setErrorMore(View view);
}
package com.rjzd.baby.presenter.impl;

import com.rjzd.baby.presenter.IPresenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * create time: 2018/5/25  11:19
 * create author: Hition
 * descriptions: BasePresenter
 */
public class BasePresenter implements IPresenter {

    protected CompositeSubscription mCompositeSubscription;

    /**
     * 防止由于没有及时取消，Activity/Fragment无法销毁导致的内存泄露
     */
    @Override
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void addSubscription(Subscription subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscriber);
    }
}

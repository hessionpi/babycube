package com.rjzd.baby.presenter;

import rx.Subscription;

/**
 * create time: 2018/5/25
 * create author: Hition
 * descriptions: IPresenter
 */
public interface IPresenter {

    // 取消订阅，防止出现内存泄漏
    void onUnsubscribe();

    // 订阅事件
    void addSubscription(Subscription subscriber);
}

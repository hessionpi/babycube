package com.rjzd.baby.presenter.impl;

import com.rjzd.baby.api.ErrorException;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.model.IListener;
import com.rjzd.baby.presenter.IPresenter;
import com.rjzd.baby.view.IView;
import java.net.SocketTimeoutException;
import retrofit2.HttpException;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * create time: 2018/5/25  11:19
 * create author: Hition
 * descriptions: BasePresenter
 */
public abstract class BasePresenter implements IPresenter,IListener {

    private CompositeSubscription mCompositeSubscription;
    IView mView;

    BasePresenter(IView view) {
        this.mView = view;
    }

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

    @Override
    public void onSuccess(BaseResponse data, int flag) {
        mView.onComplete(data,flag);
    }

    @Override
    public void onFailed(Throwable e, int flag) {
        if(e instanceof HttpException){
            // 网络异常
            mView.onFailShow(ErrorException.HTTP_ERROR);
        }else if(e instanceof SocketTimeoutException){
            // 网络连接超时
            mView.onFailShow(ErrorException.SOCKET_TIMEOUT);
        }
    }
}

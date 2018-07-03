package com.rjzd.baby.model.imp;

import com.rjzd.baby.BabyConstants;
import com.rjzd.baby.api.SystemAPI;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.model.IListener;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * create time: 2018/6/8  10:39
 * create author: Hition
 * descriptions: 处理系统类的model，发验证码、检查更新等
 */

public class SystemModel {

    private IListener listener;
    private SystemAPI api;

    public SystemModel(IListener listener) {
        this.listener = listener;
        api = new SystemAPI();
    }

    /**
     * 发送短信验证码
     * @param mobile                手机号
     */
    public Subscription sendSMSCode(String mobile){
        Observable<BaseResponse> observable = api.sendSMSCode(mobile);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t, BabyConstants.SYSTEM_SEND_SMS_CODE);
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        listener.onSuccess(baseResponse, BabyConstants.SYSTEM_SEND_SMS_CODE);
                    }
                });
    }









}

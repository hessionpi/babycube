package com.rjzd.baby.presenter.impl;

import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.model.IListener;
import com.rjzd.baby.model.imp.SystemModel;
import com.rjzd.baby.view.IView;

/**
 * create time: 2018/6/8  10:45
 * create author: Hition
 * descriptions: SystemPresenter
 */

public class SystemPresenter extends BasePresenter implements IListener{

    private IView mView;
    private SystemModel mModel;

    public SystemPresenter(IView mView){
        this.mView = mView;
        this.mModel = new SystemModel(this);
    }

    public void sendSMSCode(String mobile){
        addSubscription(mModel.sendSMSCode(mobile));
    }


    @Override
    public void onSuccess(BaseResponse data, int flag) {
        mView.onComplete(data,flag);
    }

    @Override
    public void onFailed(Throwable e, int flag) {
        mView.onFailShow(flag);
    }
}

package com.rjzd.baby.presenter.impl;

import com.rjzd.baby.model.IListener;
import com.rjzd.baby.model.imp.SystemModel;
import com.rjzd.baby.view.IView;


/**
 * create time: 2018/6/8  10:45
 * create author: Hition
 * descriptions: SystemPresenter
 */

public class SystemPresenter extends BasePresenter implements IListener{

    private SystemModel mModel;

    public SystemPresenter(IView mView){
        super(mView);
        this.mModel = new SystemModel(this);
    }

    public void sendSMSCode(String mobile){
        addSubscription(mModel.sendSMSCode(mobile));
    }

}

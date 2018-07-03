package com.rjzd.baby.presenter.impl;

import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.model.IListener;
import com.rjzd.baby.model.imp.UserModel;
import com.rjzd.baby.view.IView;
import com.zd.baby.api.model.ReqOAuthLogin;
import com.zd.baby.api.model.ReqUpdateUserInfo;

/**
 * create time: 2018/6/8  11:32
 * create author: Hition
 * descriptions: UserPresenter，用来处理用户相关逻辑的presenter
 */

public class UserPresenter extends BasePresenter implements IListener {

    private IView mView;
    private UserModel mModel;

    public UserPresenter(IView mView){
        this.mView = mView;
        this.mModel = new UserModel(this);
    }

    public void register(String smsCode, String mobile){
        addSubscription(mModel.register(smsCode,mobile));
    }

    public void login(String smsCode, String mobile, String password){
        addSubscription(mModel.login(smsCode,mobile,password));
    }

    public void oauthLogin(ReqOAuthLogin request){
        addSubscription(mModel.oauthLogin(request));
    }

    public void getUserInfo(){
        addSubscription(mModel.getUserInfo());
    }

    public void updateUserInfo(ReqUpdateUserInfo request){
        addSubscription(mModel.updateUserInfo(request));
    }








    @Override
    public void onSuccess(BaseResponse data, int flag) {
        mView.onComplete(data, flag);
    }

    @Override
    public void onFailed(Throwable e, int flag) {
        mView.onFailShow(flag);
    }
}

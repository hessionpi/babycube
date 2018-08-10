package com.rjzd.baby.model.imp;

import com.rjzd.baby.BabyConstants;
import com.rjzd.baby.api.UserAPI;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.model.IListener;
import com.zd.baby.api.model.ReqOAuthLogin;
import com.zd.baby.api.model.ReqUpdateUserInfo;
import com.zd.baby.api.model.ResGetUserInfo;
import com.zd.baby.api.model.ResLogin;
import com.zd.baby.api.model.ResOAuthLogin;
import com.zd.baby.api.model.ResRegister;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * create time: 2018/6/8  10:50
 * create author: Hition
 * descriptions: UserModel
 */

public class UserModel {

    private IListener listener;
    private UserAPI api;

    public UserModel(IListener listener){
        this.listener = listener;
        api = new UserAPI();
    }

    /**
     * 用户注册
     * @param smsCode           短信验证码
     * @param mobile            手机号
     */
    public Subscription register(String smsCode, String mobile){
        Observable<BaseResponse<ResRegister>> observable = api.register(smsCode,mobile);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<ResRegister>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t, BabyConstants.USER_REGISTER);
                    }

                    @Override
                    public void onNext(BaseResponse<ResRegister> baseResponse) {
                        listener.onSuccess(baseResponse, BabyConstants.USER_REGISTER);
                    }
                });
    }

    /**
     * 用户登录（动态密码登录/账号密码登录）
     * @param smsCode               短信验证码
     * @param mobile                手机号
     */
    public Subscription login(String smsCode, String mobile){
        Observable<BaseResponse<ResLogin>> observable = api.login(smsCode,mobile);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<ResLogin>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t, BabyConstants.USER_LOGIN);
                    }

                    @Override
                    public void onNext(BaseResponse<ResLogin> baseResponse) {
                        listener.onSuccess(baseResponse, BabyConstants.USER_LOGIN);
                    }
                });
    }

    /**
     * 第三方平台登录
     * @param  request     请求body
     */
    public Subscription oauthLogin(ReqOAuthLogin request ){
        Observable<BaseResponse<ResOAuthLogin>> observable = api.oauthLogin(request);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<ResOAuthLogin>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t, BabyConstants.USER_OAUTHLOGIN);
                    }

                    @Override
                    public void onNext(BaseResponse<ResOAuthLogin> baseResponse) {
                        listener.onSuccess(baseResponse, BabyConstants.USER_OAUTHLOGIN);
                    }
                });
    }

    /**
     * 获取用户个人资料信息
     */
    public Subscription getUserInfo(){
        Observable<BaseResponse<ResGetUserInfo>> observable = api.getUserInfo();
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<ResGetUserInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t, BabyConstants.USER_GET_USERUINFO);
                    }

                    @Override
                    public void onNext(BaseResponse<ResGetUserInfo> baseResponse) {
                        listener.onSuccess(baseResponse, BabyConstants.USER_GET_USERUINFO);
                    }
                });
    }

    /**
     * 修改用户个人资料
     * @param request                   请求实体
     */
    public Subscription updateUserInfo(ReqUpdateUserInfo request){
        Observable<BaseResponse> observable = api.updateUserInfo(request);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t, BabyConstants.USER_UPDATE_USERUINFO);
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        listener.onSuccess(baseResponse, BabyConstants.USER_UPDATE_USERUINFO);
                    }
                });
    }













}

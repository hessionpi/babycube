package com.rjzd.baby.api;

import com.google.gson.Gson;
import com.rjzd.baby.BabyConstants;
import com.rjzd.baby.entity.BaseResponse;
import com.zd.baby.api.model.ReqGetUserInfo;
import com.zd.baby.api.model.ReqLogin;
import com.zd.baby.api.model.ReqOAuthLogin;
import com.zd.baby.api.model.ReqRegister;
import com.zd.baby.api.model.ReqUpdateUserInfo;
import com.zd.baby.api.model.ResGetUserInfo;
import com.zd.baby.api.model.ResLogin;
import com.zd.baby.api.model.ResOAuthLogin;
import com.zd.baby.api.model.ResRegister;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * create time: 2018/6/6  18:11
 * create author: Hition
 * descriptions: 用户相关接口
 */

public class UserAPI {

    private APIService service;

    public UserAPI(){
        service = APIManager.getInstance().retrofit.create(APIService.class);
    }

    /**
     * 用户注册
     * @param smsCode           短信验证码
     * @param mobile            手机号
     */
    public Observable<BaseResponse<ResRegister>> register(String smsCode, String mobile) {
        ReqRegister req = new ReqRegister();
        req.setHeader(APIManager.getInstance().putHeaderByReq(null));
        req.setAction(BabyConstants.ACTION_REGISTER);
        req.setSmsCode(smsCode);
        req.setMobile(mobile);

        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);
        return service.register(requestBody);
    }

    /**
     * 用户登录
     * @param smsCode                       短信验证码
     * @param mobile                        手机号
     */
    public Observable<BaseResponse<ResLogin>> login(String smsCode, String mobile) {
        ReqLogin req = new ReqLogin();
        req.setHeader(APIManager.getInstance().putHeaderByReq(null));
        req.setAction(BabyConstants.ACTION_LOGIN);
        req.setSmsCode(smsCode);
        req.setMobile(mobile);

        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);
        return service.login(requestBody);
    }

    /**
     * 第三方平台登录
     *
     */
    public Observable<BaseResponse<ResOAuthLogin>> oauthLogin(ReqOAuthLogin request ) {
        request.setHeader(APIManager.getInstance().putHeaderByReq(null));
        request.setAction(BabyConstants.ACTION_OAUTHLOGIN);

        Gson gson = new Gson();
        String content = gson.toJson(request);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);
        return service.oauthLogin(requestBody);
    }

    /**
     * 获取用户个人资料信息
     */
    public Observable<BaseResponse<ResGetUserInfo>> getUserInfo() {
        ReqGetUserInfo req = new ReqGetUserInfo();
        req.setHeader(APIManager.getInstance().putHeaderByReq(null));
        req.setAction(BabyConstants.ACTION_GETUSERINFO);

        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);
        return service.getUserInfo(requestBody);
    }

    /**
     * 修改用户个人资料
     * @param req                   请求实体
     */
    public Observable<BaseResponse> updateUserInfo(ReqUpdateUserInfo req) {
        req.setHeader(APIManager.getInstance().putHeaderByReq(null));
        req.setAction(BabyConstants.ACTION_UPDATEUSERINFO);

        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);
        return service.updateUserInfo(requestBody);
    }











}

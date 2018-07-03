package com.rjzd.baby.api;

import com.google.gson.Gson;
import com.rjzd.baby.BabyConstants;
import com.rjzd.baby.entity.BaseResponse;
import com.zd.baby.api.model.ReqSendSMSCode;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * create time: 2018/6/6  17:22
 * create author: Hition
 * descriptions: 系统类API，包括发送短信验证码、系统更新等
 */

public class SystemAPI {

    APIService service;

    public SystemAPI(){
        service = APIManager.getInstance().retrofit.create(APIService.class);
    }

    /**
     * 发送短信验证码
     * @param mobile            手机号
     */
    public Observable<BaseResponse> sendSMSCode(String mobile) {
        ReqSendSMSCode req = new ReqSendSMSCode();
        req.setHeader(APIManager.getInstance().putHeaderByReq(null));
        req.setAction(BabyConstants.ACTION_SEND_SMS_CODE);
        req.setMobile(mobile);

        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);
        return service.sendSMSCode(requestBody);
    }














}

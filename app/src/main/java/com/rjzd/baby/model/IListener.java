package com.rjzd.baby.model;

import com.rjzd.baby.entity.BaseResponse;

/**
 * create time: 2018/5/25
 * create author: Hition
 * descriptions: IListener
 */
public interface IListener<T> {

    // 请求响应成功
    void onSuccess(BaseResponse<T> data, int flag);

    // 请求响应失败
    void onFailed(Throwable e,int flag);

}

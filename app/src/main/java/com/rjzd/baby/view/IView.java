package com.rjzd.baby.view;

import com.rjzd.baby.entity.BaseResponse;

/**
 * create time: 2018/6/7  11:35
 * create author: Hition
 * descriptions: IView
 */

public interface IView<T> {

    void onPrepare();

    // data  响应返回数据源，flag    API接口请求标识
    void onComplete(BaseResponse<T> data, int flag);

    // 数据获取失败，View显示
    void onFailShow(int flag);

}

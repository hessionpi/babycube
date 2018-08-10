package com.rjzd.baby.api;

import android.content.Context;

import com.rjzd.baby.R;
import com.rjzd.baby.tools.ToastUtils;

/**
 * create time: 2018/7/6  15:58
 * create author: Hition
 * descriptions: 异常处理类
 */

public class ExceptionHandler {

    /**
     * 处理异常
     */
    public static void handleException(Context mContext,int errorcode){
        switch (errorcode){
            case ErrorException.HTTP_ERROR:
                ToastUtils.show(mContext,ErrorException.networkException);
                break;

            case ErrorException.SOCKET_TIMEOUT:
                ToastUtils.show(mContext,ErrorException.timeoutException);
                break;

        }
    }

}

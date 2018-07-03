package com.rjzd.baby.entity;

import java.io.Serializable;

/**
 * create time: 2018/6/6  17:27
 * create author: Hition
 * descriptions: 接口返回统一封装对象
 */

public class BaseResponse<T> implements Serializable {

    /**
     * 错误码，0表示成功，其他表示失败
     */
    private int returnValue;
    /**
     * 错误码的描述
     */
    private String returnMsg;
    /**
     * 返回的数据，泛型对象
     */
    private T returnData;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public T getReturnData() {
        return returnData;
    }

    public void setReturnData(T returnData) {
        this.returnData = returnData;
    }

}

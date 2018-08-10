package com.rjzd.baby.api;

/**
 * create time: 2018/7/6  15:03
 * create author: Hition
 * descriptions: 错误异常定义
 */

public class ErrorException {
    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1000;
    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 1001;
    /**
     * 网络错误
     */
    public static final int SOCKET_TIMEOUT = 1002;
    public static final String timeoutException = "服务器异常，网络请求超时";

    /**
     * 协议出错
     */
    public static final int HTTP_ERROR = 1003;
    public static final String networkException = "网络异常";

}

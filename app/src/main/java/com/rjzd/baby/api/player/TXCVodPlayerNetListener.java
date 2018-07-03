package com.rjzd.baby.api.player;


/**
 * create time: 2018/5/29  11:46
 * create author: Hition
 * descriptions: TXCVodPlayerNetListener
 */
public interface TXCVodPlayerNetListener {
    /**
     * 播放信息查询成功
     * @param netApi
     */
    void onNetSuccess(TXCVodPlayerNetApi netApi, TXPlayInfoResponse playInfo);

    /**
     * 播放信息查询失败
     * @param netApi
     * @param reason 失败原因
     * @param code   错误码
     */
    void onNetFailed(TXCVodPlayerNetApi netApi, String reason, int code);
}

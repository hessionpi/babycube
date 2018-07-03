package com.rjzd.baby.tools;

import android.util.Base64;

import com.orhanobut.logger.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * create time: 2018/6/6  16:42
 * create author: Hition
 * descriptions: API接口加密签名工具类
 */

public class SignTools {

    private static final String HMAC_ALGORITHM = "HmacSHA1";
    private static final String CONTENT_CHARSET = "UTF-8";
    private static final String m_strSecKey = "emR5eWZhbmdAODQ3MjY4ODY=";


    public static String createSign(String platform,long currenttime, int expiretime, int random) {
        String sign ;
        String contextStr = "";
        try {
            contextStr += "platform=" + java.net.URLEncoder.encode(platform, "utf8");
            contextStr += "&currenttime=" + currenttime;
            contextStr += "&expiretime=" + expiretime;
            contextStr += "&random=" + random;

            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(m_strSecKey.getBytes(CONTENT_CHARSET), mac.getAlgorithm());
            mac.init(secretKey);
            byte[] hash = mac.doFinal(contextStr.getBytes(CONTENT_CHARSET));
            sign = new String(Base64.encode(hash,Base64.DEFAULT));
            sign = sign.replace(" ", "").replace("\n", "").replace("\r", "");
        } catch (Exception e) {
            Logger.e(e.getMessage());
            return null;
        }
        return sign;
    }
}

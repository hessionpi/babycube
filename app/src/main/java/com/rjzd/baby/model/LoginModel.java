package com.rjzd.baby.model;

import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.Serializable;

/**
 * 用户信息实体，用于序列化
 * <p>
 * Created by Hition on 2017/12/29.
 */

public class LoginModel implements Serializable {

    private String userId;

    // 用户头像原图地址
    private String headpic;

    // 用户头像，缩略图地址
    private String headpicThumb;

    // 用户昵称
    private String nickname;

    // 用户手机号
    private String mobile;

    // 用户性别
    private int sex;

    // 个性签名
    private String signature;

    // 用户所在地区省份
    private String province;

    // 用户所在地区城市
    private String city;

    // 身高
    private int height;

    // 体重
    private int weight;

    // 生日
    private String birthday;

    private boolean hasBaby;

    private SHARE_MEDIA oauthPlatform;

    public LoginModel() {

    }

    public LoginModel(String userId, String headpic, String thumbnail, String nickname) {
        this.userId = userId;
        this.headpic = headpic;
        this.headpicThumb = thumbnail;
        this.nickname = nickname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public String getHeadpicThumb() {
        return headpicThumb;
    }

    public void setHeadpicThumb(String headpicThumb) {
        this.headpicThumb = headpicThumb;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public boolean isHasBaby() {
        return hasBaby;
    }

    public void setHasBaby(boolean hasBaby) {
        this.hasBaby = hasBaby;
    }

    public SHARE_MEDIA getOauthPlatform() {
        return oauthPlatform;
    }

    public void setOauthPlatform(SHARE_MEDIA oauthPlatform) {
        this.oauthPlatform = oauthPlatform;
    }
}

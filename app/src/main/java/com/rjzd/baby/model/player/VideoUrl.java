package com.rjzd.baby.model.player;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * create time: 2018/5/29  10:29
 * create author: Hition
 * descriptions: VideoUrl
 */
public class VideoUrl implements Parcelable {

    private String mFormatName;//视频格式名称，例如高清，标清，720P等等
    private String mFormatUrl;//视频Url
    private boolean isOnlineVideo = true;//是否在线视频 默认在线视频

    public String getFormatName() {
        return mFormatName;
    }

    public void setFormatName(String formatName) {
        mFormatName = formatName;
    }

    public String getFormatUrl() {
        return mFormatUrl;
    }

    public void setFormatUrl(String formatUrl) {
        mFormatUrl = formatUrl;
    }

    public boolean isOnlineVideo() {
        return isOnlineVideo;
    }

    public void setIsOnlineVideo(boolean isOnlineVideo) {
        this.isOnlineVideo = isOnlineVideo;
    }

    public boolean equal(VideoUrl url) {
        if (null != url) {
            return getFormatName().equals(url.getFormatName()) && getFormatUrl().equals(url.getFormatUrl());
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public VideoUrl() {
    }

    public VideoUrl(Parcel source) {
        mFormatName = source.readString();
        mFormatUrl = source.readString();
        isOnlineVideo = source.readInt() == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFormatName);
        dest.writeString(mFormatUrl);
        dest.writeInt(isOnlineVideo ? 1 : 0);
    }

    public static final Parcelable.Creator<VideoUrl> CREATOR = new Parcelable.Creator<VideoUrl>() {
        @Override
        public VideoUrl[] newArray(int size) {
            return new VideoUrl[size];
        }

        @Override
        public VideoUrl createFromParcel(Parcel source) {
            return new VideoUrl(source);
        }
    };
}

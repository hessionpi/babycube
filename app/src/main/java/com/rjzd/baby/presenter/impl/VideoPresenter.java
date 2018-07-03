package com.rjzd.baby.presenter.impl;

import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.model.IListener;
import com.rjzd.baby.model.imp.VideoModel;
import com.rjzd.baby.view.IView;
import com.zd.baby.api.model.VideoFilter;

/**
 * create time: 2018/6/12  18:06
 * create author: Hition
 * descriptions: 视频相关处理的Presenter
 */

public class VideoPresenter extends BasePresenter implements IListener {
    private IView mView;
    private VideoModel mModel;

    public VideoPresenter(IView mView){
        this.mView = mView;
        this.mModel = new VideoModel(this);
    }

    public void getVideoClassify(int status){
        addSubscription(mModel.getVideoClassify(status));
    }

    public void getAllOfVideos(VideoFilter filter, int pageNo, int pageSize){
        addSubscription(mModel.getAllOfVideos(filter,pageNo,pageSize));
    }

    public void getVideoDetails(long videoId){
        addSubscription(mModel.getVideoDetails(videoId));
    }

    public void getSimilarVideos(long videoId, int pageNo, int pageSize){
        addSubscription(mModel.getSimilarVideos(videoId,pageNo,pageSize));
    }

    public void getAllDiscuss(long videoId, int pageNo, int pageSize){
        addSubscription(mModel.getAllDiscuss(videoId,pageNo,pageSize));
    }

    public void publishDiscuss(long videoId, String contents){
        addSubscription(mModel.publishDiscuss(videoId,contents));
    }

    @Override
    public void onSuccess(BaseResponse data, int flag) {
        mView.onComplete(data,flag);
    }

    @Override
    public void onFailed(Throwable e, int flag) {
        mView.onFailShow(flag);
    }
}

package com.rjzd.baby.presenter.impl;


import com.rjzd.baby.model.IListener;
import com.rjzd.baby.model.imp.VideoModel;
import com.rjzd.baby.view.IView;

/**
 * create time: 2018/6/12  18:06
 * create author: Hition
 * descriptions: 视频相关处理的Presenter
 */

public class VideoPresenter extends BasePresenter implements IListener {
    private VideoModel mModel;

    public VideoPresenter(IView mView){
        super(mView);
        this.mModel = new VideoModel(this);
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

}

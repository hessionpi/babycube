package com.rjzd.baby.model.imp;

import com.rjzd.baby.BabyConstants;
import com.rjzd.baby.api.VideoAPI;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.model.IListener;
import com.zd.baby.api.model.ResAllOfVideos;
import com.zd.baby.api.model.ResVideoClassify;
import com.zd.baby.api.model.ResAllDiscuss;
import com.zd.baby.api.model.ResSimilarVideos;
import com.zd.baby.api.model.ResVideoDetails;
import com.zd.baby.api.model.VideoFilter;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * create time: 2018/6/12  17:52
 * create author: Hition
 * descriptions: 有关视频处理业务逻辑的model
 */

public class VideoModel {

    private IListener listener;
    private VideoAPI videoAPI;

    public VideoModel(IListener listener) {
        this.listener = listener;
        videoAPI = new VideoAPI();
    }

    /**
     * 获取视频分类
     * @param status                      宝宝状态
     */
    public Subscription getVideoClassify(int status){
        Observable<BaseResponse<ResVideoClassify>> observable = videoAPI.getVideoClassify(status);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t, BabyConstants.VIDEO_VIDEOCLASSIFY);
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        listener.onSuccess(baseResponse, BabyConstants.VIDEO_VIDEOCLASSIFY);
                    }
                });
    }

    /**
     * 根据条件筛选视频
     * @param filter                    筛选条件
     * @param pageNo                    当前页码
     * @param pageSize                  分页尺寸
     */
    public Subscription getAllOfVideos(VideoFilter filter, int pageNo, int pageSize){
        Observable<BaseResponse<ResAllOfVideos>> observable = videoAPI.getAllOfVideos(filter,pageNo,pageSize);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t, BabyConstants.VIDEO_ALLOFVIDEOS);
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        listener.onSuccess(baseResponse, BabyConstants.VIDEO_ALLOFVIDEOS);
                    }
                });
    }

    /**
     * 获取视频详情
     * @param videoId    视频id
     */
    public Subscription getVideoDetails(long videoId){
        Observable<BaseResponse<ResVideoDetails>> observable = videoAPI.getVideoDetails(videoId);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t, BabyConstants.VIDEO_VIDEODETAILS);
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        listener.onSuccess(baseResponse, BabyConstants.VIDEO_VIDEODETAILS);
                    }
                });
    }

    /**
     * 获取相似视频
     * @param videoId               视频id
     * @param pageNo                页码
     * @param pageSize              每页尺寸
     */
    public Subscription getSimilarVideos(long videoId, int pageNo, int pageSize){
        Observable<BaseResponse<ResSimilarVideos>> observable = videoAPI.getSimilarVideos(videoId,pageNo,pageSize);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t, BabyConstants.VIDEO_SIMILARVIDEOS);
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        listener.onSuccess(baseResponse, BabyConstants.VIDEO_SIMILARVIDEOS);
                    }
                });
    }

    /**
     * 获取视频的所有评论
     * @param videoId                       视频id
     * @param pageNo                        页码
     * @param pageSize                      每页尺寸
     */
    public Subscription getAllDiscuss(long videoId, int pageNo, int pageSize){
        Observable<BaseResponse<ResAllDiscuss>> observable = videoAPI.getAllDiscuss(videoId,pageNo,pageSize);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t, BabyConstants.VIDEO_ALLDISCUSS);
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        listener.onSuccess(baseResponse, BabyConstants.VIDEO_ALLDISCUSS);
                    }
                });
    }

    /**
     * 发布视频评论
     *
     * @param videoId               视频id
     * @param contents              视频内容
     */
    public Subscription publishDiscuss(long videoId, String contents){
        Observable<BaseResponse> observable = videoAPI.publishDiscuss(videoId,contents);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t, BabyConstants.VIDEO_PUBLISHDISCUSS);
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        listener.onSuccess(baseResponse, BabyConstants.VIDEO_PUBLISHDISCUSS);
                    }
                });
    }




}

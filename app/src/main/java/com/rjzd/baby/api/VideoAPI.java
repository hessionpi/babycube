package com.rjzd.baby.api;

import com.google.gson.Gson;
import com.rjzd.baby.BabyConstants;
import com.rjzd.baby.entity.BaseResponse;
import com.zd.baby.api.model.ReqAllDiscuss;
import com.zd.baby.api.model.ReqAllOfVideos;
import com.zd.baby.api.model.ReqPublishDiscuss;
import com.zd.baby.api.model.ReqSimilarVideos;
import com.zd.baby.api.model.ReqVideoClassify;
import com.zd.baby.api.model.ReqVideoDetails;
import com.zd.baby.api.model.ResAllOfVideos;
import com.zd.baby.api.model.ResVideoClassify;
import com.zd.baby.api.model.ResAllDiscuss;
import com.zd.baby.api.model.ResSimilarVideos;
import com.zd.baby.api.model.ResVideoDetails;
import com.zd.baby.api.model.VideoFilter;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * create time: 2018/6/12  17:13
 * create author: Hition
 * descriptions: 视频API封装
 */

public class VideoAPI {
    private APIService service;

    public VideoAPI(){
        service = APIManager.getInstance().retrofit.create(APIService.class);
    }

    /**
     * 获取视频分类
     * @param status                      宝宝状态
     */
    public Observable<BaseResponse<ResVideoClassify>> getVideoClassify(int status) {
        ReqVideoClassify req = new ReqVideoClassify();
        req.setHeader(APIManager.getInstance().putHeaderByReq(null));
        req.setAction(BabyConstants.ACTION_VIDEOCLASSIFY);
        req.setBabyStatus(status);

        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);
        return service.getVideoClassify(requestBody);
    }

    /**
     * 根据条件筛选视频
     * @param filter                    筛选条件
     * @param pageNo                    当前页码
     * @param pageSize                  分页尺寸
     */
    public Observable<BaseResponse<ResAllOfVideos>> getAllOfVideos(VideoFilter filter, int pageNo, int pageSize) {
        ReqAllOfVideos req = new ReqAllOfVideos();
        req.setHeader(APIManager.getInstance().putHeaderByReq(null));
        req.setAction(BabyConstants.ACTION_ALLOFVIDEOS);
        req.setFilter(filter);
        req.setPageNo(pageNo);
        req.setPageSize(pageSize);

        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);
        return service.getAllOfVideos(requestBody);
    }

    /**
     * 获取视频详情
     * @param videoId    视频id
     */
    public Observable<BaseResponse<ResVideoDetails>> getVideoDetails(long videoId) {
        ReqVideoDetails req = new ReqVideoDetails();
        req.setHeader(APIManager.getInstance().putHeaderByReq(null));
        req.setAction(BabyConstants.ACTION_VIDEODETAILS);
        req.setVideoId(videoId);

        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);
        return service.getVideoDetails(requestBody);
    }

    /**
     * 获取相似视频
     * @param videoId               视频id
     * @param pageNo                页码
     * @param pageSize              每页尺寸
     */
    public Observable<BaseResponse<ResSimilarVideos>> getSimilarVideos(long videoId, int pageNo, int pageSize) {
        ReqSimilarVideos req = new ReqSimilarVideos();
        req.setHeader(APIManager.getInstance().putHeaderByReq(null));
        req.setAction(BabyConstants.ACTION_SIMILARVIDEOS);
        req.setVideoId(videoId);
        req.setPageNo(pageNo);
        req.setPageSize(pageSize);

        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);
        return service.getSimilarVideos(requestBody);
    }

    /**
     * 获取视频的所有评论
     * @param videoId                       视频id
     * @param pageNo                        页码
     * @param pageSize                      每页尺寸
     */
    public Observable<BaseResponse<ResAllDiscuss>> getAllDiscuss(long videoId, int pageNo, int pageSize) {
        ReqAllDiscuss req = new ReqAllDiscuss();
        req.setHeader(APIManager.getInstance().putHeaderByReq(null));
        req.setAction(BabyConstants.ACTION_ALLDISCUSS);
        req.setVideoId(videoId);
        req.setPageNo(pageNo);
        req.setPageSize(pageSize);

        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);
        return service.getAllDiscuss(requestBody);
    }

    /**
     * 发布视频评论
     *
     * @param videoId               视频id
     * @param contents              视频内容
     */
    public Observable<BaseResponse> publishDiscuss(long videoId, String contents) {
        ReqPublishDiscuss req = new ReqPublishDiscuss();
        req.setHeader(APIManager.getInstance().putHeaderByReq(null));
        req.setAction(BabyConstants.ACTION_PUBLISHDISCUSS);
        req.setVideoId(videoId);
        req.setComments(contents);

        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);
        return service.publishDiscuss(requestBody);
    }



}

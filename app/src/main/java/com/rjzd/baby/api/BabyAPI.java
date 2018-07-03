package com.rjzd.baby.api;

import com.google.gson.Gson;
import com.rjzd.baby.BabyConstants;
import com.rjzd.baby.entity.BaseResponse;
import com.zd.baby.api.model.ReqAddBaby;
import com.zd.baby.api.model.ReqAllBaby;
import com.zd.baby.api.model.ReqBabyBaseInfo;
import com.zd.baby.api.model.ReqChangeBaby;
import com.zd.baby.api.model.ReqDeleteBaby;
import com.zd.baby.api.model.ReqPregnancyBabyChanges;
import com.zd.baby.api.model.ReqPregnancyMomChanges;
import com.zd.baby.api.model.ReqRecommendInfo;
import com.zd.baby.api.model.ReqUpdateRecommend;
import com.zd.baby.api.model.ResAllBaby;
import com.zd.baby.api.model.ResBabyBaseInfo;
import com.zd.baby.api.model.ResPregnancyBabyChanges;
import com.zd.baby.api.model.ResPregnancyMomChanges;
import com.zd.baby.api.model.ResRecommendInfo;
import com.zd.baby.api.model.ResUpdateRecommend;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * create time: 2018/6/6  18:36
 * create author: Hition
 * descriptions: 宝宝相关的服务API
 */

public class BabyAPI {

    private APIService service;

    public BabyAPI() {
        service = APIManager.getInstance().retrofit.create(APIService.class);
    }

    /**
     * 获取所有baby
     */
    public Observable<BaseResponse<ResAllBaby>> getBabys() {
        ReqAllBaby req = new ReqAllBaby();
        req.setHeader(APIManager.getInstance().putHeaderByReq(null));
        req.setAction(BabyConstants.ACTION_ALLBABY);

        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);
        return service.getBabys(requestBody);
    }

    /**
     * 添加 baby
     *
     * @param babyStatus       宝宝状态
     * @param dueDate          预产期
     * @param babySex          宝宝性别
     * @param babyName         宝宝姓名
     * @param babyBirthday     宝宝生日
     * @param lastMenstruation 末次月经开始时间
     * @param duration         月经持续时间
     */
    public Observable<BaseResponse> addBaby(int babyStatus, String dueDate, int babySex,
                                            String babyName, String babyBirthday, String lastMenstruation, int duration,int circle) {
        ReqAddBaby req = new ReqAddBaby();
        req.setHeader(APIManager.getInstance().putHeaderByReq(null));
        req.setAction(BabyConstants.ACTION_ADDBABY);
        req.setBabyStatus(babyStatus);
        req.setDueDate(dueDate);
        req.setBabySex(babySex);
        req.setBabyName(babyName);
        req.setBabyBirthday(babyBirthday);
        req.setLastMenstruation(lastMenstruation);
        req.setDuration(duration);
        req.setMenstruationCycle(circle);

        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);
        return service.addBaby(requestBody);
    }

    /**
     * 更新宝宝信息
     *
     * @param babyId           宝宝id
     * @param babyStatus       宝宝状态
     * @param dueDate          预产期
     * @param babySex          宝宝性别
     * @param babyName         宝宝姓名
     * @param babyBirthday     宝宝生日
     * @param lastMenstruation 末次月经开始时间
     * @param duration         月经持续时间
     */
    public Observable<BaseResponse> updateBaby(int babyId, int babyStatus, String dueDate, int babySex,
                                               String babyName, String babyBirthday, String lastMenstruation, int duration) {
        ReqChangeBaby req = new ReqChangeBaby();
        req.setHeader(APIManager.getInstance().putHeaderByReq(null));
        req.setAction(BabyConstants.ACTION_CHANGEBABY);
        req.setBabyId(babyId);
        req.setBabyStatus(babyStatus);
        req.setDueDate(dueDate);
        req.setBabySex(babySex);
        req.setBabyName(babyName);
        req.setBabyBirthday(babyBirthday);
        req.setLastMenstruation(lastMenstruation);
        req.setDuration(duration);

        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);
        return service.updateBaby(requestBody);
    }

    /**
     * 删除baby
     *
     * @param babyId 宝宝id
     */
    public Observable<BaseResponse> deleteBaby(int babyId) {
        ReqDeleteBaby req = new ReqDeleteBaby();
        req.setHeader(APIManager.getInstance().putHeaderByReq(null));
        req.setAction(BabyConstants.ACTION_DELETEBABY);
        req.setBabyId(babyId);

        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);
        return service.deleteBaby(requestBody);
    }

    /**
     * 宝宝变化
     *
     * @param babyId 宝宝id
     * @param week 怀孕周数
     */
    public Observable<BaseResponse<ResPregnancyBabyChanges>> pregnancyBabyChanges(int babyId, int week) {
        ReqPregnancyBabyChanges req = new ReqPregnancyBabyChanges();
        req.setHeader(APIManager.getInstance().putHeaderByReq(null));
        req.setAction(BabyConstants.ACTION_BABY_CHANGE);
        req.setBabyId(babyId);
        req.setWeek(week);

        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);
        return service.pregnancyBabyChanges(requestBody);
    }
    /**
     * 妈妈变化
     *
     * @param week 怀孕周数
     */
    public Observable<BaseResponse<ResPregnancyMomChanges>> pregnancyMomChanges( int week) {
        ReqPregnancyMomChanges req = new ReqPregnancyMomChanges();
        req.setHeader(APIManager.getInstance().putHeaderByReq(null));
        req.setAction(BabyConstants.ACTION_MOM_CHANGE);

        req.setWeek(week);

        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);
        return service.pregnancyMomChanges(requestBody);
    }

    /**
     * 宝宝信息
     *
     * @param babyId 宝宝id

     */
    public Observable<BaseResponse<ResBabyBaseInfo>> babyBaseInfo(int babyId) {
        ReqBabyBaseInfo req = new ReqBabyBaseInfo();
        req.setHeader(APIManager.getInstance().putHeaderByReq(null));
        req.setAction(BabyConstants.ACTION_BABY_INFO);
        req.setBabyId(babyId);


        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);
        return service.babyBaseInfo(requestBody);
    }



    /**
     * 根据宝宝成长状况获取推荐信息
     *
     * @param babyStatus 宝宝状态
     * @param timeSpan 时间跨度

     */
    public Observable<BaseResponse<ResRecommendInfo>> recommendInfo(int babyStatus, int timeSpan) {
        ReqRecommendInfo req = new ReqRecommendInfo();
        req.setHeader(APIManager.getInstance().putHeaderByReq(null));
        req.setAction(BabyConstants.ACTION_RECOMMEND_INFO);
        req.setBabyStatus(babyStatus);
        req.setTimeSpan(timeSpan);


        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);
        return service.recommendInfo(requestBody);
    }
    /**
     * 更新单条推荐视频
     *
     * @param classifyId 视频分类id
     * @param videoId 已看过视频id
     * @param babyStatus 宝宝状态
     * @param timeSpan 时间跨度

     */
    public Observable<BaseResponse<ResUpdateRecommend>> updateRecommend(String classifyId, long videoId, int babyStatus, int timeSpan) {
        ReqUpdateRecommend req = new ReqUpdateRecommend();
        req.setHeader(APIManager.getInstance().putHeaderByReq(null));
        req.setAction(BabyConstants.ACTION_UPDATE_RECOMMEND);
        req.setClassifyId(classifyId);
        req.setVideoId(videoId);
        req.setBabyStatus(babyStatus);
        req.setTimeSpan(timeSpan);


        Gson gson = new Gson();
        String content = gson.toJson(req);
        RequestBody requestBody = RequestBody.create(APIManager.CONTENT_TYPE, content);
        return service.updateRecommend(requestBody);
    }

}

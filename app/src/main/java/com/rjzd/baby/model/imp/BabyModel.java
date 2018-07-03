package com.rjzd.baby.model.imp;

import com.rjzd.baby.BabyConstants;
import com.rjzd.baby.api.BabyAPI;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.model.IListener;
import com.zd.baby.api.model.ResAllBaby;
import com.zd.baby.api.model.ResBabyBaseInfo;
import com.zd.baby.api.model.ResPregnancyBabyChanges;
import com.zd.baby.api.model.ResPregnancyMomChanges;
import com.zd.baby.api.model.ResRecommendInfo;
import com.zd.baby.api.model.ResUpdateRecommend;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * create time: 2018/6/6  18:41
 * create author: Hition
 * descriptions: 有关baby的model处理
 */

public class BabyModel {

    private IListener listener;
    private BabyAPI babyAPI;

    public BabyModel(IListener listener) {
        this.listener = listener;
        babyAPI = new BabyAPI();
    }


    /**
     * 获取所有宝宝
     */
    public Subscription getBabys(){
        Observable<BaseResponse<ResAllBaby>> observable = babyAPI.getBabys();
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t,BabyConstants.BABY_MANAGE_GET);
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        listener.onSuccess(baseResponse, BabyConstants.BABY_MANAGE_GET);
                    }
                });
    }

    /**
     * 添加 baby
     * @param babyStatus                            宝宝状态
     * @param dueDate                               预产期
     * @param babySex                               宝宝性别
     * @param babyName                              宝宝姓名
     * @param babyBirthday                          宝宝生日
     * @param lastMenstruation                      末次月经开始时间
     * @param duration                              月经持续时间
     */
    public Subscription addBaby(int babyStatus,String dueDate,int babySex,
                                   String babyName,String babyBirthday,String lastMenstruation,int duration,int circle){
        Observable<BaseResponse> observable = babyAPI.addBaby(babyStatus,dueDate,babySex,babyName,babyBirthday,lastMenstruation,duration,circle);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t,BabyConstants.BABY_MANAGE_ADD);
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        listener.onSuccess(baseResponse, BabyConstants.BABY_MANAGE_ADD);
                    }
                });
    }

    /**
     * 更新宝宝信息
     * @param babyId                                宝宝id
     * @param babyStatus                            宝宝状态
     * @param dueDate                               预产期
     * @param babySex                               宝宝性别
     * @param babyName                              宝宝姓名
     * @param babyBirthday                          宝宝生日
     * @param lastMenstruation                      末次月经开始时间
     * @param duration                              月经持续时间
     */
    public Subscription updateBaby(int babyId,int babyStatus,String dueDate,int babySex,
                                   String babyName,String babyBirthday,String lastMenstruation,int duration){
        Observable<BaseResponse> observable = babyAPI.updateBaby(babyId,babyStatus,dueDate,babySex,babyName,babyBirthday,lastMenstruation,duration);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t,BabyConstants.BABY_MANAGE_UPDATE);
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        listener.onSuccess(baseResponse, BabyConstants.BABY_MANAGE_UPDATE);
                    }
                });
    }

    /**
     * 删除宝宝
     * @param babyId            宝宝id
     */
    public Subscription deleteBaby(int babyId){
        Observable<BaseResponse> observable = babyAPI.deleteBaby(babyId);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t,BabyConstants.BABY_MANAGE_DELETE);
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        listener.onSuccess(baseResponse, BabyConstants.BABY_MANAGE_DELETE);
                    }
                });
    }
    /**
     * 获取孕期宝宝变化
     * @param babyId            宝宝id
     * @param week            怀孕周数
     */
public Subscription pregnancyBabyChanges(int babyId,int week){
    Observable<BaseResponse<ResPregnancyBabyChanges>> observable = babyAPI.pregnancyBabyChanges(babyId,week);
    return observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<BaseResponse<ResPregnancyBabyChanges>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable t) {
                    listener.onFailed(t,BabyConstants.BABY_CHANGE);
                }

                @Override
                public void onNext(BaseResponse<ResPregnancyBabyChanges> baseResponse) {
                    listener.onSuccess(baseResponse, BabyConstants.BABY_CHANGE);
                }
            });
}

    /**
     * 获取孕期妈妈变化

     * @param week            怀孕周数
     */
    public Subscription pregnancyMomChanges(int week){
        Observable<BaseResponse<ResPregnancyMomChanges>> observable = babyAPI.pregnancyMomChanges(week);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<ResPregnancyMomChanges>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t,BabyConstants.MOM_CHANGE);
                    }

                    @Override
                    public void onNext(BaseResponse<ResPregnancyMomChanges> baseResponse) {
                        listener.onSuccess(baseResponse, BabyConstants.MOM_CHANGE);
                    }
                });
    }




    /**
     * 获取宝宝信息

     * @param babyId            宝宝id
     */
    public Subscription babyBaseInfo(int babyId){
        Observable<BaseResponse<ResBabyBaseInfo>> observable = babyAPI.babyBaseInfo(babyId);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<ResBabyBaseInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t,BabyConstants.BABY_INFO);
                    }

                    @Override
                    public void onNext(BaseResponse<ResBabyBaseInfo> baseResponse) {
                        listener.onSuccess(baseResponse, BabyConstants.BABY_INFO);
                    }
                });
    }



    /**
     * 根据宝宝成长状况获取推荐信息
     *
     * @param babyStatus 宝宝状态
     * @param timeSpan 时间跨度

     */
    public Subscription recommendInfo(int babyStatus, int timeSpan) {
        Observable<BaseResponse<ResRecommendInfo>> observable = babyAPI.recommendInfo(babyStatus,timeSpan);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<ResRecommendInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t,BabyConstants.RECOMMENDINFO_INFO);
                    }

                    @Override
                    public void onNext(BaseResponse<ResRecommendInfo> baseResponse) {
                        listener.onSuccess(baseResponse, BabyConstants.RECOMMENDINFO_INFO);
                    }
                });

    }
    /**
     * 更新单条推荐视频
     *
     * @param classifyId 视频分类id
     * @param videoId 已看过视频id
     * @param babyStatus 宝宝状态
     * @param timeSpan 时间跨度

     */
    public Subscription updateRecommend(String classifyId, long videoId, int babyStatus, int timeSpan){
        Observable<BaseResponse<ResUpdateRecommend>> observable = babyAPI.updateRecommend(classifyId,videoId,babyStatus,timeSpan);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<ResUpdateRecommend>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t,BabyConstants.UPDATE_RECOMMEND);
                    }

                    @Override
                    public void onNext(BaseResponse<ResUpdateRecommend> baseResponse) {
                        listener.onSuccess(baseResponse, BabyConstants.UPDATE_RECOMMEND);
                    }
                });

    }
}

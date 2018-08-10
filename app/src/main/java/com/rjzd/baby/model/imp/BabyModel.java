package com.rjzd.baby.model.imp;

import com.rjzd.baby.BabyConstants;
import com.rjzd.baby.api.BabyAPI;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.model.IListener;
import com.zd.baby.api.model.ResAddBaby;
import com.zd.baby.api.model.ResAllBaby;
import com.zd.baby.api.model.ResBabyGrowthCycle;
import com.zd.baby.api.model.ResPregnancyBabyChanges;
import com.zd.baby.api.model.ResPregnancyMomChanges;
import com.zd.baby.api.model.ResRecommendInfo;

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
     * @param babySex                               宝宝性别
     * @param babyName                              宝宝姓名
     * @param babyBirthday                          宝宝生日
     *
     */
    public Subscription addBaby(int babySex,String babyName, String babyBirthday){
        Observable<BaseResponse<ResAddBaby>> observable = babyAPI.addBaby(babySex,babyName,babyBirthday);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<ResAddBaby>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t,BabyConstants.BABY_MANAGE_ADD);
                    }

                    @Override
                    public void onNext(BaseResponse<ResAddBaby> baseResponse) {
                        listener.onSuccess(baseResponse, BabyConstants.BABY_MANAGE_ADD);
                    }
                });
    }

    /**
     * 更新宝宝信息
     * @param babyId           宝宝id
     * @param babySex          宝宝性别
     * @param babyName         宝宝姓名
     * @param babyBirthday     宝宝生日
     * @param babyThumb        宝宝头像缩略图
     */
    public Subscription updateBaby(int babyId, int babySex, String babyName, String babyBirthday,String babyThumb){
        Observable<BaseResponse> observable = babyAPI.updateBaby(babyId,babySex,babyName,babyBirthday,babyThumb);
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
    public Subscription babyGrowthCycle(int babyId){
        Observable<BaseResponse<ResBabyGrowthCycle>> observable = babyAPI.babyGrowthCycle(babyId);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<ResBabyGrowthCycle>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onFailed(t,BabyConstants.BABY_GROWTH_CYCLE);
                    }

                    @Override
                    public void onNext(BaseResponse<ResBabyGrowthCycle> baseResponse) {
                        listener.onSuccess(baseResponse, BabyConstants.BABY_GROWTH_CYCLE);
                    }
                });
    }



    /**
     * 根据宝宝成长状况获取推荐信息
     * @param babyId       宝宝id
     * @param currentStatus   当前状态
     * @param requireStage    待请求阶段
     * @param stageUnit    单位
     */
    public Subscription recommendInfo(int babyId,int currentStatus, String requireStage,String stageUnit) {
        Observable<BaseResponse<ResRecommendInfo>> observable = babyAPI.recommendInfo(babyId,currentStatus,requireStage,stageUnit);
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





}

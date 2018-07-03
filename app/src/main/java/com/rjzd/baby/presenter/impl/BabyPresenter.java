package com.rjzd.baby.presenter.impl;

import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.model.IListener;
import com.rjzd.baby.model.imp.BabyModel;
import com.rjzd.baby.view.IView;

/**
 * create time: 2018/6/7  11:25
 * create author: Hition
 * descriptions: 处理所有有关baby业务逻辑的Presenter
 */

public class BabyPresenter extends BasePresenter implements IListener{

    private IView mView;
    private BabyModel mModel;

    public BabyPresenter(IView view) {
        this.mView = view;
        this.mModel = new BabyModel(this);
    }

    /**
     * 获取所有宝宝的档案
     */
    public void getAllBabys() {
        addSubscription(mModel.getBabys());
    }

    /**
     * 添加宝宝
     * @param babyStatus                    宝宝状态
     * @param dueDate                       宝宝预产期
     * @param babySex                       宝宝性别
     * @param babyName                      宝宝姓名
     * @param babyBirthday                  宝宝生日
     * @param lastMenstruation              末次月经开始时间
     * @param duration                      月经持续天数
     * @param circle                       月经周期
     */
    public void addBaby(int babyStatus,String dueDate,int babySex,
                           String babyName,String babyBirthday,String lastMenstruation,
                           int duration,int circle) {
        addSubscription(mModel.addBaby(babyStatus,dueDate,babySex,babyName,babyBirthday,lastMenstruation,duration,circle));
    }

    /**
     * 更新宝宝档案
     * @param babyId                    宝宝id
     */
    public void updateBaby(int babyId,int babyStatus,String dueDate,int babySex,
                           String babyName,String babyBirthday,String lastMenstruation,int duration) {
        addSubscription(mModel.updateBaby(babyId,babyStatus,dueDate,babySex,babyName,babyBirthday,lastMenstruation,duration));
    }

    /**
     * 删除宝宝档案
     * @param babyId                    宝宝id
     */
    public void deleteBaby(int babyId) {
        addSubscription(mModel.deleteBaby(babyId));
    }

    /**
     * 宝宝变化
     * @param babyId  宝宝id
     * @param week   周数
     */
    public void pregnancyBabyChanges(int babyId,int week) {
        addSubscription(mModel.pregnancyBabyChanges(babyId,week));

    }

    /**
     * 妈妈变化
     * @param week   周数
     */
    public void pregnancyMomChanges(int week) {
        addSubscription(mModel.pregnancyMomChanges(week));
    }
    /**
     * 获取宝宝信息

     * @param babyId            宝宝id
     */
    public void babyBaseInfo(int babyId){addSubscription(mModel.babyBaseInfo(babyId));}

    /**
     * 根据宝宝成长状况获取推荐信息
     *
     * @param babyStatus 宝宝状态
     * @param timeSpan 时间跨度

     */
    public void recommendInfo(int babyStatus, int timeSpan){addSubscription(mModel.recommendInfo(babyStatus,timeSpan));}

    /**
     * 更新单条推荐视频
     *
     * @param classifyId 视频分类id
     * @param videoId 已看过视频id
     * @param babyStatus 宝宝状态
     * @param timeSpan 时间跨度

     */
    public void updateRecommend(String classifyId, long videoId, int babyStatus, int timeSpan){addSubscription(mModel.updateRecommend(classifyId,videoId,babyStatus,timeSpan));}
    @Override
    public void onSuccess(BaseResponse data, int flag) {
        mView.onComplete(data,flag);
    }

    @Override
    public void onFailed(Throwable e, int flag) {
        mView.onFailShow(flag);
    }
}

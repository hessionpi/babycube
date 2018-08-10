package com.rjzd.baby.presenter.impl;

import com.rjzd.baby.model.imp.BabyModel;
import com.rjzd.baby.view.IView;

/**
 * create time: 2018/6/7  11:25
 * create author: Hition
 * descriptions: 处理所有有关baby业务逻辑的Presenter
 */

public class BabyPresenter extends BasePresenter{

    private BabyModel mModel;

    public BabyPresenter(IView view) {
        super(view);
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
     * @param babySex                       宝宝性别
     * @param babyName                      宝宝姓名
     * @param babyBirthday                  宝宝生日
     *
     */
    public void addBaby(int babySex,String babyName, String babyBirthday) {
        addSubscription(mModel.addBaby(babySex,babyName,babyBirthday));
    }

    /**
     * 更新宝宝档案
     * @param babyId                    宝宝id
     */
    public void updateBaby(int babyId, int babySex, String babyName, String babyBirthday,String babyThumb) {
        addSubscription(mModel.updateBaby(babyId,babySex,babyName,babyBirthday,babyThumb));
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
    public void babyGrowthCycle(int babyId){addSubscription(mModel.babyGrowthCycle(babyId));}

    /**
     * 根据宝宝成长状况获取推荐信息
     * @param babyId       宝宝id
     * @param currentStatus   当前状态
     * @param requireStage    待请求阶段
     * @param stageUnit    单位
     */
    public void recommendInfo(int babyId,int currentStatus, String requireStage,String stageUnit){
        addSubscription(mModel.recommendInfo(babyId,currentStatus,requireStage,stageUnit));
    }



}

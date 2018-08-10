package com.rjzd.baby.api;

import com.rjzd.baby.entity.BaseResponse;
import com.zd.baby.api.model.ResAddBaby;
import com.zd.baby.api.model.ResAllBaby;
import com.zd.baby.api.model.ResAllDiscuss;
import com.zd.baby.api.model.ResBabyGrowthCycle;
import com.zd.baby.api.model.ResGetUserInfo;
import com.zd.baby.api.model.ResLogin;
import com.zd.baby.api.model.ResOAuthLogin;
import com.zd.baby.api.model.ResPregnancyBabyChanges;
import com.zd.baby.api.model.ResPregnancyMomChanges;
import com.zd.baby.api.model.ResRecommendInfo;
import com.zd.baby.api.model.ResRegister;
import com.zd.baby.api.model.ResSimilarVideos;
import com.zd.baby.api.model.ResVideoDetails;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * create time: 2018/6/6  17:25
 * create author: Hition
 * descriptions: 所有请求数据服务，使用Retrofit方式
 */

public interface APIService {

    @POST("interface.htmls")
    Observable<BaseResponse> sendSMSCode(@Body RequestBody requestBody);



    @POST("interface.htmls")
    Observable<BaseResponse<ResRegister>> register(@Body RequestBody requestBody);
    @POST("interface.htmls")
    Observable<BaseResponse<ResLogin>> login(@Body RequestBody requestBody);
    @POST("interface.htmls")
    Observable<BaseResponse<ResOAuthLogin>> oauthLogin(@Body RequestBody requestBody);
    @POST("interface.htmls")
    Observable<BaseResponse<ResGetUserInfo>> getUserInfo(@Body RequestBody requestBody);
    @POST("interface.htmls")
    Observable<BaseResponse> updateUserInfo(@Body RequestBody requestBody);












    @POST("interface.htmls")
    Observable<BaseResponse<ResAllBaby>> getBabys(@Body RequestBody requestBody);
    @POST("interface.htmls")
    Observable<BaseResponse<ResAddBaby>> addBaby(@Body RequestBody requestBody);
    @POST("interface.htmls")
    Observable<BaseResponse> updateBaby(@Body RequestBody requestBody);
    @POST("interface.htmls")
    Observable<BaseResponse> deleteBaby(@Body RequestBody requestBody);
    @POST("interface.htmls")
    Observable<BaseResponse<ResBabyGrowthCycle>> babyGrowthCycle(@Body RequestBody requestBody);
    @POST("interface.htmls")
    Observable<BaseResponse<ResRecommendInfo>> recommendInfo(@Body RequestBody requestBody);


    @POST("interface.htmls")
    Observable<BaseResponse<ResPregnancyBabyChanges>> pregnancyBabyChanges(@Body RequestBody requestBody);
    @POST("interface.htmls")
    Observable<BaseResponse<ResPregnancyMomChanges>> pregnancyMomChanges(@Body RequestBody requestBody);


    @POST("interface.htmls")
    Observable<BaseResponse<ResVideoDetails>> getVideoDetails(@Body RequestBody requestBody);
    @POST("interface.htmls")
    Observable<BaseResponse<ResSimilarVideos>> getSimilarVideos(@Body RequestBody requestBody);
    @POST("interface.htmls")
    Observable<BaseResponse<ResAllDiscuss>> getAllDiscuss(@Body RequestBody requestBody);
    @POST("interface.htmls")
    Observable<BaseResponse> publishDiscuss(@Body RequestBody requestBody);



}

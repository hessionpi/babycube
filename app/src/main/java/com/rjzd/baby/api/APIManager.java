package com.rjzd.baby.api;

import android.app.Application;
import android.text.TextUtils;
import com.rjzd.baby.BabyApplication;
import com.rjzd.baby.BabyConstants;
import com.rjzd.baby.model.UserInfoCenter;
import com.rjzd.baby.tools.NetWorkUtil;
import com.rjzd.baby.tools.SignTools;
import com.zd.baby.api.model.ReqHeader;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * create time: 2018/5/25  11:26
 * create author: Hition
 * descriptions: API 管理类，API大总管，主要封装网络访问请求
 */
public class APIManager {

    public static boolean mIsDevelopEnv = true;
    public static String REQUEST_URL_API = "";

    private static APIManager instance;
    public Retrofit retrofit;
    public static final MediaType CONTENT_TYPE = MediaType.parse("application/json");

    public static void setDevEnv() {
        if (mIsDevelopEnv) {
            REQUEST_URL_API = BabyConstants.SVR_POST_URL_DEVELOP + "/baby/";
        } else {
            REQUEST_URL_API = BabyConstants.SVR_POST_URL_ONLINE + "/baby/";
        }
    }

    private APIManager() {
        Application globalApp = BabyApplication.getApplication();
        File cacheDir = globalApp.getExternalCacheDir();
        // 设置OkHttp请求数据时缓存
        File cacheFile = new File(cacheDir, BabyConstants.NETWORK_CASH_PATH);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                boolean isConnect = NetWorkUtil.isNetworkConnected(globalApp);

                if (!isConnect) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }

                Response response = chain.proceed(request);
                if (isConnect) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .addHeader("Content-Type", "application/json;charset=UTF-8")
                            .addHeader("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .addHeader("Content-Type", "application/json;charset=UTF-8")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };

        OkHttpClient client = new OkHttpClient.Builder().cache(cache)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(REQUEST_URL_API)
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                //增加返回值为Oservable<T>的支持
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
    }

    public static APIManager getInstance() {
        if (instance == null) {
            synchronized (APIManager.class) {
                if (instance == null) {
                    instance = new APIManager();
                }
            }
        }
        return instance;
    }


    /**
     * 公共头     包括接口版本，加密等字段
     * @param apiVersion
     * @return
     */
    public ReqHeader putHeaderByReq(String apiVersion){
        long currenttime = System.currentTimeMillis();
        int random = new Random(Integer.MAX_VALUE).nextInt();
        String sign = SignTools.createSign(BabyConstants.PLATFORM_VALUE,currenttime,BabyConstants.API_EXPIRETIME,random);

        ReqHeader reqheader = new ReqHeader();
        if(TextUtils.isEmpty(apiVersion)){
            reqheader.setApiversion(BabyConstants.API_VERSION_VALUE_DEFAULT);
        }else{
            reqheader.setApiversion(apiVersion);
        }

        reqheader.setUserId(UserInfoCenter.getInstance().getUserId());
        reqheader.setPlatform(BabyConstants.PLATFORM_VALUE);
        reqheader.setCurrenttime(currenttime);
        reqheader.setExpiretime(BabyConstants.API_EXPIRETIME);
        reqheader.setRandom(random);
        reqheader.setSign(sign);
        return reqheader;
    }

}

package com.rjzd.baby;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.rjzd.baby.api.APIManager;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

/**
 * create time: 2018/5/25
 * create author: Hition
 * descriptions: BabyApplication
 */
public class BabyApplication extends Application {
    private static BabyApplication instance;

    public static BabyApplication getApplication() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setUMConfig();
        checkEnvironment();
    }

    /**
     * 友盟初始化以及平台设置
     */
    private void setUMConfig() {
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this,UMConfigure.DEVICE_TYPE_PHONE,null);
        PlatformConfig.setWeixin("wx653103a0978df9ae", "84ce7296cfcb907911a985d5622d0786");
        PlatformConfig.setSinaWeibo("1468093617", "ec0174f1288dd38482db16415428eede","https://api.weibo.com/oauth2/default.html");
    }

    /**
     * 检测环境，然后根据环境设置开发环境和线上环境
     */
    private void checkEnvironment() {
        boolean isDubugPackage = isApkInDebug();
        // 配置API访问地址
        APIManager.mIsDevelopEnv = isDubugPackage;
        APIManager.setDevEnv();

        // 极光推送，设置开启日志
//        JPushInterface.setDebugMode(isDubugPackage);

        if(isDubugPackage){// develop 版本配置
            // 云通信服务相关配置
//            BabyConstants.IMSDK_APPID = 1400046151;
            // COS存储服务相关配置
            /*BabyConstants.COS_BUCKET = "fang";
            BabyConstants.COS_APPID = "1254235267";
            BabyConstants.COS_REGION = COSEndPoint.COS_SH;*/

        }else{// release 版本
            // 云通信服务相关配置
//            FangConstants.IMSDK_APPID = 1400079609;
            // COS存储服务相关配置
            /*FangConstants.COS_BUCKET = "fang";
            FangConstants.COS_APPID = "1256351189";
            FangConstants.COS_REGION = COSEndPoint.COS_SH;*/
        }

        // 配置Logger，日志输出
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .methodCount(0)             // （可选）要显示的方法行数。 默认2
                .tag("BABY")               //（可选）每个日志的全局标记。 默认PRETTY_LOGGER（如上图）
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return isDubugPackage;
            }
        });
    }

    /**
     * 是否是debug版
     *
     * @return true 是debug版 else release版
     */
    private boolean isApkInDebug(){
        try {
            ApplicationInfo info = getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }











    /**
     * 主要解决dex突破65535时候，项目在android5.0 以下报错Java.lang.NoClassDefFoundError
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}

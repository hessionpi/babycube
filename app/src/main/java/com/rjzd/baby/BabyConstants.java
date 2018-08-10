package com.rjzd.baby;

/**
 * create time: 2018/5/25  11:17
 * create author: Hition
 * descriptions: 用于存放系统静态变量
 */


public class BabyConstants {

    public static final String PLATFORM_VALUE = "android";
    public static final String API_VERSION_VALUE_DEFAULT = "1.0.0";
    // API超时时间  默认600秒
    public static final int API_EXPIRETIME = 600;

    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int SIMILARVIDEO_PAGE_SIZE = 3;


    /**
     * 序列化登录后用户信息存放路径
     */
    public static final String LOGIN_MODEL_FILENAME = "/user";

    //业务Server的Http配置
    public static final String SVR_POST_URL_DEVELOP = "http://111.202.205.174";
    public static final String SVR_POST_URL_ONLINE = "http://111.202.205.174";
    public static final String NETWORK_CASH_PATH = "baby_cache_network";

    public static final String ADDRESS_ABOUT_US = "https://www.baidu.com";





    /***** API action 定义  *********************************************************/
    public static final String ACTION_SEND_SMS_CODE = "SendSMSCode";
    public static final String ACTION_REGISTER = "Register";
    public static final String ACTION_LOGIN = "Login";
    public static final String ACTION_OAUTHLOGIN = "OAuthLogin";
    public static final String ACTION_GETUSERINFO = "GetUserInfo";
    public static final String ACTION_UPDATEUSERINFO = "UpdateUserInfo";
    public static final String ACTION_ALLBABY = "AllBaby";
    public static final String ACTION_ADDBABY = "AddBaby";
    public static final String ACTION_CHANGEBABY = "ChangeBaby";
    public static final String ACTION_DELETEBABY = "DeleteBaby";
    public static final String ACTION_BABY_CHANGE = "PregnancyBabyChanges";
    public static final String ACTION_MOM_CHANGE = "PregnancyMomChanges";
    public static final String ACTION_BABY_GROWTH_CYCLE = "BabyGrowthCycle";
    public static final String ACTION_RECOMMEND_INFO = "RecommendInfo";
    public static final String ACTION_UPDATE_RECOMMEND = "UpdateRecommend";


    public static final String ACTION_VIDEOCLASSIFY = "VideoClassify";
    public static final String ACTION_ALLOFVIDEOS = "AllOfVideos";
    public static final String ACTION_VIDEODETAILS = "VideoDetails";
    public static final String ACTION_SIMILARVIDEOS = "SimilarVideos";
    public static final String ACTION_ALLDISCUSS = "AllDiscuss";
    public static final String ACTION_PUBLISHDISCUSS = "PublishDiscuss";




    /********** action end  **********************************************************/

    /*******************  接口响应成功 标识   **************/
    //默认
    public static final int DEFAULT = 0;
    public static final int SYSTEM_SEND_SMS_CODE = 100;

    public static final int USER_REGISTER = 200;
    public static final int USER_LOGIN = 201;
    public static final int USER_GET_USERUINFO = 202;
    public static final int USER_UPDATE_USERUINFO = 203;
    public static final int USER_OAUTHLOGIN = 204;


    public static final int VIDEO_VIDEOCLASSIFY = 300;
    public static final int VIDEO_ALLOFVIDEOS = 301;
    public static final int VIDEO_VIDEODETAILS = 302;
    public static final int VIDEO_SIMILARVIDEOS = 303;
    public static final int VIDEO_ALLDISCUSS = 304;
    public static final int VIDEO_PUBLISHDISCUSS = 305;



    public static final int BABY_MANAGE_GET = 400;
    public static final int BABY_MANAGE_ADD = 401;
    public static final int BABY_MANAGE_UPDATE = 402;
    public static final int BABY_MANAGE_DELETE = 403;
    public static final int BABY_CHANGE = 404;
    public static final int MOM_CHANGE = 405;
    public static final int BABY_GROWTH_CYCLE = 405;
    public static final int RECOMMENDINFO_INFO = 406;
    public static final int UPDATE_RECOMMEND= 407;
    /************** it's end        **************************/


    public static String COS_BUCKET = "baby";
    public static String COS_APPID = "1254235267";
    // 云 API 密钥 SecretId
    public static String SECRETID = "AKIDcQ7dy9LycQH0YHPeBXiiYCmDT2Wqe7BH";
    // 云 API 密钥 SecretKey
    public static String SECRETKEY = "JWZS9eyDMQNDxX0KSO8g5HJArTAi4GKD";
    //SecretKey 的有效时间，单位秒
    public static long KEYDURATION = 600;
    // 存储桶所在的地域
    public static String COS_REGION = "ap-shanghai";

    //视频分享页
    public static final String VIDEO_SHARE = "babyweb/vod/vodDetails.htmls?videoid=";

    /*public static final String VOD_APPID = "1256468886";
    public static final String VOD_APPKEY = "1973fcc2b70445af8b51053d4f9022bb";
    public static final String PLAYER_DEFAULT_VIDEO = "play_default_video";
    public static final String PLAYER_VIDEO_ID = "video_id";
    public static final String PLAYER_VIDEO_NAME = "video_name";
    public static final String SERVER_IP = "http://demo.vod2.myqcloud.com/shortvideo";
    public static final String ADDRESS_SIG = SERVER_IP + "/api/v1/misc/upload/signature";
    public static final String ADDRESS_VIDEO_LIST = SERVER_IP + "/api/v1/resource/videos";
    public static final String ADDRESS_VIDEO_INFO = SERVER_IP + "/api/v1/resource/videos/#";
    public static final String ADDRESS_VIDEO_REPORT = SERVER_IP + "/api/v1/resource/videos/";
    */








}

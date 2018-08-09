namespace java com.zd.baby.api.model   // java
namespace cocoa baby



/**
* 模型
*
**/

// 公共请求头信息
struct ReqHeader{
    1:  string apiversion,                   // API版本号
    2:  string platform,                     // 平台信息（android、ios、web）
    3:  i64 currenttime,                     // 当前时间戳  毫秒
    4:  i32 expiretime,                      // 秘钥有效期  600 表示 有效期为600秒
    5:  i32 random,                          // 随机数
    6:  string sign,                         // 签名信息
    7:  string userId
}


/**
* 发送短信验证码
**/
struct ReqSendSMSCode{
    1:ReqHeader header,
    2:string action,
    3:string mobile
}









/**
* 新用户注册
**/
struct ReqRegister{
    1:ReqHeader header,
    2:string action,
    3:string smsCode,
    4:string mobile
}
struct ResRegister{
    1:string userId,
    2:string headpicThumb,
    3:string headpic,
    4:string nickName,
    5:i32 sex,
    6:list<BabyInfo> babys
}

/**
* 登录
**/
struct ReqLogin{
   1:ReqHeader header,
   2:string action,
   3:string smsCode,
   4:string password,
   5:string mobile
}
struct ResLogin{
    1:string userId,
    2:string headpicThumb,
    3:string headpic,
    4:string nickName,
    5:i32 sex,
    6:list<BabyInfo> babys
}

/**
* 第三方授权登录
**/
struct ReqOAuthLogin{
    1:ReqHeader header,
    2:string action,
    3:string weixinId,
    4:string qqId,
    5:string sinaId,
    6:string nickname,
    7:string iconUrl,
    8:i32 sex,
    9:string province,
    10:string city
}
struct ResOAuthLogin{
    1:string userId,
    2:string headpicThumb,
    3:string headpic,
    4:string nickName,
    5:i32 sex,
    6:list<BabyInfo> babys
}

/**
* 获取我的个人资料
**/
struct ReqGetUserInfo{
    1:ReqHeader header,
    2:string action
}
struct ResGetUserInfo{
    1:string headpicThumb,
    2:string headpic,
    3:string nickName,
    4:i32 sex,
    5:string province,
    6:string city,
    7:i32 height,
    8:i32 weight,
    9:string birthday
}


/**
* 更新用户个人资料
**/
struct ReqUpdateUserInfo{
    1:ReqHeader header,
    2:string action,
    3:string headPic,
    4:string nickname,
    5:i32 sex,
    6:string province,
    7:string city
    8:i32 hight,
    9:i32 weight,
    10:string birthday
}


















/**
* 获取我所有的宝宝
**/
struct ReqAllBaby{
    1:ReqHeader header,
    2:string action
}
struct BabyInfo{
    1:i32 babyId,
    2:i32 babyStatus,
    3:string babyThumb,
    4:i32 babySex,
    5:string babyName,
    6:string babyBirthday,
	7:string babyAge
}
struct ResAllBaby{
    1: list<BabyInfo> babyList
}

/**
* 添加宝宝档案
**/
struct ReqAddBaby{
    1:ReqHeader header,
    2:string action,
    3:i32 babySex,
    4:string babyName,
    5:string babyBirthday
}
struct ResAddBaby{
	1:i32 babyId,
	2:i32 babyStatus,
	3:string babyName,
	4:string babyBirthday,
	5:string babyAge
}


/**
* 修改宝宝档案
**/
struct ReqChangeBaby{
    1:ReqHeader header,
    2:string action,
    3:i32 babyId
    4:string babyThumb,
    5:i32 babySex,
    6:string babyName,
    7:string babyBirthday
}

/**
* 删除宝宝档案
*
**/
struct ReqDeleteBaby{
    1:ReqHeader header,
    2:string action,
    3:i32 babyId
}

/**
* 获取宝宝基本信息
**/
struct ReqBabyGrowthCycle{
    1:ReqHeader header,
    2:string action,
    3:i32 babyId
}
struct GrowthStage{
    1:string firLineDesc,
    2:string secLineDesc,
    3:i32 currentStatus,
    4:i32 expectProgress,
    5:string currentStage,
    6:string currentUnit
}
struct ResBabyGrowthCycle{
    1:i32 actualIndex,
    2:i32 actualProgress,
    3:list<GrowthStage> growthStage
}

/**
* 根据宝宝状态获取推荐信息
**/
struct ReqRecommendInfo{
    1:ReqHeader header,
    2:string action,
    3:i32 babyId,
    4:i32 currentStatus,
    5:string requireStage,
    6:string stageUnit
}
enum RecommendType{
    PHOTO_TEXT,
    TEXT,
    AUDIO,
    VIDEO
}
struct Recommend{
    1:i64 recommendId,
    2:string cover,
    3:string title,
    4:string subTitle,
    5:string url,
    6:RecommendType type,
	7:i64 duration,
    8:bool isAd
}
struct RecommendInfo{
    1:string title
    2:string date,
    3:bool isToday,
    4:list<Recommend> recomContents
}
struct ResRecommendInfo{
    1:list<RecommendInfo> recommends,
    2:string babyContour,
    3:string babyPrototype,
    4:string shapeDescription
}



/**
* 获取孕期宝宝变化
**/
struct ReqPregnancyBabyChanges{
    1:ReqHeader header,
    2:string action,
    3:i32 babyId,
    4:i32 week
}
struct PregnancyBabyChange{
    1:string pregnancyDay,
    2:string date,
    3:string changesDesc
}
struct ResPregnancyBabyChanges{
    1:list<PregnancyBabyChange> babyChanges
}

/**
* 获取孕周妈妈美变化列表
**/
struct ReqPregnancyMomChanges{
    1:ReqHeader header,
    2:string action,
    3:i32 week
}
struct PregnancyMomChange{
    1:string changesTitle,
    2:string changesDesc
}
struct ResPregnancyMomChanges{
    1:string imgAddress,
    2:list<PregnancyMomChange> momChanges
}
























/**
* 获取视频分类
**/
struct ReqVideoClassify{
    1:ReqHeader header,
    2:string action,
    3:i32 babyStatus
}
struct VideoClassify{
    1:string classifyId,
    2:string classifyName
}
struct ResVideoClassify{
    1:list<VideoClassify> classifyList
}

/**
* 全部视频
**/
struct VideoFilter{
    1:string classifyId,
    2:i32 week,
    3:i32 birthStage
}
struct ReqAllOfVideos{
    1:ReqHeader header,
    2:string action,
    3:VideoFilter filter,
    4:i32 pageNo,
    5:i32 pageSize
}
struct SimpleVideo{
    1:i64 videoId,
    2:string videoCover,
    3:string videoTitle,
    4:string videoClassifyId,
    5:string videoClassify,
    6:string videoDescription,
    7:i64 videoDuration,
    8:i64 videoSize
}
struct ResAllOfVideos{
    1:i32 total,
    2:list<SimpleVideo> videoList
}


/**
* 获取视频详情
**/
struct ReqVideoDetails{
   1:ReqHeader header,
   2:string action,
   3:i64 videoId
}
struct ResVideoDetails{
    1:string videoCover,
    2:string videoTitle
    3:string videoClassfy,
    4:string videoAudience,
    5:string videoDescription,
    6:i64 videoDuration,
    7:i64 videoSize,
    8:string playUrl
}

/**
* 获取文章详情
**/
struct ReqArticleDetails{
   1:ReqHeader header,
   2:string action,
   3:i64 articleId
}
struct ResArticleDetails{
    1:i64 articleId,
    2:string createTime
    3:string createUser,
    4:string title,
    5:string frontcover,
    6:string description,
    7:string content
}
/**
* 获取相似视频
**/
struct ReqSimilarVideos{
    1:ReqHeader header,
    2:string action,
    3:i64 videoId,
    4:i32 pageNo,
    5:i32 pageSize
}
struct SimilarVideo{
    1:i64 videoId,
    2:string videoCover,
    3:string videoTitle
    4:string videoClassfy,
    5:string videoDescription,
    6:i64 videoDuration,
    7:i64 videoSize,
    8:string playUrl
}
struct ResSimilarVideos{
    1:i32 similarTotal,
    2:list<SimilarVideo> similarVideos
}

/**
* 获取视频所有评论
**/
struct ReqAllDiscuss{
    1:ReqHeader header,
    2:string action,
    3:i64 videoId,
    4:i32 pageNo,
    5:i32 pageSize
}
struct Discuss{
    1:i64 discussId,
    2:string publisherId,
    3:string publisherPhoto,
    4:string publisherName,
    5:string publishTime,
    6:string publishContent
}
struct ResAllDiscuss{
    1:i32 discussTotal,
    2:list<Discuss> discussList
}

/**
* 发表评论
**/
struct ReqPublishDiscuss{
    1:ReqHeader header,
    2:string action,
    3:i64 videoId,
    4:string comments
}




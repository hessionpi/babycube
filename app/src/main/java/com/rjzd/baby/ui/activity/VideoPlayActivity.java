package com.rjzd.baby.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.orhanobut.logger.Logger;
import com.rjzd.baby.BabyConstants;
import com.rjzd.baby.R;
import com.rjzd.baby.api.APIManager;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.model.player.VodRspData;
import com.rjzd.baby.presenter.impl.VideoPresenter;
import com.rjzd.baby.tools.DensityUtil;
import com.rjzd.baby.tools.NetWorkUtil;
import com.rjzd.baby.tools.ToastUtils;
import com.rjzd.baby.tools.ZDUtils;
import com.rjzd.baby.ui.adapter.DiscussAdapter;
import com.rjzd.baby.ui.adapter.VideoListAdapter;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;
import com.rjzd.baby.ui.widget.MyItemDecoration;
import com.rjzd.baby.ui.widget.videoview.MediaController;
import com.rjzd.baby.ui.widget.videoview.SuperVideoPlayer;
import com.rjzd.baby.view.IView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.zd.baby.api.model.Discuss;
import com.zd.baby.api.model.ResAllDiscuss;
import com.zd.baby.api.model.ResSimilarVideos;
import com.zd.baby.api.model.ResVideoDetails;
import com.zd.baby.api.model.SimilarVideo;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create time: 2018/5/29  16:51
 * create author: Hition
 * descriptions: VideoPlayActivity
 */

public class VideoPlayActivity extends BaseActivity implements View.OnClickListener, IView {

    @BindView(R.id.iv_back)
    ImageView mBack;
    @BindView(R.id.video_player_view)
    SuperVideoPlayer mSuperVideoPlayer;

    @BindView(R.id.tv_video_title)
    TextView mTitle;
    @BindView(R.id.tv_classfy)
    TextView mClassfy;
    @BindView(R.id.tv_see_object)
    TextView mSeeObject;
    @BindView(R.id.tv_video_description)
    TextView mVideoDesc;
    @BindView(R.id.iv_wx_circle)
    ImageView mWXCircle;
    @BindView(R.id.iv_wx_friends)
    ImageView mWXFriends;

    @BindView(R.id.rv_video_list)
    RecyclerView rvVideoList;
    @BindView(R.id.rv_discuss)
    RecyclerView rvDiscuss;
    @BindView(R.id.tv_discuss_empty)
    TextView emptyView;
    @BindView(R.id.btn_publish_comments)
    Button mPublishButton;
    @BindView(R.id.scrollView)
    ScrollView mScroll;

    @BindView(R.id.ll_play_cost_flow)
    LinearLayout costFlowLayout;
    @BindView(R.id.tv_tips_play_cost_flow)
    TextView mTipsView;
    @BindView(R.id.btn_play_continue)
    Button mContinuePlay;

    @BindView(R.id.ll_play_done_share)
    LinearLayout playdoneShareLayout;
    @BindView(R.id.tv_wx_circle)
    TextView mSharetoWXCircle;
    @BindView(R.id.tv_wx)
    TextView mSharetoWX;
    @BindView(R.id.tv_play_again)
    TextView mPlayAgain;
    @BindView(R.id.pb_play_loading)
    ProgressBar mPlayloadingPb;
    @BindView(R.id.tv_tips_failed)
    TextView mLoadFailed;
    @BindView(R.id.tv_reload)
    TextView mReload;


    private VideoListAdapter mVideoAdapter;
    private long videoId;
    private int similarPageNo = 1;
    private int discussPageNo = 1;
    private DiscussAdapter mDiscussAdapter;

    private static String SHARE_SUMMARY ="数十万准家长都在看的专业原创孕育知识视频";
    private String playUrl;
    private String title;
    private String videoCover;
    private VideoPresenter presenter;
    private ShareAction mShareAction = new ShareAction(this);
    private SHARE_MEDIA sharePlatform;

    public static void startActivity(Context context,long videoId) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        intent.putExtra("video_id",videoId);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video_play);
        ButterKnife.bind(this);
        initView();
        videoId = getIntent().getLongExtra("video_id",0);
        presenter = new VideoPresenter(this);
        requestData();
    }

    // 获取视频详情、相似视频、视频评论
    private void requestData() {
        presenter.getVideoDetails(videoId);
        presenter.getSimilarVideos(videoId,similarPageNo,BabyConstants.SIMILARVIDEO_PAGE_SIZE);
        presenter.getAllDiscuss(videoId,discussPageNo,BabyConstants.DEFAULT_PAGE_SIZE);
    }


    private void initView() {
        mSuperVideoPlayer.setVideoPlayCallback(mVideoPlayCallback);

        // 相似视频
        rvVideoList.setLayoutManager(new LinearLayoutManager(this));
        mVideoAdapter = new VideoListAdapter(this);
        SimilarVideoFooter footerView = new SimilarVideoFooter();
        mVideoAdapter.addFooter(footerView);
        rvVideoList.setAdapter(mVideoAdapter);

        MyItemDecoration decoration = new MyItemDecoration(this,LinearLayoutManager.HORIZONTAL, 1, Color.parseColor("#e8e8e8"));
        rvDiscuss.addItemDecoration(decoration);
        LinearLayoutManager discussLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvDiscuss.setLayoutManager(discussLayoutManager);
        mDiscussAdapter = new DiscussAdapter(this);
        rvDiscuss.setAdapter(mDiscussAdapter);
        mDiscussAdapter.setMore(R.layout.view_recyclerview_more,new OnLoadMoreDiscussListener());

        mContinuePlay.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mWXCircle.setOnClickListener(this);
        mWXFriends.setOnClickListener(this);
        mSharetoWXCircle.setOnClickListener(this);
        mSharetoWX.setOnClickListener(this);
        mPlayAgain.setOnClickListener(this);
        mReload.setOnClickListener(this);

        mVideoAdapter.setOnItemClickListener((int position) -> {
            if(mSuperVideoPlayer.playDone){
                mPlayAgain.setVisibility(View.GONE);
                playdoneShareLayout.setVisibility(View.GONE);
                mSuperVideoPlayer.playDone = false;
            }
            // item点击事件,播放视频、并请求相似视频接口、请求该视频相关的评论
            SimilarVideo sVideo = mVideoAdapter.getItem(position);
            mSuperVideoPlayer.updateUI("");

            videoId = sVideo.getVideoId();
            presenter.getVideoDetails(videoId);
            similarPageNo = 1;
            mVideoAdapter.clear();
            presenter.getSimilarVideos(sVideo.getVideoId(),similarPageNo,BabyConstants.SIMILARVIDEO_PAGE_SIZE);
            discussPageNo = 1;
            mDiscussAdapter.clear();
            presenter.getAllDiscuss(videoId,discussPageNo,BabyConstants.DEFAULT_PAGE_SIZE);
        });
        mPublishButton.setOnClickListener(this);


    }

    /**
     * 播放视频
     * @param url                   视频播放地址
     */
    private void playVideoWithUrl(String url) {
        mSuperVideoPlayer.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(url)){
            ToastUtils.show(this,"无效的播放地址");
            return ;
        }
        mSuperVideoPlayer.setAutoHideController(false);
        mSuperVideoPlayer.setPlayUrl(url);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSuperVideoPlayer.loadVideo();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSuperVideoPlayer.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSuperVideoPlayer.onPause();
    }

    /**
     * 超级播放器回调
     */
    private SuperVideoPlayer.VideoPlayCallbackImpl mVideoPlayCallback = new SuperVideoPlayer.VideoPlayCallbackImpl() {
        @Override
        public void onCloseVideo() {
            mSuperVideoPlayer.onDestroy();
            mSuperVideoPlayer.setVisibility(View.GONE);
            resetPageToPortrait();
        }

        @Override
        public void onPlayStart() {
            // 视频开始播放，隐藏加载动画
            mPlayloadingPb.setVisibility(View.GONE);

        }

        @Override
        public void onSwitchPageType() {
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                mSuperVideoPlayer.setPageType(MediaController.PageType.EXPAND);
            }
        }

        @Override
        public void onPlayFinish() {
//            mPlayBtnView.setVisibility(View.VISIBLE);
            mPlayAgain.setVisibility(View.VISIBLE);
            playdoneShareLayout.setVisibility(View.VISIBLE);
        }

        @Override
        public void onBack() {
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
            } else {
                finish();
            }
        }

        @Override
        public void onLoadVideoInfo(VodRspData data) {
//            mVideoAdapter.add(data);
        }
    };

    /***
     * 恢复屏幕至竖屏
     */
    private void resetPageToPortrait() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
        }
    }


    /***
     * 旋转屏幕之后回调
     *
     * @param newConfig newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 根据屏幕方向重新设置播放器的大小
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mPublishButton.setVisibility(View.GONE);
            /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
            getWindow().getDecorView().invalidate();
            float height = DensityUtil.getScreenWidth(this);
            float width = DensityUtil.getScreenHeight(this);
            mSuperVideoPlayer.getLayoutParams().height = (int) width;
            mSuperVideoPlayer.getLayoutParams().width = (int) height;
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mPublishButton.setVisibility(View.VISIBLE);
            /*final WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);*/
            float width = DensityUtil.getScreenWidth(this);
            float height = DensityUtil.dp2px(200.f);
            mSuperVideoPlayer.getLayoutParams().height = (int) height;
            mSuperVideoPlayer.getLayoutParams().width = (int) width;
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_reload:
                // 重新加载获取视频内容
                mPlayloadingPb.setVisibility(View.VISIBLE);
                mReload.setVisibility(View.GONE);
                mLoadFailed.setVisibility(View.GONE);
                presenter.getVideoDetails(videoId);
                break;

            case R.id.iv_wx_circle:
            case R.id.tv_wx_circle:
                sharePlatform = SHARE_MEDIA.WEIXIN_CIRCLE;
                startShare();
                break;

            case R.id.iv_wx_friends:
            case R.id.tv_wx:
                sharePlatform = SHARE_MEDIA.WEIXIN;
                startShare();
                break;

            case R.id.btn_play_continue:
                playVideoWithUrl(playUrl);
                break;

            case R.id.tv_play_again:
                // 重新播放
                mPlayAgain.setVisibility(View.GONE);
                playdoneShareLayout.setVisibility(View.GONE);
                mSuperVideoPlayer.playDone = false;
                mSuperVideoPlayer.goOnPlay();
                break;

            case R.id.btn_publish_comments:
                // 弹出软键盘和发表布局
                showPublishInput();
                break;
        }
    }

    /**
     * 分享
     */
    private void startShare(){
        UMVideo umVideo = new UMVideo(APIManager.REQUEST_URL_API+BabyConstants.VIDEO_SHARE+videoId);
        umVideo.setTitle(title);
        UMImage thumb = new UMImage(this, videoCover);
        umVideo.setThumb(thumb);
        umVideo.setDescription(SHARE_SUMMARY);

        mShareAction.setPlatform(sharePlatform)
                .withMedia(umVideo)
                .setCallback(umShareListener)
                .share();
    }

    /**
     * 显示发布布局，并弹出软键盘
     */
    private PopupWindow showPublishInput() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_publish_input,null);
        PopupWindow popupwindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        EditText mInput = contentView.findViewById(R.id.et_input);
        TextView mPublish = contentView.findViewById(R.id.tv_publish);
        mPublish.setOnClickListener((View v) -> {
            String inputText = mInput.getText().toString().trim();
            presenter.publishDiscuss(videoId,inputText);
            popupwindow.dismiss();
        });
        mInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mPublish.setClickable(true);
                    mPublish.setTextColor(ContextCompat.getColor(VideoPlayActivity.this,R.color.cl_primary));
                } else {
                    mPublish.setClickable(false);
                    mPublish.setTextColor(ContextCompat.getColor(VideoPlayActivity.this,R.color.cl_c5c5c5));
                }
            }
        });

        popupwindow.setFocusable(true);
        popupwindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupwindow.setOutsideTouchable(true);//点击pop外部是否取消
        popupwindow.setBackgroundDrawable(new ColorDrawable());
        popupwindow.showAtLocation(mPublishButton, Gravity.BOTTOM,0,0);
        return popupwindow;
    }

    /**
     * 加载更多相似视频
     */
    private void loadMoreSimilarVideos(){
        similarPageNo ++;
        presenter.getSimilarVideos(videoId,similarPageNo,BabyConstants.SIMILARVIDEO_PAGE_SIZE);
    }

    /**
     * 加载更多评论
     */
    private class OnLoadMoreDiscussListener implements XMBaseAdapter.OnLoadMoreListener{

        @Override
        public void onLoadMore() {
            discussPageNo ++;
            presenter.getAllDiscuss(videoId,discussPageNo,BabyConstants.DEFAULT_PAGE_SIZE);
        }
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onComplete(BaseResponse data, int flag) {
        if(data == null){
            if(flag == BabyConstants.VIDEO_VIDEODETAILS){
                mLoadFailed.setVisibility(View.VISIBLE);
                mReload.setVisibility(View.VISIBLE);
                mPlayloadingPb.setVisibility(View.GONE);
            }
            return ;
        }

        switch (data.getReturnValue()) {
            case 0:
                if (flag == BabyConstants.VIDEO_VIDEODETAILS) {
                    if (null == data.getReturnData()) {
                        return;
                    }
                    ResVideoDetails videoDetails = (ResVideoDetails) data.getReturnData();
                    if (null != videoDetails) {
                        title = videoDetails.getVideoTitle();
                        videoCover = videoDetails.getVideoCover();
                        playUrl = videoDetails.getPlayUrl();
                        mTitle.setText(title);
                        mClassfy.setText(videoDetails.getVideoClassfy());
                        mSeeObject.setText(videoDetails.getVideoAudience());
                        mVideoDesc.setText(videoDetails.getVideoDescription());
                        // 检验播放地址的合法性，并开始播放视频，如果是4G状态下则提示流量消耗
                        if(NetWorkUtil.NETWORKTYPE_WIFI != NetWorkUtil.getNetWorkType(this)){
                            costFlowLayout.setVisibility(View.VISIBLE);
                            String tipsCost = getString(R.string.tips_play_with4g);
                            mTipsView.setText(String.format(tipsCost,ZDUtils.getFormatSize(videoDetails.getVideoSize())));
                        }else{
                            costFlowLayout.setVisibility(View.GONE);
                            playVideoWithUrl(playUrl);
                        }

                    }
                } else if (flag == BabyConstants.VIDEO_SIMILARVIDEOS) {
                    if (null == data.getReturnData()) {
                        return;
                    }
                    ResSimilarVideos similarData = (ResSimilarVideos) data.getReturnData();
                    int totalNum = similarData.getSimilarTotal();
                    int maxPage = ZDUtils.calculateMaxPage(totalNum, BabyConstants.SIMILARVIDEO_PAGE_SIZE);
                    if(similarPageNo == maxPage){
                        mVideoAdapter.removeAllFooter();
                    }
                    List<SimilarVideo> simpleVideos = similarData.getSimilarVideos();
                    if (null != simpleVideos && !simpleVideos.isEmpty()) {
                        mVideoAdapter.addAll(simpleVideos);
                    }
                } else if (flag == BabyConstants.VIDEO_ALLDISCUSS) {
                    if (null == data.getReturnData()) {
                        if (discussPageNo == 1) {
                            emptyView.setVisibility(View.VISIBLE);
                        }
                        return;
                    }

                    emptyView.setVisibility(View.GONE);
                    ResAllDiscuss allDiscuss = (ResAllDiscuss) data.getReturnData();
                    if (null != allDiscuss) {
                        int totalNum = allDiscuss.getDiscussTotal();
                        int maxPage = ZDUtils.calculateMaxPage(totalNum, BabyConstants.DEFAULT_PAGE_SIZE);
                        if(discussPageNo == maxPage){
                            mDiscussAdapter.stopMore();
                        }
                        List<Discuss> discussesList = allDiscuss.getDiscussList();
                        if (null != discussesList && !discussesList.isEmpty()) {
                            mDiscussAdapter.addAll(discussesList);
                        }
                    }
                }else if(flag == BabyConstants.VIDEO_PUBLISHDISCUSS){
                    mScroll.scrollTo(0,mScroll.getHeight());
                    // 发布视频成功了，刷新评论列表
                    ToastUtils.show(this,"发布成功");
                    mDiscussAdapter.clear();
                    discussPageNo = 1;
                    presenter.getAllDiscuss(videoId,discussPageNo,BabyConstants.DEFAULT_PAGE_SIZE);
                }

                break;

            case 500:
                Logger.e("服务器错误：" + data.getReturnMsg());
                break;

        }

    }

    @Override
    public void onFailShow(int flag) {

    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Logger.d("plat", "platform" + platform+"分享成功了");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Logger.e("share", platform + " 分享失败啦");
            if (t != null) {
                Logger.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Logger.e("share", platform + " 分享取消了");
        }
    };


    private class SimilarVideoFooter implements XMBaseAdapter.ItemView {

        @Override
        public View onCreateView(ViewGroup parent) {
            View footerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_similar_footer, null);
            return footerView;
        }

        @Override
        public void onBindView(View footerView) {
            footerView.setOnClickListener((View v) -> {
                loadMoreSimilarVideos();
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSuperVideoPlayer.onDestroy();
        presenter.onUnsubscribe();
    }

}


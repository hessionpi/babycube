package com.rjzd.baby.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.orhanobut.logger.Logger;
import com.rjzd.baby.BabyConstants;
import com.rjzd.baby.R;
import com.rjzd.baby.api.ExceptionHandler;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.model.player.VodRspData;
import com.rjzd.baby.presenter.impl.VideoPresenter;
import com.rjzd.baby.tools.DensityUtil;
import com.rjzd.baby.tools.NetWorkUtil;
import com.rjzd.baby.tools.ToastUtils;
import com.rjzd.baby.tools.ZDUtils;
import com.rjzd.baby.ui.widget.videoview.SuperVideoPlayer;
import com.rjzd.baby.view.IView;
import com.zd.baby.api.model.ResVideoDetails;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create time: 2018/5/29  16:51
 * create author: Hition
 * descriptions: VideoPlayActivity
 */

public class VideoPlayActivity extends BaseActivity implements View.OnClickListener, IView {

    @BindView(R.id.video_player_view)
    SuperVideoPlayer mSuperVideoPlayer;
    @BindView(R.id.iv_back)
    ImageView mBack;
    @BindView(R.id.ll_play_cost_flow)
    LinearLayout costFlowLayout;
    @BindView(R.id.tv_tips_play_cost_flow)
    TextView mTipsView;
    @BindView(R.id.btn_play_continue)
    Button mContinuePlay;
    @BindView(R.id.pb_play_loading)
    ProgressBar mPlayloadingPb;
    @BindView(R.id.tv_tips_failed)
    TextView mLoadFailed;
    @BindView(R.id.tv_reload)
    TextView mReload;


    private long videoId;
    private String playUrl;
    private String title;
    private VideoPresenter presenter;


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
        initViewListener();

        videoId = getIntent().getLongExtra("video_id",0);
        presenter = new VideoPresenter(this);
        presenter.getVideoDetails(videoId);
    }

    private void initViewListener() {
        mSuperVideoPlayer.setVideoPlayCallback(mVideoPlayCallback);

        mBack.setOnClickListener(this);
        mContinuePlay.setOnClickListener(this);
        mReload.setOnClickListener(this);
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
        mSuperVideoPlayer.setAutoHideController(true);
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
            costFlowLayout.setVisibility(View.GONE);
        }

        @Override
        public void onSwitchPageType() {
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                mSuperVideoPlayer.setPageType(MediaController.PageType.EXPAND);
            }
        }

        @Override
        public void onPlayFinish() {
            // 视频播放结束
//            mSuperVideoPlayer.pausePlay(true);
        }

        @Override
        public void onBack() {
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
            } else {
                finish();
            }
        }

        @Override
        public void onLoadVideoInfo(VodRspData data) {

        }
    };

    /***
     * 恢复屏幕至竖屏
     */
    private void resetPageToPortrait() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
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
        /*if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            *//*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);*//*
            getWindow().getDecorView().invalidate();
            float height = DensityUtil.getScreenWidth(this);
            float width = DensityUtil.getScreenHeight(this);
            mSuperVideoPlayer.getLayoutParams().height = (int) width;
            mSuperVideoPlayer.getLayoutParams().width = (int) height;
            mSuperVideoPlayer.updateUI(title);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            *//*final WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);*//*
            float width = DensityUtil.getScreenWidth(this);
            float height = DensityUtil.dp2px(200.f);
            mSuperVideoPlayer.getLayoutParams().height = (int) height;
            mSuperVideoPlayer.getLayoutParams().width = (int) width;
            mSuperVideoPlayer.updateUI("");
        }*/
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

            case R.id.btn_play_continue:
                costFlowLayout.setVisibility(View.GONE);
                mPlayloadingPb.setVisibility(View.VISIBLE);
                playVideoWithUrl(playUrl);
                break;
        }
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onComplete(BaseResponse data, int flag) {
        if(data == null){
            mPlayloadingPb.setVisibility(View.GONE);
            if(flag == BabyConstants.VIDEO_VIDEODETAILS){
                mLoadFailed.setVisibility(View.VISIBLE);
                mReload.setVisibility(View.VISIBLE);
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
                        playUrl = videoDetails.getPlayUrl();

                        // 检验播放地址的合法性，并开始播放视频，如果是4G状态下则提示流量消耗
                        if(NetWorkUtil.NETWORKTYPE_WIFI != NetWorkUtil.getNetWorkType(this)){
                            mPlayloadingPb.setVisibility(View.GONE);
                            costFlowLayout.setVisibility(View.VISIBLE);
                            String tipsCost = getString(R.string.tips_play_with4g);
                            mTipsView.setText(String.format(tipsCost,ZDUtils.getFormatSize(videoDetails.getVideoSize())));
                        }else{
                            costFlowLayout.setVisibility(View.GONE);
                            playVideoWithUrl(playUrl);
                        }

                    }
                }
                break;

            case 500:
                Logger.e("服务器错误：" + data.getReturnMsg());
                break;
        }

    }

    @Override
    public void onFailShow(int errorCode) {
        ExceptionHandler.handleException(this,errorCode);
        mPlayloadingPb.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
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


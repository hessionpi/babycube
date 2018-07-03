package com.rjzd.baby.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.orhanobut.logger.Logger;
import com.rjzd.baby.BabyConstants;
import com.rjzd.baby.R;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.presenter.impl.VideoPresenter;
import com.rjzd.baby.ui.adapter.PregnancyWeeksAdapter;
import com.rjzd.baby.ui.adapter.RecommendVideoAdapter;
import com.rjzd.baby.ui.adapter.VideoClassifyAdapter;
import com.rjzd.baby.view.IView;
import com.zd.baby.api.model.ResAllOfVideos;
import com.zd.baby.api.model.ResVideoClassify;
import com.zd.baby.api.model.SimpleVideo;
import com.zd.baby.api.model.VideoClassify;
import com.zd.baby.api.model.VideoFilter;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * create time: 2018/6/11  13:30
 * create author: Hition
 * descriptions: 全部视频
 */

public class AllOfVideosActivity extends BaseActivity implements View.OnClickListener, IView {


    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.rv_hor_weeks)
    RecyclerView rvHorWeeks;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_hor_video_classify)
    RecyclerView rvHorVideoClassify;
    @BindView(R.id.rv_videos)
    RecyclerView rvVideos;

    // 已出生宝宝所处阶段
    public static final int BORN_BABY_NEONATAL = 28;// 新生儿 28天内
    public static final int BORN_BABY_INFANT = 365;//  幼儿1岁以内

    private int babyStage;
    private int babyStatus;
    private int week=0;

    private String classifyId = "";
    private int pageNo = 1;

    private VideoClassifyAdapter classifyAdapter;
    private RecommendVideoAdapter videoAdapter;

    private VideoFilter filter;
    private VideoPresenter presenter;


    public static void startActivity(Context context,int status,int stage) {
        Intent intent = new Intent(context, AllOfVideosActivity.class);
        intent.putExtra("baby_status",status);
        intent.putExtra("baby_stage",stage);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_videos);
        ButterKnife.bind(this);
        parseIntent();
        initView();
        presenter = new VideoPresenter(this);
        presenter.getVideoClassify(babyStatus);
        filter = new VideoFilter();
        filter.setClassifyId(classifyId);
        filter.setWeek(week);
        presenter.getAllOfVideos(filter,pageNo,BabyConstants.DEFAULT_PAGE_SIZE);
    }

    private void parseIntent() {
        babyStatus = getIntent().getIntExtra("baby_status",0);
        babyStage = getIntent().getIntExtra("baby_stage",0);
    }

    private void initView() {
        switch (babyStatus){
            case 0:
                tvTitle.setVisibility(View.VISIBLE);
                rvHorWeeks.setVisibility(View.GONE);
                tvTitle.setText(R.string.baby_preparing);
                break;

            case 1:// 怀孕中
                tvTitle.setVisibility(View.GONE);
                rvHorWeeks.setVisibility(View.VISIBLE);
                LinearLayoutManager horManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                rvHorWeeks.setLayoutManager(horManager);
                PregnancyWeeksAdapter weekAdapter = new PregnancyWeeksAdapter(this,getResources().getStringArray(R.array.pregnancy_week));
                rvHorWeeks.setAdapter(weekAdapter);
                weekAdapter.setOnItemClickListener((int position) ->{
                    String weekStr = weekAdapter.getItem(position);
                    int week = 0;
                    if(!weekStr.equals("全")){
                        week = Integer.parseInt(weekStr);
                    }
                    filter.setWeek(week);
                    pageNo = 1;
                    presenter.getAllOfVideos(filter,pageNo,BabyConstants.DEFAULT_PAGE_SIZE);
                });

                Logger.e("当前阶段："+babyStage);

                break;

            case 2:// 宝宝已出生
                tvTitle.setVisibility(View.VISIBLE);
                rvHorWeeks.setVisibility(View.GONE);
                if(babyStage <= BORN_BABY_NEONATAL){
                    tvTitle.setText(R.string.neonatal);
                }else if(babyStage > BORN_BABY_NEONATAL && babyStage <= BORN_BABY_INFANT){
                    tvTitle.setText(R.string.infant);
                }

                break;
        }

        // 视频分类RecycleView
        LinearLayoutManager classifyLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvHorVideoClassify.setLayoutManager(classifyLayoutManager);
        classifyAdapter = new VideoClassifyAdapter(this);
        rvHorVideoClassify.setAdapter(classifyAdapter);

        LinearLayoutManager videoLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvVideos.setLayoutManager(videoLayoutManager);
        videoAdapter = new RecommendVideoAdapter(this);
        rvVideos.setAdapter(videoAdapter);








        ivClose.setOnClickListener(this);
        classifyAdapter.setOnItemClickListener((int position) ->{
            // 视频分类
            VideoClassify classify = classifyAdapter.getItem(position);
            classifyId = classify.getClassifyId();
            pageNo = 1;
            filter.setClassifyId(classifyId);
            presenter.getAllOfVideos(filter,pageNo,BabyConstants.DEFAULT_PAGE_SIZE);
        });

        videoAdapter.setOnItemClickListener((int position)->{
            // 进入视频详情播放视频
            SimpleVideo videoinfo = videoAdapter.getItem(position);
            VideoPlayActivity.startActivity(AllOfVideosActivity.this,videoinfo.getVideoId());
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close:
                finish();
                break;

            default:

                break;
        }
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onComplete(BaseResponse data, int flag) {
        if(null != data.getReturnData()){
            switch (data.getReturnValue()){
                case 0:
                    if(flag == BabyConstants.VIDEO_VIDEOCLASSIFY){
                        ResVideoClassify classify = (ResVideoClassify) data.getReturnData();
                        List<VideoClassify> videoClassifies = classify.getClassifyList();
                        if(null!=videoClassifies && !videoClassifies.isEmpty()){
                            VideoClassify allClassify = new VideoClassify();
                            allClassify.setClassifyId("");
                            allClassify.setClassifyName("全部");
                            classifyAdapter.add(allClassify);
                            classifyAdapter.addAll(videoClassifies);
                        }
                    }else if(flag == BabyConstants.VIDEO_ALLOFVIDEOS){
                        ResAllOfVideos allData = (ResAllOfVideos) data.getReturnData();
                        if(null != allData){
                            allData.getTotal();
                            List<SimpleVideo> allVideos = allData.getVideoList();
                            if(null != allVideos && !allVideos.isEmpty()){
                                if(pageNo == 1){
                                    videoAdapter.setData(allVideos);
                                    videoAdapter.notifyDataSetChanged();
                                }else{
                                    videoAdapter.addAll(allVideos);
                                }
                            }else{
                                videoAdapter.clear();
                            }
                        }
                    }

                    break;

                case 500:
                    Logger.e("服务器错误："+data.getReturnMsg());
                    break;
            }
        }

    }

    @Override
    public void onFailShow(int flag) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
    }
}

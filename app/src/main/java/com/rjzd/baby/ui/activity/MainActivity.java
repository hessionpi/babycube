package com.rjzd.baby.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.rjzd.baby.BabyConstants;
import com.rjzd.baby.R;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.entity.Tools;
import com.rjzd.baby.model.LoginModel;
import com.rjzd.baby.model.UserInfoCenter;
import com.rjzd.baby.presenter.impl.BabyPresenter;
import com.rjzd.baby.presenter.impl.UserPresenter;
import com.rjzd.baby.tools.DensityUtil;
import com.rjzd.baby.tools.ToastUtils;
import com.rjzd.baby.ui.adapter.BabyAdapter;
import com.rjzd.baby.ui.adapter.PregnancyAdapter;
import com.rjzd.baby.ui.adapter.RecommendVideoAdapter;
import com.rjzd.baby.ui.adapter.ToolsAdapter;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;
import com.rjzd.baby.ui.tools.imgloader.ImageLoader;
import com.rjzd.baby.ui.widget.dialog.DialogManager;
import com.rjzd.baby.ui.widget.dialog.UniversalDialogListener;
import com.rjzd.baby.ui.widget.recycler.ScaleLayoutManager;
import com.rjzd.baby.view.IView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.UmengErrorCode;
import com.zd.baby.api.model.BabyInfo;
import com.zd.baby.api.model.ResAllBaby;
import com.zd.baby.api.model.ResBabyBaseInfo;
import com.zd.baby.api.model.ResGetUserInfo;
import com.zd.baby.api.model.ResRecommendInfo;
import com.zd.baby.api.model.ResUpdateRecommend;
import com.zd.baby.api.model.SimpleVideo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create time: 2018/5/24  17:30
 * create author: Hition
 * descriptions: MainActivity
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, IView, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.right)
    CoordinatorLayout right;
    @BindView(R.id.nav_view)
    NavigationView left;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;


    @BindView(R.id.rv_recommend_video)
    RecyclerView rvRecommendVideo;


    private View navHeaderView;
    private ImageView mHeadPic;
    private TextView mNickname;

    private boolean isDrawer = false;

    private ScaleLayoutManager viewPagerLayoutManager;
    ScaleLayoutManager viewPagerLayoutManager1;
    ScaleLayoutManager viewPagerLayoutManager2;

    RecyclerView recycler;

    RecyclerView rvPregnancy;

    ProgressBar pbWeek;

    ImageView ivWeek;

    RecyclerView rvBaby;

    RecyclerView rvTools;
    LinearLayout ll_pregnancy;
    LinearLayout ll_plan_for_pregnancy;
    LinearLayout ll_with_child;
    LinearLayout ll_baby;

    TextView tv_no_baby;
    TextView tv_date;
    BabyAdapter adapter;
    TextView tv_pregnancy_week;
    ImageView iv_shapeImgThumb;
    TextView tv_shapeDescription;
    // TextView tv_mom_pregnancy;
    TextView tv_momchanges;
    TextView tv_plan_for_pregnancy_day;
    TextView tv_pregnancy_time;
    TextView tv_baby_name;

    TextView tv_with_child_today;
    TextView tv_baby_today;
    TextView tv_change_state;
    TextView tv_baby_birth;
    LinearLayout ll_baby_change;
    LinearLayout ll_mom_change;

    PregnancyAdapter pregnancyAdapter;
    PregnancyAdapter pregnancyAdapter1;
    RecommendVideoAdapter recommendVideoAdapter;


    private LoginModel userInfo;

    private UserPresenter presenter;
    private BabyPresenter babyPresenter;

    private int babyStatus = 1;
    private int babyStage = 0;
    String babyName;

    ResBabyBaseInfo info;
    List<BabyInfo> babys;
    List<Tools> strings = new ArrayList<>();
    int babyId;
    BabyInfo babyInfo;
    int index;
    int babyPosition = 0;
    int babyInterval = 0;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        userInfo = UserInfoCenter.getInstance().getLoginModel();
        presenter = new UserPresenter(this);
        babyPresenter = new BabyPresenter(this);
        initView();
        initViewData();
        requestData();
    }

    private void initViewData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvRecommendVideo.setLayoutManager(linearLayoutManager);
        recommendVideoAdapter = new RecommendVideoAdapter(this);
        HeaderView view = new HeaderView();
        recommendVideoAdapter.addHeader(view);
        rvRecommendVideo.setNestedScrollingEnabled(false);
        rvRecommendVideo.setAdapter(recommendVideoAdapter);
        // 只有当有数据时才添加footer
        FooterView footerView = new FooterView();
        recommendVideoAdapter.addFooter(footerView);


        recommendVideoAdapter.setOnItemClickListener((int position) -> {
            SimpleVideo video = simpleVideos.get(position);
            if (null != video) {
                index = position;
                VideoPlayActivity.startActivity(MainActivity.this, video.getVideoId());
                babyPresenter.updateRecommend(video.getVideoClassifyId(), video.getVideoId(), babyStatus, babyStage);
            }
        });

    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_nav_user);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        getToolsData();
        // 左边菜单栏注册item被点击事件监听
        left.setNavigationItemSelectedListener(this);

        navHeaderView = left.getHeaderView(0);
        mHeadPic = navHeaderView.findViewById(R.id.iv_head_pic);
        mNickname = navHeaderView.findViewById(R.id.tv_nickname);

        toolbar.setNavigationOnClickListener((View v) -> {
            mDrawer.openDrawer(GravityCompat.START);
        });

        right.setOnTouchListener((View view, MotionEvent motionEvent) -> {
            if (isDrawer) {
                return left.dispatchTouchEvent(motionEvent);
            } else {
                return false;
            }
        });

        mDrawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                isDrawer = true;
                //获取屏幕的宽高
                /*WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                //设置右面的布局位置  根据左面菜单的right作为右面布局的left   左面的right+屏幕的宽度（或者right的宽度这里是相等的）为右面布局的right
                right.layout(left.getRight(), 0, left.getRight() + display.getWidth(), display.getHeight());*/
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawer = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


    }

    private void requestData() {
        presenter.getUserInfo();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (null != userInfo) {
            ImageLoader.loadTransformImage(this, userInfo.getHeadpicThumb(),R.drawable.ic_user_head_pic_default,R.drawable.ic_user_head_pic_default, mHeadPic, 0);
            mNickname.setText(userInfo.getNickname());
        }

    }

    void getToolsData() {
        Tools tools1 = new Tools();
        tools1.setId(R.drawable.money);
        tools1.setName("计步赢钱");

        Tools tools2 = new Tools();
        tools2.setId(R.drawable.experts);
        tools2.setName("专家答");


        Tools tools3 = new Tools();
        tools3.setId(R.drawable.eat);
        tools3.setName("能不能吃");

        Tools tools4 = new Tools();
        tools4.setId(R.drawable.medical);
        tools4.setName("产检时间表");

        Tools tools5 = new Tools();
        tools5.setId(R.drawable.dictionary);
        tools5.setName("专家答");

        strings.add(tools1);
        strings.add(tools2);
        strings.add(tools3);
        strings.add(tools4);
        strings.add(tools5);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 菜单栏被创建出来
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_nav, menu);
        return true;
    }

    /**
     * 菜单栏被选择
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_tools:
                // 选择了添加工具


                break;

        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_user_info:
                UserinfoActivity.startActivity(this);
                break;

            case R.id.nav_my_baby:
                BabyManageActivity.startActivity(this);
                break;

            case R.id.nav_account:

                AccountSafeActivity.startActivity(this);
                break;

            case R.id.nav_recommend:


                break;

            case R.id.nav_praise:
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id=" + getPackageName()));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.nav_about_us:
                //WebActivity.startActivity(this, BabyConstants.ADDRESS_ABOUT_US, "关于我们");
                AboutUsActivity.startActivity(this);
                break;

            case R.id.logout:

                DialogManager.showDialogWith2ButtonNoTitle(this, R.string.tips_logout, R.string.ok, R.string.cancel, new UniversalDialogListener() {
                    @Override
                    public void onNegative() {

                    }

                    @Override
                    public void onPositive() {
                        //注册JPush别名,未登录用户用unregister来作为别名
//                        JPushInterface.setAlias(this, 0, "unregister");
                        // 删除授权，下次每次三方登录都会弹出授权
                        UMShareAPI.get(getApplicationContext()).deleteOauth(MainActivity.this,
                                UserInfoCenter.getInstance().getLoginModel().getOauthPlatform(),
                                umAuthListener);
                        // 退出登录，清空数据
                        UserInfoCenter.getInstance().reset();
                        UserInfoCenter.getInstance().clearUserInfo();

                        MemberActivity.startActivity(MainActivity.this);
                        finish();
                    }
                });
                break;


        }

//        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPrepare() {

    }

    List<SimpleVideo> simpleVideos;

    @Override
    public void onComplete(BaseResponse data, int flag) {
        if (null != data) {
            switch (data.getReturnValue()) {
                case 0:
                    if (flag == BabyConstants.USER_GET_USERUINFO) {
                        ResGetUserInfo uData = (ResGetUserInfo) data.getReturnData();
                        if (null != uData) {
                            userInfo.setHeadpicThumb(uData.getHeadpicThumb());
                            userInfo.setHeadpic(uData.getHeadpic());
                            userInfo.setNickname(uData.getNickName());
                            userInfo.setSex(uData.getSex());
                            userInfo.setProvince(uData.getProvince());
                            userInfo.setCity(uData.getCity());
                            userInfo.setHeight(uData.getHeight());
                            userInfo.setWeight(uData.getWeight());
                            userInfo.setBirthday(uData.getBirthday());
                            UserInfoCenter.getInstance().setLoginBean(userInfo);
                        }

                    } else if (flag == BabyConstants.BABY_MANAGE_GET) {
                        ResAllBaby babyData = (ResAllBaby) data.getReturnData();
                        babys = babyData.getBabyList();
                        if (null != babys && !babys.isEmpty()) {
                          /*  if (adapter.getCount() > 0) {
                                adapter.clear();
                            }*/
                            babyName = babys.get(babyPosition).getBabyName();
                            adapter.setData(babys);
                            recycler.scrollToPosition(babyPosition);
                            adapter.notifyDataSetChanged();

                            babyId = babys.get(babyPosition).getBabyId();
                            babyPresenter.babyBaseInfo(babys.get(babyPosition).getBabyId());
                            babyInfo = babys.get(babyPosition);
                        }
                    } else if (flag == BabyConstants.BABY_INFO) {
                        info = (ResBabyBaseInfo) data.getReturnData();
                        babyStatus = info.getBabyStatus();
                        babyStage = info.getCurrentStage();
                        if (babyInterval==0){
                            babyInterval=babyStage;
                        }

                        if (babyStatus == 0) {//备孕
                            ll_plan_for_pregnancy.setVisibility(View.VISIBLE);
                            ll_with_child.setVisibility(View.GONE);
                            ll_baby.setVisibility(View.GONE);
                            ll_pregnancy.setVisibility(View.GONE);
                            tv_no_baby.setVisibility(View.VISIBLE);
                            babyInterval = 0;
                            setPlanForPregnancyData();
                        } else if (babyStatus == 1) {//怀孕中
                            ll_plan_for_pregnancy.setVisibility(View.GONE);
                            ll_with_child.setVisibility(View.VISIBLE);
                            ll_baby.setVisibility(View.GONE);
                            ll_pregnancy.setVisibility(View.VISIBLE);
                            tv_no_baby.setVisibility(View.GONE);
                            setWithChaildData();
                        } else if (babyStatus == 2) {//已出生
                            ll_plan_for_pregnancy.setVisibility(View.GONE);
                            ll_with_child.setVisibility(View.GONE);
                            ll_baby.setVisibility(View.VISIBLE);
                            ll_pregnancy.setVisibility(View.GONE);
                            tv_no_baby.setVisibility(View.VISIBLE);

                            setBabyData();
                        }
                        babyPresenter.recommendInfo(babyStatus,babyInterval);


                    } else if (flag == BabyConstants.RECOMMENDINFO_INFO) {
                        ResRecommendInfo resRecommendInfo = (ResRecommendInfo) data.getReturnData();
                        if (babyStatus == 1) {//怀孕中
                            ImageLoader.load(this, resRecommendInfo.getShapeImgThumb(), iv_shapeImgThumb);
                            tv_shapeDescription.setText(resRecommendInfo.getShapeDescription());
                            //tv_mom_pregnancy.setText("您怀孕" + babyStage + "周了");
                            tv_momchanges.setText(resRecommendInfo.getMomChanges());
                            tv_pregnancy_time.setText(resRecommendInfo.getCurrentDate());
                        } else {
                            tv_no_baby.setText(resRecommendInfo.getCurrentDate());
                        }
                        simpleVideos = resRecommendInfo.getRecommendVideos();
                        recommendVideoAdapter.setData(simpleVideos);

                    } else if (flag == BabyConstants.UPDATE_RECOMMEND) {
                        ResUpdateRecommend resUpdateRecommend = (ResUpdateRecommend) data.getReturnData();

                        if (resUpdateRecommend != null) {
                            SimpleVideo simpleVideo = new SimpleVideo();
                            simpleVideo.setVideoClassify(resUpdateRecommend.getVideoClassify());
                            simpleVideo.setVideoId(resUpdateRecommend.getVideoId());
                            simpleVideo.setVideoClassifyId(resUpdateRecommend.getVideoClassifyId());
                            simpleVideo.setVideoCover(resUpdateRecommend.getVideoCover());
                            simpleVideo.setVideoDescription(resUpdateRecommend.getVideoDescription());
                            simpleVideo.setVideoTitle(resUpdateRecommend.getVideoTitle());
                            simpleVideo.setVideoDuration(resUpdateRecommend.getVideoDuration());
                            simpleVideo.setVideoSize(resUpdateRecommend.getVideoSize());

                            simpleVideos.remove(index);
                            simpleVideos.add(index, simpleVideo);
                            // recommendVideoAdapter.setData(simpleVideos);
                            recommendVideoAdapter.remove(index);
                            recommendVideoAdapter.add(index, simpleVideo);
                            recommendVideoAdapter.notifyItemChanged(index);

                        }

                    }
                    break;

                case 500:

                    break;

            }


        }
    }

    //宝宝数据
    private void setBabyData() {
        tv_baby_name.setText(babyName);
        pregnancyAdapter1.setData(info.getBabyInterval());
        rvBaby.scrollToPosition(babyInterval-1);
        if(babyInterval==info.getCurrentStage()){
            tv_baby_today.setVisibility(View.GONE);
        }else{
            tv_baby_today.setVisibility(View.VISIBLE);
        }
        viewPagerLayoutManager2.setStackFromEnd(true);
    }

    //怀孕数据
    private void setWithChaildData() {

        float size = info.getBabyInterval().size();
        pbWeek.setMax((int) size);
        pbWeek.setProgress(babyInterval-info.getBabyInterval().get(0));
        ivWeek.setPadding((int) (pbWeek.getProgress() / size * DensityUtil.dp2px(345)), 0, 0, 0);
        pregnancyAdapter.setData(info.getBabyInterval());
        rvPregnancy.scrollToPosition(babyInterval-info.getBabyInterval().get(0));
        pregnancyAdapter.setRemainingDays(info.getRemainingDays(), babyStage);
        pregnancyAdapter.notifyDataSetChanged();
        tv_pregnancy_week.setText("第" + babyInterval + "周");
        if(babyInterval==info.getCurrentStage()){
            tv_with_child_today.setVisibility(View.GONE);
        }else{
            tv_with_child_today.setVisibility(View.GONE);
        }
        if (babyStage < 38) {
            tv_baby_birth.setVisibility(View.GONE);
        } else {
            tv_baby_birth.setVisibility(View.VISIBLE);
        }

    }

    //备孕数据
    private void setPlanForPregnancyData() {
        tv_plan_for_pregnancy_day.setText("备孕中");
        tv_date.setText(info.getNextMenstruation());
    }

    @Override
    public void onFailShow(int flag) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_pregnancy:
                //  PregnancyChangesActivity.startActivity(this, babyId, babyStage);
                break;
            case R.id.ll_baby_change:
                PregnancyChangesActivity.startActivity(this, babyId, babyStage-3, 0);
                break;
            case R.id.ll_mom_change:
                PregnancyChangesActivity.startActivity(this, babyId, babyStage-3, 1);
                break;
            case R.id.tv_with_child_today:
                rvPregnancy.scrollToPosition(info.getCurrentStage() - info.getBabyInterval().get(0));
                float size = info.getBabyInterval().size();
                ivWeek.setPadding((int) (pbWeek.getProgress() / size * DensityUtil.dp2px(345)), 0, 0, 0);
                babyPresenter.recommendInfo(babyStatus, babyStage);
                break;
            case R.id.tv_baby_today:
                rvBaby.scrollToPosition(info.getCurrentStage());
                float size1 = info.getBabyInterval().size();
                ivWeek.setPadding((int) (pbWeek.getProgress() / size1 * DensityUtil.dp2px(345)), 0, 0, 0);
                babyPresenter.recommendInfo(babyStatus, babyStage);
                break;
            case R.id.tv_change_state:

                AddBabyActivity.startActivity(this, babyInfo, AddBabyActivity.SWITCH_STATUS_FROM_PREPAR_TO_PREGNANCY);
                break;
            case R.id.tv_baby_birth:
                AddBabyActivity.startActivity(this, babyInfo, AddBabyActivity.SWITCH_STATUS_FROM_PREGNANCY_TO_BIRTH);
                break;
        }
    }

    private class HeaderView implements XMBaseAdapter.ItemView {
        @Override
        public View onCreateView(ViewGroup parent) {
            View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_baby_header, null);
            return headerView;
        }

        @Override
        public void onBindView(View headerView) {
            ll_plan_for_pregnancy = headerView.findViewById(R.id.ll_plan_for_pregnancy);
            ll_with_child = headerView.findViewById(R.id.ll_with_child);
            ll_baby = headerView.findViewById(R.id.ll_baby);
            ll_pregnancy = headerView.findViewById(R.id.ll_pregnancy);
            tv_no_baby = headerView.findViewById(R.id.tv_no_baby);

            recycler = headerView.findViewById(R.id.recycler);
            rvPregnancy = headerView.findViewById(R.id.rv_pregnancy);
            pbWeek = headerView.findViewById(R.id.pb_week);
            ivWeek = headerView.findViewById(R.id.iv_week);
            rvBaby = headerView.findViewById(R.id.rv_baby);
            rvTools = headerView.findViewById(R.id.rv_tools);
            tv_date = headerView.findViewById(R.id.tv_date);
            tv_pregnancy_week = headerView.findViewById(R.id.tv_pregnancy_week);
            iv_shapeImgThumb = headerView.findViewById(R.id.iv_shapeImgThumb);
            tv_shapeDescription = headerView.findViewById(R.id.tv_shapeDescription);
            // tv_mom_pregnancy = headerView.findViewById(R.id.tv_mom_pregnancy);
            tv_momchanges = headerView.findViewById(R.id.tv_momchanges);
            tv_plan_for_pregnancy_day = headerView.findViewById(R.id.tv_plan_for_pregnancy_day);
            tv_pregnancy_time = headerView.findViewById(R.id.tv_pregnancy_time);
            tv_baby_name = headerView.findViewById(R.id.tv_baby_name);
            tv_with_child_today = headerView.findViewById(R.id.tv_with_child_today);
            tv_baby_today = headerView.findViewById(R.id.tv_baby_today);
            tv_change_state = headerView.findViewById(R.id.tv_change_state);
            tv_baby_birth = headerView.findViewById(R.id.tv_baby_birth);
            ll_baby_change = headerView.findViewById(R.id.ll_baby_change);
            ll_mom_change = headerView.findViewById(R.id.ll_mom_change);


            ll_pregnancy.setOnClickListener(MainActivity.this);
            tv_with_child_today.setOnClickListener(MainActivity.this);
            tv_baby_today.setOnClickListener(MainActivity.this);
            tv_change_state.setOnClickListener(MainActivity.this);
            tv_baby_birth.setOnClickListener(MainActivity.this);
            ll_baby_change.setOnClickListener(MainActivity.this);
            ll_mom_change.setOnClickListener(MainActivity.this);

            //  babyPresenter.babyBaseInfo(babys.get(0).getBabyId());
            setHeaderData();


        }

        private void setHeaderData() {
            babyPresenter.getAllBabys();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
            rvTools.setLayoutManager(linearLayoutManager);
            ToolsAdapter toolsAdapter = new ToolsAdapter(MainActivity.this);

            toolsAdapter.setData(strings);
            rvTools.setAdapter(toolsAdapter);


            viewPagerLayoutManager = new ScaleLayoutManager(MainActivity.this, DensityUtil.dp2px(10), 1f);
            recycler.setLayoutManager(viewPagerLayoutManager);
            adapter = new BabyAdapter(MainActivity.this);
            recycler.setAdapter(adapter);
            adapter.setOnItemClickListener(new XMBaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    babyPosition = position;
                    recommendVideoAdapter.clear();
                    recycler.scrollToPosition(babyPosition);
                    babyInfo = adapter.getItem(position);
                    babyName = babyInfo.getBabyName();
                    babyId = babyInfo.getBabyId();
                    babyInterval=0;
                    babyPresenter.babyBaseInfo(babyInfo.getBabyId());
                }
            });
            recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        int current = viewPagerLayoutManager.getCurrentPosition();
                        babyPosition = current;

                        recommendVideoAdapter.clear();
                        babyInterval=0;
                        babyInfo = adapter.getItem(current);
                        babyName = babyInfo.getBabyName();
                        babyId = babyInfo.getBabyId();
                        babyPresenter.babyBaseInfo(babyInfo.getBabyId());
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                }
            });

            viewPagerLayoutManager1 = new ScaleLayoutManager(MainActivity.this, DensityUtil.dp2px(10), 0.5f);
            rvPregnancy.setLayoutManager(viewPagerLayoutManager1);
            pregnancyAdapter = new PregnancyAdapter(MainActivity.this, 1);
            rvPregnancy.setAdapter(pregnancyAdapter);
           /* pregnancyAdapter.setOnItemClickListener(new XMBaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Integer integer = pregnancyAdapter.getItem(position);
                    babyInterval = integer;
                    rvPregnancy.scrollToPosition(babyInterval);
                    babyPresenter.recommendInfo(babyStatus, integer);
                }
            });*/
            rvPregnancy.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        int current = viewPagerLayoutManager1.getCurrentPosition();
                        Integer integer = pregnancyAdapter.getItem(current);
                        babyInterval = integer;
                        recommendVideoAdapter.clear();
                        babyPresenter.recommendInfo(babyStatus, integer);
                        float size = info.getBabyInterval().size();
                        ivWeek.setPadding((int) ((integer - info.getBabyInterval().get(0)) / size * DensityUtil.dp2px(345)), 0, 0, 0);
                        tv_pregnancy_week.setText("第" + integer + "周");
                        if(integer==info.getCurrentStage()){
                            tv_with_child_today.setVisibility(View.GONE);
                        }else{
                            tv_with_child_today.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                }
            });


            viewPagerLayoutManager2 = new ScaleLayoutManager(MainActivity.this, DensityUtil.dp2px(10), 0.5f);
            rvBaby.setLayoutManager(viewPagerLayoutManager2);
            pregnancyAdapter1 = new PregnancyAdapter(MainActivity.this, 2);
            rvBaby.setAdapter(pregnancyAdapter1);
          /*  pregnancyAdapter1.setOnItemClickListener(new XMBaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Integer integer = pregnancyAdapter1.getItem(position);
                    babyInterval = integer;
                    rvBaby.scrollToPosition(babyInterval);
                    babyPresenter.recommendInfo(babyStatus, integer);
                }
            });*/

            rvBaby.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        int current = viewPagerLayoutManager2.getCurrentPosition();
                        recommendVideoAdapter.clear();
                        Integer integer = pregnancyAdapter1.getItem(current);
                        babyInterval = integer;
                        babyPresenter.recommendInfo(babyStatus, integer);
                        if(integer==info.getCurrentStage()){
                            tv_baby_today.setVisibility(View.GONE);
                        }else{
                            tv_baby_today.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                }
            });
        }
    }

    private class FooterView implements XMBaseAdapter.ItemView {

        @Override
        public View onCreateView(ViewGroup parent) {
            View footerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_baby_footer, null);
            return footerView;
        }

        @Override
        public void onBindView(View footerView) {
            footerView.setOnClickListener((View v) -> {
                AllOfVideosActivity.startActivity(MainActivity.this, babyStatus, babyStage);
            });
        }
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> data) {
            Logger.v("删除授权成功");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            String mess = throwable.getMessage();
            if (mess.equals(UmengErrorCode.NotInstall.getMessage())) {
                ToastUtils.show(getApplicationContext(), "删除授权失败:未安装应用");
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {

        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventLocation(String command) {
        if (!TextUtils.isEmpty(command) && command.equals("refresh")) {
            babyInterval=0;
            babyPresenter.getAllBabys();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
        babyPresenter.onUnsubscribe();
        EventBus.getDefault().unregister(this);
    }
}

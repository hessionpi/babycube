package com.rjzd.baby.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.rjzd.baby.BabyConstants;
import com.rjzd.baby.R;
import com.rjzd.baby.api.ExceptionHandler;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.entity.DrawerMenu;
import com.rjzd.baby.model.LoginModel;
import com.rjzd.baby.model.UserInfoCenter;
import com.rjzd.baby.presenter.impl.BabyPresenter;
import com.rjzd.baby.presenter.impl.UserPresenter;
import com.rjzd.baby.tools.DensityUtil;
import com.rjzd.baby.tools.NetWorkUtil;
import com.rjzd.baby.tools.SPUtils;
import com.rjzd.baby.tools.ToastUtils;
import com.rjzd.baby.ui.adapter.BabyAdapter;
import com.rjzd.baby.ui.adapter.MenuAdapter;
import com.rjzd.baby.ui.adapter.RecommendAdapter;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;
import com.rjzd.baby.ui.tools.imgloader.ImageLoader;
import com.rjzd.baby.ui.widget.CircleView;
import com.rjzd.baby.ui.widget.ObservableScrollView;
import com.rjzd.baby.ui.widget.ScrollLinearLayoutManager;
import com.rjzd.baby.ui.widget.dialog.DialogManager;
import com.rjzd.baby.ui.widget.dialog.UniversalDialogListener;
import com.rjzd.baby.view.IView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.UmengErrorCode;
import com.zd.baby.api.model.BabyInfo;
import com.zd.baby.api.model.GrowthStage;
import com.zd.baby.api.model.RecommendInfo;
import com.zd.baby.api.model.ResAllBaby;
import com.zd.baby.api.model.ResBabyGrowthCycle;
import com.zd.baby.api.model.ResGetUserInfo;
import com.zd.baby.api.model.ResRecommendInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create time: 2018/5/24  17:30
 * create author: Hition
 * descriptions: MainActivity
 */
public class MainActivity extends BaseActivity implements IView, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.right)
    CoordinatorLayout right;
    @BindView(R.id.nav_view)
    NavigationView left;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.rv_menu)
    RecyclerView rvNavMenu;
    @BindView(R.id.tv_logout)
    TextView mLogout;

    @BindView(R.id.rv_recommend)
    RecyclerView rvRecommend;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.iv_shapeImgThumb)
    ImageView ivShapeImgThumb;
    @BindView(R.id.tv_shapeDescription)
    TextView tvShapeDescription;
    @BindView(R.id.seekbar)
    CircleView seekbar;
    @BindView(R.id.sv_mian)
    ObservableScrollView svMain;
    @BindView(R.id.ll_menu_bottom)
    LinearLayout llMenuBottom;
    @BindView(R.id.tv_bottom_back)
    TextView tvBottomBack;
    @BindView(R.id.tv_bottom_next)
    TextView tvBottomNext;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    @BindView(R.id.iv_bottom_back)
    ImageView ivBottomBack;
    @BindView(R.id.iv_bottom_next)
    ImageView ivBottomNext;
    @BindView(R.id.first_two)
    TextView firstTwo;
    @BindView(R.id.first_one)
    TextView firstOne;
    @BindView(R.id.first_three)
    TextView firstThree;
    @BindView(R.id.second)
    TextView second;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.tv_baby_birth)
    TextView tvBabyBirth;
    @BindView(R.id.ll_baby_birth)
    LinearLayout llBabyBirth;
    @BindView(R.id.content_main)
    RelativeLayout contentMain;
    @BindView(R.id.ll_baby_change)
    LinearLayout llBabyChange;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.rv_nav_babies)
    RecyclerView mBabyList;
    @BindView(R.id.ll_babygrowth_no_network)
    LinearLayout llBabyGrowthNoNetwork;
    @BindView(R.id.ll_babyrecommend_no_network)
    LinearLayout llbabyrecommendNoNetwork;

    BabyAdapter babyAdapter;
    @BindView(R.id.ll_recommend)
    LinearLayout llRecommend;
    @BindView(R.id.view_bottom)
    View viewBottom;
    @BindView(R.id.rv_ll_bottom_bt)
    RelativeLayout rvLlBottomBt;
    @BindView(R.id.ll_first)
    LinearLayout llFirst;
    private View navHeaderView;
    private ImageView mHeadPic;
    private TextView mNickname;

    private FrameLayout mCurrentBaby;
    private TextView mBabyName;
    private TextView mBabyage;
    private CheckBox mExpandView;


    private boolean isDrawer = false;


    RecommendAdapter recommendAdapter;

    MenuAdapter menuAdapter;

    private LoginModel userInfo;

    private UserPresenter presenter;
    private BabyPresenter babyPresenter;


    private int deletedBabyId;
    int babyId;

    private DefaultDispBaby dispBaby;


    List<GrowthStage> growthStages;
    int currentIndex;

    int expectProgress;
    int actualProgress;
    int currentStatus;
    int actuallndex;
    int actualStutas;
    ScrollLinearLayoutManager linearLayoutManager;
    GrowthStage growthStage;

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

    void loadBabyGrowthData(int babyid) {
        if (NetWorkUtil.isNetworkConnected(this)) {
            svMain.setVisibility(View.VISIBLE);
            llBabyGrowthNoNetwork.setVisibility(View.GONE);
            babyPresenter.babyGrowthCycle(babyid);
        } else {
            llBabyGrowthNoNetwork.setVisibility(View.VISIBLE);
            svMain.setVisibility(View.GONE);
        }

    }

    void loadBabyRecommendData(int babyId, int currentStatus, String requireStage, String stageUnit) {
        if (growthStages != null && growthStages.size() != 0) {
            if (NetWorkUtil.isNetworkConnected(this)) {
                llRecommend.setVisibility(View.VISIBLE);
                llbabyrecommendNoNetwork.setVisibility(View.GONE);
                babyPresenter.recommendInfo(babyId, currentStatus, requireStage, stageUnit);
            } else {
                llbabyrecommendNoNetwork.setVisibility(View.VISIBLE);
                llRecommend.setVisibility(View.GONE);
                llBabyChange.setVisibility(View.GONE);
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.GONE);
            }
        } else {
            llBabyGrowthNoNetwork.setVisibility(View.VISIBLE);
            svMain.setVisibility(View.GONE);
        }
    }

    private void initViewData() {
        linearLayoutManager = new ScrollLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvRecommend.setLayoutManager(linearLayoutManager);
        recommendAdapter = new RecommendAdapter(this);
        rvRecommend.setNestedScrollingEnabled(false);
        rvRecommend.setAdapter(recommendAdapter);


        tvBack.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        tvBottomBack.setOnClickListener(this);
        ivBottomBack.setOnClickListener(this);
        tvBottomNext.setOnClickListener(this);
        ivBottomNext.setOnClickListener(this);

        tvBabyBirth.setOnClickListener(this);
        llBabyGrowthNoNetwork.setOnClickListener(this);
        llbabyrecommendNoNetwork.setOnClickListener(this);
    }

    private void initView() {
        Typeface mtypeface = Typeface.createFromAsset(getAssets(), "font/DINEngschriftStd.otf");
        firstTwo.setTypeface(mtypeface);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_nav_user);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        navHeaderView = left.getHeaderView(0);
        mHeadPic = navHeaderView.findViewById(R.id.iv_head_pic);
        mNickname = navHeaderView.findViewById(R.id.tv_nickname);
        mCurrentBaby = navHeaderView.findViewById(R.id.fl_baby);
        mBabyName = navHeaderView.findViewById(R.id.tv_baby_name);
        mBabyage = navHeaderView.findViewById(R.id.tv_baby_age);
        mExpandView = navHeaderView.findViewById(R.id.rb_expand);
        // 初始化左侧菜单
        initLeftNavigationMenu();
        // 左边菜单栏注册item被点击事件监听
//        left.setNavigationItemSelectedListener(this);

        mNickname.setOnClickListener(this);
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
                // 侧滑菜单被打开了
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawer = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        svMain.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (actualStutas == 1 && actualProgress >= 9000 && y - oldy > 0) {
                    llBabyBirth.setVisibility(View.VISIBLE);
                } else {
                    llBabyBirth.setVisibility(View.GONE);
                }
            }
        });
        // 退出登录
        mLogout.setOnClickListener(this);

    }

    /**
     * 侧滑菜单
     */
    private void initLeftNavigationMenu() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvNavMenu.setLayoutManager(linearLayoutManager);
        List<DrawerMenu> menuList = new ArrayList<>();
        menuList.add(new DrawerMenu(R.drawable.ic_nav_menu_uinfo, R.string.userinfo));
        menuList.add(new DrawerMenu(R.drawable.ic_nav_menu_mybaby, R.string.my_baby));
        menuList.add(new DrawerMenu(R.drawable.ic_menu_account_safe, R.string.nav_menu_account_safe));
        menuList.add(new DrawerMenu(R.drawable.ic_nav_menu_recommend, R.string.nav_menu_recommend));
        menuList.add(new DrawerMenu(R.drawable.ic_nav_menu_praise, R.string.nav_menu_praise));
        menuList.add(new DrawerMenu(R.drawable.ic_nav_menu_about_us, R.string.nav_menu_about_us));
        menuAdapter = new MenuAdapter(this, menuList);
        rvNavMenu.setAdapter(menuAdapter);
        menuAdapter.setOnItemClickListener((int position) -> {
            switch (position) {
                case 0:
                    if (userInfo != null) {
                        UserinfoActivity.startActivity(this);
                    } else {
                        LoginActivity.startActivityForResult(this);
                    }

                    break;

                case 1:
                    if (userInfo != null) {
                        BabyManageActivity.startActivity(this);
                    } else {
                        LoginActivity.startActivityForResult(this);
                    }

                    break;

                case 2:
                    AccountSafeActivity.startActivity(this);
                    break;

                case 3:

                    break;

                case 4:
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("market://details?id=" + getPackageName()));
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 5:
                    AboutUsActivity.startActivity(this);
                    break;

            }
        });

        String defaultBaby = (String) SPUtils.get("default_baby", "");
        if (!TextUtils.isEmpty(defaultBaby)) {
            String[] defBabyArg = defaultBaby.split(";");
            if (defBabyArg.length == 4) {
                // 默认宝宝id
                babyId = Integer.parseInt(defBabyArg[0]);
                mBabyName.setText(defBabyArg[1]);
                mBabyage.setText(defBabyArg[2]);
                changeTheme(Integer.parseInt(defBabyArg[3]));
            }
        }
        // babyPresenter.babyGrowthCycle(babyId);
        loadBabyGrowthData(babyId);
        LinearLayoutManager babyLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mBabyList.setLayoutManager(babyLayoutManager);
        babyAdapter = new BabyAdapter(this);
        babyAdapter.addFooter(new NavBabyFooter());
        mBabyList.setAdapter(babyAdapter);

        babyAdapter.setOnItemClickListener(new XMBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                BabyInfo babyinfo = babyAdapter.getItem(position);
                saveDefaultBabyinfo(babyinfo.getBabyId(), babyinfo.getBabyName(), babyinfo.getBabyAge(), babyinfo.getBabyStatus());
                mBabyName.setText(babyinfo.getBabyName());
                mBabyage.setText(babyinfo.getBabyAge());
                babyId = babyinfo.getBabyId();
                changeTheme(babyinfo.getBabyStatus());
            }
        });

        mExpandView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mBabyList.setVisibility(View.VISIBLE);
                } else {
                    mBabyList.setVisibility(View.GONE);
                }
            }
        });

    }

    private void changeTheme(int babystatus) {
        if (babystatus == 1) {
            // 怀孕中，修改标题栏和状态栏的颜色为蓝色
            toolbar.setBackgroundResource(R.color.colorPrimary);
            navHeaderView.setBackgroundResource(R.color.colorPrimary);
        } else if (babystatus == 2) {
            // 已出生，修改标题栏和状态栏的颜色为草黄色
            toolbar.setBackgroundResource(R.color.cl_93ba43);
            navHeaderView.setBackgroundResource(R.color.cl_93ba43);
        }
        loadBabyGrowthData(babyId);
        // babyPresenter.babyGrowthCycle(babyId);
    }

    private void requestData() {
        presenter.getUserInfo();
        babyPresenter.getAllBabys();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (null != userInfo) {
            ImageLoader.loadTransformImage(this, userInfo.getHeadpicThumb(), R.drawable.ic_user_head_pic_default, R.drawable.ic_user_head_pic_default, mHeadPic, 0);
            mNickname.setText(userInfo.getNickname());
            mLogout.setVisibility(View.VISIBLE);
            // view.setLoginHeaderData();
        } else {
            mLogout.setVisibility(View.GONE);
            mHeadPic.setImageResource(R.drawable.ic_user_head_pic_default);
            mNickname.setText("登录");
        }


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

                ToolsActivity.startActivity(this);
                break;

        }
        return true;
    }


    private class NavBabyFooter implements XMBaseAdapter.ItemView {

        @Override
        public View onCreateView(ViewGroup parent) {
            return LayoutInflater.from(MainActivity.this).inflate(R.layout.item_nav_left_baby_manage_footer, null);
        }

        @Override
        public void onBindView(View headerView) {
            headerView.setOnClickListener(v -> {
                BabyManageActivity.startActivity(MainActivity.this);
            });
        }
    }

    @Override
    public void onPrepare() {

    }


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

                    } else if (flag == BabyConstants.BABY_GROWTH_CYCLE) {
                        ResBabyGrowthCycle resBabyGrowthCycle = (ResBabyGrowthCycle) data.getReturnData();
                        if (resBabyGrowthCycle != null) {
                            actuallndex = resBabyGrowthCycle.getActualIndex();
                            actualProgress = resBabyGrowthCycle.getActualProgress();
                            actualStutas = resBabyGrowthCycle.getGrowthStage().get(actuallndex).getCurrentStatus();
                            currentIndex = actuallndex;

                            growthStages = resBabyGrowthCycle.getGrowthStage();
                            if (actualStutas == 1 && actualProgress >= 9000) {
                                llBabyBirth.setVisibility(View.VISIBLE);
                            } else {
                                llBabyBirth.setVisibility(View.GONE);
                            }
                            if (currentIndex >= 0) {
                                setGrowthStages(currentIndex);
                            }

                        }


                    } else if (flag == BabyConstants.RECOMMENDINFO_INFO) {
                        ResRecommendInfo resRecommendInfo = (ResRecommendInfo) data.getReturnData();
                        if (resRecommendInfo != null) {
                            String babyContour = resRecommendInfo.getBabyContour();
                            String babyPrototype = resRecommendInfo.getBabyPrototype();
                            String shapeDescription = resRecommendInfo.getShapeDescription();
                            if (currentStatus == 1) {
                                ImageLoader.load(this, babyContour, R.drawable.ic_default_banner_pregancy, R.drawable.ic_default_banner_pregancy, ivPic);
                            } else {
                                ImageLoader.load(this, babyContour, R.drawable.ic_default_banner_born, R.drawable.ic_default_banner_born, ivPic);
                            }
                            if (babyPrototype == null || "".equals(babyPrototype)) {
                                llBabyChange.setVisibility(View.GONE);
                                line1.setVisibility(View.GONE);
                                line2.setVisibility(View.VISIBLE);
                            } else {
                                llBabyChange.setVisibility(View.VISIBLE);
                                ImageLoader.load(this, babyPrototype, ivShapeImgThumb);
                                tvShapeDescription.setText(shapeDescription);
                                line1.setVisibility(View.VISIBLE);
                                line2.setVisibility(View.GONE);
                            }

                            List<RecommendInfo> recommendInfos = resRecommendInfo.getRecommends();
                            recommendAdapter.setCurrentStatus(currentStatus, expectProgress);
                            if (recommendInfos != null && recommendInfos.size() != 0) {
                                recommendAdapter.setData(recommendInfos);
                                for (int i = 0; i < recommendInfos.size(); i++) {
                                    if (recommendInfos.get(i).getIsToday()) {
                                        rvRecommend.scrollToPosition(i);
                                    }
                                }
                                recommendAdapter.notifyDataSetChanged();
                                if (recommendInfos.size() >= 3) {
                                    viewBottom.setVisibility(View.VISIBLE);
                                    rvLlBottomBt.setVisibility(View.VISIBLE);
                                } else {
                                    viewBottom.setVisibility(View.GONE);
                                    rvLlBottomBt.setVisibility(View.GONE);
                                }
                            }
                        }
                    } else if (flag == BabyConstants.BABY_MANAGE_GET) {
                        // 宝宝列表
                        ResAllBaby babies = (ResAllBaby) data.getReturnData();
                        List<BabyInfo> babyInfoList = babies.getBabyList();
                        if (null == babyInfoList || babyInfoList.isEmpty()) {
                            mCurrentBaby.setVisibility(View.INVISIBLE);
                            mBabyList.setVisibility(View.GONE);
                            return;
                        }
                        mCurrentBaby.setVisibility(View.VISIBLE);
                        babyAdapter.setData(babyInfoList);
                        babyAdapter.notifyDataSetChanged();

                        if (dispBaby == DefaultDispBaby.UPDATE) {
                            for (BabyInfo baby : babyInfoList) {
                                if (baby.getBabyId() == babyId) {
                                    mBabyName.setText(baby.getBabyName());
                                    mBabyage.setText(baby.getBabyAge());
                                    saveDefaultBabyinfo(baby.getBabyId(), baby.getBabyName(), baby.getBabyAge(), baby.getBabyStatus());
                                    loadBabyGrowthData(babyId);
                                    break;
                                }
                            }
                        } else if (dispBaby == DefaultDispBaby.DELETE) {
                            // 删除的孩子有显示中的话，就把列表第一个孩子作为显示中孩子
                            if (deletedBabyId == babyId) {
                                BabyInfo dispBaby = babyInfoList.get(0);
                                mBabyName.setText(dispBaby.getBabyName());
                                mBabyage.setText(dispBaby.getBabyAge());
                                babyId = dispBaby.getBabyId();
                                saveDefaultBabyinfo(dispBaby.getBabyId(), dispBaby.getBabyName(), dispBaby.getBabyAge(), dispBaby.getBabyStatus());
                                loadBabyGrowthData(babyId);
                                // babyPresenter.babyGrowthCycle(babyId);
                            }
                        }
                        dispBaby = DefaultDispBaby.DEFAULT;
                    }
                    break;

                case 500:

                    break;

            }


        }
    }

    private void setGrowthStages(int index) {
        if (growthStages != null && growthStages.size() != 0 && index >= 0 && index < growthStages.size()) {
            growthStage = growthStages.get(index);
            currentStatus = growthStage.getCurrentStatus();
            expectProgress = growthStage.getExpectProgress();
            if (actualStutas == currentStatus) {
                seekbar.setColorProgress(actualProgress);
            } else if (actualStutas == 1 && currentStatus == 2) {
                seekbar.setColorProgress(0);
            } else if (actualStutas == 2 && currentStatus == 1) {
                seekbar.setColorProgress(10000);
            }
            seekbar.setProgress(growthStage.getExpectProgress());
            String firLineDesc = growthStage.getFirLineDesc();
            String secLineDesc = growthStage.getSecLineDesc();

            String[] firs = firLineDesc.split(",");
            Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*[+]?");

            if (firs.length >= 2) {
                Matcher isNum = pattern.matcher(firs[1]);
                if (isNum.matches()) {
                    firstTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.getResources().getDimension(R.dimen.font_size_60));
                    firstTwo.setTextSize(60);
                    LinearLayout.LayoutParams playImageLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    playImageLP.setMargins(0, DensityUtil.dp2px(25), 0, 0);
                    llFirst.setLayoutParams(playImageLP);
                } else {
                    firstTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.getResources().getDimension(R.dimen.font_size_29));
                    firstTwo.setTextSize(29);
                    LinearLayout.LayoutParams playImageLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    playImageLP.setMargins(0, DensityUtil.dp2px(55), 0, 0);
                    llFirst.setLayoutParams(playImageLP);
                }
                firstOne.setText(firs[0] + "");
                firstTwo.setText(firs[1] + "");
                if (firs.length == 3) {
                    firstThree.setText(firs[2] + "");
                } else {
                    firstThree.setText(null);
                }
            } else {
                firstOne.setText(null);
                firstTwo.setText(null);
                firstThree.setText(null);
            }


            if (secLineDesc != null && !"".equals(secLineDesc)) {
                line.setVisibility(View.VISIBLE);
                second.setVisibility(View.VISIBLE);
                second.setText(secLineDesc);
            } else {
                line.setVisibility(View.INVISIBLE);
                second.setVisibility(View.INVISIBLE);
            }

            if (currentStatus == 1) {//怀孕中
                setWithChaildData(index);
            } else {//已出生
                setBabyData(index);
            }
            loadBabyRecommendData(babyId, currentStatus, growthStage.getCurrentStage(), growthStage.getCurrentUnit());
            //babyPresenter.recommendInfo(babyId, currentStatus, growthStage.getCurrentStage(), growthStage.getCurrentUnit());
        }
    }

    //宝宝数据
    private void setBabyData(int index) {
        seekbar.setProgressThumb(R.drawable.sy_icon_babyzhizhen);
        ivBack.setVisibility(View.VISIBLE);
        ivNext.setVisibility(View.VISIBLE);
        tvBack.setVisibility(View.GONE);
        tvNext.setVisibility(View.GONE);
        ivBottomBack.setVisibility(View.VISIBLE);
        ivBottomNext.setVisibility(View.VISIBLE);
        tvBottomBack.setVisibility(View.GONE);
        tvBottomNext.setVisibility(View.GONE);
        TextPaint tp = second.getPaint();
        tp.setFakeBoldText(true);
       /* ivNext.setFocusable(true);
        ivBottomNext.setFocusable(true);*/
        if (index == growthStages.size() - 1) {
            ivNext.setVisibility(View.GONE);
            ivBottomNext.setVisibility(View.GONE);
         /*   ivNext.setFocusable(false);
            ivBottomNext.setFocusable(false);*/
        }
        if (growthStages.get(index - 1).getCurrentStatus() == 1) {
            tvBack.setVisibility(View.VISIBLE);
            tvBack.setText(Integer.parseInt(growthStages.get(index - 1).getCurrentStage()) + "");
            tvBottomBack.setText(Integer.parseInt(growthStages.get(index - 1).getCurrentStage()) + "");
            ivBack.setVisibility(View.GONE);
            tvBottomBack.setVisibility(View.VISIBLE);
            ivBottomBack.setVisibility(View.GONE);
        }
    }

    //怀孕数据
    private void setWithChaildData(int index) {
        seekbar.setProgressThumb(R.drawable.sy_icon_zhizhen);
        tvBack.setVisibility(View.VISIBLE);
        tvNext.setVisibility(View.VISIBLE);
        ivBack.setVisibility(View.GONE);
        ivNext.setVisibility(View.GONE);
        tvBottomBack.setVisibility(View.VISIBLE);
        tvBottomNext.setVisibility(View.VISIBLE);
        ivBottomBack.setVisibility(View.GONE);
        ivBottomNext.setVisibility(View.GONE);
        TextPaint tp = second.getPaint();
        tp.setFakeBoldText(false);
        if (index == 0) {
            tvBack.setVisibility(View.GONE);
            tvBottomBack.setVisibility(View.GONE);
        } else {
            tvBack.setText(Integer.parseInt(growthStages.get(index - 1).getCurrentStage()) + "");
            tvBottomBack.setText(Integer.parseInt(growthStages.get(index - 1).getCurrentStage()) + "");
        }
        tvNext.setText(Integer.parseInt(growthStages.get(index + 1).getCurrentStage()) + "");
        tvBottomNext.setText(Integer.parseInt(growthStages.get(index + 1).getCurrentStage()) + "");
        if (growthStages.get(index + 1).getCurrentStatus() == 2) {
            tvNext.setVisibility(View.GONE);
            ivNext.setVisibility(View.VISIBLE);
            tvBottomNext.setVisibility(View.GONE);
            ivBottomNext.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailShow(int errorCode) {
        ExceptionHandler.handleException(this, errorCode);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_back:

            case R.id.iv_back:


                currentIndex = currentIndex - 1;
                if (currentIndex >= 0) {
                    setGrowthStages(currentIndex);
                }
                break;

            case R.id.tv_bottom_back:
            case R.id.iv_bottom_back:
                svMain.scrollTo(0, 0);
                currentIndex = currentIndex - 1;
                if (currentIndex >= 0) {
                    setGrowthStages(currentIndex);
                }
                break;
            case R.id.tv_next:
            case R.id.iv_next:

                currentIndex = currentIndex + 1;
                if (currentIndex >= 0) {
                    setGrowthStages(currentIndex);
                }

                break;

            case R.id.tv_bottom_next:
            case R.id.iv_bottom_next:
                svMain.scrollTo(0, 0);
                currentIndex = currentIndex + 1;
                if (currentIndex >= 0) {
                    setGrowthStages(currentIndex);
                }

                break;
            case R.id.tv_baby_birth:
                //跳到宝宝出生页面
                BabyManageActivity.startActivity(this);
                break;
            case R.id.tv_logout:
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
                        SPUtils.remove("default_baby");
                        UserInfoCenter.getInstance().reset();
                        UserInfoCenter.getInstance().clearUserInfo();
                        userInfo = null;
                        mLogout.setVisibility(View.GONE);
                        mDrawer.closeDrawer(GravityCompat.START);
                        // 恢复到未登录状态
                        toolbar.setBackgroundResource(R.color.colorPrimary);
                        navHeaderView.setBackgroundResource(R.color.colorPrimary);
                        mLogout.setVisibility(View.GONE);
                        mHeadPic.setImageResource(R.drawable.ic_user_head_pic_default);
                        mNickname.setText("登录");
                        mCurrentBaby.setVisibility(View.INVISIBLE);
                        // 未登录状态下默认宝宝
                        loadBabyGrowthData(0);
                        // babyPresenter.babyGrowthCycle(0);
//                        finish();
                    }
                });
                break;
            case R.id.tv_nickname:
                if (userInfo != null) {
                    UserinfoActivity.startActivity(this);
                } else {
                    LoginActivity.startActivityForResult(this);
                }
                break;
            case R.id.ll_babygrowth_no_network:
                loadBabyGrowthData(babyId);
                break;
            case R.id.ll_babyrecommend_no_network:
                loadBabyRecommendData(babyId, currentStatus, growthStage.getCurrentStage(), growthStage.getCurrentUnit());
                break;
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
    public void onEventBabyRefresh(String command) {
        if (!TextUtils.isEmpty(command)) {
            if (command.equals("refresh_baby")) {
                // 修改宝宝信息，如果是显示中的宝宝，就需要更新了
                dispBaby = DefaultDispBaby.UPDATE;
                babyPresenter.getAllBabys();
            } else if (command.contains("delete_baby")) {
                deletedBabyId = Integer.parseInt(command.substring(command.lastIndexOf("/") + 1));
                // 删除宝宝，如果是被显示的删除掉了，就把第一个宝宝拿出来显示
                dispBaby = DefaultDispBaby.DELETE;
                babyPresenter.getAllBabys();
            }
        }
    }

    private enum DefaultDispBaby {
        DEFAULT,
        UPDATE,
        DELETE
    }

    /**
     * 保存默认宝宝
     *
     * @param babyId     宝宝id
     * @param babyName   宝宝姓名
     * @param babyAge    宝宝年龄
     * @param babyStatus 宝宝状态
     */
    private void saveDefaultBabyinfo(int babyId, String babyName, String babyAge, int babyStatus) {
        StringBuilder builder = new StringBuilder();
        builder.append(babyId)
                .append(";")
                .append(babyName != null ? babyName : "")
                .append(";")
                .append(babyAge != null ? babyAge : "")
                .append(";")
                .append(babyStatus);
        SPUtils.put("default_baby", builder.toString());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            userInfo = UserInfoCenter.getInstance().getLoginModel();
            // 登陆成功了，需要更新宝宝列表啦
            mDrawer.closeDrawer(GravityCompat.START);
            babyPresenter.getAllBabys();
            String defaultBaby = (String) SPUtils.get("default_baby", "");
            if (!TextUtils.isEmpty(defaultBaby)) {
                String[] defBabyArg = defaultBaby.split(";");
                if (defBabyArg.length == 4) {
                    // 默认宝宝id
                    babyId = Integer.parseInt(defBabyArg[0]);
                    mBabyName.setText(defBabyArg[1]);
                    mBabyage.setText(defBabyArg[2]);
                    changeTheme(Integer.parseInt(defBabyArg[3]));
                }
            }
            loadBabyGrowthData(babyId);
            // babyPresenter.babyGrowthCycle(babyId);
           /* ImageLoader.loadTransformImage(this, userInfo.getHeadpicThumb(), R.drawable.ic_user_head_pic_default, R.drawable.ic_user_head_pic_default, mHeadPic, 0);
            mNickname.setText(userInfo.getNickname());
            mLogout.setVisibility(View.VISIBLE);*/
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

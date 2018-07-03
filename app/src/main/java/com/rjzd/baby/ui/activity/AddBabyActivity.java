package com.rjzd.baby.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.rjzd.baby.BabyConstants;
import com.rjzd.baby.R;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.model.UserInfoCenter;
import com.rjzd.baby.presenter.impl.BabyPresenter;
import com.rjzd.baby.tools.ToastUtils;
import com.rjzd.baby.tools.ZDUtils;
import com.rjzd.baby.ui.widget.ToolsbarView;
import com.rjzd.baby.ui.widget.dialog.DialogManager;
import com.rjzd.baby.ui.widget.dialog.OnDialogListener;
import com.rjzd.baby.view.IView;
import com.tencent.cos.xml.utils.StringUtils;
import com.zd.baby.api.model.BabyInfo;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create time: 2018/6/1  14:40
 * create author: Hition
 * descriptions: 添加宝宝或者修改宝宝
 */

public class AddBabyActivity extends BaseActivity implements View.OnClickListener, IView {

    @BindView(R.id.layout_title_bar)
    ToolsbarView mToolbarView;
    @BindView(R.id.tv_title_bar)
    TextView tvTitleBar;
    @BindView(R.id.rg_baby_status)
    RadioGroup rgBabyStatus;
    @BindView(R.id.vs_preparing)
    ViewStub vsPreparing;
    @BindView(R.id.fl_due_date)
    FrameLayout mPreganDueDateLayout;
    @BindView(R.id.tv_due_date)
    TextView mPreganDueDate;
    @BindView(R.id.layout_pregnancy)
    LinearLayout pregnancyLayout;
    @BindView(R.id.tv_calculate)
    TextView tvCalculate;
    @BindView(R.id.btn_calculate)
    Button btnCalc;
    @BindView(R.id.btn_calculate_cancel)
    Button btnCancelCalc;
    @BindView(R.id.sv_calculate)
    ScrollView svCalculate;
    @BindView(R.id.vs_born)
    ViewStub vsBorn;
    @BindView(R.id.btn_done)
    Button mDone;

    View preparingLayout;
    View bornLayout;

    @BindView(R.id.tv_last_menstruation)
    TextView tvLastMenstruation;
    @BindView(R.id.fl_last_menstruation)
    FrameLayout flLastMenstruation;
    @BindView(R.id.tv_circle_menstruation)
    TextView tvCircleMenstruation;
    @BindView(R.id.fl_circle_menstruation)
    FrameLayout flCircleMenstruation;
    @BindView(R.id.ll_add_baby)
    LinearLayout llAddBaby;
    @BindView(R.id.rb_baby_preparing)
    RadioButton rbBabyPreparing;
    @BindView(R.id.rb_baby_pregnancy)
    RadioButton rbBabyPregnancy;
    @BindView(R.id.rb_baby_born)
    RadioButton rbBabyBorn;

    private BabyInfo mBabyInfo;


    FrameLayout mPreparLastMenstrLayout;
    FrameLayout mPreparDurationLayout;
    FrameLayout mPreparCycleLayout;
    TextView mPreparLastMenstr;
    TextView mPreparDuration;
    TextView mPreparCycle;

    FrameLayout mBabyNameLayout;
    EditText mBabyName;
    FrameLayout mBabySexLayout;
    TextView mBabySex;
    FrameLayout mBabyBirthdayLayout;
    TextView mBabyBirthday;


    private ArrayList<String> menstrDurationArray;
    private ArrayList<String> menstrCycleArray;


    private static final int BABY_STATUS_PREPARING = 0;
    private static final int BABY_STATUS_PREGNANCY = 1;
    private static final int BABY_STATUS_BORN = 2;


    private int babyStatus = BABY_STATUS_PREGNANCY;
    private String babyName="宝宝";
    private int babySex;
    private String babyBirthday;
    private String dueDate;
    private String lastMenstr;
    private int menstDuration = 7;
    private int preparMenstrCircle = 28;
    private String lastDate;
    private String menstrcycle;
    private BabyPresenter presenter;

    boolean isFrist;
    boolean isUpdate;
    boolean showBornOnly;

    public static final int SWITCH_STATUS_FROM_PREPAR_TO_PREGNANCY = 100;
    public static final int SWITCH_STATUS_FROM_PREGNANCY_TO_BIRTH = 200;


    public static void startActivity(Context context, boolean isFrist,boolean showBornOnly) {
        Intent intent = new Intent(context, AddBabyActivity.class);
        intent.putExtra("isFrist", isFrist);
        intent.putExtra("isShowBornOnly", showBornOnly);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, BabyInfo babyInfo) {
        Intent intent = new Intent(context, AddBabyActivity.class);
        intent.putExtra("baby_change", true);
        intent.putExtra("baby_info", babyInfo);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, BabyInfo babyInfo,int switchStatus) {
        Intent intent = new Intent(context, AddBabyActivity.class);
        intent.putExtra("baby_change", true);
        intent.putExtra("baby_info", babyInfo);
        intent.putExtra("switch_status", switchStatus);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFrist = getIntent().getBooleanExtra("isFrist", false);
        if (isFrist) {
            getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
        }
        setContentView(R.layout.activity_add_baby);
        ButterKnife.bind(this);
        babyBirthday = ZDUtils.formatCurrent("yyyy-MM-dd");
        dueDate = ZDUtils.getNextDateStr(babyBirthday,36*7);
        parseIntent();
        initView();
        presenter = new BabyPresenter(this);
        finalData();
    }

    private void finalData() {
        menstrDurationArray = new ArrayList();
        menstrCycleArray = new ArrayList<>();
        for (int d = 2; d < 15; d++) {
            menstrDurationArray.add(d + "天");
        }
        for (int c = 14; c < 61; c++) {
            menstrCycleArray.add(c + "天");
        }
    }

    private void parseIntent() {
        showBornOnly = getIntent().getBooleanExtra("isShowBornOnly",false);
        if(showBornOnly){
            showBirthLayoutOnly();
            return ;
        }

        if (isFrist) {
            llAddBaby.setBackground(ContextCompat.getDrawable(this, R.drawable.member_bg));
            tvTitleBar.setVisibility(View.VISIBLE);
            mToolbarView.setVisibility(View.GONE);
            //该方法加载不同状态的颜色组合
            ColorStateList csl = getResources().getColorStateList(R.color.selector_textcolor_baby_add_frist);
            rbBabyPreparing.setTextColor(csl);
            rbBabyPregnancy.setTextColor(csl);
            rbBabyBorn.setTextColor(csl);
        } else {
            llAddBaby.setBackground(null);
            mToolbarView.setVisibility(View.VISIBLE);
            tvTitleBar.setVisibility(View.GONE);

            ColorStateList csl = getResources().getColorStateList(R.color.selector_textcolor_baby_add);
            rbBabyPreparing.setTextColor(csl);
            rbBabyPregnancy.setTextColor(csl);
            rbBabyBorn.setTextColor(csl);
        }

        //修改宝宝
        isUpdate = getIntent().getBooleanExtra("baby_change", false);
        if (!isUpdate) {
            return;
        }

        mBabyInfo = (BabyInfo) getIntent().getSerializableExtra("baby_info");
    }

    private void initView() {
        mPreganDueDate.setText(dueDate);

        // 修改宝宝信息
        int switchStatus = getIntent().getIntExtra("switch_status",0);
        if (null != mBabyInfo) {
            mToolbarView.setTitle(R.string.change);
            switch (mBabyInfo.getBabyStatus()) {
                case 0:// 孩子准备中
                    if(switchStatus == SWITCH_STATUS_FROM_PREPAR_TO_PREGNANCY){
                        showPregnancyOnly();
                    }else{
                        rgBabyStatus.check(R.id.rb_baby_preparing);
                        showPreparingLayout();
                    }
                    break;

                case 1:// 孩子孕育中
                    if(switchStatus == SWITCH_STATUS_FROM_PREGNANCY_TO_BIRTH){
                        showBirthLayoutOnly();
                    }else{
                        rgBabyStatus.check(R.id.rb_baby_pregnancy);
                        showPregnancyLayout();
                    }
                    break;

                case 2:// 孩子已出生进行修改就只能够修改孩子基本信息
                    /*rgBabyStatus.check(R.id.rb_baby_born);
                    showBornLayout();*/
                    showBirthLayoutOnly();
                    break;
            }
        }

        // java 8 lambda表达式，高级语法用法
        rgBabyStatus.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_baby_preparing:
                    // 备孕中
                    showPreparingLayout();
                    break;

                case R.id.rb_baby_pregnancy:
                    // 怀孕中
                    showPregnancyLayout();
                    break;

                case R.id.rb_baby_born:
                    // 孩子已出生
                    showBornLayout();
                    break;
            }
        });


        mPreganDueDateLayout.setOnClickListener(this);

        tvCalculate.setOnClickListener(this);
        btnCalc.setOnClickListener(this);
        btnCancelCalc.setOnClickListener(this);
        mDone.setOnClickListener(this);
        flLastMenstruation.setOnClickListener(this);
        flCircleMenstruation.setOnClickListener(this);
    }

    /**
     * 只显示宝宝出生布局
     */
    private void showBirthLayoutOnly(){
        rbBabyPreparing.setVisibility(View.GONE);
        rbBabyPregnancy.setVisibility(View.GONE);
        rbBabyBorn.setChecked(true);
        rbBabyBorn.setClickable(false);
        showBornLayout();
    }

    /**
     * 只显示怀孕布局
     */
    private void showPregnancyOnly(){
        rbBabyPreparing.setVisibility(View.GONE);
        rbBabyBorn.setVisibility(View.GONE);
        rbBabyPregnancy.setChecked(true);
        rbBabyPregnancy.setClickable(false);
        showPregnancyLayout();
    }

    /**
     * 备孕中布局
     */
    private void showPreparingLayout() {
        babyStatus = BABY_STATUS_PREPARING;
        if (vsPreparing.getParent() != null) {
            preparingLayout = vsPreparing.inflate();
            mPreparLastMenstrLayout = preparingLayout.findViewById(R.id.fl_preparing_last_menstruation);
            mPreparDurationLayout = preparingLayout.findViewById(R.id.fl_duration_menstruation);
            mPreparCycleLayout = preparingLayout.findViewById(R.id.fl_cycle_menstruation_prepar);
            mPreparLastMenstr = preparingLayout.findViewById(R.id.tv_preparing_last_menstruation);
            mPreparDuration = preparingLayout.findViewById(R.id.tv_duration_menstruation);
            mPreparCycle = preparingLayout.findViewById(R.id.tv_cycle_menstruation_prepar);

            mPreparLastMenstrLayout.setOnClickListener(this);
            mPreparDurationLayout.setOnClickListener(this);
            mPreparCycleLayout.setOnClickListener(this);
        } else {
            preparingLayout.setVisibility(View.VISIBLE);
        }
        mDone.setVisibility(View.VISIBLE);
        pregnancyLayout.setVisibility(View.GONE);
        if (bornLayout != null) {
            bornLayout.setVisibility(View.GONE);
        }

        if (null != mBabyInfo && mBabyInfo.getBabyStatus() == 0) {
            mPreparLastMenstr.setText(mBabyInfo.getLastMenstruation());
            mPreparDuration.setText(mBabyInfo.getDuration() + "天");
        }
    }

    /**
     * 怀孕中布局
     */
    private void showPregnancyLayout() {
        babyStatus = BABY_STATUS_PREGNANCY;
        pregnancyLayout.setVisibility(View.VISIBLE);
        svCalculate.setVisibility(View.GONE);

        if (preparingLayout != null) {
            preparingLayout.setVisibility(View.GONE);
        }
        if (bornLayout != null) {
            bornLayout.setVisibility(View.GONE);
        }
        mDone.setVisibility(View.VISIBLE);
        if (null != mBabyInfo && mBabyInfo.getBabyStatus() == 1) {
            mPreganDueDate.setText(mBabyInfo.getDueDate());
        }
    }

    /**
     * 宝宝已出生布局
     */
    private void showBornLayout() {
        babyStatus = BABY_STATUS_BORN;
        if (vsBorn.getParent() != null) {
            bornLayout = vsBorn.inflate();
            mBabyNameLayout = bornLayout.findViewById(R.id.fl_baby_nickname);
            mBabyName = bornLayout.findViewById(R.id.et_baby_nick_name);
            mBabySexLayout = bornLayout.findViewById(R.id.baby_sex);
            mBabySex = bornLayout.findViewById(R.id.tv_baby_sex);
            mBabyBirthdayLayout = bornLayout.findViewById(R.id.fl_baby_birthday);
            mBabyBirthday = bornLayout.findViewById(R.id.tv_baby_birthday);
            mBabyBirthday.setText(babyBirthday);

            mBabySexLayout.setOnClickListener(this);
            mBabyBirthdayLayout.setOnClickListener(this);
        } else {
            bornLayout.setVisibility(View.VISIBLE);
        }
        if (preparingLayout != null) {
            preparingLayout.setVisibility(View.GONE);
        }
        pregnancyLayout.setVisibility(View.GONE);
        mDone.setVisibility(View.VISIBLE);
        if (null != mBabyInfo && mBabyInfo.getBabyStatus() == 2) {
            babyName = mBabyInfo.getBabyName();
            babySex = mBabyInfo.getBabySex();
            babyBirthday = mBabyInfo.getBabyBirthday();

            mBabyName.setText(babyName);
            String sex = "男";
            if (1 == babySex) {
                sex = "男";
            } else if (2 == babySex) {
                sex = "女";
            }
            mBabySex.setText(sex);
            mBabyBirthday.setText(babyBirthday);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_preparing_last_menstruation:
                // 备孕选择上次月经
                ArrayList<String> years = new ArrayList<>();
                ArrayList<String> months = new ArrayList<>();
                ArrayList<String> days = new ArrayList<>();
                int currentYear = ZDUtils.getCurrentYear();
                int currentMonth = ZDUtils.getCurrentMonth();
                years.add(currentYear + "");
                for (int i = currentMonth-1; i <= currentMonth; i++) {
                    months.add(i + "");
                }
                for (int d = 1; d <= 31; d++) {
                    days.add(d + "");
                }
                DialogManager.showDateDialog(this, years,months, days, new OnDialogListener<String>() {
                    @Override
                    public void onNegative() {

                    }

                    @Override
                    public void onPositive(String... args) {
                        String selYear = args[0];
                        String selMonth = args[1];
                        String selDay = args[2];
                        lastMenstr = selYear + "-" + selMonth + "-" + selDay;
                        mPreparLastMenstr.setText(selYear + "年" + selMonth + "月" + selDay + "日");
                    }
                });

                break;

            case R.id.fl_duration_menstruation:
                // 备孕选择月经持续天数
                DialogManager.showSingleDialog(this, getString(R.string.duration_menstruation), menstrDurationArray, new OnDialogListener<String>() {
                    @Override
                    public void onNegative() {

                    }

                    @Override
                    public void onPositive(String... args) {
                        mPreparDuration.setText(args[0]);
                        menstDuration = Integer.parseInt(args[0].replace("天", ""));
                    }
                });

                break;

            case R.id.fl_cycle_menstruation_prepar:
                // 备孕中 月经周期
                DialogManager.showSingleDialog(this, getString(R.string.cycle_menstruation), menstrCycleArray, new OnDialogListener<String>() {
                    @Override
                    public void onNegative() {

                    }

                    @Override
                    public void onPositive(String... args) {
                        String menstrcycle = args[0];
                        mPreparCycle.setText(menstrcycle);
                        preparMenstrCircle = Integer.parseInt(menstrcycle.replace("天",""));
                    }
                });

                break;

            case R.id.fl_due_date:
                // 孕中手动选择预产期
                ArrayList<String> dueY = new ArrayList<>();
                ArrayList<String> dueD = new ArrayList<>();
                int cYear = ZDUtils.getCurrentYear();
                for (int i = cYear; i < cYear + 2; i++) {
                    dueY.add(i + "");
                }
                for (int d = 1; d <= 31; d++) {
                    dueD.add(d + "");
                }
                DialogManager.showDateDialog(this, dueY,null, dueD, new OnDialogListener<String>() {
                    @Override
                    public void onNegative() {

                    }

                    @Override
                    public void onPositive(String... args) {
                        String selYear = args[0];
                        String selMonth = args[1];
                        String selDay = args[2];
                        dueDate = selYear + "-" + selMonth + "-" + selDay;
                        mPreganDueDate.setText(selYear + "年" + selMonth + "月" + selDay + "日");
                    }
                });
                break;

            case R.id.baby_sex:
                // 已出生宝宝性别
                DialogManager.showSexDialog(this, new OnDialogListener<String>() {
                    @Override
                    public void onNegative() {

                    }

                    @Override
                    public void onPositive(String... args) {
                        babySex = Integer.parseInt(args[0]);
                        mBabySex.setText(args[1]);
                    }
                });
                break;

            case R.id.fl_baby_birthday:
                // 已出生宝宝生日选择
                ArrayList<String> birthY = new ArrayList<>();
                ArrayList<String> birthD = new ArrayList<>();
                int year = ZDUtils.getCurrentYear();
                for (int i = year - 1; i <= year; i++) {
                    birthY.add(i + "");
                }
                for (int d = 1; d <= 31; d++) {
                    birthD.add(d + "");
                }
                DialogManager.showDateDialog(this, birthY,null, birthD, new OnDialogListener<String>() {
                    @Override
                    public void onNegative() {

                    }

                    @Override
                    public void onPositive(String... args) {
                        String selYear = args[0];
                        String selMonth = args[1];
                        String selDay = args[2];
                        babyBirthday = selYear + "-" + selMonth + "-" + selDay;
                        mBabyBirthday.setText(selYear + "年" + selMonth + "月" + selDay + "日");
                    }
                });

                break;

            case R.id.tv_calculate: //   开始计算预产期
                svCalculate.setVisibility(View.VISIBLE);
                mDone.setVisibility(View.GONE);

                break;
            //末次月经
            case R.id.fl_last_menstruation:
                ArrayList<String> lastY = new ArrayList<>();
                ArrayList<String> lastM = new ArrayList<>();
                ArrayList<String> lastD = new ArrayList<>();
                int lYear = ZDUtils.getCurrentYear();
                int lMonth = ZDUtils.getCurrentMonth();
                lastY.add(lYear + "");
                for (int i = lMonth-1; i <= lMonth; i++) {
                    lastM.add(i + "");
                }
                for (int d = 1; d <= 31; d++) {
                    lastD.add(d + "");
                }
                DialogManager.showDateDialog(this, lastY, lastM,lastD, new OnDialogListener<String>() {
                    @Override
                    public void onNegative() {

                    }

                    @Override
                    public void onPositive(String... args) {
                        String selYear = args[0];
                        String selMonth = args[1];
                        String selDay = args[2];
                        lastDate = selYear + "-" + selMonth + "-" + selDay;
                        tvLastMenstruation.setText(selYear + "年" + selMonth + "月" + selDay + "日");
                    }
                });
                break;
            //月经周期
            case R.id.fl_circle_menstruation:
                DialogManager.showSingleDialog(this, getString(R.string.cycle_menstruation), menstrCycleArray, new OnDialogListener<String>() {
                    @Override
                    public void onNegative() {

                    }

                    @Override
                    public void onPositive(String... args) {
                        menstrcycle = args[0];
                        tvCircleMenstruation.setText(menstrcycle);
                    }
                });

                break;
            case R.id.btn_calculate:// 计算预产期
                //末次月经日期的月份加9或减3，为预产期月份数;天数加7，为预产期日。
                //   例如：末次月经是2014年4月15日，日期15+7=22，月份4-3=1，预产期为2015年1月22日。
                if (!StringUtils.isEmpty(lastDate) && !StringUtils.isEmpty(menstrcycle)) {
                    String[] strings = menstrcycle.split("天");
                    String sData = strings[0];
                    int data = Integer.decode(sData);
                    dueDate = ZDUtils.getNextDateStr(lastDate, 280 + data - 28);
                    mPreganDueDate.setText(dueDate);
                    svCalculate.setVisibility(View.GONE);
                    mDone.setVisibility(View.VISIBLE);
                } else {
                    ToastUtils.show(this, "选择日期");
                }
                break;

            case R.id.btn_calculate_cancel: // 取消计算预产期
                svCalculate.setVisibility(View.GONE);
                mDone.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_done:
                // 备孕布局
                if(null!=preparingLayout && preparingLayout.getVisibility()==View.VISIBLE){
                    if(TextUtils.isEmpty(lastMenstr)){
                        ToastUtils.show(this,"请输入上次月经开始时间");
                        return;
                    }
                }

                // 怀孕中布局
                if(null!=pregnancyLayout && pregnancyLayout.getVisibility()==View.VISIBLE){
                    if(TextUtils.isEmpty(dueDate)){
                        ToastUtils.show(this,"请输入预产期");
                        return;
                    }
                }

                // 已出生宝宝布局
                if(null!=bornLayout && bornLayout.getVisibility()==View.VISIBLE){
                    babyName = mBabyName.getText().toString();
                    if(TextUtils.isEmpty(babyName)){
                        ToastUtils.show(this,"请输入宝宝昵称");
                        return ;
                    }
                    if(0 == babySex){
                        ToastUtils.show(this,"请输入宝宝性别");
                        return ;
                    }
                    if(TextUtils.isEmpty(babyBirthday)){
                        ToastUtils.show(this,"请输入宝宝生日");
                        return ;
                    }
                }

                if(isUpdate){
                    // 修改宝宝信息成功了
                    if(null != mBabyInfo){
                        presenter.updateBaby(mBabyInfo.getBabyId(),babyStatus,dueDate,babySex,babyName,babyBirthday,lastMenstr,menstDuration);
                    }
                }else{
                    mDone.setClickable(false);
                    //  完成
                    presenter.addBaby(babyStatus, dueDate, babySex, babyName, babyBirthday, lastMenstr, menstDuration,preparMenstrCircle);
                }

                break;
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
                    if(flag == BabyConstants.BABY_MANAGE_ADD || flag == BabyConstants.BABY_MANAGE_UPDATE){
                        if(isFrist){
                            // 首次注册成功添加宝宝成功了
                            UserInfoCenter.getInstance().getLoginModel().setHasBaby(true);
                            MainActivity.startActivity(this);
                        }
                        finish();
                        EventBus.getDefault().post("refresh");
                    }
                    break;

                case 500:
                    Logger.v("服务器异常：" + data.getReturnMsg());
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

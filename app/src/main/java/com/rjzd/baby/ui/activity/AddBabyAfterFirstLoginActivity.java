package com.rjzd.baby.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.orhanobut.logger.Logger;
import com.rjzd.baby.R;
import com.rjzd.baby.api.ExceptionHandler;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.model.LoginModel;
import com.rjzd.baby.model.UserInfoCenter;
import com.rjzd.baby.presenter.impl.BabyPresenter;
import com.rjzd.baby.tools.SPUtils;
import com.rjzd.baby.tools.ZDUtils;
import com.rjzd.baby.ui.widget.dialog.DialogManager;
import com.rjzd.baby.ui.widget.dialog.OnDialogListener;
import com.rjzd.baby.view.IView;
import com.zd.baby.api.model.ResAddBaby;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create time: 2018/7/27  10:47
 * create author: Hition
 * descriptions: 首次登陆后添加宝宝
 */

public class AddBabyAfterFirstLoginActivity extends BaseActivity implements View.OnClickListener, IView {

    @BindView(R.id.tv_baby_birthday)
    TextView tvBabyBirthday;
    @BindView(R.id.btn_save)
    Button btnSave;

    private ArrayList<String> years = new ArrayList<>();
    private ArrayList<String> months = new ArrayList<>();
    private ArrayList<String> days = new ArrayList<>();
    private BabyPresenter babyPresenter;

    private String jumpFrom;
    public static void startActivity(Context context,String jump) {
        Intent intent = new Intent(context, AddBabyAfterFirstLoginActivity.class);
        intent.putExtra("jump_from",jump);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_baby_first_login);
        ButterKnife.bind(this);
        jumpFrom = getIntent().getStringExtra("jump_from");
        String currentDate = ZDUtils.formatCurrent("yyyy-MM-dd");
        tvBabyBirthday.setText(currentDate);
        initFinalData();
        initViewListener();
        babyPresenter = new BabyPresenter(this);
    }

    private void initFinalData() {
        int currYear = ZDUtils.getCurrentYear();
        for(int y=currYear-1;y<=currYear+1;y++){
            years.add(String.valueOf(y));
        }
        for(int m=1;m<=12;m++){
            months.add(String.valueOf(m));
        }
        for(int d=1;d<=31;d++){
            days.add(String.valueOf(d));
        }

    }

    private void initViewListener() {
        tvBabyBirthday.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_baby_birthday:
                // 弹出选择日期的轮子
                DialogManager.showDateDialog(this, years, months, days, new OnDialogListener<String>() {
                    @Override
                    public void onNegative() {

                    }

                    @Override
                    public void onPositive(String... args) {
                        if(args.length >= 3){
                            String birthday = args[0]+"-"+args[1]+"-"+args[2];
                            tvBabyBirthday.setText(birthday);
                        }
                    }
                });
                break;

            case R.id.btn_save:
                String babyBirthday = tvBabyBirthday.getText().toString();
                babyPresenter.addBaby(0,"宝宝",babyBirthday);
                break;
        }
    }


    @Override
    public void onPrepare() {

    }

    @Override
    public void onComplete(BaseResponse data, int flag) {
        if(null != data){
            switch (data.getReturnValue()){
                case 0:
                    // 成功
                    ResAddBaby baby = (ResAddBaby) data.getReturnData();
                    if(null != baby){
                        // 添加宝宝成功后，作为默认宝宝，记在本地
                        StringBuilder builder = new StringBuilder(baby.getBabyId()+"");
                        builder.append(";")
                                .append(baby.getBabyName() != null ? baby.getBabyName() : "" )
                                .append(";")
                                .append(baby.getBabyAge() !=null ? baby.getBabyAge() : "")
                                .append(";")
                                .append(baby.getBabyStatus());
                        SPUtils.put("default_baby",builder.toString());

                        UserInfoCenter uCenter = UserInfoCenter.getInstance();
                        LoginModel lModel = uCenter.getLoginModel();
                        lModel.setHasBaby(true);
                        uCenter.setLoginBean(lModel);
                        if(jumpFrom.equals("splash")){
                            MainActivity.startActivity(this);
                        }
                        finish();
                    }
                    break;

                case 500:
                    Logger.e("服务器异常："+data.getReturnMsg());
                    break;

            }
        }
    }

    @Override
    public void onFailShow(int errorCode) {
        ExceptionHandler.handleException(this,errorCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        babyPresenter.onUnsubscribe();
    }
}

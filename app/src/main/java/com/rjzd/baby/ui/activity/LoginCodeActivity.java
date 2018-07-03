package com.rjzd.baby.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.rjzd.baby.R;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.model.LoginModel;
import com.rjzd.baby.model.UserInfoCenter;
import com.rjzd.baby.presenter.impl.SystemPresenter;
import com.rjzd.baby.presenter.impl.UserPresenter;
import com.rjzd.baby.tools.MyCodeTimer;
import com.rjzd.baby.tools.NetWorkUtil;
import com.rjzd.baby.tools.ToastUtils;
import com.rjzd.baby.ui.widget.edit.ClearEditText;
import com.rjzd.baby.view.IView;
import com.zd.baby.api.model.BabyInfo;
import com.zd.baby.api.model.ResLogin;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.rjzd.baby.BabyConstants.USER_LOGIN;
/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: 登录验证码
 */
public class LoginCodeActivity extends BaseActivity implements View.OnClickListener, IView {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.cet_phone)
    ClearEditText cetPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.gain_code)
    TextView gainCode;
    @BindView(R.id.tv_login_pwd)
    TextView tvLoginPwd;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    SystemPresenter systemPresenter;
    UserPresenter userPresenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginCodeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_code);
        ButterKnife.bind(this);
        systemPresenter = new SystemPresenter(this);
        userPresenter = new UserPresenter(this);
        initView();
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        gainCode.setOnClickListener(this);
        tvLoginPwd.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.gain_code:
                String mobil = cetPhone.getText().toString();
                if (mobil.length() != 11) {
                    ToastUtils.showToast(this, "手机号应为11位数字");
                    return;
                }
                if (NetWorkUtil.isNetworkConnected(this)) {
                    codeTimer();
                    systemPresenter.sendSMSCode(mobil);
                }
                break;
            case R.id.tv_login_pwd:
                LoginPwdActivity.startActivity(this);
                break;
            case R.id.tv_login:
                String loginMobile = cetPhone.getText().toString();
                String loginCode = etCode.getText().toString();
                if (loginMobile.length() != 11) {
                    ToastUtils.showToast(this, "手机号应为11位数字");
                    return;
                }
                if (TextUtils.isEmpty(loginCode.trim())) {
                    ToastUtils.showToast(this, "验证码不能为空！");
                    return;
                }

                userPresenter.login(loginCode, loginMobile, "");
                break;
        }
    }

    void codeTimer() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            MyCodeTimer timer = new MyCodeTimer(60 * 1000, 1000, gainCode, this,R.color.white);
            timer.start();
            gainCode.setTextColor(ContextCompat.getColor(this, R.color.white));
            gainCode.setEnabled(false);
        }
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onComplete(BaseResponse data, int flag) {
        switch (flag) {
            case USER_LOGIN:
                if (0 == data.getReturnValue()) {
                    ResLogin resLogin = (ResLogin) data.getReturnData();
                    if (resLogin != null) {
                        String userId = resLogin.getUserId();
                        String nickname = resLogin.getNickName();
                        String headpic = resLogin.getHeadpic();
                        String headpicThumb = resLogin.getHeadpicThumb();
                        int sex = resLogin.getSex();

                        LoginModel loginInfo = new LoginModel(userId, headpic, headpicThumb, nickname);
                        loginInfo.setSex(sex);
                        List<BabyInfo> babyInfos = resLogin.getBabys();
                        boolean hasBaby = !(null == babyInfos || babyInfos.isEmpty());
                        loginInfo.setHasBaby(hasBaby);
                        UserInfoCenter.getInstance().setLoginBean(loginInfo);
                        ToastUtils.showToast(getApplicationContext(), "登录成功");
                        if (!hasBaby){
                            AddBabyActivity.startActivity(this,true,false);
                        }else{
                            MainActivity.startActivity(this);
                        }

                        finish();
                    }
                }else{
                    ToastUtils.showToast(getApplicationContext(), data.getReturnMsg());
                }
                break;
        }
    }

    @Override
    public void onFailShow(int flag) {

    }
}

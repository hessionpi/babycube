package com.rjzd.baby.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rjzd.baby.R;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.model.LoginModel;
import com.rjzd.baby.model.UserInfoCenter;
import com.rjzd.baby.presenter.impl.UserPresenter;
import com.rjzd.baby.tools.ToastUtils;
import com.rjzd.baby.ui.widget.edit.ClearEditText;
import com.rjzd.baby.view.IView;
import com.zd.baby.api.model.ResLogin;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rjzd.baby.BabyConstants.USER_LOGIN;
/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: 登录密码
 */
public class LoginPwdActivity extends BaseActivity implements View.OnClickListener, IView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_forget_pwd)
    TextView tvForgetpwd;
    @BindView(R.id.cet_phone)
    ClearEditText cetPhone;
    @BindView(R.id.cet_pwd)
    ClearEditText cetPwd;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    UserPresenter userPresenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginPwdActivity.class);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pwd);
        ButterKnife.bind(this);
        userPresenter = new UserPresenter(this);
        initView();
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        tvForgetpwd.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_forget_pwd:
                FindPasswordActivity.startActivity(this);
                break;
            case R.id.tv_login:
                String loginMobile = cetPhone.getText().toString();
                String password = cetPwd.getText().toString();
                if (loginMobile.length() != 11) {
                    ToastUtils.showToast(this, "手机号应为11位数字");
                    return;
                }

                if (TextUtils.isEmpty(password.trim())) {
                    ToastUtils.showToast(this, "密码不能为空！");
                    return;
                }
                if (password.trim().length() < 6) {
                    ToastUtils.showToast(this, "密码不能小于6个字符！");
                    return;
                }
                userPresenter.login("", password, loginMobile);
                break;
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
                        UserInfoCenter.getInstance().setLoginBean(loginInfo);
                        MainActivity.startActivity(this);
                        finish();
                    }
                }else{
                    ToastUtils.show(getApplicationContext(), data.getReturnMsg(), Toast.LENGTH_SHORT);
                }
                break;
        }
    }

    @Override
    public void onFailShow(int flag) {

    }
}

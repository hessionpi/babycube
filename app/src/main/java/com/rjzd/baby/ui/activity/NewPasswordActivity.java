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
import com.rjzd.baby.presenter.impl.SystemPresenter;
import com.rjzd.baby.presenter.impl.UserPresenter;
import com.rjzd.baby.tools.MyCodeTimer;
import com.rjzd.baby.tools.NetWorkUtil;
import com.rjzd.baby.tools.ToastUtils;
import com.rjzd.baby.ui.widget.edit.ClearEditText;
import com.rjzd.baby.view.IView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: 新密码页面
 */
public class NewPasswordActivity extends BaseActivity implements View.OnClickListener,IView{
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.gain_code)
    TextView gainCode;
    @BindView(R.id.cet_new_pwd)
    ClearEditText cetNewPwd;
    @BindView(R.id.tv_gain_code)
    TextView tvGainCode;
    String    mobile;
    SystemPresenter systemPresenter;
    UserPresenter userPresenter;
    public static void startActivity(Context context,String mobile) {
        Intent intent = new Intent(context, NewPasswordActivity.class);
        intent.putExtra("mobile", mobile);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        ButterKnife.bind(this);
        systemPresenter=new SystemPresenter(this);
        userPresenter=new UserPresenter(this);
initView();
    }

    private void initView() {
        mobile = getIntent().getStringExtra("mobile");
        ivBack.setOnClickListener(this);
        gainCode.setOnClickListener(this);
        tvGainCode.setOnClickListener(this);
        if (NetWorkUtil.isNetworkConnected(this)) {
            codeTimer();
            systemPresenter.sendSMSCode(mobile);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.gain_code:
                if (NetWorkUtil.isNetworkConnected(this)) {
                    codeTimer();
                    systemPresenter.sendSMSCode(mobile);
                }
                break;
            case R.id.tv_gain_code:
                String smsCode = etCode.getText().toString();
                String pwd = cetNewPwd.getText().toString();
                if (TextUtils.isEmpty(smsCode.trim())) {
                    ToastUtils.showToast(this, "验证码不能为空！");
                    return;
                }

                if (TextUtils.isEmpty(pwd.trim())) {
                    ToastUtils.showToast(this, "密码不能为空！");
                    return;
                }
                if (pwd.trim().length()<6) {
                    ToastUtils.showToast(this, "密码不能小于6个字符！");
                    return;
                }
//                userPresenter.resetPassword(mobile,smsCode,pwd);
                break;
        }
    }
    void codeTimer(){
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
        switch (flag){
            /*case USER_RESETPASSWORD:
                if (0 == data.getReturnValue()) {
                    ToastUtils.showToast(getApplicationContext(),"设置新密码成功");
                    finish();
                }
                break;*/
        }
    }

    @Override
    public void onFailShow(int flag) {

    }
}

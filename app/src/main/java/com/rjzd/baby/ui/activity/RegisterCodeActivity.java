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
import android.widget.Toast;

import com.rjzd.baby.R;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.model.LoginModel;
import com.rjzd.baby.model.UserInfoCenter;
import com.rjzd.baby.presenter.impl.SystemPresenter;
import com.rjzd.baby.presenter.impl.UserPresenter;
import com.rjzd.baby.tools.MyCodeTimer;
import com.rjzd.baby.tools.NetWorkUtil;
import com.rjzd.baby.tools.ToastUtils;
import com.rjzd.baby.ui.widget.dialog.DialogManager;
import com.rjzd.baby.ui.widget.dialog.UniversalDialogListener;
import com.rjzd.baby.view.IView;
import com.zd.baby.api.model.BabyInfo;
import com.zd.baby.api.model.ResRegister;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rjzd.baby.BabyConstants.USER_REGISTER;
/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: 注册验证码
 */
public class RegisterCodeActivity extends BaseActivity implements View.OnClickListener, IView {
    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.gain_code)
    TextView gainCode;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    @BindView(R.id.et_code)
    EditText etCode;
    String mobile;
    SystemPresenter systemPresenter;
    UserPresenter userPresenter;

    public static void startActivity(Context context, String mobile) {
        Intent intent = new Intent(context, RegisterCodeActivity.class);
        intent.putExtra("mobile", mobile);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_code);
        ButterKnife.bind(this);
        systemPresenter = new SystemPresenter(this);
        userPresenter = new UserPresenter(this);
        initView();
    }

    private void initView() {
        mobile = getIntent().getStringExtra("mobile");
        ivBack.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        gainCode.setOnClickListener(this);
        if (NetWorkUtil.isNetworkConnected(this)) {
            codeTimer();
            systemPresenter.sendSMSCode(mobile);
        }
        codeTimer();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                DialogManager.showDialogWith2ButtonNoTitle(this, R.string.tips_register_exit, R.string.yes, R.string.no, new UniversalDialogListener() {
                    @Override
                    public void onNegative() {

                    }

                    @Override
                    public void onPositive() {
                        finish();
                    }
                });
                break;
            case R.id.gain_code:

                if (NetWorkUtil.isNetworkConnected(this)) {
                    codeTimer();
                    systemPresenter.sendSMSCode(mobile);
                }
                break;
            case R.id.tv_ok:
                String smsCode = etCode.getText().toString();
                if (TextUtils.isEmpty(smsCode.trim())) {
                    ToastUtils.showToast(this, "验证码不能为空！");
                    return;
                }
                userPresenter.register(smsCode, mobile);

                break;
        }
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onComplete(BaseResponse data, int flag) {
        switch (flag) {
            case USER_REGISTER:
                if (0== data.getReturnValue()) {
                    ResRegister resRegister = (ResRegister) data.getReturnData();
                    if (resRegister != null) {
                        // 注册成功了，发消息通知登录注册页面要关闭页面啦
                        EventBus.getDefault().post("mobile_code_register_success");

                        String userId = resRegister.getUserId();
                        String nickname = resRegister.getNickName();
                        String headpic = resRegister.getHeadpic();
                        String headpicThumb = resRegister.getHeadpicThumb();
                        int sex = resRegister.getSex();

                        LoginModel loginInfo = new LoginModel(userId, headpic, headpicThumb, nickname);
                        loginInfo.setSex(sex);
                        List<BabyInfo> babyInfos = resRegister.getBabys();
                        boolean hasBaby = !(null == babyInfos || babyInfos.isEmpty());
                        loginInfo.setHasBaby(hasBaby);
                        UserInfoCenter.getInstance().setLoginBean(loginInfo);
                        ToastUtils.show(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT);
                        if (!hasBaby){
                            AddBabyActivity.startActivity(this,true,false);
                        }else{
                            MainActivity.startActivity(this);
                        }
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

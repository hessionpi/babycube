package com.rjzd.baby.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.orhanobut.logger.Logger;
import com.rjzd.baby.BabyConstants;
import com.rjzd.baby.R;
import com.rjzd.baby.api.ExceptionHandler;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.model.LoginModel;
import com.rjzd.baby.model.UserInfoCenter;
import com.rjzd.baby.presenter.impl.SystemPresenter;
import com.rjzd.baby.presenter.impl.UserPresenter;
import com.rjzd.baby.tools.MyCodeTimer;
import com.rjzd.baby.tools.SPUtils;
import com.rjzd.baby.tools.ToastUtils;
import com.rjzd.baby.ui.widget.edit.ClearEditText;
import com.rjzd.baby.view.IView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.UmengErrorCode;
import com.zd.baby.api.model.BabyInfo;
import com.zd.baby.api.model.ReqOAuthLogin;
import com.zd.baby.api.model.ResLogin;
import com.zd.baby.api.model.ResOAuthLogin;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: 登录页面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, IView {


    @BindView(R.id.login_wechat)
    ImageView loginWechat;
    @BindView(R.id.login_sina)
    ImageView loginSina;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.et_mobile)
    ClearEditText etMobile;
    @BindView(R.id.et_sms_code)
    EditText etSmsCode;
    @BindView(R.id.tv_send_sms_code)
    TextView tvSendSmsCode;
    @BindView(R.id.user_agreement)
    TextView userAgreement;
    @BindView(R.id.iv_login)
    ImageView ivLogin;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.ll_other_login)
    LinearLayout llOtherLogin;
    @BindView(R.id.tv_user_agreement)
    TextView tvUserAgreement;

    private SHARE_MEDIA platform = null;
    private UserPresenter userPresenter;
    private SystemPresenter systemPresenter;

    public static void startActivityForResult(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        Bundle transBundle = ActivityOptionsCompat.makeCustomAnimation(activity,R.anim.anim_enter_from_bottom,0).toBundle();
        activity.startActivityForResult(intent,10,transBundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        userPresenter = new UserPresenter(this);
        systemPresenter = new SystemPresenter(this);
    }

    private void initView() {
        tvSendSmsCode.setOnClickListener(this);
        userAgreement.setOnClickListener(this);
        tvUserAgreement.setOnClickListener(this);
        ivLogin.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        loginWechat.setOnClickListener(this);
        loginSina.setOnClickListener(this);
        etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //
                if(s.length()>0){
                    llLogin.setVisibility(View.VISIBLE);
                    llOtherLogin.setVisibility(View.GONE);
                    tvSendSmsCode.setClickable(true);
                    tvSendSmsCode.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.cl_333333));
                }else{
                    llLogin.setVisibility(View.GONE);
                    llOtherLogin.setVisibility(View.VISIBLE);
                    tvSendSmsCode.setClickable(false);
                    tvSendSmsCode.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.cl_999999));
                }
            }
        });

        etSmsCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //
                if(s.length()>5){
                    ivLogin.setImageResource(R.drawable.ic_login_enable);
                    ivLogin.setClickable(true);
                }else{
                    ivLogin.setImageResource(R.drawable.ic_login_disable);
                    ivLogin.setClickable(false);
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                finish();
                break;

            case R.id.tv_send_sms_code:
                // 发送短信验证码
                String sendmobile = etMobile.getText().toString();
                systemPresenter.sendSMSCode(sendmobile);
                startTimer();
                break;

            case R.id.user_agreement:
            case R.id.tv_user_agreement:
                // 用户协议

                break;

            case R.id.iv_login:
                String mobile = etMobile.getText().toString();
                String smsCode = etSmsCode.getText().toString();
                userPresenter.login(smsCode,mobile);
                break;

            case R.id.login_wechat:
                platform = SHARE_MEDIA.WEIXIN;
                oauthLogin();
                break;
            case R.id.login_sina:
                platform = SHARE_MEDIA.SINA;
                oauthLogin();
                break;
        }
    }

    //第三方登录
    public void oauthLogin() {
        UMShareAPI.get(getApplicationContext()).getPlatformInfo(this, platform, umAuthListener);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Logger.v("授权开始");
        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> data) {
            //回调成功，即登陆成功后这里返回Map<String, String> map，map里面就是用户的信息，可以拿出来使用了
            Logger.v("授权成功");
            if (data != null) {
                ReqOAuthLogin oauth = new ReqOAuthLogin();
                if (share_media == SHARE_MEDIA.WEIXIN) {
                    String unionid = data.get("uid");
                    String screen_name = data.get("name");
                    String gender = data.get("gender");
                    String profile_image_url = data.get("iconurl");
                    String province = data.get("province");
                    String city = data.get("city");

                    oauth.setWeixinId(unionid);
                    switch (gender) {
                        case "男":
                            oauth.setSex(1);
                            break;

                        case "女":
                            oauth.setSex(2);
                            break;

                        case "0":
                        case "1":
                        case "2":
                            oauth.setSex(Integer.parseInt(gender));
                            break;

                        default:
                            oauth.setSex(0);
                            break;
                    }
                    oauth.setNickname(screen_name);
                    oauth.setIconUrl(profile_image_url);
                    oauth.setProvince(province);
                    oauth.setCity(city);
                    userPresenter.oauthLogin(oauth);
                } else if (share_media == SHARE_MEDIA.SINA) {
                    String sinaId = data.get("uid");
                    String screen_name = data.get("name");
                    String gender = data.get("gender");
                    String profile_image_url = data.get("iconurl");
                    String location = data.get("location");
                    String[] locArray = location.trim().split(" ");
                    String province = "";
                    String city = "";
                    if (locArray.length >= 2) {
                        province = locArray[0];
                        city = locArray[1];
                    }
                    Logger.e("新浪登录成功后用户信息：" + sinaId + "," + screen_name + "," + gender + "," + profile_image_url + "," + location);
                    oauth.setSinaId(sinaId);
                    if (gender.equals("男")) {
                        oauth.setSex(1);
                    } else if (gender.equals("女")) {
                        oauth.setSex(2);
                    }
                    oauth.setNickname(screen_name);
                    oauth.setIconUrl(profile_image_url);
                    oauth.setProvince(province);
                    oauth.setCity(city);
                    userPresenter.oauthLogin(oauth);
                }
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            String mess = throwable.getMessage();
            if (mess.equals(UmengErrorCode.NotInstall.getMessage())) {
                ToastUtils.show(getApplicationContext(), "授权失败:未安装应用");
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            Logger.v("授权取消");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getApplicationContext()).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onComplete(BaseResponse data, int flag) {
        if (null != data) {
            switch (data.getReturnValue()) {
                case 0:
                    if (flag == BabyConstants.USER_OAUTHLOGIN) {
                        ResOAuthLogin loginData = (ResOAuthLogin) data.getReturnData();
                        if (loginData != null) {
                            String userId = loginData.getUserId();
                            String nickname = loginData.getNickName();
                            String headpic = loginData.getHeadpic();
                            String headpicThumb = loginData.getHeadpicThumb();
                            int sex = loginData.getSex();
                            List<BabyInfo> babyInfos = loginData.getBabys();

                            saveUserInfo(userId,nickname,headpic,headpicThumb,sex,babyInfos);
                        }
                    }else if(flag == BabyConstants.USER_LOGIN){
                        ResLogin loginData = (ResLogin) data.getReturnData();
                        if (loginData != null) {
                            String userId = loginData.getUserId();
                            String nickname = loginData.getNickName();
                            String headpic = loginData.getHeadpic();
                            String headpicThumb = loginData.getHeadpicThumb();
                            int sex = loginData.getSex();
                            List<BabyInfo> babyInfos = loginData.getBabys();

                            saveUserInfo(userId,nickname,headpic,headpicThumb,sex,babyInfos);
                        }
                    }

                    break;

                case 1:
                    ToastUtils.show(this,data.getReturnMsg());
                    break;

                case 500:
                    Logger.e("服务端异常：" + data.getReturnMsg());
                    break;
            }
        }
    }

    private void saveUserInfo(String userId,String nickname,String headpic,String headpicThumb,int sex,List<BabyInfo> babyInfos){
        LoginModel loginInfo = new LoginModel(userId, headpic, headpicThumb, nickname);
        loginInfo.setSex(sex);
        boolean hasBaby = !(null == babyInfos || babyInfos.isEmpty());
        loginInfo.setHasBaby(hasBaby);
        loginInfo.setOauthPlatform(platform);
        UserInfoCenter.getInstance().setLoginBean(loginInfo);
        ToastUtils.showToast(getApplicationContext(), "登录成功");
        if (!hasBaby) {
            //  首次登陆没有宝宝就去添加宝宝
            AddBabyAfterFirstLoginActivity.startActivity(this,"login");
        } else {
            StringBuilder builder = new StringBuilder(babyInfos.get(0).getBabyId()+"");
            builder.append(";")
                    .append(babyInfos.get(0).getBabyName()!=null ? babyInfos.get(0).getBabyName() : "")
                    .append(";")
                    .append(babyInfos.get(0).getBabyAge()!=null ? babyInfos.get(0).getBabyAge() : "")
                    .append(";")
                    .append(babyInfos.get(0).getBabyStatus());
            SPUtils.put("default_baby",builder.toString());
        }

        // 用户登录成功
        finish();
        setResult(200);
    }


    @Override
    public void onFailShow(int errorCode) {
        ExceptionHandler.handleException(this,errorCode);
    }

    private void startTimer() {
        MyCodeTimer timer = new MyCodeTimer(60 * 1000, 1000, tvSendSmsCode, this,R.color.cl_333333);
        timer.start();
        tvSendSmsCode.setTextColor(ContextCompat.getColor(this, R.color.cl_999999));
        tvSendSmsCode.setEnabled(false);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.anim_exit_from_top);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userPresenter.onUnsubscribe();
    }
}

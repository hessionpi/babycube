package com.rjzd.baby.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.orhanobut.logger.Logger;
import com.rjzd.baby.BabyConstants;
import com.rjzd.baby.R;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.model.LoginModel;
import com.rjzd.baby.model.UserInfoCenter;
import com.rjzd.baby.presenter.impl.UserPresenter;
import com.rjzd.baby.tools.ToastUtils;
import com.rjzd.baby.view.IView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.UmengErrorCode;
import com.zd.baby.api.model.BabyInfo;
import com.zd.baby.api.model.ReqOAuthLogin;
import com.zd.baby.api.model.ResOAuthLogin;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: 登录注册页面
 */
public class MemberActivity extends BaseActivity implements View.OnClickListener, IView {

    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.login_wechat)
    ImageView loginWechat;
    @BindView(R.id.login_sina)
    ImageView loginSina;

    private SHARE_MEDIA platform = null;
    private UserPresenter userPresenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MemberActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        ButterKnife.bind(this);
        initView();
        userPresenter = new UserPresenter(this);

        EventBus.getDefault().register(this);
    }

    private void initView() {
        tvLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        loginWechat.setOnClickListener(this);
        loginSina.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                LoginCodeActivity.startActivity(this);
                break;

            case R.id.tv_register:
                RegisterMobileActivity.startActivity(this);
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
                    String screen_name =data.get("name");
                    String gender = data.get("gender");
                    String profile_image_url =data.get("iconurl");
                    String province = data.get("province");
                    String city = data.get("city");

                    oauth.setWeixinId(unionid);
                    switch (gender){
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
                    String screen_name =data.get("name");
                    String gender = data.get("gender");
                    String profile_image_url =data.get("iconurl");
                    String location = data.get("location");
                    String[] locArray = location.trim().split(" ");
                    String province = "";
                    String city = "";
                    if(locArray.length >= 2){
                        province = locArray[0];
                        city = locArray[1];
                    }
                    Logger.e("新浪登录成功后用户信息："+sinaId+","+screen_name+","+gender+","+profile_image_url+","+location);
                    oauth.setSinaId(sinaId);
                    if(gender.equals("男")){
                        oauth.setSex(1);
                    }else if(gender.equals("女")){
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
        if(null != data){
            switch (data.getReturnValue()){
                case 0:
                    if(flag == BabyConstants.USER_OAUTHLOGIN){
                       ResOAuthLogin loginData = (ResOAuthLogin) data.getReturnData();
                       if(loginData != null){
                           String userId = loginData.getUserId();
                           String nickname = loginData.getNickName();
                           String headpic = loginData.getHeadpic();
                           String headpicThumb = loginData.getHeadpicThumb();
                           int sex = loginData.getSex();

                           LoginModel loginInfo = new LoginModel(userId, headpic, headpicThumb, nickname);
                           loginInfo.setSex(sex);
                           List<BabyInfo> babyInfos = loginData.getBabys();
                           boolean hasBaby = !(null == babyInfos || babyInfos.isEmpty());
                           loginInfo.setHasBaby(hasBaby);
                           loginInfo.setOauthPlatform(platform);
                           UserInfoCenter.getInstance().setLoginBean(loginInfo);
                           ToastUtils.showToast(getApplicationContext(), "登录成功");
                           if (!hasBaby){
                               AddBabyActivity.startActivity(this,true,false);
                           }else{
                               MainActivity.startActivity(this);
                           }
                           finish();
                       }
                    }

                    break;

                case 500:
                    Logger.e("服务端异常："+data.getReturnData());
                    break;


            }


        }
    }

    @Override
    public void onFailShow(int flag) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventLocation(String command) {
        if(!TextUtils.isEmpty(command) && command.equals("mobile_code_register_success")){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userPresenter.onUnsubscribe();
        EventBus.getDefault().unregister(this);
    }
}

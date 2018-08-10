package com.rjzd.baby.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.rjzd.baby.R;
import com.rjzd.baby.model.LoginModel;
import com.rjzd.baby.model.UserInfoCenter;
import com.rjzd.baby.tools.SPUtils;



/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: 启动页
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                init();
            }
        }, 1200);
    }

    private void init() {
        boolean is_first = (boolean) SPUtils.get("is_first", true);
        if (is_first) {
            //第一次进入，进入导航页，is_first改成false
            GuideActivity.startActivity(this);
            SPUtils.put("is_first", false);
        } else {
            //第二次进入
            LoginModel model = UserInfoCenter.getInstance().getLoginModel();
            if(model != null && !model.isHasBaby()){
                // 如果没有宝宝就跳转至首次添加宝宝页
                AddBabyAfterFirstLoginActivity.startActivity(this,"splash");
            }else{
                // 如果有宝宝了，跳转去首页
                MainActivity.startActivity(this);
            }
        }
        finish();
    }

}
package com.rjzd.baby.ui.activity;

import android.support.v7.app.AppCompatActivity;

import com.rjzd.baby.tools.ToastUtils;

/**
 * create time: 2018/5/25  10:44
 * create author: Hition
 * descriptions: BaseActivity
 */


public class BaseActivity extends AppCompatActivity {







    public void showToast(int msgResId) {
        showToast(getString(msgResId));
    }

    public void showToast(String msg) {
        ToastUtils.show(this,msg);
    }

}

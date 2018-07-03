package com.rjzd.baby.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rjzd.baby.R;
/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: 绑定手机号
 */
public class BindingMobileActivity extends AppCompatActivity {
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, BindingMobileActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding_mobile);
    }
}

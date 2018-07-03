package com.rjzd.baby.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rjzd.baby.R;
import com.rjzd.baby.tools.ToastUtils;
import com.rjzd.baby.ui.widget.edit.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: 注册手机号
 */
public class RegisterMobileActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.cet_phone)
    ClearEditText cetPhone;
    @BindView(R.id.ll_number)
    LinearLayout llNumber;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RegisterMobileActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_mobile);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvNext.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_next:
                String mobile = cetPhone.getText().toString();
                if (mobile.length()!=11) {
                    ToastUtils.showToast(this, "手机号应为11位数字");
                    return;
                }
                RegisterCodeActivity.startActivity(this,mobile);
                finish();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}

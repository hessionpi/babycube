package com.rjzd.baby.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rjzd.baby.R;
import com.rjzd.baby.tools.ToastUtils;
import com.rjzd.baby.ui.widget.edit.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: 找回密码
 */
public class FindPasswordActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_gain_code)
    TextView tvGainCode;
    @BindView(R.id.cet_phone)
    ClearEditText cetPhone;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, FindPasswordActivity.class);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        ivBack.setOnClickListener(this);
        tvGainCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_gain_code:
                String loginMobile = cetPhone.getText().toString();
                if (loginMobile.length() != 11) {
                    ToastUtils.show(this, "手机号应为11位数字", Toast.LENGTH_LONG);
                    return;
                }
                NewPasswordActivity.startActivity(this,loginMobile);
                break;
        }
    }
}

package com.rjzd.baby.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rjzd.baby.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: 账户安全
 */
public class AccountSafeActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_binding)
    TextView tvBinding;
boolean isBinding=false;
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AccountSafeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_safe);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        tvBinding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
                case  R.id.tv_binding:
                    if (isBinding){
                       // tvBinding.setBackground(ContextCompat.getDrawable(this,R.drawable.shape_gray_unbundle_bg));
                        UnbindMobileActivity.startActivity(this);
                    }else{
                        BindingMobileActivity.startActivity(this);

                    }

                    break;
        }
    }
}

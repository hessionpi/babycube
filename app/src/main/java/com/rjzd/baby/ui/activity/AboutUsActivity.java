package com.rjzd.baby.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.rjzd.baby.R;
import butterknife.ButterKnife;

/**
 * create time: 2018/6/29  15:03
 * create author: Hition
 * descriptions: 关于我们
 */

public class AboutUsActivity extends BaseActivity{

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AboutUsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
    }
}

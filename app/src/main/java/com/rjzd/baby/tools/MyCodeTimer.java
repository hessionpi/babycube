package com.rjzd.baby.tools;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;


/**
 * Created by Administrator on 2017/10/12.
 */

public class MyCodeTimer extends CountDownTimer {
    TextView view;
    Context context;
    int colorid;

    public MyCodeTimer(long millisInFuture, long countDownInterval, TextView view, Context context, int colorid) {
        super(millisInFuture, countDownInterval);
        this.view = view;
        this.context = context;
        this.colorid = colorid;
    }


    @Override
    public void onTick(long millisUntilFinished) {
        view.setText(millisUntilFinished / 1000 + " S");
    }

    @Override
    public void onFinish() {
        view.setEnabled(true);
        view.setTextColor(ContextCompat.getColor(context, colorid));
        view.setText("发送验证码");
    }
}


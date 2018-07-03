package com.rjzd.baby.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rjzd.baby.R;
import com.rjzd.baby.ui.adapter.recycleadapter.BaseViewHolder;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;

/**
 * Created by Administrator on 2018/6/1.
 */

/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: 怀孕中周期适配器
 */
public class PregnancyAdapter extends XMBaseAdapter<Integer> {
    int babyStatus;
    int remainingDays;
    int babyStage;

    public PregnancyAdapter(Context context) {
        super(context);
    }
    public PregnancyAdapter(Context context, int babyStatus) {
        super(context);
        this.babyStatus = babyStatus;

    }
    public void setRemainingDays( int remainingDays, int babyStage){
    this.remainingDays = remainingDays;
    this.babyStage = babyStage;
}



    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PregnancyHolder(parent, R.layout.item_recycler_pregnancy);
    }

    private class PregnancyHolder extends BaseViewHolder<Integer> {

        TextView tv_week;
        TextView tv_date;

        public PregnancyHolder(ViewGroup parent, int res) {
            super(parent, res);
            tv_week = $(R.id.tv_week);
            tv_date = $(R.id.tv_date);


        }

        @Override
        public void setData(Integer data) {
            if (babyStatus == 1) {
                tv_week.setText("孕" + data + "周");
                if (data.equals(babyStage)) {
                    tv_date.setText("距离预产期还有" + remainingDays + "天");
                } else {
                    tv_date.setVisibility(View.GONE);
                }

            } else {
                tv_week.setText(data + "天");
                tv_date.setVisibility(View.GONE);
            }
        }
    }
}



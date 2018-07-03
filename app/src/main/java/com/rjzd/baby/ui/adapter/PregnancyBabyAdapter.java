package com.rjzd.baby.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rjzd.baby.R;
import com.rjzd.baby.ui.adapter.recycleadapter.BaseViewHolder;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;
import com.zd.baby.api.model.PregnancyBabyChange;

/**
 * Created by Administrator on 2018/6/7.
 */
/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: 怀孕中宝宝变化适配器
 */
public class PregnancyBabyAdapter extends XMBaseAdapter<PregnancyBabyChange> {

    public PregnancyBabyAdapter(Context context) {
        super(context);
    }


    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PregnancyBabyHolder(parent, R.layout.item_recycler_baby);
    }

    private class PregnancyBabyHolder extends BaseViewHolder<PregnancyBabyChange> {

private  TextView tv_day;
private  TextView date;
private  TextView tv_changes_desc;

        public PregnancyBabyHolder(ViewGroup parent, int res) {
            super(parent, res);
            tv_day=$(R.id.tv_day);
            date=$(R.id.date);
            tv_changes_desc=$(R.id.tv_changes_desc);

        }

        @Override
        public void setData(PregnancyBabyChange data) {
           tv_day.setText(data.getPregnancyDay());
           date.setText(data.getDate());
            tv_changes_desc.setText(data.getChangesDesc());
        }
    }
}



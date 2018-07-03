package com.rjzd.baby.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.rjzd.baby.R;
import com.rjzd.baby.ui.adapter.recycleadapter.BaseViewHolder;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;

/**
 * create time: 2018/6/11  14:13
 * create author: Hition
 * descriptions: 孕中周数适配器
 */

public class PregnancyWeeksAdapter extends XMBaseAdapter<String>{

    private String selection = "全";

    public PregnancyWeeksAdapter(Context context, String[] objects) {
        super(context, objects);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PregnancyWeeks(parent, R.layout.item_recycler_pregnancy_weeks);
    }

    private class PregnancyWeeks extends BaseViewHolder<String>{

        private TextView mWeek;

        PregnancyWeeks(ViewGroup parent, int res) {
            super(parent, res);
            mWeek = $(R.id.tv_week);
        }

        @Override
        public void setData(String data) {
            mWeek.setText(data);
            mWeek.setTag(data);

            if (selection.equals(data)) {
                mWeek.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                mWeek.setBackgroundResource(R.drawable.shape_circle);
            } else {
                mWeek.setTextColor(ContextCompat.getColor(mContext,R.color.cl_2a7f8e));
                mWeek.setBackgroundResource(R.drawable.shape_circle_pregnancy_week);
            }

            mWeek.setOnClickListener((View v) -> {
                String tag = (String) mWeek.getTag();
                if(!selection.equals(tag)){
                    selection = tag;
                }
                notifyDataSetChanged();

                if(null != mItemClickListener){
                    mItemClickListener.onItemClick(PregnancyWeeksAdapter.this.getPosition(data));
                }
            });
        }
    }
}

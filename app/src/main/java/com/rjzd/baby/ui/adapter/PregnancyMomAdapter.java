package com.rjzd.baby.ui.adapter;/**
 * Created by Administrator on 2018/6/20.
 */

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rjzd.baby.R;
import com.rjzd.baby.ui.adapter.recycleadapter.BaseViewHolder;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;
import com.zd.baby.api.model.PregnancyMomChange;

/**
 * create time: 2018/6/20  15:15
 * create author: Administrator
 * descriptions: PregnancyMomAdapter
 */

public class PregnancyMomAdapter extends XMBaseAdapter<PregnancyMomChange> {

    public PregnancyMomAdapter(Context context) {
        super(context);
    }


    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PregnancyMomHolder(parent, R.layout.item_recycler_mom);
    }

    private class PregnancyMomHolder extends BaseViewHolder<PregnancyMomChange> {

        private TextView tv_title;
        private TextView tv_changes_desc;

        public PregnancyMomHolder(ViewGroup parent, int res) {
            super(parent, res);
            tv_title = $(R.id.tv_title);
            tv_changes_desc = $(R.id.tv_changes_desc);

        }

        @Override
        public void setData(PregnancyMomChange data) {
            tv_title.setText(data.getChangesTitle());
            tv_changes_desc.setText(data.getChangesDesc());
        }
    }
}
package com.rjzd.baby.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import com.rjzd.baby.R;
import com.rjzd.baby.ui.adapter.recycleadapter.BaseViewHolder;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;
import java.util.List;

/**
 * create time: 2018/6/12  11:00
 * create author: Hition
 * descriptions: 通用只有一个text的数据适配器
 */

public class CommonTextAdapter extends XMBaseAdapter<String> {

    public CommonTextAdapter(Context context, List<String> objects) {
        super(context, objects);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonTextHolder(parent, R.layout.item_recycler_string_text);
    }

    private class CommonTextHolder extends BaseViewHolder<String>{

        private TextView mText;

        public CommonTextHolder(ViewGroup parent, int res) {
            super(parent, res);
            mText = $(R.id.tv_txt);
        }

        @Override
        public void setData(String data) {
            mText.setText(data);
        }
    }
}

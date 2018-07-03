package com.rjzd.baby.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import com.rjzd.baby.R;
import com.rjzd.baby.entity.Province;
import com.rjzd.baby.ui.adapter.recycleadapter.BaseViewHolder;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;
import java.util.List;

/**
 * create time: 2018/6/12  9:41
 * create author: Hition
 * descriptions: 省份选择数据适配器
 */

public class ProvinceAdapter extends XMBaseAdapter<Province> {

    public ProvinceAdapter(Context context, List<Province> objects) {
        super(context, objects);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProvinceHolder(parent, R.layout.item_recycler_string_text);
    }

    private class ProvinceHolder extends BaseViewHolder<Province>{

        private TextView mText;

        public ProvinceHolder(ViewGroup parent, int res) {
            super(parent, res);
            mText = $(R.id.tv_txt);
        }

        @Override
        public void setData(Province data) {
            mText.setText(data.getName());
        }
    }
}

package com.rjzd.baby.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rjzd.baby.R;
import com.rjzd.baby.entity.Tools;
import com.rjzd.baby.ui.adapter.recycleadapter.BaseViewHolder;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;

/**
 * Created by Administrator on 2018/5/31.
 */
/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: 工具适配器
 */
public class ToolsAdapter extends XMBaseAdapter<Tools> {

    public ToolsAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ToolsHolder(parent, R.layout.item_recycler_tools);
    }

    private class ToolsHolder extends BaseViewHolder<Tools>{
ImageView iv_img;
TextView tv_text;


        public ToolsHolder(ViewGroup parent, int res) {
            super(parent, res);
            iv_img=$(R.id.iv_img);
            tv_text=$(R.id.tv_text);
        }

        @Override
        public void setData(Tools data) {
            tv_text.setText(data.getName());
            iv_img.setBackground(ContextCompat.getDrawable(mContext,data.getId()));
        }
    }
}


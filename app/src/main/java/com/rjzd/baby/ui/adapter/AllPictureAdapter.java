package com.rjzd.baby.ui.adapter;/**
 * Created by Administrator on 2018/6/13.
 */

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rjzd.baby.R;
import com.rjzd.baby.entity.PictureBean;
import com.rjzd.baby.ui.adapter.recycleadapter.BaseViewHolder;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;
import com.rjzd.baby.ui.tools.imgloader.ImageLoader;

/**
 * create time: 2018/6/13  17:59
 * create author: Administrator
 * descriptions: 所有图片适配器
 */

public class AllPictureAdapter extends XMBaseAdapter<PictureBean> {

    public AllPictureAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new AllPictureHolder(parent, R.layout.item_recycler_all_picture);
    }

    private class AllPictureHolder extends BaseViewHolder<PictureBean>{


ImageView iv_picture;
        public AllPictureHolder(ViewGroup parent, int res) {
            super(parent, res);
            iv_picture=$(R.id.iv_picture);
        }

        @Override
        public void setData(PictureBean data) {
            ImageLoader.loadTransformImage(mContext, data.getPath(), iv_picture);
        }
    }
}


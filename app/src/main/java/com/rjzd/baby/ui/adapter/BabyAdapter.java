package com.rjzd.baby.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rjzd.baby.R;
import com.rjzd.baby.ui.adapter.recycleadapter.BaseViewHolder;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;
import com.rjzd.baby.ui.tools.imgloader.ImageLoader;
import com.zd.baby.api.model.BabyInfo;

/**
 * Created by Administrator on 2018/6/1.
 */
/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: 宝宝图片适配器
 */
public class BabyAdapter extends XMBaseAdapter<BabyInfo> {

    public BabyAdapter(Context context) {
        super(context);
    }




    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BabyHolder(parent, R.layout.item_recycler_image);
    }

    private class BabyHolder extends BaseViewHolder<BabyInfo>{


        private ImageView image;

        public BabyHolder(ViewGroup parent, int res) {
            super(parent, res);
            image = $(R.id.image);


        }

        @Override
        public void setData(BabyInfo data) {
           ImageLoader.load(mContext,data.getBabyThumb(),image);
           // image.setImageResource(data);
        }
    }
}


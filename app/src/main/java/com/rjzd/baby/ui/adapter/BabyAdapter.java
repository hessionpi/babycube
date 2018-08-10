package com.rjzd.baby.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.rjzd.baby.R;
import com.rjzd.baby.ui.adapter.recycleadapter.BaseViewHolder;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;
import com.rjzd.baby.ui.tools.imgloader.ImageLoader;
import com.zd.baby.api.model.BabyInfo;

/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: 首页侧滑宝宝列表数据适配器
 */
public class BabyAdapter extends XMBaseAdapter<BabyInfo> {

    public BabyAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BabyHolder(parent, R.layout.item_nav_left_baby_manage);
    }

    private class BabyHolder extends BaseViewHolder<BabyInfo>{

        private ImageView mBabyPhoto;
        private TextView mBabyName;
        private TextView mBabyAge;

        BabyHolder(ViewGroup parent, int res) {
            super(parent, res);
            mBabyPhoto = $(R.id.iv_baby_photo);
            mBabyName = $(R.id.tv_baby_name);
            mBabyAge = $(R.id.tv_baby_age);
        }

        @Override
        public void setData(BabyInfo data) {
            int errorRes = R.drawable.ic_baby_default_pregnancy;
            if(data.getBabyStatus()==1){
                errorRes = R.drawable.ic_baby_default_pregnancy;
            }else if(data.getBabyStatus()==2){
                errorRes = R.drawable.ic_baby_default_born;
            }
            ImageLoader.loadTransformImage(mContext,data.getBabyThumb(),errorRes,errorRes,mBabyPhoto,0);
            mBabyName.setText(data.getBabyName());
            mBabyAge.setText(data.getBabyAge());
        }
    }
}


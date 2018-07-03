package com.rjzd.baby.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.rjzd.baby.R;
import com.rjzd.baby.ui.adapter.recycleadapter.BaseViewHolder;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;
import com.rjzd.baby.ui.tools.imgloader.ImageLoader;
import com.zd.baby.api.model.BabyInfo;

/**
 * create time: 2018/6/1  14:43
 * create author: Hition
 * descriptions: 宝宝管理适配器
 */

public class BabyManageAdapter extends XMBaseAdapter<BabyInfo>{

    private OnBabyManageListener babyManageListener;

    public void setBabyManageListener(OnBabyManageListener babyManageListener) {
        this.babyManageListener = babyManageListener;
    }

    public BabyManageAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BabyManageHolder(parent, R.layout.recycle_item_mybaby);
    }

    private class BabyManageHolder extends BaseViewHolder<BabyInfo>{

        private ImageView mBabyPhoto;
        private TextView mBabyName;
        private TextView mBabyBirthDay;
        private TextView mUpdate;
        private TextView mDelete;

        public BabyManageHolder(ViewGroup parent, int res) {
            super(parent, res);
            mBabyPhoto = $(R.id.iv_baby_avatar);
            mBabyName = $(R.id.tv_baby_name);
            mBabyBirthDay = $(R.id.tv_baby_birthday);
            mUpdate = $(R.id.tv_update_baby);
            mDelete = $(R.id.tv_update_delete);
        }

        @Override
        public void setData(BabyInfo data) {
            int defaultEmptyDrawable = 0;
            String title = null;
            String date = null;
            switch (data.getBabyStatus()){
                case 0:
                    defaultEmptyDrawable = R.drawable.ic_default_prepar;
                    title = "备孕中";
                    date = "末次月经:"+data.getLastMenstruation();
                    break;

                case 1:
                    defaultEmptyDrawable = R.drawable.ic_default_pregnancy;
                    title = "怀孕中";
                    date = "预产期:"+data.getDueDate();
                    break;

                case 2:
                    if(data.getBabySex()==1){
                        // 男宝宝
                        defaultEmptyDrawable = R.drawable.ic_default_baby_boy;
                    }else if(data.getBabySex()==2){
                        // 女宝宝
                        defaultEmptyDrawable = R.drawable.ic_default_baby_girl;
                    }
                    title = data.getBabyName();
                    date = data.getBabyBirthday();
                    break;
            }
            ImageLoader.load(mContext,data.getBabyThumb(),defaultEmptyDrawable,defaultEmptyDrawable,mBabyPhoto);
            mBabyName.setText(title);
            mBabyBirthDay.setText(date);
            mUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    babyManageListener.onBabyUpdate(data);
                }
            });
            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    babyManageListener.onBabyDelete(data.getBabyId(),BabyManageAdapter.this.getPosition(data));
                }
            });
        }
    }

    public interface OnBabyManageListener {
        void onBabyUpdate(BabyInfo baby);

        void onBabyDelete(int babyId,int position);
    }


}

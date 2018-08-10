package com.rjzd.baby.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
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

        ImageView ivBabyStatus;
        ImageView ivBabyDelete;
        ImageView ivBabyPhoto;
        TextView tvBabyBirthday;
        TextView tvBabySex;
        TextView tvBabyName;

        BabyManageHolder(ViewGroup parent, int res) {
            super(parent, res);
            ivBabyStatus = $(R.id.iv_baby_status);
            ivBabyDelete = $(R.id.iv_baby_delete);
            ivBabyPhoto = $(R.id.iv_baby_photo);
            tvBabyBirthday = $(R.id.tv_birthday);
            tvBabySex = $(R.id.tv_sex);
            tvBabyName = $(R.id.tv_baby_name);
        }

        @Override
        public void setData(BabyInfo data) {
            int position = BabyManageAdapter.this.getPosition(data);
            if(1 == data.getBabyStatus()){
                ivBabyStatus.setBackgroundResource(R.drawable.ic_mybaby_pregancy);
            }else if(2 == data.getBabyStatus()){
                ivBabyStatus.setBackgroundResource(R.drawable.ic_mybaby_birth);
            }
            if(TextUtils.isEmpty(data.getBabyThumb())){
                ivBabyPhoto.setBackgroundResource(R.drawable.ic_default_camera);
            }else{
                ImageLoader.loadTransformImage(mContext,data.getBabyThumb(),ivBabyPhoto,0);
            }
            tvBabyBirthday.setText(data.getBabyBirthday());
            int sex = data.getBabySex();
            String sexStr = "未知";
            switch (sex){
                case 0:
                    sexStr = "未知";
                    break;

                case 1:
                    sexStr = "男";
                    break;

                case 2:
                    sexStr = "女";
                    break;
            }
            tvBabySex.setText(sexStr);
            tvBabyName.setText(data.getBabyName());

            ivBabyDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    babyManageListener.onBabyDelete(data.getBabyId(),data.getBabyStatus(),position);
                }
            });
        }
    }

    public interface OnBabyManageListener {
        void onBabyDelete(int babyId,int babyStatus,int position);
    }


}

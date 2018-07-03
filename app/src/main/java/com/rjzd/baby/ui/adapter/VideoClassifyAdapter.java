package com.rjzd.baby.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.rjzd.baby.R;
import com.rjzd.baby.ui.adapter.recycleadapter.BaseViewHolder;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;
import com.zd.baby.api.model.VideoClassify;


/**
 * create time: 2018/6/12  14:58
 * create author: Hition
 * descriptions: 视频分类数据适配器
 */

public class VideoClassifyAdapter extends XMBaseAdapter<VideoClassify> {
    private String select = "全部";

    public VideoClassifyAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoClassifyHolder(parent, R.layout.item_recycler_video_classify);
    }

    private class VideoClassifyHolder extends BaseViewHolder<VideoClassify>{

        private TextView mClassify;

        VideoClassifyHolder(ViewGroup parent, int res) {
            super(parent, res);
            mClassify = $(R.id.tv_classify);
        }

        @Override
        public void setData(VideoClassify data) {
            mClassify.setText(data.getClassifyName());
            String currentTag = data.getClassifyName();
            mClassify.setTag(currentTag);
            if (select.equals(currentTag)) {
                mClassify.setTextColor(ContextCompat.getColor(mContext,R.color.cl_primary));
            } else {
                mClassify.setTextColor(ContextCompat.getColor(mContext,R.color.black));
            }

            mClassify.setOnClickListener((View v)->{
                String tag = (String) mClassify.getTag();
                if(!select.equals(tag)){
                    select = tag;
                }
                notifyDataSetChanged();

                if(null != mItemClickListener){
                    mItemClickListener.onItemClick(VideoClassifyAdapter.this.getPosition(data));
                }


            });


        }
    }

}

package com.rjzd.baby.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.rjzd.baby.R;
import com.rjzd.baby.ui.adapter.recycleadapter.BaseViewHolder;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;
import com.rjzd.baby.ui.tools.imgloader.ImageLoader;
import com.zd.baby.api.model.Discuss;

/**
 * create time: 2018/6/14  17:12
 * create author: Hition
 * descriptions: 视频评论 数据适配器
 */

public class DiscussAdapter extends XMBaseAdapter<Discuss> {

    public DiscussAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiscussViewHolder(parent, R.layout.item_discuss);
    }

    private class DiscussViewHolder extends BaseViewHolder<Discuss>{

        ImageView mAvatarThumb;
        TextView mPublisher;
        TextView mPublishTime;
        TextView mPublishContent;

        DiscussViewHolder(ViewGroup parent, int res) {
            super(parent, res);
            mAvatarThumb = $(R.id.iv_avatar_thumb);
            mPublisher = $(R.id.tv_publisher);
            mPublishTime = $(R.id.tv_publish_time);
            mPublishContent = $(R.id.tv_publish_comments);
        }

        @Override
        public void setData(Discuss data) {
            ImageLoader.loadTransformImage(mContext,data.getPublisherPhoto(),R.drawable.ic_user_head_pic_default,R.drawable.ic_user_head_pic_default,mAvatarThumb,0);
            mPublisher.setText(data.getPublisherName());
            mPublishTime.setText(data.getPublishTime());
            mPublishContent.setText(data.getPublishContent());
        }
    }




}

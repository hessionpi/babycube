package com.rjzd.baby.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.rjzd.baby.R;
import com.rjzd.baby.tools.ZDUtils;
import com.rjzd.baby.ui.adapter.recycleadapter.BaseViewHolder;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;
import com.rjzd.baby.ui.tools.imgloader.ImageLoader;
import com.zd.baby.api.model.SimilarVideo;

/**
 * create time: 2018/5/29  15:13
 * create author: Hition
 * descriptions: 视频列表适配器
 */

public class VideoListAdapter  extends XMBaseAdapter<SimilarVideo>{

    public VideoListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoListHolder(parent, R.layout.item_new_vod);
    }

    private class VideoListHolder extends BaseViewHolder<SimilarVideo>{

        private TextView mDuration;
        private TextView mTitle;
        private ImageView mThumb;
        private TextView mVideoDesc;

        VideoListHolder(ViewGroup parent, int res) {
            super(parent, res);
            mDuration = $(R.id.tv_video_duration);
            mTitle = $(R.id.tv_video_title);
            mThumb = $(R.id.iv_cover_thumb);
            mVideoDesc = $(R.id.tv_video_description);
        }

        @Override
        public void setData(SimilarVideo data) {
            ImageLoader.load(mContext,data.getVideoCover(),mThumb);
            mTitle.setText(data.getVideoTitle());
            mDuration.setText(ZDUtils.formattedTime(data.getVideoDuration()));
            mVideoDesc.setText(data.getVideoDescription());
        }
    }
}

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
import com.zd.baby.api.model.SimpleVideo;

/**
 * Created by Administrator on 2018/6/1.
 */
/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: 推荐视频适配器
 */
public class RecommendVideoAdapter extends XMBaseAdapter<SimpleVideo> {

    public RecommendVideoAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecommendVideoHolder(parent, R.layout.item_recycler_recommend_video);
    }

    private class RecommendVideoHolder extends BaseViewHolder<SimpleVideo>{

        private TextView mDuration;
        private TextView mTitle;
        private TextView tv_video_category;
        private ImageView iv_video_img;
        private TextView tv_video_explain;

        RecommendVideoHolder(ViewGroup parent, int res) {
            super(parent, res);
            mDuration = $(R.id.tv_video_duration);
            mTitle = $(R.id.tv_video_title);
            tv_video_category = $(R.id.tv_video_category);
            iv_video_img = $(R.id.iv_video_img);
            tv_video_explain = $(R.id.tv_video_explain);
        }

        @Override
        public void setData(SimpleVideo data) {
            ImageLoader.load(mContext,data.getVideoCover(),R.drawable.ic_cover_default,R.drawable.ic_cover_default,iv_video_img);
            mTitle.setText(data.getVideoTitle());
            tv_video_category.setText(data.getVideoClassify());
            mDuration.setText(ZDUtils.formattedTime(data.getVideoDuration()));
            tv_video_explain.setText(data.getVideoDescription());
        }
    }
}


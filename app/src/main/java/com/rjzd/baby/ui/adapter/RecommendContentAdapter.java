package com.rjzd.baby.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rjzd.baby.R;
import com.rjzd.baby.tools.DensityUtil;
import com.rjzd.baby.tools.ZDUtils;
import com.rjzd.baby.ui.activity.VideoPlayActivity;
import com.rjzd.baby.ui.activity.WebActivity;
import com.rjzd.baby.ui.adapter.recycleadapter.BaseViewHolder;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;
import com.rjzd.baby.ui.tools.imgloader.ImageLoader;
import com.zd.baby.api.model.Recommend;
import com.zd.baby.api.model.RecommendType;


/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: 推荐视频适配器
 */
public class RecommendContentAdapter extends XMBaseAdapter<Recommend> {

    public RecommendContentAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecommendVideoHolder(parent, R.layout.item_recycler_recommend_content);
    }

    @Override
    public int getViewType(int position) {
        return super.getViewType(position);
    }


    private class RecommendVideoHolder extends BaseViewHolder<Recommend> {

        LinearLayout ll_content;

        RecommendVideoHolder(ViewGroup parent, int res) {
            super(parent, res);
            ll_content = $(R.id.ll_content);
        }

        @Override
        public void setData(Recommend data) {
            if (data.getType() == RecommendType.TEXT) {
                TextView textTitleView = new TextView(mContext);
                LinearLayout.LayoutParams textTitleLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                textTitleLP.setMargins(DensityUtil.dp2px(15), DensityUtil.dp2px(17), DensityUtil.dp2px(15), 0);
                TextPaint tp = textTitleView.getPaint();
                tp.setFakeBoldText(true);
                textTitleView.setLayoutParams(textTitleLP);
                textTitleView.setTextColor(ContextCompat.getColor(mContext,R.color.cl_333333));

                textTitleView.setTextSize(16);
                textTitleView.setText(data.getTitle());

                View view = new View(mContext);
                LinearLayout.LayoutParams viewLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,DensityUtil.dp2px(1));
                viewLP.setMargins(DensityUtil.dp2px(15),  DensityUtil.dp2px(17), DensityUtil.dp2px(15), 0);
                view.setLayoutParams(viewLP);
                view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.cl_line));

                TextView textSubTitleView = new TextView(mContext);
                LinearLayout.LayoutParams textSubTitleP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                textSubTitleP.setMargins(DensityUtil.dp2px(15),  DensityUtil.dp2px(17), DensityUtil.dp2px(15), DensityUtil.dp2px(17));
                textSubTitleView.setLayoutParams(textSubTitleP);
                textSubTitleView.setTextColor(ContextCompat.getColor(mContext,R.color.cl_666666));
                textSubTitleView.setTextSize(14);
                textSubTitleView.setMaxLines(2);
                textSubTitleView.setEllipsize(TextUtils.TruncateAt.END);
                textSubTitleView.setText(data.getSubTitle());
                ll_content.addView(textTitleView);
                ll_content.addView(view);
                ll_content.addView(textSubTitleView);


            } else if (data.getType() == RecommendType.PHOTO_TEXT) {
                TextView phototextTitleView = new TextView(mContext);
                LinearLayout.LayoutParams phototextTitleLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                phototextTitleLP.setMargins(DensityUtil.dp2px(15), DensityUtil.dp2px(17), DensityUtil.dp2px(15), 0);
                phototextTitleView.setLayoutParams(phototextTitleLP);
                TextPaint tp = phototextTitleView.getPaint();
                tp.setFakeBoldText(true);
                phototextTitleView.setTextColor(ContextCompat.getColor(mContext,R.color.cl_333333));
                phototextTitleView.setTextSize(16);
                phototextTitleView.setText(data.getTitle());

                ImageView phototextImageView = new ImageView(mContext);
                phototextImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                LinearLayout.LayoutParams phototextImgLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(192));
                phototextImgLP.setMargins(2, DensityUtil.dp2px(17), 2,0);
                phototextImageView.setLayoutParams(phototextImgLP);
                ImageLoader.load(mContext,data.getCover(),R.drawable.ic_cover_default,R.drawable.ic_cover_default,phototextImageView);

                TextView phototextSubTitleView = new TextView(mContext);
                LinearLayout.LayoutParams phototextSubTitleP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                phototextSubTitleP.setMargins(DensityUtil.dp2px(15), DensityUtil.dp2px(17), DensityUtil.dp2px(15), DensityUtil.dp2px(17));
                phototextSubTitleView.setLayoutParams(phototextSubTitleP);
                phototextSubTitleView.setTextColor(ContextCompat.getColor(mContext,R.color.cl_666666));
                phototextSubTitleView.setTextSize(14);
                phototextSubTitleView.setMaxLines(2);
                phototextSubTitleView.setEllipsize(TextUtils.TruncateAt.END);
                phototextSubTitleView.setText(data.getSubTitle());
                ll_content.addView(phototextTitleView);
                ll_content.addView(phototextImageView);
                ll_content.addView(phototextSubTitleView);
            } else if (data.getType() == RecommendType.AUDIO) {


            } else if (data.getType() == RecommendType.VIDEO){
                TextView textTitleView = new TextView(mContext);
                LinearLayout.LayoutParams textTitleLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                textTitleLP.setMargins(DensityUtil.dp2px(15), DensityUtil.dp2px(17), DensityUtil.dp2px(15), 0);
                textTitleView.setLayoutParams(textTitleLP);
                TextPaint tp = textTitleView.getPaint();
                tp.setFakeBoldText(true);
                textTitleView.setTextColor(ContextCompat.getColor(mContext,R.color.cl_333333));
                textTitleView.setTextSize(16);
                textTitleView.setText(data.getTitle());

                FrameLayout flayout = new FrameLayout(mContext);
                FrameLayout.LayoutParams flayoutLP = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(192));
                flayoutLP.setMargins(2, DensityUtil.dp2px(17), 2, 0);
                flayout.setLayoutParams(flayoutLP);

                ImageView phototextImageView = new ImageView(mContext);
                phototextImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                LinearLayout.LayoutParams phototextImgLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                phototextImgLP.setMargins(0, 0, 0, DensityUtil.dp2px(5));
                phototextImageView.setLayoutParams(phototextImgLP);
                ImageLoader.load(mContext,data.getCover(),R.drawable.ic_cover_default,R.drawable.ic_cover_default,phototextImageView);

                RelativeLayout hlayout = new RelativeLayout(mContext);
                RelativeLayout.LayoutParams hlayoutLP = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                hlayout.setLayoutParams(hlayoutLP);


                ImageView playImageView = new ImageView(mContext);
                RelativeLayout.LayoutParams playImageLP = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                playImageLP.setMargins(0, 0, 0, DensityUtil.dp2px(5));
                playImageLP.addRule(RelativeLayout.CENTER_IN_PARENT);
                playImageView.setLayoutParams(playImageLP);
                playImageView.setImageResource(R.drawable.sy_icon_play);

                TextView dateView = new TextView(mContext);
                RelativeLayout.LayoutParams dateLP = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dateLP.setMargins(0, 0, DensityUtil.dp2px(8), DensityUtil.dp2px(9));
                dateLP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                dateLP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

                dateView.setLayoutParams(dateLP);
                dateView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.shape_video_duration_bg));
                dateView.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                dateView.setTextSize(13);
                dateView.setText(ZDUtils.formattedTime(data.getDuration()));


                TextView textSubTitleView = new TextView(mContext);
                LinearLayout.LayoutParams textSubTitleP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                textSubTitleP.setMargins(DensityUtil.dp2px(15), DensityUtil.dp2px(17), DensityUtil.dp2px(15),  DensityUtil.dp2px(17));
                textSubTitleView.setLayoutParams(textSubTitleP);
                textSubTitleView.setTextColor(ContextCompat.getColor(mContext,R.color.cl_666666));
                textSubTitleView.setTextSize(14);
                textSubTitleView.setMaxLines(2);
                textSubTitleView.setEllipsize(TextUtils.TruncateAt.END);
                textSubTitleView.setText(data.getSubTitle());


                hlayout.addView(playImageView);
                hlayout.addView(dateView);
                flayout.addView(phototextImageView);
                flayout.addView(hlayout);
                ll_content.addView(textTitleView);
                ll_content.addView(flayout);
                ll_content.addView(textSubTitleView);
            }
            ll_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.getType() == RecommendType.VIDEO){
                        VideoPlayActivity.startActivity(mContext,data.getRecommendId());
                    }else{
                        WebActivity.startActivity(mContext,data.getUrl(),true,data.getTitle(),data.getSubTitle(),data.getCover());
                    }
                }
            });
        }
    }
}


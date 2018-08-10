package com.rjzd.baby.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rjzd.baby.R;
import com.rjzd.baby.ui.adapter.recycleadapter.BaseViewHolder;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;
import com.rjzd.baby.ui.widget.ScrollLinearLayoutManager;
import com.zd.baby.api.model.RecommendInfo;

/**
 * Created by Administrator on 2018/6/1.
 */

/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: 推荐适配器
 */
public class RecommendAdapter extends XMBaseAdapter<RecommendInfo> {

    public RecommendAdapter(Context context) {
        super(context);
    }
int currentStatus;
    int progress;
    public  void  setCurrentStatus(int currentStatus,int progress){
        this.currentStatus=currentStatus;
        this.progress=progress;
    }
    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecommendVideoHolder(parent, R.layout.item_recycler_recommend);
    }

    private class RecommendVideoHolder extends BaseViewHolder<RecommendInfo>{

        private LinearLayout ll_pregnancy;
        private TextView tv_pregnancy_title;
        private TextView tv_pregnancy_date;
        private ImageView iv_pregnancy_circle;

        private LinearLayout ll_baby;
        private TextView tv_baby_date;
        private View baby_line;
        private ImageView  iv_baby_circle;
        private RecyclerView rv_recommend_content;


        RecommendVideoHolder(ViewGroup parent, int res) {
            super(parent, res);
            ll_pregnancy = $(R.id.ll_pregnancy);
            tv_pregnancy_title = $(R.id.tv_pregnancy_title);
            tv_pregnancy_date = $(R.id.tv_pregnancy_date);
            iv_pregnancy_circle = $(R.id.iv_pregnancy_circle);

            ll_baby = $(R.id.ll_baby);
            tv_baby_date = $(R.id.tv_baby_date);
            iv_baby_circle = $(R.id.iv_baby_circle);
            baby_line = $(R.id.baby_line);

            rv_recommend_content = $(R.id.rv_recommend_content);
        }

        @Override
        public void setData(RecommendInfo data) {


            if (currentStatus==1){//怀孕中
                ll_pregnancy.setVisibility(View.VISIBLE);
                ll_baby.setVisibility(View.GONE);

                tv_pregnancy_title.setText(data.getTitle());
                tv_pregnancy_date.setText(data.getDate());
                if (data.getIsToday()){

                    iv_pregnancy_circle.setImageResource(R.drawable.shape_today_circle);
                    tv_pregnancy_title.setTextColor(ContextCompat.getColor(mContext,R.color.cl_f3aa15));
                    tv_pregnancy_date.setTextColor(ContextCompat.getColor(mContext,R.color.cl_f3aa15));

                }else{
                    iv_pregnancy_circle.setImageResource(R.drawable.shape_circle);
                    tv_pregnancy_title.setTextColor(ContextCompat.getColor(mContext,R.color.cl_666666));
                    tv_pregnancy_date.setTextColor(ContextCompat.getColor(mContext,R.color.cl_999999));
                }
            }else{//出生
                ll_pregnancy.setVisibility(View.GONE);
                ll_baby.setVisibility(View.VISIBLE);

                if (tv_baby_date == null || "".equals(tv_baby_date)) {
                    tv_baby_date.setVisibility(View.GONE);
                    iv_baby_circle.setVisibility(View.GONE);
                    baby_line.setVisibility(View.GONE);
                } else {
                    tv_baby_date.setVisibility(View.VISIBLE);
                    iv_baby_circle.setVisibility(View.VISIBLE);
                    baby_line.setVisibility(View.VISIBLE);
                    tv_baby_date.setText(data.getDate());
                }
                if (data.getIsToday()){
                    iv_baby_circle.setImageResource(R.drawable.shape_today_circle);
                    tv_baby_date.setTextColor(ContextCompat.getColor(mContext,R.color.cl_f3aa15));
                }else{
                    iv_baby_circle.setImageResource(R.drawable.shape_baby_circle);
                    tv_baby_date.setTextColor(ContextCompat.getColor(mContext,R.color.cl_666666));

                }
                if (progress==10000){
                    ll_pregnancy.setVisibility(View.GONE);
                    ll_baby.setVisibility(View.GONE);
                }
            }


            LinearLayoutManager linearLayoutManager = new ScrollLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            rv_recommend_content.setLayoutManager(linearLayoutManager);
            RecommendContentAdapter   recommendContentAdapter = new RecommendContentAdapter(mContext);
            rv_recommend_content.setNestedScrollingEnabled(false);
            rv_recommend_content.setAdapter(recommendContentAdapter);
            recommendContentAdapter.setData(data.getRecomContents());

        }
    }
}


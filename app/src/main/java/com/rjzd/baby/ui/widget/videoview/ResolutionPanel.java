package com.rjzd.baby.ui.widget.videoview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import com.rjzd.baby.R;
import com.tencent.rtmp.TXBitrateItem;
import java.util.ArrayList;

/**
 * create time: 2018/5/29  10:58
 * create author: Hition
 * descriptions: 视频清晰度设置面板（流畅、高清、超清、原画）
 */
public class ResolutionPanel extends FrameLayout implements View.OnClickListener{

    private ArrayList<Button> mButtons;
    private Button mCurBtn;
    private Button mLastBtn;
    private boolean mChangeResolution = false;
    private int mLastSize = 0;


    public ResolutionPanel(Context context) {
        super(context);
        initView(context);
    }

    public ResolutionPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ResolutionPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.biz_video_media_resolution_panel, this);

        Button fluency = findViewById(R.id.button_r1);
        Button hd = findViewById(R.id.button_r2);
        Button superHD = findViewById(R.id.button_r3);
        Button original = findViewById(R.id.button_r4);
        mButtons = new ArrayList<>();
        mButtons.add(fluency);
        mButtons.add(hd);
        mButtons.add(superHD);
        mButtons.add(original);

        for (int i = 0; i < mButtons.size(); i++) {
            mButtons.get(i).setVisibility(GONE);
            mButtons.get(i).setOnClickListener(this);
        }
        mCurBtn = mButtons.get(0);
        mCurBtn.setTextColor(getResources().getColor(R.color.colorTintRed));
        mCurBtn.setBackground(getResources().getDrawable(R.drawable.ic_resolution_select_bg));
        mLastBtn = mCurBtn;
    }

    private ArrayList<TXBitrateItem> mSource;

    public void setDataSource(ArrayList<TXBitrateItem> source) {
        int size = 0;
        if (source == null || source.size() == 0) {
            size = 0;
        }
        if (mLastSize == 0 || mLastSize != size) {
            for (int i = 0; i < mButtons.size(); i++) {
                mButtons.get(i).setVisibility(GONE);
            }
        }
        mLastSize = size;

        if (source == null || source.size() == 0) {
            mButtons.get(1).setVisibility(View.VISIBLE);
            mCurBtn = mButtons.get(1);
            mCurBtn.setTextColor(getResources().getColor(R.color.colorTintRed));
            mCurBtn.setBackground(getResources().getDrawable(R.drawable.ic_resolution_select_bg));
            mLastBtn = mCurBtn;
            return;
        }
        mSource = new ArrayList<>(source);
        for (int i = 0; i < source.size(); i++) {
            mButtons.get(i).setVisibility(VISIBLE);
        }
        if (!mChangeResolution) {
            if (mLastBtn != null) {
                mLastBtn.setBackground(null);
                mLastBtn.setTextColor(getResources().getColor(R.color.white));
                mCurBtn = mButtons.get(0);
                mCurBtn.setTextColor(getResources().getColor(R.color.colorTintRed));
                mCurBtn.setBackground(getResources().getDrawable(R.drawable.ic_resolution_select_bg));
                mLastBtn = mCurBtn;
            }
            mChangeResolution = false;
        }
    }

    @Override
    public void onClick(View v) {
        if (mCurBtn == v)
            return;

        if (mLastBtn != null) {
            mLastBtn.setBackground(null);
            mLastBtn.setTextColor(getResources().getColor(R.color.white));
        }
        mCurBtn = (Button) v;
        mCurBtn.setTextColor(getResources().getColor(R.color.colorTintRed));
        mCurBtn.setBackground(getResources().getDrawable(R.drawable.ic_resolution_select_bg));

        int index = 0;
        switch (mCurBtn.getId()) {
            case R.id.button_r1:
                index = 0;
                break;
            case R.id.button_r2:
                index = 1;
                break;
            case R.id.button_r3:
                index = 2;
                break;
            case R.id.button_r4:
                index = 3;
                break;
        }

        if (mImp != null && mSource.size() > 0) {
            TXBitrateItem item = mSource.get(index);
            mImp.onResolutionChange(item.index);
            mChangeResolution = true;
        }
        mLastBtn = mCurBtn;
    }

    private ResolutionChangeImpl mImp;

    public void setResolutionChangeListener(ResolutionChangeImpl imp) {
        mImp = imp;
    }

    public interface ResolutionChangeImpl {
        void onResolutionChange(int index);
    }




}

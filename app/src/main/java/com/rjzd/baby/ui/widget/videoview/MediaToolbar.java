package com.rjzd.baby.ui.widget.videoview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.rjzd.baby.R;

/**
 * create time: 2018/5/29  10:34
 * create author: Hition
 * descriptions: 播放器工具条
 */
public class MediaToolbar extends FrameLayout implements View.OnClickListener{

    private ImageView mDanmukuBtn;
    private boolean mDanmukuOn;
    private MediaToolbarImpl mMediaToolbarImpl;
    private TextView mTitle;

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.danmuku) {
            mDanmukuOn = !mDanmukuOn;
            if (mDanmukuOn) {
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_danmuku_on);
                mDanmukuBtn.setImageBitmap(bmp);
            } else {
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_danmuku_off);
                mDanmukuBtn.setImageBitmap(bmp);
            }
            mMediaToolbarImpl.onDanmaku(mDanmukuOn);
        } else if (view.getId() == R.id.menu_more) {
            mMediaToolbarImpl.onMoreSetting();
        } else if (view.getId() == R.id.back_pl) {
            mMediaToolbarImpl.onBack();
        }
    }

    public void setMediaControl(MediaToolbarImpl mediaControl) {
        mMediaToolbarImpl = mediaControl;
    }

    public MediaToolbar(Context context) {
        super(context);
        initView(context);
    }

    public MediaToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public MediaToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.biz_video_media_toolbar, this);

        mDanmukuBtn = findViewById(R.id.danmuku);
        ImageView mMoreBtn = findViewById(R.id.menu_more);
        ImageView mBackBtn = findViewById(R.id.back_pl);
        mTitle = findViewById(R.id.title);

        mDanmukuBtn.setOnClickListener(this);
        mMoreBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
    }

    public void udpateTitle(String title) {
        if (mTitle != null) {
            mTitle.setText(title);
        }
    }

    public interface MediaToolbarImpl {
        void onDanmaku(boolean on);

        void onMoreSetting();

        void onBack();
    }

}

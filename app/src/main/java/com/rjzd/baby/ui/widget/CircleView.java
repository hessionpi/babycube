package com.rjzd.baby.ui.widget;

/**
 * Created by Administrator on 2018/7/12.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.rjzd.baby.R;
import com.rjzd.baby.tools.DensityUtil;

/**
 * CircleSeekBar
 *
 * @author lee
 */

public class CircleView extends View {

    private final boolean DEBUG = true;
    private final String TAG = "CircleSeekBar";

    private Context mContext = null;

    private Drawable mThumbDrawable = null;
    private int mThumbHeight = 0;
    private int mThumbWidth = 0;
    private int[] mThumbNormal = null;
    private int[] mThumbPressed = null;


    private int mProgressMax = 0;

    private Paint backgroundPaint = null;
    private Paint mSeekBarBackgroundPaint = null;
    private Paint ringPaint = null;
    private Paint mSeekbarProgressPaint = null;
    private RectF mArcRectF = null;

  /*  private boolean mIsShowProgressText = false;
    private Paint mProgressTextPaint = null;
    private int mProgressTextSize = 0;*/

    private int mViewHeight = 0;
    private int mViewWidth = 0;
    private int mSeekBarSize = 0;
    private int mSeekBarRadius = 0;
    private int mSeekBarCenterX = 0;
    private int mSeekBarCenterY = 0;
    private float mThumbLeft = 0;
    private float mThumbTop = 0;
    private boolean scroll;
    private int colorProgress;
    private float mSeekBarDegree = 270;
    private int mCurrentProgress = 0;
    private Boolean isFirst = true;
    float progressWidth;
    private OnSeekBarChangeListener mOnSeekBarChangeListener = null;

    public interface OnSeekBarChangeListener {

        void onProgressChanged(int progress);

        void onStartTrackingTouch();

        void onStopTrackingTouch();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initViewAttrs(attrs);
        mArcRectF = new RectF();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViewAttrs(attrs);
        mArcRectF = new RectF();
    }

    public CircleView(Context context) {
        super(context);
        mContext = context;
        initViewDefault();
        mArcRectF = new RectF();
    }

    public int getColorProgress() {
        return colorProgress;
    }

    public void setColorProgress(int colorProgress) {
        this.colorProgress = colorProgress;
    }

    private void initViewAttrs(AttributeSet attrs) {
        if (DEBUG) Log.d(TAG, "initView");
        TypedArray localTypedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CircleView);
        mThumbDrawable = localTypedArray.getDrawable(R.styleable.CircleView_android_thumb);
        mThumbWidth = mThumbDrawable.getIntrinsicWidth();
        mThumbHeight = mThumbDrawable.getIntrinsicHeight();

        mThumbNormal = new int[]{-android.R.attr.state_focused, -android.R.attr.state_pressed,
                -android.R.attr.state_selected, -android.R.attr.state_checked};
        mThumbPressed = new int[]{android.R.attr.state_focused, android.R.attr.state_pressed,
                android.R.attr.state_selected, android.R.attr.state_checked};

        progressWidth = localTypedArray.getDimension(R.styleable.CircleView_progress_width, 5);
        int progressBackgroundColor = localTypedArray.getColor(R.styleable.CircleView_progress_background, Color.GRAY);
        int progressFrontColor = localTypedArray.getColor(R.styleable.CircleView_progress_color, Color.BLUE);
        //   mSeekBarMax = localTypedArray.getInteger(R.styleable.CircleView_seekbar_max, 100);
        mProgressMax = localTypedArray.getInteger(R.styleable.CircleView_progress_max, 100);
        scroll = localTypedArray.getBoolean(R.styleable.CircleView_scroll, false);
        colorProgress = localTypedArray.getInteger(R.styleable.CircleView_progress_num, 0);


        mSeekbarProgressPaint = new Paint();
        mSeekBarBackgroundPaint = new Paint();
        ringPaint = new Paint();
        backgroundPaint = new Paint();


        mSeekbarProgressPaint.setColor(progressFrontColor);
        mSeekBarBackgroundPaint.setColor(progressBackgroundColor);
        ringPaint.setColor(Color.parseColor("#e5e5e5")); //设置圆的颜色
        backgroundPaint.setColor(Color.WHITE); //设置圆的颜色

        mSeekbarProgressPaint.setAntiAlias(true);
        mSeekBarBackgroundPaint.setAntiAlias(true);
        ringPaint.setAntiAlias(true);  //消除锯齿
        backgroundPaint.setAntiAlias(true);  //消除锯齿

        mSeekbarProgressPaint.setStyle(Paint.Style.STROKE);
        mSeekBarBackgroundPaint.setStyle(Paint.Style.STROKE);
        ringPaint.setStyle(Paint.Style.STROKE); //设置空心
        backgroundPaint.setStyle(Paint.Style.FILL); //设置空心

        mSeekbarProgressPaint.setStrokeWidth(progressWidth);
        mSeekBarBackgroundPaint.setStrokeWidth(progressWidth);
        ringPaint.setStrokeWidth(5); //设置圆的宽度
        backgroundPaint.setStrokeWidth(mSeekBarRadius); //设置圆的宽度

        localTypedArray.recycle();
    }

    private void initViewDefault() {
        mThumbDrawable = null;
        mThumbWidth = 0;
        mThumbHeight = 0;

        mThumbNormal = new int[]{-android.R.attr.state_focused, -android.R.attr.state_pressed,
                -android.R.attr.state_selected, -android.R.attr.state_checked};
        mThumbPressed = new int[]{android.R.attr.state_focused, android.R.attr.state_pressed,
                android.R.attr.state_selected, android.R.attr.state_checked};

        float progressWidth = 5;
        int progressBackgroundColor = Color.GRAY;
        int progressFrontColor = Color.BLUE;

        mProgressMax = 10000;
        colorProgress = 0;
        mSeekbarProgressPaint = new Paint();
        mSeekBarBackgroundPaint = new Paint();

        mSeekbarProgressPaint.setColor(progressFrontColor);
        mSeekBarBackgroundPaint.setColor(progressBackgroundColor);

        mSeekbarProgressPaint.setAntiAlias(true);
        mSeekBarBackgroundPaint.setAntiAlias(true);

        mSeekbarProgressPaint.setStyle(Paint.Style.STROKE);
        mSeekBarBackgroundPaint.setStyle(Paint.Style.STROKE);

        mSeekbarProgressPaint.setStrokeWidth(progressWidth);
        mSeekBarBackgroundPaint.setStrokeWidth(progressWidth);


    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (DEBUG) Log.d(TAG, "onMeasure");
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();

        mSeekBarSize = mViewWidth > mViewHeight ? mViewHeight : mViewWidth;

        mSeekBarCenterX = mViewWidth / 2;
        mSeekBarCenterY = mViewHeight / 2;

        mSeekBarRadius = mSeekBarSize / 2 - mThumbWidth / 2;
        mSeekBarRadius = mSeekBarRadius - mThumbWidth / 2;
        int left = mSeekBarCenterX - mSeekBarRadius;
        int right = mSeekBarCenterX + mSeekBarRadius;
        int top = mSeekBarCenterY - mSeekBarRadius;
        int bottom = mSeekBarCenterY + mSeekBarRadius;
        mArcRectF.set(left, top, right, bottom);
        isFirst = true;
        setThumbPosition(Math.toRadians(mSeekBarDegree));
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.drawCircle(mSeekBarCenterX, mSeekBarCenterY, mSeekBarRadius + DensityUtil.dp2px(12),
                backgroundPaint); //画出圆
        canvas.drawCircle(mSeekBarCenterX, mSeekBarCenterY, mSeekBarRadius + DensityUtil.dp2px(12),
                ringPaint); //画出圆环
        canvas.drawCircle(mSeekBarCenterX, mSeekBarCenterY, mSeekBarRadius,
                mSeekBarBackgroundPaint);
        float fProgress = colorProgress;

        canvas.drawArc(this.mArcRectF, 270, (int) (fProgress / mProgressMax * 360.0), false, mSeekbarProgressPaint);
        drawThumbBitmap(canvas);

        super.onDraw(canvas);
    }

    private void drawThumbBitmap(Canvas canvas) {
        BitmapDrawable bd = (BitmapDrawable) mThumbDrawable;
        Bitmap bm = bd.getBitmap();
        Matrix matrix = new Matrix();
        float rotate = (float) (mCurrentProgress / 10000.0 * 360);
        matrix.setRotate(rotate);//旋转
        Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                bm.getHeight(), matrix, true);
        canvas.drawBitmap(bm1, (int) mThumbLeft, (int) mThumbTop, null);
        matrix.reset();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (null != mOnSeekBarChangeListener) {
                    mOnSeekBarChangeListener.onStartTrackingTouch();
                }
                seekTo(eventX, eventY, false);
                break;

            case MotionEvent.ACTION_MOVE:
                seekTo(eventX, eventY, false);
                break;

            case MotionEvent.ACTION_UP:
                if (null != mOnSeekBarChangeListener) {
                    mOnSeekBarChangeListener.onStopTrackingTouch();
                }
                seekTo(eventX, eventY, true);
                break;
        }
        return true;
    }

    private void seekTo(float eventX, float eventY, boolean isUp) {
        if (true == isPointOnThumb(eventX, eventY) && false == isUp) {
            if (null != mThumbDrawable) {
                mThumbDrawable.setState(mThumbPressed);
            }
            double radian = Math.atan2(eventY - mSeekBarCenterY, eventX - mSeekBarCenterX);


            if (radian < 0) {
                radian = radian + 2 * Math.PI;
            }
            //  if (DEBUG) Log.e(TAG, "seekTo radian = " + radian);
            setThumbPosition(radian);

            mSeekBarDegree = (float) Math.round(Math.toDegrees(radian));
            if (mSeekBarDegree >= 270) {
                mCurrentProgress = (int) (mProgressMax * (mSeekBarDegree - 270) / 360);
            } else {
                mCurrentProgress = (int) (mProgressMax * (mSeekBarDegree + 90) / 360);
            }
            //    Log.e(TAG, "seekTo mCurrentProgress = " + mCurrentProgress);
            if (null != mOnSeekBarChangeListener) {
                mOnSeekBarChangeListener.onProgressChanged(mCurrentProgress);
            }
            invalidate();
        } else {
            if (null != mThumbDrawable) {
                mThumbDrawable.setState(mThumbNormal);
            }
            invalidate();
        }
    }

    private boolean isPointOnThumb(float eventX, float eventY) {
        boolean result = false;
        if (scroll) {
            double distance = Math.sqrt(Math.pow(eventX - mSeekBarCenterX, 2)
                    + Math.pow(eventY - mSeekBarCenterY, 2));
            if (distance < mSeekBarSize && distance > (mSeekBarSize / 2 - mThumbWidth)) {
                result = true;
            }
        }
        return result;
    }

    private void setThumbPosition(double radian) {
        //   if (DEBUG) Log.v(TAG, "setThumbPosition radian = " + radian);
        double x = mSeekBarCenterX + mSeekBarRadius * Math.cos(radian);
        double y = mSeekBarCenterY + mSeekBarRadius * Math.sin(radian);

        mThumbLeft = (float) (x - mThumbWidth / 2)-DensityUtil.dp2px(2);
        mThumbTop = (float) (y - mThumbHeight / 2)-DensityUtil.dp2px(1);
    }


    public synchronized void setProgress(int progress) {
        //  if (DEBUG) Log.v(TAG, "setProgress progress = " + progress);
        if (progress > mProgressMax) {
            progress = mProgressMax;
        }
        if (progress < 0) {
            progress = 0;
        }
        int seekBarDegree = (progress * 360 / mProgressMax);
        mSeekBarDegree = seekBarDegree + 270;
        mCurrentProgress = (mProgressMax * seekBarDegree / 360);
        setThumbPosition(Math.toRadians(mSeekBarDegree));

        invalidate();
    }

    public synchronized int getProgress() {
        return mCurrentProgress;
    }



    public void setProgressThumb(int thumbId) {
        mThumbDrawable = mContext.getResources().getDrawable(thumbId);
        mThumbWidth = mThumbDrawable.getIntrinsicWidth();
        mThumbHeight = mThumbDrawable.getIntrinsicHeight();
    }

    public void setProgressWidth(int width) {
        if (DEBUG) Log.v(TAG, "setProgressWidth width = " + width);
        mSeekbarProgressPaint.setStrokeWidth(width);
        mSeekBarBackgroundPaint.setStrokeWidth(width);
    }

    public void setProgressBackgroundColor(int color) {
        mSeekBarBackgroundPaint.setColor(color);
    }

    public void setProgressFrontColor(int color) {
        mSeekbarProgressPaint.setColor(color);
    }

    public int getProgressMax() {
        return mProgressMax;
    }

    public void setProgressMax(int mProgressMax) {
        this.mProgressMax = mProgressMax;
    }


    public void setOnSeekBarChangeListener(OnSeekBarChangeListener onSeekBarChangeListener) {
        mOnSeekBarChangeListener = onSeekBarChangeListener;
    }
}



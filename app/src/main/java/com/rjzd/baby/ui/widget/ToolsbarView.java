package com.rjzd.baby.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.rjzd.baby.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create time: 2018/5/29  13:58
 * create author: Hition
 * descriptions: 顶部标题栏
 */

public class ToolsbarView extends FrameLayout {

    @BindView(R.id.iv_left)
    ImageView leftImgAction;
    @BindView(R.id.action_left)
    TextView leftTextAction;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button rightBtnAction;
    @BindView(R.id.iv_right)
    ImageView rightImgAction;

    //  背景
    private int background;

    //  标题栏文字内容
    private String titleText;
    private int titleTextColor;

    // 标题栏左侧文字
    private String actionLeftText;
    private int actionLeftBackground;
    // 标题栏右侧文字
    private String actionRightText;
    private int actionRightColor;
    private int actionRightBackground;

    private OnLeftClickListener leftClickListener;
    private OnRightClickListener rightClickListener;

    public ToolsbarView(Context context) {
        this(context, null);
    }

    public ToolsbarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToolsbarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs,defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.Toolsbar, 0, 0);
        initConfig(typedArray);
        typedArray.recycle();

        View toolbarView = LayoutInflater.from(context).inflate(R.layout.layout_toolbar_view, this);
        ButterKnife.bind(toolbarView);
        initViews();
    }

    /**
     * 读取arrays文件中配置参数
     *
     * @param typedArray
     */
    private void initConfig(TypedArray typedArray) {
        background = typedArray.getResourceId(R.styleable.Toolsbar_background,R.color.cl_primary);
        titleText = typedArray.getString(R.styleable.Toolsbar_titleText);
        titleTextColor = typedArray.getResourceId(R.styleable.Toolsbar_titleColor,R.color.white);

        actionLeftText = typedArray.getString(R.styleable.Toolsbar_actionLeftText);
        actionLeftBackground = typedArray.getResourceId(R.styleable.Toolsbar_actionLeftBackground,R.drawable.ic_new_vod_back_normal);

        actionRightText = typedArray.getString(R.styleable.Toolsbar_actionRightText);
        actionRightColor = typedArray.getResourceId(R.styleable.Toolsbar_actionRightColor,R.color.white);
        actionRightBackground = typedArray.getResourceId(R.styleable.Toolsbar_actionRightBackground,0);
    }

    private void initViews() {
        setBackgroundResource(background);
        leftImgAction.setImageResource(actionLeftBackground);
        leftTextAction.setText(actionLeftText);

        tvTitle.setText(titleText);
        tvTitle.setTextColor(ContextCompat.getColor(getContext(),titleTextColor));
        rightBtnAction.setText(actionRightText);
        rightBtnAction.setTextColor(ContextCompat.getColor(getContext(),actionRightColor));
        rightImgAction.setImageResource(actionRightBackground);

        leftImgAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null == leftClickListener){
                    ((Activity)getContext()).finish();
                }else{
                    leftClickListener.onLeftImgClick();
                }
            }
        });
        leftTextAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != leftClickListener){
                    leftClickListener.onLeftTextClick();
                }
            }
        });
        rightBtnAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != rightClickListener){
                    rightClickListener.onRightBtnClick();
                }
            }
        });
        rightImgAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != rightClickListener){
                    rightClickListener.onRightImgClick();
                }
            }
        });
    }

    public void setTitle(String title){
        tvTitle.setText(title);
    }

    public void setTitle(int titleResId){
        tvTitle.setText(titleResId);
    }

    /**
     * 设置左侧文字是否可见
     * @param visible   gone/visible
     */
    public void setLeftTextVisible(int visible){
        leftTextAction.setVisibility(visible);
    }

    /**
     * 设置左侧返回是否可见
     * @param visible   gone/visible
     */
    public void setBackupVisible(int visible){
        leftImgAction.setVisibility(visible);
    }

    /**
     *  设置右侧按钮是否可见
     * @param visible
     */
    public void setRightBtnVisible(int visible){
        rightBtnAction.setVisibility(visible);
    }

    /**
     * 设置右侧刷新是否可见
     * @param visible
     */
    public void setRightImgVisible(int visible){
        rightImgAction.setVisibility(visible);
    }

    /**
     * 设置左边标题栏监听事件
     * @param leftClickListener
     */
    public void setOnLeftClickListener(OnLeftClickListener leftClickListener){
        this.leftClickListener = leftClickListener;
    }

    /**
     * 设置右边标题栏监听事件
     * @param rightClickListener
     */
    public void setOnRightClickListener(OnRightClickListener rightClickListener){
        this.rightClickListener = rightClickListener;
    }

    public interface OnLeftClickListener{
        void onLeftImgClick();

        void onLeftTextClick();
    }

    public interface OnRightClickListener{
        void onRightImgClick();

        void onRightBtnClick();
    }
}

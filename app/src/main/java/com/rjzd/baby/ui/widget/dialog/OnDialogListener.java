package com.rjzd.baby.ui.widget.dialog;

/**
 * create time: 2018/6/5  18:23
 * create author: Hition
 * descriptions: OnDialogListener 弹出对话框按钮监听
 */

public interface OnDialogListener<T> {
    void onNegative(); // 取消

    void onPositive(T... args);// 确定
}

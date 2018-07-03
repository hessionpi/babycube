package com.rjzd.baby.ui.widget.dialog;

import android.app.Activity;
import android.os.Bundle;
import java.util.ArrayList;

/**
 * create time: 2018/6/5  10:55
 * create author: Hition
 * descriptions: 对话框管理
 */

public class DialogManager {

    /**
     * 不带标题带有两个按钮的弹出式对话框
     * @param context            activity
     * @param msgStringId        提示
     * @param okStringId         确定
     * @param cancelStringId     取消
     * @param listener           监听
     */
    public static void showDialogWith2ButtonNoTitle(Activity context, int msgStringId, int okStringId,
                                                      int cancelStringId, UniversalDialogListener listener){
        CommonDialogNoTitleWith2Button mCallTipsDialog = new CommonDialogNoTitleWith2Button();
        Bundle bundle = new Bundle();
        bundle.putString("tips_msg",context.getString(msgStringId));
        bundle.putString("negative_txt",context.getString(cancelStringId));
        bundle.putString("positive_txt",context.getString(okStringId));
        mCallTipsDialog.setArguments(bundle);
        mCallTipsDialog.setCancelable(true);
        if (mCallTipsDialog.isAdded()){
            mCallTipsDialog.dismiss();
        }else{
            mCallTipsDialog.show(context.getFragmentManager(), "double_button_no_title");
        }

        mCallTipsDialog.setOnDialogListener(listener);
    }

    /**
     * 显示选择性别对话框
     * @param context         Activity
     * @param listener        监听
     */
    public static void showSexDialog(Activity context, OnDialogListener<String> listener){
        SexDialog mSexDialog = new SexDialog();

        mSexDialog.setCancelable(true);
        if (mSexDialog.isAdded()){
            mSexDialog.dismiss();
        }else{
            mSexDialog.show(context.getFragmentManager(), "sex_dialog");
        }

        mSexDialog.setOnDialogListener(listener);
    }

    /**
     * 时间选择对话框
     * @param context           Activity
     * @param listener          监听
     */
    public static void showDateDialog(Activity context, ArrayList<String> years, ArrayList<String> months,ArrayList<String> days, OnDialogListener<String> listener){
        DateDialog mDateDialog = new DateDialog();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("years",years);
        bundle.putStringArrayList("months",months);
        bundle.putStringArrayList("days",days);
        mDateDialog.setArguments(bundle);
        mDateDialog.setCancelable(true);
        if (mDateDialog.isAdded()){
            mDateDialog.dismiss();
        }else{
            mDateDialog.show(context.getFragmentManager(), "date_dialog");
        }

        mDateDialog.setOnDialogListener(listener);
    }

    /**
     * 带有单个轮子的dialog
     * @param context               Activity
     * @param wheelValue            轮子上面值的数组
     * @param listener              监听
     */
    public static void showSingleDialog(Activity context, String title,ArrayList<String> wheelValue,OnDialogListener<String> listener){
        SingleWheelDialog mDateDialog = new SingleWheelDialog();
        Bundle bundle = new Bundle();
        bundle.putString("dialog_title",title);
        bundle.putStringArrayList("wheel_value",wheelValue);
        mDateDialog.setArguments(bundle);
        mDateDialog.setCancelable(true);
        if (mDateDialog.isAdded()){
            mDateDialog.dismiss();
        }else{
            mDateDialog.show(context.getFragmentManager(), "single_dialog");
        }

        mDateDialog.setOnDialogListener(listener);
    }







}

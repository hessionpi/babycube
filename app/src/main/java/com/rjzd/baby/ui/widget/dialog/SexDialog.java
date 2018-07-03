package com.rjzd.baby.ui.widget.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.orhanobut.logger.Logger;
import com.rjzd.baby.R;
import com.rjzd.baby.ui.widget.wheel.WheelView;

import java.util.Arrays;

/**
 * create time: 2018/6/5  18:05
 * create author: Hition
 * descriptions: 性别选择弹出式对话框
 */

public class SexDialog extends DialogFragment {

    private static final String[] PLANETS = new String[]{"男", "女"};
    private OnDialogListener<String> dialogListener;
    private String sexIndex = "1";
    private String sex = "男";

    public void setOnDialogListener(OnDialogListener<String> dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog mDialog = new Dialog(getActivity(), R.style.commentDialog);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setContentView(R.layout.dialog_sex_wheel);

        // 自定义轮子View
        WheelView wv = mDialog.findViewById(R.id.wheel_view_sex);
        wv.setItems(Arrays.asList(PLANETS));
        wv.setSeletion(0);

        Button mCancel = mDialog.findViewById(R.id.btn_cancel);
        Button mSure = mDialog.findViewById(R.id.btn_sure);

        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Logger.d("[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
                sexIndex = String.valueOf(selectedIndex);
                sex = item;
            }
        });
        mCancel.setOnClickListener((View v) ->{
                mDialog.dismiss();
            dialogListener.onNegative();
        });
        mSure.setOnClickListener((View v) -> {
            mDialog.dismiss();
            dialogListener.onPositive(sexIndex,sex);
        });
        return mDialog;
    }


}

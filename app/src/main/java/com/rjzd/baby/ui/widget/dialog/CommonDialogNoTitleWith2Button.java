package com.rjzd.baby.ui.widget.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.rjzd.baby.R;


/**
 * create time: 2018/6/5  10:28
 * create author: Hition
 * descriptions: 常用弹出对话框封装    无标题带有两个按钮（ok/cancel）
 */

public class CommonDialogNoTitleWith2Button extends DialogFragment {

    private UniversalDialogListener dialogListener;

    public void setOnDialogListener(UniversalDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog mDialog = new Dialog(getActivity(), R.style.commentDialog);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setContentView(R.layout.dialog_no_title_with_double_button);

        TextView mTipsMsg = mDialog.findViewById(R.id.tv_message);
        Button mNegativeBtn = mDialog.findViewById(R.id.btn_negative);
        Button mPositiveBtn = mDialog.findViewById(R.id.btn_positive);

        String tipsMsg = getArguments().getString("tips_msg");
        String tipsNegText = getArguments().getString("negative_txt");
        String tipsPosText = getArguments().getString("positive_txt");
        mTipsMsg.setText(tipsMsg);
        mNegativeBtn.setText(tipsNegText);
        mPositiveBtn.setText(tipsPosText);

        mNegativeBtn.setOnClickListener((View v) ->{
            mDialog.dismiss();
            dialogListener.onNegative();
        });
        mPositiveBtn.setOnClickListener((View v) ->{
            mDialog.dismiss();
            dialogListener.onPositive();
        });
        return mDialog;
    }

}

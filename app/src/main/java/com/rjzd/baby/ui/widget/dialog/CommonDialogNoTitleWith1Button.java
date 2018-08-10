package com.rjzd.baby.ui.widget.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rjzd.baby.R;


/**
 * create time: 2018/6/5  10:28
 * create author: Hition
 * descriptions: 常用弹出对话框封装    无标题带有两个按钮（ok/cancel）
 */

public class CommonDialogNoTitleWith1Button extends DialogFragment {

    private UniversalDialogListener dialogListener;

    public void setOnDialogListener(UniversalDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog mDialog = new Dialog(getActivity(), R.style.commentDialog);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setContentView(R.layout.layout_tools_dialog);

        TextView mTipsMsg = mDialog.findViewById(R.id.title);
        TextView mPositiveBtn = mDialog.findViewById(R.id.ok);

        String tipsMsg = getArguments().getString("title");
        String tipsPosText = getArguments().getString("ok");
        mTipsMsg.setText(tipsMsg);

        mPositiveBtn.setText(tipsPosText);
        mPositiveBtn.setOnClickListener((View v) ->{
            mDialog.dismiss();
            dialogListener.onPositive();
        });
        return mDialog;
    }

}

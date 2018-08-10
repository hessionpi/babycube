package com.rjzd.baby.ui.widget.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.rjzd.baby.R;
import com.rjzd.baby.ui.widget.wheel.WheelView;
import java.util.ArrayList;

/**
 * create time: 2018/6/8  16:24
 * create author: Hition
 * descriptions: 只有一个轮子的Dialog
 */

public class SingleWheelDialog extends DialogFragment {

    private OnDialogListener<String> dialogListener;
    private String itemValue;

    public void setOnDialogListener(OnDialogListener<String> dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog mDialog = new Dialog(getActivity(), R.style.commentDialog);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setContentView(R.layout.dialog_single_wheel);

        Bundle bundle = getArguments();
        String dialogTitle = bundle.getString("dialog_title");
        ArrayList<String> wheelValue = bundle.getStringArrayList("wheel_value");
        int defPosition = bundle.getInt("default_position");

        TextView mTitle = mDialog.findViewById(R.id.tv_dialog_title);
        mTitle.setText(dialogTitle);
        // 自定义轮子View
        WheelView wv = mDialog.findViewById(R.id.wheel_view_single);
        wv.setItems(wheelValue);
        wv.setSeletion(defPosition);
        itemValue = wv.getSeletedItem();
        Button mCancel = mDialog.findViewById(R.id.btn_cancel);
        Button mSure = mDialog.findViewById(R.id.btn_sure);

        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Logger.d("[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
                itemValue = item;
            }
        });
        mCancel.setOnClickListener((View v) ->{
            mDialog.dismiss();
            dialogListener.onNegative();
        });
        mSure.setOnClickListener((View v) -> {
            mDialog.dismiss();
            dialogListener.onPositive(itemValue);
        });
        return mDialog;
    }

}

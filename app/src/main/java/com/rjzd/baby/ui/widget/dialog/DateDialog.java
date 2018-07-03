package com.rjzd.baby.ui.widget.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.rjzd.baby.R;
import com.rjzd.baby.ui.widget.wheel.WheelView;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * create time: 2018/6/6  14:33
 * create author: Hition
 * descriptions: 选择时间弹出框
 */

public class DateDialog extends DialogFragment {

    private static final String[] MONTHS = new String[]{"1", "2","3","4","5","6","7","8","9","10","11","12"};

    private OnDialogListener<String> dialogListener;
    private String year;
    private String month;
    private String day;

    public void setOnDialogListener(OnDialogListener<String> dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog mDialog = new Dialog(getActivity(), R.style.commentDialog);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setContentView(R.layout.dialog_date_whell);

        Bundle bundle = getArguments();
        ArrayList<String> years = bundle.getStringArrayList("years");
        ArrayList<String> months = bundle.getStringArrayList("months");
        ArrayList<String> days = bundle.getStringArrayList("days");


        // 自定义轮子View
        WheelView wvYear = mDialog.findViewById(R.id.wheel_year);
        wvYear.setItems(years);
        year = wvYear.getSeletedItem();

        WheelView wvMonth = mDialog.findViewById(R.id.wheel_month);
        if(null == months || months.isEmpty()){
            wvMonth.setItems(Arrays.asList(MONTHS));
        }else{
            wvMonth.setItems(months);
        }
        month = wvMonth.getSeletedItem();

        WheelView wvDay = mDialog.findViewById(R.id.wheel_day);
        wvDay.setItems(days);
        day = wvDay.getSeletedItem();

        Button mCancel = mDialog.findViewById(R.id.btn_cancel);
        Button mSure = mDialog.findViewById(R.id.btn_sure);

        wvYear.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                year = item;
            }
        });
        wvMonth.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                month = item;
            }
        });
        wvDay.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                day = item;
            }
        });

        mCancel.setOnClickListener((View v) ->{
            mDialog.dismiss();
            dialogListener.onNegative();
        });
        mSure.setOnClickListener((View v) -> {
            mDialog.dismiss();
            dialogListener.onPositive(year,month,day);
        });
        return mDialog;
    }




}

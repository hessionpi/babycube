package com.rjzd.baby.tools;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.rjzd.baby.R;

/**
 * create time: 2018/5/25
 * create author: Hition
 * descriptions: Toast 工具类
 */
public class ToastUtils {
    static Toast toast;

    private ToastUtils() {
        throw new UnsupportedOperationException("cannot be instantiated ...");
    }

    public static void show(Context paramContext, int paramInt) {
        show(paramContext, paramContext.getResources().getText(paramInt), 0);
    }

    public static void show(Context paramContext, int paramInt1, int paramInt2) {
        show(paramContext, paramContext.getResources().getText(paramInt1), paramInt2);
    }

    public static void show(Context paramContext, CharSequence paramCharSequence) {
        show(paramContext, paramCharSequence, 0);
    }

    public static void show(Context paramContext, CharSequence paramCharSequence, int paramInt) {
        if (toast != null) {
            toast.setText(paramCharSequence);
            toast.setDuration(paramInt);
            toast.show();
            return;
        }
        toast = Toast.makeText(paramContext, paramCharSequence, paramInt);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }


  public static void showToast(Context context, String message) {
        //加载Toast布局
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
        //初始化布局控件
       TextView mTextView = (TextView) toastRoot.findViewById(R.id.message);

        //为控件设置属性
        mTextView.setText(message);
        //Toast的初始化
        Toast toastStart = new Toast(context);
        //获取屏幕高度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        //Toast的Y坐标是屏幕高度的1/3，不会出现不适配的问题
        toastStart.setGravity(Gravity.CENTER, 0, 0);
        toastStart.setDuration(Toast.LENGTH_LONG);
        toastStart.setView(toastRoot);
        toastStart.show();
    }

    public static void toastCancle() {
        if (toast != null){
            toast.cancel();
        }
    }


}

package com.rjzd.baby.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.rjzd.baby.R;
import com.rjzd.baby.tools.DensityUtil;
import com.rjzd.baby.tools.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/22.
 */

public class ShareDialog {

    public static final int SHARE_TEXT = 0;
    public static final int SHARE_IMAGE = 1;
    public static final int SHARE_VIDEO = 2;

    private SHARE_MEDIA sharePlatform = SHARE_MEDIA.WEIXIN;
    Context context;
    String sharePic;
    String shareUrl;
    /**
     * 仅有分享功能
     */
    public void showShareboard(final Context context, final ShareAction mShareAction, final int shareWay, final String shareUrl,
                               final String shareTitle, final String shareDescription, String sharePic) {
        final Dialog dialog = new Dialog(context);
        this.context=context;
        this.sharePic=sharePic;this.shareUrl=shareUrl;

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_share_only);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        TextView mWXFriend = ButterKnife.findById(dialog, R.id.tv_board_wx_friend);
        TextView mWXCenter = ButterKnife.findById(dialog, R.id.tv_board_wx_center);
        TextView mShareCancel = ButterKnife.findById(dialog, R.id.tv_shareboard_only_cancel);


        mWXCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 微信朋友圈分享
                sharePlatform = SHARE_MEDIA.WEIXIN_CIRCLE;
                startShare( mShareAction,shareWay,shareUrl,  shareTitle, shareDescription);
                dialog.dismiss();
            }
        });
        mWXFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 微信好友分享
                sharePlatform = SHARE_MEDIA.WEIXIN;
                startShare(mShareAction,shareWay,shareUrl,  shareTitle, shareDescription);
                dialog.dismiss();
            }
        });

        mShareCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.setCanceledOnTouchOutside(true);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = DensityUtil.getScreenWidth(context);
        dialogWindow.setAttributes(lp);

        dialog.show();
    }

    /**
     * 分享到微信朋友圈
     */
    private void startShare(ShareAction mShareAction, int shareWay, String shareUrl, String shareTitle, String shareDescription) {
        UMImage thumb;
        if (sharePic!=null) {
            thumb = new UMImage(context, sharePic);
        }else{
            thumb = new UMImage(context, R.mipmap.ic_launcher);
        }

        mShareAction.setPlatform(sharePlatform);
        if(shareWay == SHARE_IMAGE){
            UMWeb umweb = new UMWeb(shareUrl);
            umweb.setTitle(shareTitle);
            umweb.setDescription(shareDescription);
            umweb.setThumb(thumb);
            mShareAction.withMedia(umweb);
        }else if(shareWay == SHARE_VIDEO){
            UMVideo umvideo = new UMVideo(shareUrl);
            umvideo.setTitle(shareTitle);
            umvideo.setThumb(thumb);
            umvideo.setDescription(shareDescription);
            mShareAction.withMedia(umvideo);
        }
        mShareAction.setCallback(umShareListener).share();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
          //  LogUtil.d("plat", "platform" + platform + "分享开始了");
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
           // LogUtil.d("plat", "platform" + platform + "分享成功了");
           // ToastUtils.show(, "分享成功了");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
           // LogUtil.e("share", platform + " 分享失败啦");
            if (t != null) {
              //  LogUtil.d("throw", "throw:" + t.getMessage()+shareUrl);
               ToastUtils.show(context,"网址错误"+shareUrl);
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
           // LogUtil.e("share", platform + " 分享取消了");
        }
    };


}

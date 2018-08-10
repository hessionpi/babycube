package com.rjzd.baby.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.rjzd.baby.R;
import com.rjzd.baby.tools.NetWorkUtil;
import com.rjzd.baby.ui.widget.dialog.ShareDialog;
import com.umeng.socialize.ShareAction;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create time: 2018/6/11  9:40
 * create author: Hition
 * descriptions: 对所有的静态页面网络做封装
 */

public class WebActivity extends BaseActivity implements View.OnClickListener{


    @BindView(R.id.title_back)
    ImageButton mBack;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.iv_right)
    ImageView mRightImage;
    @BindView(R.id.web_pbar)
    ProgressBar webPbar;

    @BindView(R.id.fragment_webview)
    WebView mWebView;
    @BindView(R.id.load_failed_text)
    TextView textView;
    @BindView(R.id.reload)
    Button reload;
    @BindView(R.id.load_failed_imageview)
    ImageView notnet_or_notrecord;

    @BindView(R.id.load_failed_webview)
    LinearLayout loadFailedView;

    private String data;
    private String mimeType;
    private String url;
    private String titleName;

    private String title;
    private String subTitle;
    private String cover;

//    private int userid;
    private static final String INTENT_KEY_DATA = "data";
    private static final String INTENT_KEY_MIME_TYPE = "mime_type";

    private static final String INTENT_KEY_REQUEST_URL = "request_url";
    private static final String INTENT_KEY_ACTIVITY_TITLE_NAME = "activity_title";
//    private static final String USER_ID = "userid";
    private boolean isAnimStart = false;
    private int currentProgress;
    private boolean showWebViewTag = false;
    // 友盟社会化分享
    private ShareAction mShareAction = new ShareAction(this);
    public static void startActivity(Context context, String url,boolean showRight) {
        Logger.v("WebActivity:"+url);
        Intent i = new Intent();
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setClass(context, WebActivity.class);
        i.putExtra(INTENT_KEY_REQUEST_URL, url);
        i.putExtra("show_right",showRight);
        context.startActivity(i);
    }


    /**
     * 外部方法启动本Activity的方法。
     */
    public static void startActivity(Context context, String url, String titleName) {
        Intent i = new Intent();
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setClass(context, WebActivity.class);
        i.putExtra(INTENT_KEY_REQUEST_URL, url);
        i.putExtra(INTENT_KEY_ACTIVITY_TITLE_NAME, titleName);
        context.startActivity(i);
    }

    public static void startActivity(Context context, String data, String mimeType, String titleName) {
        Intent i = new Intent();
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setClass(context, WebActivity.class);
        i.putExtra(INTENT_KEY_DATA, data);
        i.putExtra(INTENT_KEY_MIME_TYPE, mimeType);
        i.putExtra(INTENT_KEY_ACTIVITY_TITLE_NAME, titleName);
        context.startActivity(i);
    }
    public static void startActivity(Context context, String url,boolean showRight,String title,String subTitle,String cover) {
        Logger.v("WebActivity:"+url);
        Intent i = new Intent();
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setClass(context, WebActivity.class);
        i.putExtra(INTENT_KEY_REQUEST_URL, url);
        i.putExtra("show_right",showRight);
        i.putExtra("title",title);
        i.putExtra("subTitle",subTitle);
        i.putExtra("cover",cover);
        context.startActivity(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        init();
        initWebSet();
        initWebViewClient();
        initWebChromeClient();
    }

    private void setRightVisible(){
        mRightImage.setVisibility(View.VISIBLE);
    }

    private void init() {
        boolean showRight = getIntent().getBooleanExtra("show_right",false);
        if(showRight){
            setRightVisible();
        }
        // 安全漏洞
        mWebView.removeJavascriptInterface("accessibility");
        mWebView.removeJavascriptInterface("accessibilityTraversal");
        mWebView.removeJavascriptInterface("searchBoxJavaBridge_");

        data = getIntent().getStringExtra(INTENT_KEY_DATA);
        mimeType = getIntent().getStringExtra(INTENT_KEY_MIME_TYPE);
        url = getIntent().getStringExtra(INTENT_KEY_REQUEST_URL);
        titleName = getIntent().getStringExtra(INTENT_KEY_ACTIVITY_TITLE_NAME);

        title = getIntent().getStringExtra("title");
        subTitle = getIntent().getStringExtra("subTitle");
        cover = getIntent().getStringExtra("cover");



        if(!TextUtils.isEmpty(titleName)){
            mTitleName.setText(titleName);
        }

        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        }
        if (!TextUtils.isEmpty(data)) {
            mWebView.loadDataWithBaseURL(null, data, mimeType, "utf-8", null);
        }

        mBack.setOnClickListener(this);
        reload.setOnClickListener(this);
        mRightImage.setOnClickListener(this);
        //titleShare.setOnClickListener(this);
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    private void initWebSet() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(false);

        webSettings.setJavaScriptEnabled(true);
        //最重要的方法，一定要设置，这就是出不来的主要原因
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setBlockNetworkImage(false);
        webSettings.setBlockNetworkLoads(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDatabaseEnabled(true);
        webSettings.setUserAgentString("android");
        webSettings.setJavaScriptEnabled(true);
        //  mWebView.addJavascriptInterface(new JavaScriptinterface(), "android");
    }

    private void showNoNetView() {
        if (NetWorkUtil.isNetworkConnected(WebActivity.this)) {//有网络，失败情况
            notnet_or_notrecord.setBackgroundResource(R.drawable.load_failed_icon);
            textView.setText(R.string.load_failed);
        } else {//无网络
            notnet_or_notrecord.setBackgroundResource(R.drawable.load_failed_nonet_icon);
            textView.setText(R.string.no_network);
        }
        loadFailedView.setVisibility(View.VISIBLE);
    }

    private void initWebViewClient() {
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (!NetWorkUtil.isNetworkConnected(WebActivity.this)) {
                    loadFailedView.setVisibility(View.VISIBLE);
                    showWebViewTag = false;
                } else {
                    webPbar.setVisibility(View.VISIBLE);
                    webPbar.setAlpha(1.0f);
                    showWebViewTag = true;
                }
            }


            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                showWebViewTag = false;
                view.setVisibility(View.GONE);
                showNoNetView();
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (showWebViewTag) {
                    mWebView.setVisibility(View.VISIBLE);
                    loadFailedView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initWebChromeClient() {
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                currentProgress = webPbar.getProgress();
                if (newProgress >= 100 && !isAnimStart) {
                    // 防止调用多次动画
                    isAnimStart = true;
                    webPbar.setProgress(newProgress);
                    // 开启属性动画让进度条平滑消失
                    startDismissAnimation(webPbar.getProgress());
                } else {
                    // 开启属性动画让进度条平滑递增
                    startProgressAnimation(newProgress);
                }
            }
        });
    }

    /**
     * progressBar递增动画
     */
    private void startProgressAnimation(int newProgress) {
        ObjectAnimator animator = ObjectAnimator.ofInt(webPbar, "progress", currentProgress, newProgress);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    /**
     * progressBar消失动画
     */
    private void startDismissAnimation(final int progress) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(webPbar, "alpha", 1.0f, 0.0f);
        anim.setDuration(1500);  // 动画时长
        anim.setInterpolator(new DecelerateInterpolator());     // 减速
        // 关键, 添加动画进度监听器
        anim.addUpdateListener((ValueAnimator valueAnimator) -> {
            // 0.0f ~ 1.0f
            float fraction = valueAnimator.getAnimatedFraction();
            int offset = 100 - progress;
            webPbar.setProgress((int) (progress + offset * fraction));
        });

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // 动画结束
                webPbar.setProgress(0);
                webPbar.setVisibility(View.GONE);
                isAnimStart = false;
            }
        });

        anim.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.reload:
                reloadWeb();
                break;
            case R.id.iv_right:
                ShareDialog dialog = new ShareDialog();
                dialog.showShareboard(this, mShareAction, ShareDialog.SHARE_IMAGE, url, title, subTitle, cover);

                break;
        }
    }


    /**
     * 重新加载web 页面
     */
    private void reloadWeb() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            loadFailedView.setVisibility(View.GONE);
            String url = mWebView.getUrl();
            mWebView.loadUrl(url);
        } else {
            mWebView.setVisibility(View.GONE);
            showNoNetView();
        }
    }


   /* public class JavaScriptinterface {
        @JavascriptInterface
        public void attentionUser(String userid) {
            BuildingPresenter buildingPresenter = new BuildingPresenter(WebActivity.this);
            buildingPresenter.attentionOrCancel(0, userid, 1, new OnResonseListener());
            Toast.makeText(WebActivity.this, "js调用了android的方法,关注", Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void inquiryAuthuser(String userid,
                                    String thumbnail,
                                    String nickname) {
            ChatActivity.navToChat(WebActivity.this, userid, TIMConversationType.C2C);
            // Toast.makeText(WebActivity.this,"js调用了android的方法,私信",Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void callNumber(String number) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));

            if (ActivityCompat.checkSelfPermission(WebActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(intent);
            // ChatActivity.navToChat(WebActivity.this,userid, TIMConversationType.C2C);
            // Toast.makeText(WebActivity.this,"js调用了android的方法,私信",Toast.LENGTH_SHORT).show();
        }
    }
*/
   /* private class OnResonseListener implements BaseListener {
        @Override
        public void onSuccess(BaseResponse data, int flag) {
            if (0 == data.getReturnValue()) {

                ToastUtils.show(WebActivity.this, "操作成功");
            } else {
                LogUtil.e("hition===", data.getReturnMsg() + data.getReturnValue());
            }
        }

        @Override
        public void onFailed(Throwable e, int flag) {

        }
    }*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //重写onKeyDown，当浏览网页，WebView可以后退时执行后退操作。
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

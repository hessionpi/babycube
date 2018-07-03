package com.rjzd.baby.tools.cos;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.orhanobut.logger.Logger;
import com.rjzd.baby.BabyApplication;
import com.rjzd.baby.BabyConstants;
import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.object.PutObjectRequest;

/**
 * create time: 2018/6/13  10:28
 * create author: Hition
 * descriptions: 腾讯云COS上传工具类
 */

public class CosUtil {



    private Context mContext;
    private OnUploadListener mListener;
    private Handler mMainHandler;
private final static int Success=0;
    private final static int Fail=1;
    public CosUtil(Context mContext, OnUploadListener listener) {
        this.mContext = mContext;
        this.mListener = listener;

        mMainHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case Success:
                        if (mListener != null) {
                            mListener.onSuccess(msg.arg1, (String) msg.obj);
                        }
                        break;

                    case Fail:
                        if (mListener != null) {
                            mListener.onFailed(msg.arg1, (String) msg.obj);
                        }
                        break;
                }
                return false;
            }
        });

    }


    public void upload(String cosPath,String srcPath, boolean isAgain){
        //创建 CosXmlServiceConfig 对象，根据需要修改默认的配置参数
        CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder()
                .setAppidAndRegion(BabyConstants.COS_APPID, BabyConstants.COS_REGION)
                .setDebuggable(true)
                .builder();
        //创建获取签名类(请参考下面的生成签名示例，或者参考 sdk中提供的ShortTimeCredentialProvider类）
        LocalCredentialProvider localCredentialProvider = new LocalCredentialProvider(BabyConstants.SECRETID, BabyConstants.SECRETKEY, BabyConstants.KEYDURATION);
        //创建 CosXmlService 对象，实现对象存储服务各项操作.
        CosXmlService cosXmlService = new CosXmlService(BabyApplication.getApplication(),serviceConfig, localCredentialProvider);

        // cos v5 的 bucket格式为：xxx-appid, 如 test-1253960454
        String bucket = BabyConstants.COS_BUCKET+"-"+BabyConstants.COS_APPID;
        /*String cosPath = "[对象键](https://cloud.tencent.com/document/product/436/13324)，即存储到 COS 上的绝对路径"; //格式如 cosPath = "test.txt";
        String srcPath = "本地文件的绝对路径"; // 如 srcPath = Environment.getExternalStorageDirectory().getPath() + "/test.txt";*/

//        long signDuration = 600; //签名的有效期，单位为秒
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, cosPath, srcPath);
//        putObjectRequest.setSign(signDuration,null,null); //若不调用，则默认使用sdk中sign duration（60s）

  /*      putObjectRequest.setProgressListener((long progress, long max) -> {
            float result = (float) (progress * 100.0/max);
         //   Logger.w("TEST","progress =" + (long)result + "%");
        });*/

        //使用同步方法上传
        /*try {
            PutObjectResult putObjectResult = cosXmlService.putObject(putObjectRequest);

            Logger.w("TEST","success: " + putObjectResult.accessUrl);

        } catch (CosXmlClientException e) {
            //抛出异常
            Logger.w("TEST","CosXmlClientException =" + e.toString());
        } catch (CosXmlServiceException e) {
            //抛出异常
            Logger.w("TEST","CosXmlServiceException =" + e.toString());
        }*/

        cosXmlService.putObjectAsync(putObjectRequest, new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest request, CosXmlResult result) {
               Logger.w("TEST","success =" + result.accessUrl);
                Message msg = new Message();
                msg.what = Success;
                msg.obj = result.accessUrl;
                mMainHandler.sendMessage(msg);
            }

            @Override
            public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException clientException, CosXmlServiceException serviceException)  {
                String errorMsg = clientException != null ? clientException.toString() : serviceException.toString();
                Logger.w("TEST",errorMsg);
                if(isAgain){
                    upload(cosPath,srcPath,false);
                }
                Message errmsg = new Message();
                errmsg.what =Fail;
                errmsg.obj =clientException;
                mMainHandler.sendMessage(errmsg);
            }
        });


    }

public interface OnUploadListener {
    void onSuccess(int code,String url);

    void onFailed(int errorCode,String msg);
}



}

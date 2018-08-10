package com.rjzd.baby.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.rjzd.baby.R;
import com.rjzd.baby.api.ExceptionHandler;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.entity.Province;
import com.rjzd.baby.model.LoginModel;
import com.rjzd.baby.model.UserInfoCenter;
import com.rjzd.baby.presenter.impl.UserPresenter;
import com.rjzd.baby.tools.ToastUtils;
import com.rjzd.baby.tools.ZDUtils;
import com.rjzd.baby.tools.cos.CosUtil;
import com.rjzd.baby.ui.tools.imgloader.ImageLoader;
import com.rjzd.baby.ui.widget.ToolsbarView;
import com.rjzd.baby.ui.widget.dialog.DialogManager;
import com.rjzd.baby.ui.widget.dialog.OnDialogListener;
import com.rjzd.baby.ui.widget.dialog.UniversalDialogListener;
import com.rjzd.baby.view.IView;
import com.zd.baby.api.model.ReqUpdateUserInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create time: 2018/6/8  13:49
 * create author: Hition
 * descriptions: 个人资料
 */

public class UserinfoActivity extends BaseActivity implements View.OnClickListener, IView {

    @BindView(R.id.tool_bar)
    ToolsbarView toolBar;

    @BindView(R.id.iv_headpic)
    ImageView ivHeadpic;
    @BindView(R.id.tv_change_head_pic)
    TextView tvChangeHeadPic;
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_hight)
    TextView tvHeight;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;



    private LoginModel userInfo;
    private ArrayList<String> hightArray;
    private ArrayList<String> wightArray;
    private ArrayList<String> birthYearArray;
    private ArrayList<String> birthDayArray;

    private UserPresenter presenter;
    private ReqUpdateUserInfo request;

    private boolean isNeedChange = false;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UserinfoActivity.class);
        context.startActivity(intent);
    }
    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            // 拍照权限
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            // 读取存储权限
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            // 写入存储权限
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this, permissions.toArray(new String[0]), 100);
                return false;
            }
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        userInfo = UserInfoCenter.getInstance().getLoginModel();
        request = new ReqUpdateUserInfo();
        presenter = new UserPresenter(this);
        initView();

        finalData();
        EventBus.getDefault().register(this);
    }

    private void finalData() {
        hightArray = new ArrayList<>();
        wightArray = new ArrayList<>();
        birthYearArray = new ArrayList<>();
        birthDayArray = new ArrayList<>();

        for(int h=120;h<281;h++){
            hightArray.add(h+"cm");
        }
        for(int w=30;w<200;w++){
            wightArray.add(w+"kg");
        }

        int currentYear = ZDUtils.getCurrentYear();
        for(int y=currentYear-90;y<currentYear-15;y++){
            birthYearArray.add(y+"");
        }
        for(int d=1;d<32;d++){
            birthDayArray.add(d+"");
        }
    }

    private void initView() {
        toolBar.setOnLeftClickListener(new ToolsbarView.OnLeftClickListener() {
            @Override
            public void onLeftImgClick() {
                if(isNeedChange){
                    DialogManager.showDialogWith2ButtonNoTitle(UserinfoActivity.this,
                            R.string.tips_save_warning, R.string.ok, R.string.cancel, new UniversalDialogListener() {
                                @Override
                                public void onNegative() {
                                    finish();
                                }

                                @Override
                                public void onPositive() {
                                    String nickname = etNickname.getText().toString();
                                    if(!TextUtils.isEmpty(nickname)){
                                        request.setNickname(nickname);
                                    }
                                    presenter.updateUserInfo(request);
                                }
                            });
                }else{
                    finish();
                }

            }

            @Override
            public void onLeftTextClick() {

            }
        });
        toolBar.setRightBtnVisible(View.VISIBLE);
        toolBar.setOnRightClickListener(new ToolsbarView.OnRightClickListener() {
            @Override
            public void onRightImgClick() {

            }

            @Override
            public void onRightBtnClick() {
                // 调用服务器接口修改用户资料
                String nickname = etNickname.getText().toString();
                if(!TextUtils.isEmpty(nickname)){
                    request.setNickname(nickname);
                }
                presenter.updateUserInfo(request);
            }
        });


        if(null != userInfo){
            request.setHeadPic(userInfo.getHeadpic());
            request.setNickname(userInfo.getNickname());
            String province = userInfo.getProvince();
            String city = userInfo.getCity();
            int sex = userInfo.getSex();
            String birthday = userInfo.getBirthday();
            int weight = userInfo.getWeight();
            int height = userInfo.getHeight();

            request.setProvince(province);
            request.setCity(city);
            request.setSex(sex);
            request.setBirthday(birthday);
            request.setWeight(weight);
            request.setHight(height);

            ImageLoader.loadTransformImage(this,userInfo.getHeadpicThumb(),ivHeadpic,0);
            etNickname.setText(userInfo.getNickname());
            String sexStr = "未知";
            if(1 == sex){
                sexStr = "男";
            }else if(2 == sex){
                sexStr = "女";
            }
            tvSex.setText(sexStr);
            if(TextUtils.isEmpty(province) && TextUtils.isEmpty(city)){
                tvLocation.setText("未知");
            }else{
                tvLocation.setText(province+ " " +city);
            }
            if(height == 0){
                tvHeight.setText("未知");
            }else{
                tvHeight.setText(height+"cm");
            }
            if(weight == 0){
                tvWeight.setText("未知");
            }else{
                tvWeight.setText(weight+"kg");
            }
            if(TextUtils.isEmpty(birthday)){
                tvBirthday.setText("未知");
            }else{
                tvBirthday.setText(birthday);
            }

        }


        tvChangeHeadPic.setOnClickListener(this);
        tvSex.setOnClickListener(this);
        tvLocation.setOnClickListener(this);
        tvHeight.setOnClickListener(this);
        tvWeight.setOnClickListener(this);
        tvBirthday.setOnClickListener(this);
        etNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isNeedChange = true;
            }
        });
       // uploadUtils = new CosUtil(this, new UploadListener());
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_change_head_pic:
                // 跳转到选择照片
                if (checkPermission()) {
                    choosePhotos();
                }

                break;

            case R.id.tv_sex:
                // 修改性别
                DialogManager.showSexDialog(this, new OnDialogListener<String>() {
                    @Override
                    public void onNegative() {

                    }

                    @Override
                    public void onPositive(String... args) {
                        isNeedChange = true;
                        String index = args[0];
                        String sex = args[1];
                        request.setSex(Integer.parseInt(index));
                        tvSex.setText(sex);
                    }
                });
                break;

            case R.id.tv_location:
                // 修改地区
                ChooseProvinceActivity.startActivity(this);
                break;

            case R.id.tv_hight:
                // 修改身高
                DialogManager.showSingleDialog(this,getString(R.string.hight),hightArray,45, new OnDialogListener<String>() {
                    @Override
                    public void onNegative() {

                    }

                    @Override
                    public void onPositive(String... args) {
                        isNeedChange = true;
                        String hightStr = args[0];
                        int height = Integer.parseInt(hightStr.replace("cm",""));
                        request.setHight(height);
                        tvHeight.setText(height+"cm");
                    }
                });

                break;

            case R.id.tv_weight:
                // 修改体重
                DialogManager.showSingleDialog(this,getString(R.string.weight), wightArray,20, new OnDialogListener<String>() {
                    @Override
                    public void onNegative() {

                    }

                    @Override
                    public void onPositive(String... args) {
                        isNeedChange = true;
                        String weightStr = args[0];
                        int weight = Integer.parseInt(weightStr.replace("kg",""));
                        request.setWeight(weight);
                        tvWeight.setText(weight+"kg");
                    }
                });

                break;

            case R.id.tv_birthday:
                // 修改生日
                DialogManager.showDateDialog(this, birthYearArray, null,birthDayArray,
                        62,0,0, new OnDialogListener<String>() {
                    @Override
                    public void onNegative() {

                    }

                    @Override
                    public void onPositive(String... args) {
                        if(args.length>=3){
                            isNeedChange = true;
                            String birthdayYear = args[0];
                            String birthdayMonth = args[1];
                            String birthdayDay = args[2];
                            String birthday = birthdayYear+"年"+birthdayMonth+"月"+birthdayDay+"日";
                            // 调用接口修改生日
                            request.setBirthday(birthdayYear+"-"+birthdayMonth+"-"+birthdayDay);
                            tvBirthday.setText(birthday);
                        }
                    }
                });
                break;
        }
    }


    @Override
    public void onPrepare() {

    }

    @Override
    public void onComplete(BaseResponse data, int flag) {
        if(null != data ){
            switch (data.getReturnValue()){
                case 0:
                    // 修改资料成功
                    ToastUtils.show(this,"保存成功");
                    userInfo.setHeadpic(request.getHeadPic());
                    userInfo.setHeadpicThumb(request.getHeadPic());
                    userInfo.setNickname(request.getNickname());
                    userInfo.setSex(request.getSex());
                    userInfo.setProvince(request.getProvince());
                    userInfo.setCity(request.getCity());
                    userInfo.setHeight(request.getHight());
                    userInfo.setWeight(request.getWeight());
                    userInfo.setBirthday(request.getBirthday());
                    UserInfoCenter.getInstance().setLoginBean(userInfo);
                    finish();
                    break;

                case 500:
                    //Logger.e("服务器异常："+data.getReturnMsg());
                    break;

                default:

                    break;
            }

        }
    }

    @Override
    public void onFailShow(int errorCode) {
        ExceptionHandler.handleException(this,errorCode);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventLocation(Province province) {
        if(null != province){
            isNeedChange = true;
            String provinceName = province.getName();
            String cityName = province.getChooseCity();
            request.setProvince(provinceName);
            request.setCity(cityName);
            tvLocation.setText(provinceName+" "+cityName);
        }
    }

    private void choosePhotos(){
        Intent intent = new Intent(this, AllPictureActivity.class);
        startActivityForResult(intent,0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 100:
                for (int i=0;i < grantResults.length;i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                choosePhotos();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 200) {
            String srcPath = data.getStringExtra("src_path");
            if(TextUtils.isEmpty(srcPath)){
                return ;
            }

            CosUtil cosUtil = new CosUtil(this, new CosUtil.OnUploadListener() {
                @Override
                public void onSuccess(int code, String url) {
                    String headPicUrl="http://" +url;
                    ImageLoader.loadTransformImage(UserinfoActivity.this,headPicUrl,ivHeadpic,0);
                    request.setHeadPic(headPicUrl);
                    isNeedChange = true;
                }

                @Override
                public void onFailed(int errorCode, String msg) {
                    ToastUtils.show(UserinfoActivity.this,"上传失败……");
                }
            });
            cosUtil.upload("user" + "/" + UserInfoCenter.getInstance().getUserId() + "/" + System.currentTimeMillis()+".jpg", srcPath, false);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
        EventBus.getDefault().unregister(this);
    }
}

package com.rjzd.baby.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.rjzd.baby.R;
import com.rjzd.baby.api.ExceptionHandler;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.model.UserInfoCenter;
import com.rjzd.baby.presenter.impl.BabyPresenter;
import com.rjzd.baby.tools.ToastUtils;
import com.rjzd.baby.tools.ZDUtils;
import com.rjzd.baby.tools.cos.CosUtil;
import com.rjzd.baby.ui.tools.imgloader.ImageLoader;
import com.rjzd.baby.ui.widget.ToolsbarView;
import com.rjzd.baby.ui.widget.dialog.DialogManager;
import com.rjzd.baby.ui.widget.dialog.OnDialogListener;
import com.rjzd.baby.view.IView;
import com.zd.baby.api.model.BabyInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create time: 2018/6/1  14:40
 * create author: Hition
 * descriptions: 添加宝宝或者修改宝宝
 */

public class AddBabyActivity extends BaseActivity implements View.OnClickListener, IView {


    @BindView(R.id.layout_title_bar)
    ToolsbarView layoutTitleBar;
    @BindView(R.id.iv_baby_photo)
    ImageView ivBabyPhoto;
    @BindView(R.id.tv_add_photo)
    TextView mAddPhoto;
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_sex)
    TextView tvSex;

    @BindView(R.id.btn_save)
    Button btnSave;


    private BabyInfo mBabyInfo;
    private BabyPresenter presenter;
    boolean isUpdate;

    private int babyId;
    private int babySex;
    private String babyName;
    private String babyBirthday;
    private String babyThumb;


    ArrayList<String> years = new ArrayList<>();
    ArrayList<String> months = new ArrayList<>();
    ArrayList<String> days = new ArrayList<>();
    int yearPosition;
    int monthPosition;
    int dayPosition;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AddBabyActivity.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, BabyInfo babyInfo) {
        Intent intent = new Intent(context, AddBabyActivity.class);
        intent.putExtra("baby_change", true);
        intent.putExtra("baby_info", babyInfo);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_baby);
        ButterKnife.bind(this);
        parseIntent();
        initView();
        presenter = new BabyPresenter(this);
    }

    private void parseIntent() {
        //修改宝宝
        isUpdate = getIntent().getBooleanExtra("baby_change", false);
        if (isUpdate) {
            mBabyInfo = (BabyInfo) getIntent().getSerializableExtra("baby_info");
        }
    }

    private void initView() {
        // 修改宝宝信息
        int birthY = 0;
        int birthM = 0 ;
        int birthD = 0 ;
        if(isUpdate){
            layoutTitleBar.setTitle(R.string.change);
            mAddPhoto.setVisibility(View.GONE);
            if(null != mBabyInfo){
                babyId = mBabyInfo.getBabyId();
                babySex = mBabyInfo.getBabySex();
                babyName = mBabyInfo.getBabyName();
                babyBirthday = mBabyInfo.getBabyBirthday();
                babyThumb = mBabyInfo.getBabyThumb();

                ImageLoader.loadTransformImage(this,babyThumb,ivBabyPhoto,0);
                etNickname.setText(babyName);
                tvBirthday.setText(babyBirthday);
                String sexStr = "未知";
                if(1 == babySex){
                    sexStr = "男";
                }else if(2 == babySex){
                    sexStr = "女";
                }
                tvSex.setText(sexStr);
            }

            birthY = ZDUtils.getDateField("yyyy-MM-dd",babyBirthday, "year");
            birthM = ZDUtils.getDateField("yyyy-MM-dd",babyBirthday, "month");
            birthD = ZDUtils.getDateField("yyyy-MM-dd",babyBirthday, "day");
        }else{
            layoutTitleBar.setTitle(R.string.add);
            ivBabyPhoto.setImageResource(R.drawable.ic_default_camera);
        }

        ivBabyPhoto.setOnClickListener(this);
        tvBirthday.setOnClickListener(this);
        tvSex.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        // init the year、month、day
        int cYear = ZDUtils.getCurrentYear();
        for(int i=cYear-1;i<=cYear+1;i++){
            String year = String.valueOf(i);
            years.add(year);
            if(isUpdate && birthY == i){
                yearPosition =years.indexOf(year);
            }
        }
        for(int m=1;m<13;m++){
            String month = String.valueOf(m);
            months.add(month);
            if(isUpdate && birthM == m){
                monthPosition = months.indexOf(month);
            }
        }
        for(int d=1;d<=31;d++){
            String day =String.valueOf(d);
            days.add(day);
            if(isUpdate && birthD == d){
                dayPosition = days.indexOf(day);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_baby_photo:
                // 为宝宝添加一张照片
                if (checkPermission()) {
                    choosePhotos();
                }
                break;

            case R.id.tv_sex:
                // 选择性别
                DialogManager.showSexDialog(this, new OnDialogListener<String>() {
                    @Override
                    public void onNegative() {

                    }

                    @Override
                    public void onPositive(String... args) {
                        if(args.length == 2){
                            String sexInt = args[0];
                            String sex = args[1];
                            babySex = Integer.parseInt(sexInt);
                            tvSex.setText(sex);
                        }

                    }
                });
                break;

            case R.id.tv_birthday:
                // 选择生日
                DialogManager.showDateDialog(this, years, months, days,yearPosition,monthPosition,dayPosition,new OnDialogListener<String>() {
                    @Override
                    public void onNegative() {

                    }

                    @Override
                    public void onPositive(String... args) {
                        if(args.length == 6){
                            babyBirthday = args[0]+"-"+args[1]+"-"+args[2];
                            tvBirthday.setText(babyBirthday);
                            yearPosition = Integer.parseInt(args[3]);
                            monthPosition = Integer.parseInt(args[4]);
                            dayPosition = Integer.parseInt(args[5]);
                        }
                    }
                });
                break;

            case R.id.btn_save:
                babyName = etNickname.getText().toString();

                if(isUpdate && babyId == 0){
                    return ;
                }
                if(TextUtils.isEmpty(babyName)){
                    ToastUtils.show(this,"请填写宝宝名字");
                    return;
                }
                if(TextUtils.isEmpty(babyBirthday)){
                    ToastUtils.show(this,"请选择宝宝生日");
                    return;
                }

                if(isUpdate){
                    presenter.updateBaby(babyId,babySex,babyName,babyBirthday,babyThumb);
                }else{
                    presenter.addBaby(babySex,babyName,babyBirthday);
                }
                break;
        }
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onComplete(BaseResponse data, int flag) {
        if (null != data) {
            switch (data.getReturnValue()) {
                case 0:
                    ToastUtils.show(this,"保存成功");
                    finish();
                    EventBus.getDefault().post("refresh_baby");
                    break;

                case 500:
                    Logger.v("服务器异常：" + data.getReturnMsg());
                    break;
            }
        }
    }

    @Override
    public void onFailShow(int errorCode) {
        ExceptionHandler.handleException(this, errorCode);
    }

    private void choosePhotos(){
        Intent intent = new Intent(this, AllPictureActivity.class);
        startActivityForResult(intent,0);
    }

    /**
     * 相机等动态权限申请
     * @return true or false
     */
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
                    babyThumb = "http://" + url;
                    ImageLoader.loadTransformImage(AddBabyActivity.this,babyThumb,ivBabyPhoto,0);
                }

                @Override
                public void onFailed(int errorCode, String msg) {
                    ToastUtils.show(AddBabyActivity.this,"上传失败……");
                }
            });
            cosUtil.upload("user" + "/" + UserInfoCenter.getInstance().getUserId() + "/baby" + System.currentTimeMillis()+".jpg", srcPath, false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
    }
}

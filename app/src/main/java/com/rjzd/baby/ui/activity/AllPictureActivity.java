package com.rjzd.baby.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rjzd.baby.R;
import com.rjzd.baby.entity.PictureBean;
import com.rjzd.baby.model.UserInfoCenter;
import com.rjzd.baby.tools.DensityUtil;
import com.rjzd.baby.tools.ToastUtils;
import com.rjzd.baby.tools.cos.CosUtil;
import com.rjzd.baby.ui.adapter.AllPictureAdapter;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;
import com.rjzd.baby.ui.widget.ToolsbarView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: 所有视频
 */
public class AllPictureActivity extends BaseActivity {

    @BindView(R.id.layout_title_bar)
    ToolsbarView layoutTitleBar;
    @BindView(R.id.rv_picture)
    RecyclerView rvPicture;

    List<PictureBean> mediaBeen;
    AllPictureAdapter allPictureAdapter;
    Uri photoUri;
    Uri photoOutputUri;
    protected static final int CAMERA = 0;
    protected static final int CROP_BITMAP = 1;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AllPictureActivity.class);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_picture);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        rvPicture.setLayoutManager(gridLayoutManager);

        allPictureAdapter = new AllPictureAdapter(this);
        HeaderView headerView = new HeaderView();
        allPictureAdapter.addHeader(headerView);
        rvPicture.setAdapter(allPictureAdapter);
        getAllPhotoInfo();
        allPictureAdapter.setData(mediaBeen);
        allPictureAdapter.setOnItemClickListener(new XMBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
              String path=allPictureAdapter.getItem(position).getPath();
                File file = new File(path);
                Uri   imageUri = FileProvider.getUriForFile(AllPictureActivity.this,
                        "com.rjzd.baby,FileProvider",file);
                cropPhoto(imageUri);
            }
        });
    }


    /**
     * 读取手机中所有图片信息
     */
    private void getAllPhotoInfo() {

        mediaBeen = new ArrayList<>();
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projImage = {MediaStore.Images.Media._ID
                , MediaStore.Images.Media.DATA
                , MediaStore.Images.Media.SIZE
                , MediaStore.Images.Media.DISPLAY_NAME};
        Cursor mCursor = getContentResolver().query(mImageUri,
                projImage,
                MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED + " desc");

        if (mCursor != null) {
            while (mCursor.moveToNext()) {

                String displayName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                // 获取图片的路径

                String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));


                //用于展示相册初始化界面
                mediaBeen.add(new PictureBean(path, displayName));

            }
            mCursor.close();
        }

    }


    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri inputUri) {
        // 调用系统裁剪图片的 Action
        Intent cropPhotoIntent = new Intent("com.android.camera.action.CROP");
        // 设置数据Uri 和类型
        cropPhotoIntent.setDataAndType(inputUri, "image/*");
        // 授权应用读取 Uri，这一步要有，不然裁剪程序会崩溃
        cropPhotoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cropPhotoIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropPhotoIntent.putExtra("crop", "true");
        cropPhotoIntent.putExtra("aspectX", 1);
        cropPhotoIntent.putExtra("aspectY", 1);
        cropPhotoIntent.putExtra("outputX", DensityUtil.getScreenWidth(AllPictureActivity.this));
        cropPhotoIntent.putExtra("outputY", DensityUtil.getScreenWidth(AllPictureActivity.this));
        // 设置图片的最终输出目录
        photoOutputUri = Uri.parse("file:////sdcard/upload_img.jpg");
        cropPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoOutputUri);
        cropPhotoIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        cropPhotoIntent.putExtra("noFaceDetection", true);
        startActivityForResult(cropPhotoIntent, CROP_BITMAP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            // 通过返回码判断是哪个应用返回的数据
            switch (requestCode) {
                // 拍照
                case CAMERA:
                    cropPhoto(photoUri);
                    break;
                // 裁剪图片
                case CROP_BITMAP:
                    if (data != null) {
                        Intent intent = new Intent();
                        intent.putExtra("src_path", photoOutputUri.getPath());
                        setResult(200, intent);
                        finish();
                    }
                    break;
            }
        }
    }

    private class HeaderView implements XMBaseAdapter.ItemView {
        @Override
        public View onCreateView(ViewGroup parent) {
            View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_all_picture, null);

            return headerView;
        }

        @Override
        public void onBindView(View headerView) {
            ImageView imageView = headerView.findViewById(R.id.iv_picture);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    File outputImage = new File(Environment.getExternalStorageDirectory(),
                            System.currentTimeMillis() + ".png");
                    try {
                        if (outputImage.exists()) {
                            outputImage.delete();//如果文件存在，则删除
                        }
                        outputImage.createNewFile();//创建一个新文件
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    photoUri = Uri.fromFile(outputImage);
                    // createFile();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());
                    }
                    Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent1.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(intent1, CAMERA);
                }
            });


        }


    }
}

package com.rjzd.baby.ui.tools.imgloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.rjzd.baby.ui.tools.imgloader.CircleTransform;
import com.rjzd.baby.ui.tools.imgloader.RoundTransform;

import java.io.File;

/**
 * create time: 2018/5/25
 * create author: Hition
 * descriptions: 利用Glide加载图片
 */
public class ImageLoader {

    /**
     * 加载原图
     * @param url   图片地址
     * @param emptyResId  未加载时图片资源id
     * @param errorResId  加载失败图片资源id
     * @param view    imageView
     */
    public static void load(Context context, String url, int emptyResId, int errorResId, ImageView view){
        Glide.with(context.getApplicationContext()).load(url).placeholder(emptyResId).error(errorResId).into(view);
    }

    public static void load(Context context, String url, ImageView view){
        Glide.with(context).load(url).into(view);
    }

    public static void loadFromFile(Context context, String path, ImageView view){
        Glide.with(context.getApplicationContext()).load(new File(path)).into(view);
    }

    /**
     * 记载本地Gif图
     * @param resId             图片资源id
     * @param view              目标ImageView
     */
    public static void loadLocalGif(Context context, int resId, ImageView view){
        Glide.with(context).load(resId).asGif().into(view);
    }


    /**
     * 加载圆形图像或者圆角图像
     * @param context
     * @param url   图片地址
     * @param emptyResId  未加载时图片资源id
     * @param errorResId  加载失败图片资源id
     * @param view     imageView
     * @param radius   圆角度数   0 圆形   非0代表磨圆弧角度数
     */
    public static void loadTransformImage(Context context, String url, int emptyResId, int errorResId, ImageView view, int radius){
        if(0 == radius){
            Glide.with(context.getApplicationContext()).load(url).transform(new CircleTransform(context)).placeholder(emptyResId).error(errorResId).into(view);
        }else{
            Glide.with(context.getApplicationContext()).load(url).transform(new RoundTransform(context)).placeholder(emptyResId).error(errorResId).into(view);
        }
    }

    public static void loadTransformImage(Context context, String url, ImageView view, int radius){
        if(0 == radius){
            Glide.with(context.getApplicationContext()).load(url).transform(new CircleTransform(context)).into(view);
        }else{
            Glide.with(context.getApplicationContext()).load(url).transform(new RoundTransform(context)).into(view);
        }
    }

    public static void loadTransformImage(Context context, String url, ImageView view){
        Glide.with(context.getApplicationContext()).load(url).transform(new RoundTransform(context)).into(view);
    }

    public static void blurBgPic(final Context context, final ImageView view, final String url, int defResId) {
        if (context == null || view == null) {
            return;
        }

        if (TextUtils.isEmpty(url)) {
            view.setImageResource(defResId);
        } else {
            Glide.with(context).load(url).asBitmap().placeholder(defResId).into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                            if (resource == null) {
                                return;
                            }

                            final Bitmap bitmap = blurBitmap(resource, context);
                            view.post(new Runnable() {
                                @Override
                                public void run() {
                                    view.setImageBitmap(bitmap);
                                }
                            });
                        }
                    });
        }
    }

    private static Bitmap blurBitmap(Bitmap resource, Context context) {
        Bitmap bitmap = Bitmap.createBitmap(resource.getWidth(), resource.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG);
        PorterDuffColorFilter filter =
                new PorterDuffColorFilter( Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);
        paint.setColorFilter(filter);
        canvas.drawBitmap(resource, 0, 0, paint);

        RenderScript rs = RenderScript.create(context.getApplicationContext());
        Allocation input = Allocation.createFromBitmap(rs, bitmap, Allocation.MipmapControl.MIPMAP_NONE,
                Allocation.USAGE_SCRIPT);
        Allocation output = Allocation.createTyped(rs, input.getType());
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        blur.setInput(input);
        blur.setRadius(10);
        blur.forEach(output);
        output.copyTo(bitmap);
        rs.destroy();

        return bitmap;
    }


}

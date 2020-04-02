package com.jaydroid.base_lib.utils;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * bitmap的工具类
 * 用于bitmap的转换，压缩
 *
 * @author zhanghao
 */
public class BitmapUtils {
    private static final String TAG = BitmapUtils.class.getSimpleName();

    /**
     * 期望宽度
     */
    private static int desiredWidth;

    /**
     * 期望高度
     */
    private static int desiredHeight;

    /**
     * Byte[]转Bitmap
     */
    public static Bitmap bytes2Bitmap(byte... data) {

        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    /**
     * Bitmap转Byte[]
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //100表示不压缩 60表示压缩40%
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * Bitmap转Drawable
     */
    @TargetApi(Build.VERSION_CODES.DONUT)
    public static Drawable bitmap2Drawable(Bitmap bitmap) {

        return new BitmapDrawable(null, bitmap);
    }

    /**
     * Drawable转Bitmap
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {

        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

    /**
     * 得到bitmap的大小
     *
     * @param bitmap 原位图
     * @return 位图大小
     */
    public static int getBitmapSize(Bitmap bitmap) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        // 在低版本中用一行的字节x高度
        return bitmap.getRowBytes() * bitmap.getHeight(); //earlier version
    }

    /**
     * 旋转图像
     *
     * @param bitmap  需要旋转的位图
     * @param degrees 旋转角度
     * @return 旋转后的位图
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {

        if (degrees != 0) {
            Matrix matrix = new Matrix();
            matrix.setRotate(degrees);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                    matrix, true);
        }
        return bitmap;
    }

    /**
     * bitmap保存到文件
     *
     * @param file   保存的目标文件
     * @param bitmap 需要保存的bitmap
     */
    public static void bitmap2File(File file, Bitmap bitmap) {

        try {
            if (!file.exists())
                file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmap2Bytes(bitmap));
            fos.flush();
            fos.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 计算图片最合适的压缩比例
     *
     * @param actualWidth   实际宽度
     * @param actualHeight  实际高度
     * @param desiredWidth  期望宽度
     * @param desiredHeight 期望高度
     * @return 最合适压缩比例
     */
    private static int findBestSampleSize(
            int actualWidth, int actualHeight, int desiredWidth, int desiredHeight) {

        double wr = (double) actualWidth / desiredWidth;
        double hr = (double) actualHeight / desiredHeight;
        double ratio = Math.min(wr, hr);
        float n = 1.0f;
        while ((n * 2) <= ratio) {
            n *= 2;
        }
        return (int) n;
    }

    /**
     * volley的图片处理源码
     * 计算合理的尺寸
     *
     * @param maxPrimary      主要纬度的最大值
     * @param maxSecondary    次要纬度的最大值
     * @param actualPrimary   主要纬度的实际值
     * @param actualSecondary 次要纬度的实际值
     * @return 计算后的尺寸（宽高）
     */
    private static int getResizedDimension(
            int maxPrimary, int maxSecondary, int actualPrimary, int actualSecondary) {
        // maxPrimary与maxSecondary没有指定，就返回actualPrimary
        if (maxPrimary == 0 && maxSecondary == 0) {
            return actualPrimary;
        }
        //maxPrimary没有指定，缩放率（ratio）是由次要纬度影响的，返回 maxPrimary*ratio
        if (maxPrimary == 0) {
            double ratio = (double) maxSecondary / (double) actualSecondary;
            return (int) (actualPrimary * ratio);
        }
        //maxSecondary没有指定，就返回maxPrimary
        if (maxSecondary == 0) {
            return maxPrimary;
        }

        double ratio = (double) actualSecondary / (double) actualPrimary;
        int resized = maxPrimary;
        if (resized * ratio > maxSecondary) {
            resized = (int) (maxSecondary / ratio);
        }
        return resized;
    }

    /**
     * 从Bytes中加载图片
     *
     * @param bytes     字节数组保存的图片
     * @param reqWidth  期望宽度
     * @param reqHeight 期望高度
     * @return 压缩后的bitmap
     */
    public static Bitmap decodeSampledBitmapFromBytes(
            byte[] bytes, int reqWidth, int reqHeight) {
        // 首先不加载图片,仅获取图片尺寸
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 当inJustDecodeBounds设为true时,不会加载图片仅获取图片尺寸信息
        options.inJustDecodeBounds = true;
        // 此时仅会将图片信息会保存至options对象内,decode方法不会返回bitmap对象
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        // 得到计算好的options，目标宽、目标高
        options = getBestOptions(options, reqWidth, reqHeight);
        // 得到压缩后的图片
        Bitmap src = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        return createScaleBitmap(src, desiredWidth, desiredHeight); // 进一步得到目标大小的缩略图;
    }

    /**
     * 从Resources中加载图片
     */
    public static Bitmap decodeSampledBitmapFromResource(
            Resources res, int resId, int reqWidth,
            int reqHeight) {
        // 首先不加载图片,仅获取图片尺寸
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 设置成了true,不占用内存，只获取bitmap宽高
        options.inJustDecodeBounds = true;
        // 此时仅会将图片信息会保存至options对象内,decode方法不会返回bitmap对象
        BitmapFactory.decodeResource(res, resId, options);
        // 得到计算好的options，目标宽、目标高
        options = getBestOptions(options, reqWidth, reqHeight);
        // 得到压缩后的图片
        Bitmap src = BitmapFactory.decodeResource(res, resId, options);
        return createScaleBitmap(src, desiredWidth, desiredHeight); // 进一步得到目标大小的缩略图
    }

    /**
     * 从SD卡上加载图片
     */
    public static Bitmap decodeSampledBitmapFromFile(String pathName, int reqWidth, int reqHeight) {
        // 首先不加载图片,仅获取图片尺寸
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 设置成了true,不占用内存，只获取bitmap宽高
        options.inJustDecodeBounds = true;
        // 此时仅会将图片信息会保存至options对象内,decode方法不会返回bitmap对象
        BitmapFactory.decodeFile(pathName, options);
        // 得到计算好的options，目标宽、目标高
        options = getBestOptions(options, reqWidth, reqHeight);
        // 得到压缩后的图片
        Bitmap src = BitmapFactory.decodeFile(pathName, options);
        return createScaleBitmap(src, desiredWidth, desiredHeight);// 进一步得到目标大小的缩略图
    }

    /**
     * 计算目标宽度，目标高度，inSampleSize
     *
     * @return BitmapFactory.Options对象
     */
    private static BitmapFactory.Options getBestOptions(
            BitmapFactory.Options options, int
            reqWidth, int reqHeight) {
        // 读取图片实际宽高
        int actualWidth = options.outWidth;
        int actualHeight = options.outHeight;
        // 获取期望宽高
        desiredWidth = getResizedDimension(reqWidth, reqHeight, actualWidth, actualHeight);
        desiredHeight = getResizedDimension(reqHeight, reqWidth, actualHeight, actualWidth);
        // 获取最合适的压缩比例
        options.inSampleSize = findBestSampleSize(actualWidth, actualHeight, desiredWidth,
                desiredHeight);
        // 当inJustDecodeBounds设为false时,decode方法就会返回图片对象
        options.inJustDecodeBounds = false;
        return options;
    }

    /**
     * 通过传入的bitmap，进行压缩，得到符合标准的bitmap
     */
    public static Bitmap createScaleBitmap(Bitmap tempBitmap, int desiredWidth, int desiredHeight) {
        // If necessary, scale down to the maximal acceptable size.
        if (tempBitmap != null && (tempBitmap.getWidth() > desiredWidth || tempBitmap.getHeight()
                > desiredHeight)) {
            // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响
            Bitmap bitmap = Bitmap.createScaledBitmap(tempBitmap, desiredWidth, desiredHeight,
                    true);
            tempBitmap.recycle(); // 释放Bitmap的native像素数组
            return bitmap;
        } else {
            // 如果没有缩放，那么不回收
            return tempBitmap;
        }
    }

    /**
     * bitmap转为base64
     *
     * @param bitmap  源图片
     * @param quality 压缩比
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap, int quality) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, quality, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data base64字符串
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {

        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 将两张位图拼接成一张(横向拼接)
     *
     * @param first
     * @param second
     * @return
     */
    public static Bitmap add2Bitmap(Bitmap first, Bitmap second) {

        int width = first.getWidth() + second.getWidth();
        int height = Math.max(first.getHeight(), second.getHeight());
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, first.getWidth(), 0, null);
        return result;
    }

    /**
     * 将两张位图拼接成一张(纵向拼接)
     *
     * @param first
     * @param second
     * @return
     */
    public static Bitmap add2BitmapVertical(Bitmap first, Bitmap second) {

        if (first == null || second == null) {
            return null;
        }
        int width = Math.max(first.getWidth(), second.getWidth());
        int height = first.getHeight() + second.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, 0, first.getHeight(), null);
        return result;
    }
}

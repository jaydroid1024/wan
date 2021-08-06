package com.jay.base_lib.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * 图片工具类
 *
 * @author zhanghao
 * @version 1.0
 */
public class ImageUtil {
    private static final String TAG = ImageUtil.class.getSimpleName();

    private static final int MAX_SIZE = 1024;

    /**
     * 相册返回bitmap
     */
    public static Bitmap getBitmapFromAlbum(Context context, Intent data) {

        if (data == null) {
            return null;
        }
        Uri uri = data.getData();
        int degrees = getOrientation(context, uri);

        ContentResolver content_resolver = context.getContentResolver();
        if ((content_resolver == null) || (uri == null)) {
            return null;
        }
        Bitmap bitmap;
        Cursor cursor = content_resolver.query(uri, null, null, null, null);
        if ((cursor == null) || (cursor.getCount() <= 0)) {
            try {
                return MediaStore.Images.Media.getBitmap(content_resolver, uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        cursor.moveToFirst();

        int i = cursor.getColumnIndex("_data");
        if (i < 0) {
            int j = cursor.getColumnIndex("document_id");
            String str = cursor.getString(j);
            if ((str == null) || (!str.contains("image:"))) {
                return null;
            }
            Uri image_uri = Uri.parse("content://media/external/images/media/" + str.substring(6));
            ContentResolver image_resolver = context.getContentResolver();
            if ((image_resolver == null) || (image_uri == null)) {
                return null;
            }
            cursor = image_resolver.query(image_uri, null, null, null, null);
            if ((cursor == null) || (cursor.getCount() <= 0)) {
                return null;
            }
            cursor.moveToFirst();
            i = cursor.getColumnIndex("_data");
            if (i < 0) {
                return null;
            }
            bitmap = getBitmapTransform("" + cursor.getString(i), MAX_SIZE);
        } else {
            bitmap = getBitmapTransform("" + cursor.getString(i), MAX_SIZE);
        }

        cursor.close();

        if (bitmap == null) {
            return null;
        }
        /*按照比例缩放*/
        if ((bitmap.getWidth() > MAX_SIZE) || (bitmap.getHeight() > MAX_SIZE)) {
            int width;
            int height;
            if (bitmap.getWidth() >= bitmap.getHeight()) {
                float scale = bitmap.getWidth() / (float) MAX_SIZE;
                width = MAX_SIZE;
                height = (int) (bitmap.getHeight() / scale);
            } else {
                float scale = bitmap.getHeight() / (float) MAX_SIZE;
                width = (int) (bitmap.getWidth() / scale);
                height = MAX_SIZE;
            }
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height);
        }
        return BitmapUtils.rotateBitmap(bitmap, degrees);
    }

    /**
     * 相机拍照返回bitmap
     */
    public static Bitmap getBitmapFromCamera(String filePath) {

        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        int degrees = getDegrees(getOrientation(filePath));

        Bitmap bitmap = getBitmapTransform(filePath, MAX_SIZE);
        if (bitmap == null) {
            return null;
        }
        if ((bitmap.getWidth() > MAX_SIZE) || (bitmap.getHeight() > MAX_SIZE)) {
            int width;
            int height;
            if (bitmap.getWidth() >= bitmap.getHeight()) {
                float scale = bitmap.getWidth() / (float) MAX_SIZE;
                width = MAX_SIZE;
                height = (int) (bitmap.getHeight() / scale);
            } else {
                float scale = bitmap.getHeight() / (float) MAX_SIZE;
                width = (int) (bitmap.getWidth() / scale);
                height = MAX_SIZE;
            }
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height);
        }

        return BitmapUtils.rotateBitmap(bitmap, degrees);
    }

    /**
     * 压缩图片
     */
    public static Bitmap getBitmapTransform(String filePath, int paramInt) {

        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;
        int i = option.outHeight / paramInt;
        if (i < 0) {
            i = 1;
        }
        option.inJustDecodeBounds = false;
        option.inSampleSize = i;
        return BitmapFactory.decodeFile(filePath, option);
    }

    /**
     * 压缩图片
     */
    public static Bitmap getBitmapTransform(byte[] data, int paramInt) {

        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;
        int i = option.outHeight / paramInt;
        if (i < 0) {
            i = 1;
        }
        option.inJustDecodeBounds = false;
        option.inSampleSize = i;
        return BitmapFactory.decodeByteArray(data, 0, data.length, option);
    }

    /**
     * 取得图片的原始方向
     */
    public static int getOrientation(String filePath) {

        try {
            return new ExifInterface(filePath)
                    .getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 取得图片的原始方向
     */
    public static int getOrientation(Context context, Uri photoUri) {

        int orientation = 0;
        Cursor cursor =
                context
                        .getContentResolver()
                        .query(
                                photoUri,
                                new String[]{MediaStore.Images.ImageColumns.ORIENTATION},
                                null,
                                null,
                                null);
        if (cursor != null) {
            if (cursor.getCount() != 1) {
                return -1;
            }
            cursor.moveToFirst();
            orientation = cursor.getInt(0);
            cursor.close();
        }
        return orientation;
    }

    /**
     * 取得图片该旋转的角度
     */
    public static int getDegrees(int orientation) {

        int degrees;
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                degrees = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                degrees = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                degrees = 270;
                break;
            default:
                degrees = 0;
                break;
        }
        return degrees;
    }

    /**
     * 图片添加文字
     */
    public static Bitmap drawTextToBitmap(Context context, Bitmap bitmap, String text) {

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setTextSize(DensityUtils.dip2px(10));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        Bitmap.Config bitmapConfig = bitmap.getConfig();
        paint.setDither(true);
        paint.setFilterBitmap(true);
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }

        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawText(text, 0, bitmap.getHeight() - 5, paint);
        return bitmap;
    }

    /**
     * 通过Uri获取文件
     *
     * @param context
     * @param uri
     * @return
     */
    public static File getFileFromMediaUri(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        switch (uri.getScheme()) {
            case "content":
                return getFileFromContentUri(context, uri);
            case "file":
                // new File(uri.toString().replace("file://", ""));
                return new File(uri.getPath());
            default:
                return null;
        }
    }

    public static File getFileFromContentUri(Context context, Uri uri) {
        String filePath;
        ContentResolver cr = context.getContentResolver();
        // 根据Uri从数据库中找
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            // 获取图片路径
            int i = cursor.getColumnIndex("_data");
            if (i < 0) {
                int j = cursor.getColumnIndex("document_id");
                String str = cursor.getString(j);
                if ((str == null) || (!str.contains("image:"))) {
                    return null;
                }
                Uri image_uri = Uri.parse("content://media/external/images/media/" + str.substring(6));
                ContentResolver image_resolver = context.getContentResolver();
                if ((image_resolver == null) || (image_uri == null)) {
                    return null;
                }
                cursor = image_resolver.query(image_uri, null, null, null, null);
                if ((cursor == null) || (cursor.getCount() <= 0)) {
                    return null;
                }
                cursor.moveToFirst();
                i = cursor.getColumnIndex("_data");
                if (i < 0) {
                    return null;
                }
                filePath = cursor.getString(i);
            } else {
                filePath = cursor.getString(i);
            }
            Log.d(TAG, "getBitmapFromAlbum filePath:" + cursor.getString(i));
            Log.d(TAG, "getBitmapFromAlbum uri:" + uri);
            cursor.close();
            if (filePath != null) {
                return new File(filePath);
            }
        }
        return null;
    }
}

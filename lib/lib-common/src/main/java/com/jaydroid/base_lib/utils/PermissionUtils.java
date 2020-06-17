package com.jaydroid.base_lib.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 * 权限设置相关工具类
 *
 * @author zhanghao
 * @version 1.0
 * @date 2019-11-26 19:00
 */
public class PermissionUtils {

    /**
     * 跳转到权限设置
     *
     * @param activity 上下文
     */
    public static void openPermissionSetting(Activity activity) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            openSystemConfig(activity);
        } else {
            try {
                openApplicationInfo(activity);
            } catch (Exception e) {
                e.printStackTrace();
                openSystemConfig(activity);
            }
        }
    }

    /**
     * 应用信息界面
     *
     * @param activity 上下文
     */
    public static void openApplicationInfo(Activity activity) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        activity.startActivity(localIntent);
    }

    /**
     * 系统设置界面
     *
     * @param activity 上下文
     */
    public static void openSystemConfig(Activity activity) {
        try {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

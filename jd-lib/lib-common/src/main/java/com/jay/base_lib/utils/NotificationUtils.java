package com.jay.base_lib.utils;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationManagerCompat;

/**
 * 通知栏权限相关工具类
 *
 * @author zhanghao
 * @version 1.0
 * @date 2019-11-26 19:00
 */
public class NotificationUtils {

    /**
     * 检测是否有通知栏权限
     *
     * @param context 上下文
     * @return true/false
     */
    public static boolean checkNotificationEnabled(Context context) {
        return NotificationManagerCompat.from(context.getApplicationContext())
                .areNotificationsEnabled();
    }

    /**
     * 跳转到通知权限设置页
     *
     * @param activity 上下文
     */
    public static void openPush(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 这种方案适用于 API 26, 即8.0（含8.0）以上可以用
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, activity.getPackageName());
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, activity.getApplicationInfo().uid);
            activity.startActivity(intent);
        } else {
            PermissionUtils.openPermissionSetting(activity);
        }
    }

    /**
     * 创建通知通道
     *
     * @param context     上下文
     * @param channelID   通道ID
     * @param channelName 通道名称
     */
    public static void createNotificationChannel(
            Context context, String channelID, String channelName) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_NONE);
            channel.setSound(null, null);
            channel.setVibrationPattern(null);

            NotificationManager manager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }
}

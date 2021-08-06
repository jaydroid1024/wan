package com.jay.base_lib.utils;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

/**
 * @author zhanghao
 * @version 1.0
 */
public class LocationUtils {
    /**
     * 手机定位服务是否开启
     *
     * @return true/false
     */
    public static boolean isOpen(Context context) {

        return isGPS(context) || isNetwork(context);
    }

    /**
     * App定位权限是否开启
     *
     * @return true/false
     */
    public static boolean isAppOpen(Context context) {

        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission
                .ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * NETWORK是否打开
     *
     * @return true/false
     */
    private static boolean isNetwork(Context context) {

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // NETWORK_PROVIDER : 通过WLAN或移动网络(3G/2G)
        // 确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /**
     * GPS是否打开
     *
     * @return true/false
     */
    private static boolean isGPS(Context context) {

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // GPS_PROVIDER : 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 强制帮用户打开GPS
     *
     * @param context
     */
    public static void openGPS(Context context) throws PendingIntent.CanceledException {

        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
    }

    /**
     * 打开手机定位设置界面
     *
     * @param activity 上下文
     */
    public static void openSetting(Activity activity, int requestCode) {

        // 转到手机设置界面，用户设置GPS
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivityForResult(intent, requestCode); // 设置完成后返回到原来的界面
    }

}

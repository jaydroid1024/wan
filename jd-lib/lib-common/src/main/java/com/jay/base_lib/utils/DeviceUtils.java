package com.jay.base_lib.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.UUID;

/**
 * Created by zhanghao on 2017-08-13 下午4:09.
 * <p>
 * # # # # # # # # # # # # # # # # # # # # # # # # # # #
 * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #                                                   #
 * # # # # # # # # # # # # # # # # # # # # # # # # # # #
 */
public class DeviceUtils {
    private static final String DEVICE_UNIQUE_ID = "device_unique_id";

    /**
     * 判断是否平板设备
     *
     * @param context 上下文
     * @return true:平板,false:手机
     */
    public static boolean isTablet(Context context) {

        return (context.getResources().getConfiguration().screenLayout & Configuration
                .SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 获取设备唯一标识
     */
    public static String getDeviceUniqueID(Context context) {
        //从SharedPreferences文件中获取数据
        Object object = SPUtils.get(context, DEVICE_UNIQUE_ID, "");
        //判断是否为null
        if (object == null) {
            //若为null,生成一个唯一标识
            return getUUID(context);
        } else {
            //若不为null,需判断是否为空字符串
            String device_unique_id = object.toString();
            if (TextUtils.isEmpty(device_unique_id)) {
                //若为空字符串,生成一个唯一标识
                return getUUID(context);
            }
            //若不为空字符串,直接返回
            return device_unique_id;
        }
    }

    /**
     * 已android_id,device_id,UUID为基础生成一个唯一标识
     *
     * @param context 上下文
     * @return 字符串
     */
    private static String getUUID(Context context) {
        //获取Android_id
        String uuid = getAndroidID(context);
        //判断Android_id是否为空并且Android_id是否等于"9774d56d682e549c"
        if (TextUtils.isEmpty(uuid) || "9774d56d682e549c".equals(uuid)) {
            //获取Device_id
            uuid = getIMEI(context);
            //判断Device_id是否为空
            if (TextUtils.isEmpty(uuid)) {
                //生成随机UUID
                uuid = UUID.randomUUID().toString();
            } else {
                //已Device_id生成一个UUID
                uuid = UUID.nameUUIDFromBytes(uuid.getBytes()).toString();
            }
        } else {
            //否则,已Android_id生成一个UUID
            uuid = UUID.nameUUIDFromBytes(uuid.getBytes()).toString();
        }
        SPUtils.put(context, DEVICE_UNIQUE_ID, uuid);
        return uuid;
    }

    /**
     * 获取android_id
     *
     * @return android_id
     */
    private static String getAndroidID(Context context) {

        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取IMEI(device_id)
     * 需要添加权限  android.permission.READ_PHONE_STATE
     *
     * @return IMEI
     */
    private static String getIMEI(Context context) {

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context
                .TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 获取IMSI
     * 需要添加权限  android.permission.READ_PHONE_STATE
     *
     * @return IMSI
     */
    private static String getIMSI(Context context) {

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context
                .TELEPHONY_SERVICE);
        return tm.getSubscriberId();
    }
}

package com.jaydroid.base_lib.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * 打电话、发短信工具类
 *
 * @author zhanghao
 * @version 1.0
 */
public class PhoneAndSMSUtils {
    /**
     * 打电话
     *
     * @param context      上下文
     * @param phone_number 电话号码
     */
    public static void callPhone(Context context, String phone_number) {

        if (TextUtils.isEmpty(phone_number)) {
            ToastUtils.showLong("号码不能为空!");
            return;
        }

        try {
            context.startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone_number.trim())));
        } catch (Exception exception) {
            exception.printStackTrace();
            ToastUtils.showLong("无法拨打电话！");
        }
    }

    /**
     * 调用系统拨号界面
     *
     * @param context      上下文
     * @param phone_number 电话号码
     */
    public static void callSysPhoneUI(Context context, String phone_number) {

        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.DIAL");
            intent.setData(Uri.parse("tel:" + phone_number));
            context.startActivity(intent);
        } catch (Exception exception) {
            exception.printStackTrace();
            ToastUtils.showLong("无法拨打电话！");
        }
    }

    /**
     * 调用系统发送短信界面
     *
     * @param context      上下文
     * @param phone_number 电话号码
     */
    public static void callSysSMSUI(Context context, String phone_number) {

        Uri uri = Uri.parse("smsto:" + phone_number);
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(sendIntent);
    }
}

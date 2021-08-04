/**
 * Created by zhanghao on 2016-07-29 上午11:05.
 * <p/>
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
package com.jaydroid.base_lib.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * @author zhanghao
 * @version 1.0
 */
public class AlarmManagerUtils {
    private static final int REQUEST_CODE = 0;

    /**
     * 设置定时器
     *
     * @param context         上下文
     * @param triggerAtMillis 开始时间
     * @param intervalMillis  轮询间隔时间
     * @param isRepeate       是否重置
     */
    public static void setAlarmTime(
            Context context, long triggerAtMillis, long intervalMillis, boolean isRepeate, Intent
            intent) {

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getService(context, REQUEST_CODE, intent, PendingIntent
                .FLAG_UPDATE_CURRENT);
        //sdk19之后,AlarmManager无法准确的循环定时执行任务
        //需自己写循环执行任务
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pi);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pi);
        } else {
            if (isRepeate) {
                am.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, intervalMillis, pi);
            }
        }
    }

    /**
     * 取消定时器
     *
     * @param context 上下文
     */
    public static void cancelAlarm(Context context, Intent intent) {

        PendingIntent pi = PendingIntent.getService(context, REQUEST_CODE, intent, PendingIntent
                .FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pi);
    }

}

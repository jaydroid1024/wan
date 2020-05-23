package com.jaydroid.base_lib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期时间工具类
 *
 * @author zhanghao
 * @date 2014-12-19
 */
@SuppressLint("SimpleDateFormat")
public class DateUtils {
    /**
     * 日期的格式
     */
    public static final String DATE_FORMAT = "yyyy/MM/dd HH:mm";
    public static final String WEEK_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
    public static final String YEAR_FORMAT = "yyyyMMdd";
    public static final String YEAR_MONTH_DATE_FORMAT = "yyyy.MM.dd";
    public static final String YEAR_MONTH_DATE_SUB_LINE_FORMAT = "yyyy-MM-dd";
    /**
     * 20分钟
     */
    public static final long date20 = 20 * 60 * 1000L;
    /**
     * 10分钟
     */
    public static final long date10 = 10 * 60 * 1000L;
    /**
     * 1天
     */
    public static final long DAY_1 = 24 * 60 * 60 * 1000L;
    /**
     * 三个月
     */
    public static final long MONTH_3 = 3 * 30 * DAY_1;
    /**
     * 1h
     */
    public static final long HOUR_1 = 60 * 60 * 1000L;
    /**
     * 1s
     */
    public static final long SECOND_1 = 1000L;
    /**
     * 60s
     */
    public static final long SECOND_60 = 60 * 1000L;
    private static final String TAG = DateUtils.class.getSimpleName();
    private static final String DATE_SECOND_FORMAT = "yyyy-MM-dd " + "HH:mm:ss";
    // 精确到分
    public static SimpleDateFormat DATE_TIME_MINUTE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd " + "HH:mm");
    // 精确到毫秒
    public static SimpleDateFormat DATE_TIME_MS_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd " + "HH" + ":mm:ss.SSS");
    private static SimpleDateFormat todayFormat = new SimpleDateFormat("dd");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("M月d日");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat dayFormat = new SimpleDateFormat("M月d日 HH:mm");
    private static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy年M月d日 HH:mm");
    private static SimpleDateFormat yearMonthDayFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    private static SimpleDateFormat yearDateFormat = new SimpleDateFormat("yyyy年M月d日");
    private static SimpleDateFormat yearDateWithoutFormat = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat yearMonthFormat = new SimpleDateFormat("yyyy年M月");
    // ISO8601 精确到毫秒
    private static DateFormat ISO_8601_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss" + "" + ".SSSZ");
    // 精确到秒
    private static SimpleDateFormat ISO_8601_SECOND_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd'T" + "'HH:mm:ss.SSS");
    // ISO8601 精确到秒
    private static SimpleDateFormat ISO_8601_SECOND_FORMAT_1 =
            new SimpleDateFormat("yyyy-MM-dd'T" + "'HH:mm:ss");
    // 精确到秒
    private static SimpleDateFormat DATE_TIME_SECOND_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd " + "HH:mm:ss");

    /**
     * 将时间字符串装换成指定格式的Date对象
     *
     * @param dateString
     * @return long
     */
    public static Date stringToDate(String dateString) {

        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date dateValue = simpleDateFormat.parse(dateString, position);
        return dateValue;
    }

    /**
     * 将时间字符串装换成指定格式的Date对象
     *
     * @param dateString
     * @return long
     */
    public static Date dateTimeSencondFormatStringToDate(String dateString) {

        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_SECOND_FORMAT);
        Date dateValue = simpleDateFormat.parse(dateString, position);
        return dateValue;
    }

    /**
     * 将毫秒数转换成指定格式时间
     *
     * @param currentTime
     * @return String
     */
    public static String getCurrentTimeAsFormat(long currentTime) {

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.format(currentTime);
    }

    /**
     * 将毫秒数转换成指定格式时间
     *
     * @param currentTime
     * @return String
     */
    public static String getCurrentTimeAsFormat(long currentTime, String pattern) {

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(currentTime);
    }

    /**
     * 将指定时间戳转换成"yyyy-MM-dd HH:mm:ss"格式时间
     *
     * @param currentTime
     * @return
     */
    public static String getCurrentTimeToSecendAsFormat(long currentTime) {

        return DATE_TIME_SECOND_FORMAT.format(currentTime);
    }

    /**
     * 获取当前日期之前后的时间
     *
     * @param beforeDays (-1:前一天 1：后一天)
     * @return 返回MM月dd日格式的日期字符串
     */
    public static String getDateString(int beforeDays) {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, beforeDays);
        return dateFormat.format(c.getTime());
    }

    /**
     * 获取当前日期之前后的时间
     *
     * @param beforeDays (-1:前一天 1：后一天)
     * @return 返回MM月dd日格式的日期字符串
     */
    public static String getYearMonthDateString(int beforeDays) {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, beforeDays);
        return yearDateFormat.format(c.getTime());
    }

    /**
     * 获取当前日期之前后的时间
     *
     * @param beforeDays (-1:前一天 1：后一天)
     * @return 返回MM月dd日格式的日期字符串
     */
    public static String getYearMonthDateWithoutString(int beforeDays) {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, beforeDays);
        return yearDateWithoutFormat.format(c.getTime());
    }

    /**
     * 获取当前日期之前后的时间
     *
     * @param beforeDays (-1:前一天 1：后一天)
     * @return 返回MM月dd日格式的日期字符串
     */
    public static String getYearMonthDateString(
            int beforeDays, String date_str, String date_format_str) {
        // 初始化日期格式
        SimpleDateFormat date_format = new SimpleDateFormat(date_format_str);
        try {
            Date date = date_format.parse(date_str);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            cal.add(Calendar.DATE, beforeDays);
            return yearDateFormat.format(cal.getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取当前日期之前后的时间
     *
     * @param beforeDays (-1:前一天 1：后一天)
     * @return 返回日期数值
     */
    public static long getDateLong(int beforeDays) {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, beforeDays);
        return c.getTimeInMillis();
    }

    /**
     * 获取当前日期之前后的时间
     *
     * @param beforeDays  -1:前一天 1：后一天
     * @param currentTime 当前时间
     * @return
     */
    public static Date getDateLong(int beforeDays, long currentTime) {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTimeInMillis(currentTime);
        c.add(Calendar.DATE, beforeDays);
        return c.getTime();
    }

    /**
     * 获取当前日期之前后的时间
     *
     * @param beforeMonth (-1:前一月 1：后一月)
     * @return 返回MM月dd日格式的日期字符串
     */
    public static String getMonthString(int beforeMonth) {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, beforeMonth);
        return yearMonthFormat.format(c.getTime());
    }

    /**
     * 获取当前日期
     *
     * @return 返回dd日格式的日期字符串
     */
    public static String getDateString(String today) {

        return todayFormat.format(today);
    }

    /**
     * 获取当前日期
     *
     * @return 返回MM月dd日格式的日期字符串
     */
    public static String getCurrentDateString() {

        return dateFormat.format(new Date());
    }

    /**
     * 获取当前年月
     *
     * @return 返回yyyy年MM月格式的日期字符串
     */
    public static String getCurrentYearMonthString() {

        return yearMonthFormat.format(new Date());
    }

    /**
     * 获取当前年月日
     *
     * @return 返回yyyy年MM月dd日格式的日期字符串
     */
    public static String getCurrentYearDateString() {

        return yearDateFormat.format(new Date());
    }

    /**
     * 获取当前年月日时分秒
     *
     * @return 返回yyyy年MM月dd日格式的日期字符串
     */
    public static String getCurrentYearMonthDayDateString() {

        return yearMonthDayFormat.format(new Date());
    }

    /**
     * 获取当前日期是哪一分钟
     *
     * @return 当前分钟
     */
    public static int getCurrentMinute() {

        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MINUTE);
    }

    /**
     * 获取当前日期是哪一小时
     *
     * @return 当前小时
     */
    public static int getCurrentHourOftDay() {

        Calendar c = Calendar.getInstance();
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当前日期是哪一天
     *
     * @return 当前天
     */
    public static int getCurrentDay() {

        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前日期是哪一月
     *
     * @return 当前月
     */
    public static int getCurrentMonth() {

        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前日期是哪一年
     *
     * @return 当前年
     */
    public static int getCurrentYear() {

        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    /**
     * 将毫秒数转换成指定的时间（HH:mm）
     */
    public static String millisecondToTime(long millis) {

        if (String.valueOf(millis).length() == 10) {
            millis *= SECOND_1;
        }
        Calendar c = Calendar.getInstance();
        int currentDay = c.get(Calendar.DAY_OF_MONTH);
        c.setTimeInMillis(millis);
        int day = c.get(Calendar.DAY_OF_MONTH);
        if (day != currentDay) {
            return dayFormat.format(c.getTime());
        }
        return "今天 " + timeFormat.format(c.getTime());
    }

    /**
     * 获取设备的当前时间
     *
     * @return long
     */
    public static long getCurrentTime() {

        Date curDate = new Date(System.currentTimeMillis()); // 获取当前时间
        return curDate.getTime();
    }

    /**
     * 获取设备的当前时间 UTC
     *
     * @return long
     */
    public static String getCurrentTimeUTC() {

        Date curDate = new Date(System.currentTimeMillis()); // 获取当前时间
        return formatISO8601(curDate);
    }

    /**
     * 时间戳转字符串换成年月日
     *
     * @param time 时间戳
     * @return 时间格式字符串
     */
    public static String getStringToYear(String time) {

        if (TextUtils.isEmpty(time)) {
            return "";
        }
        int size = time.length();
        long t = Long.parseLong(time);
        if (size == 10) {
            t = t * SECOND_1;
        }
        Date d = new Date(t);
        return yearFormat.format(d);
    }

    /**
     * 时间戳转字符串换成月日
     *
     * @param time 时间戳
     * @return 时间格式字符串
     */
    public static String getStringToDate(String time) {

        if (TextUtils.isEmpty(time)) {
            return "";
        }
        int size = time.length();
        long t = Long.parseLong(time);
        if (size == 10) {
            t = t * SECOND_1;
        }
        Date d = new Date(t);
        return dayFormat.format(d);
    }

    /**
     * 根据自动格式日期转换成只有日
     *
     * @param date_str        日期字符串
     * @param date_format_str 日期格式
     * @return 加1后的日期字符串
     */
    public static String stringDateOfWeek(String date_str, String date_format_str) {
        // 初始化日期格式
        SimpleDateFormat date_format = new SimpleDateFormat(date_format_str);
        try {
            Date date = date_format.parse(date_str);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            String mWay = String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
            if ("1".equals(mWay)) {
                mWay = "天";
            } else if ("2".equals(mWay)) {
                mWay = "一";
            } else if ("3".equals(mWay)) {
                mWay = "二";
            } else if ("4".equals(mWay)) {
                mWay = "三";
            } else if ("5".equals(mWay)) {
                mWay = "四";
            } else if ("6".equals(mWay)) {
                mWay = "五";
            } else if ("7".equals(mWay)) {
                mWay = "六";
            }
            return "星期" + mWay;
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取WEEK
     *
     * @return String
     */
    public static String getWeek() {

        Calendar cal = Calendar.getInstance();
        String week = "";
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                week = "天";
                break;
            case Calendar.MONDAY:
                week = "一";
                break;
            case Calendar.TUESDAY:
                week = "二";
                break;
            case Calendar.WEDNESDAY:
                week = "三";
                break;
            case Calendar.THURSDAY:
                week = "四";
                break;
            case Calendar.FRIDAY:
                week = "五";
                break;
            case Calendar.SATURDAY:
                week = "六";
                break;
        }
        return "星期" + week;
    }

    /**
     * 获取DAY
     *
     * @return 加1后的日期字符串
     */
    public static int getDay() {

        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 根据自动格式日期转换成只有日
     *
     * @param date_str        日期字符串
     * @param date_format_str 日期格式
     * @return 加1后的日期字符串
     */
    public static String stringDateToDate(String date_str, String date_format_str) {
        // 初始化日期格式
        SimpleDateFormat date_format = new SimpleDateFormat(date_format_str);
        try {
            Date date = date_format.parse(date_str);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            // cal.add(Calendar.DATE, beforeDays);
            return todayFormat.format(cal.getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 输入的是String，格式诸如20120102，实现加一天的功能，返回的格式为String，诸如20120103
     */
    public static String stringDatePlus(String row) throws ParseException {

        String year = row.substring(0, 4);
        String month = row.substring(4, 6);
        String day = row.substring(6);
        String date1 = year + "-" + month + "-" + day;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(date1);
        Calendar cd = Calendar.getInstance();
        cd.setTime(startDate);
        cd.add(Calendar.DATE, 1);
        String dateStr = sdf.format(cd.getTime());
        String year1 = dateStr.substring(0, 4);
        String month1 = dateStr.substring(5, 7);
        String day1 = dateStr.substring(8);
        return year1 + month1 + day1;
    }

    /**
     * 根据自动格式日期自动加1
     *
     * @param date_str        日期字符串
     * @param date_format_str 日期格式
     * @return 加1后的日期字符串
     */
    public static String stringDatePlus(String date_str, String date_format_str) {
        // 初始化日期格式
        SimpleDateFormat date_format = new SimpleDateFormat(date_format_str);
        try {
            Date date = date_format.parse(date_str);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            // 日期自动加1
            cal.add(Calendar.DATE, 1);

            return date_format.format(cal.getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 根据自动格式日期自动加1
     *
     * @param date_str        日期字符串
     * @param date_format_str 日期格式
     * @return 加1后的日期字符串
     */
    public static String stringDatePlus(String date_str, String date_format_str, int before_days) {
        // 初始化日期格式
        SimpleDateFormat date_format = new SimpleDateFormat(date_format_str);
        try {
            Date date = date_format.parse(date_str);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            // 日期自动加1
            cal.add(Calendar.DATE, before_days);

            return date_format.format(cal.getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 输入的是String，格式诸如20120102，实现减一天的功能，返回的格式为String，诸如20120101
     */
    public static String stringDateDecrease(String row) throws ParseException {

        String year = row.substring(0, 4);
        String month = row.substring(4, 6);
        String day = row.substring(6);
        String date1 = year + "-" + month + "-" + day;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(date1);
        Calendar cd = Calendar.getInstance();
        cd.setTime(startDate);
        cd.add(Calendar.DATE, -1);
        String dateStr = sdf.format(cd.getTime());
        String year1 = dateStr.substring(0, 4);
        String month1 = dateStr.substring(5, 7);
        String day1 = dateStr.substring(8);
        return year1 + month1 + day1;
    }

    /**
     * 输入的格式为String，诸如20120101，返回的格式为String，诸如2012-01-01
     */
    public static String stringDateChange(String date) {

        if (date == null) {
            return "";
        }
        if (date.length() == "20120101".length()) {
            String year = date.substring(0, 4);
            String month = date.substring(4, 6);
            String day = date.substring(6);
            return year + "-" + month + "-" + day;
        } else {
            return date;
        }
    }

    /**
     * 日期向后推一天
     *
     * @param date 格式：20120101
     * @return 20120102
     */
    public static String tonextday(String date) {

        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day + 1);
        Date newdate = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String da = format.format(newdate);
        return da;
    }

    /**
     * 获取当前日期上一周的开始日期 （周日）
     */
    public static String previousWeekByDate(String date) {

        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK); // 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.SUNDAY); // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int s = cal.get(Calendar.DAY_OF_WEEK); // 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - s); // 根据日历的规则，给当前日期减往星期几与一个星期第一天的差值
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        String imptimeBegin = sdf.format(cal.getTime());
        //      System.out.println("所在周星期日的日期："+imptimeBegin);
        return imptimeBegin;
    }

    /**
     * 获取当前日期上一周的结束日期 （周六）
     */
    public static String previousWeekEndDayByDate(String date) {

        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK); // 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.SUNDAY); // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int s = cal.get(Calendar.DAY_OF_WEEK); // 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() + (6 - s));
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        String imptimeBegin = sdf.format(cal.getTime());
        //      System.out.println("星期六的日期："+imptimeBegin);
        return imptimeBegin;
    }

    /**
     * 获取当前日期当前一周的开始日期 （周日）
     */
    public static String getCurrentWeekFirstDayByDate(String date) {

        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK); // 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.SUNDAY); // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int s = cal.get(Calendar.DAY_OF_WEEK); // 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - s); // 根据日历的规则，给当前日期减往星期几与一个星期第一天的差值

        String imptimeBegin = sdf.format(cal.getTime());
        //  System.out.println("所在周星期日的日期："+imptimeBegin);
        return imptimeBegin;
    }

    /**
     * 获取当前日期当前一周的结束日期 （周六）
     */
    public static String getCurrentWeekEndDayByDate(String date) {

        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK); // 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.SUNDAY); // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int s = cal.get(Calendar.DAY_OF_WEEK); // 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() + (6 - s));

        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }

    /**
     * 返回上一个月的第一天
     *
     * @return 20120201
     */
    public static String previousMonthByDate(String date) {

        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 2, 1);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        String imptimeBegin = sdf.format(cal.getTime());
        //      System.out.println(imptimeBegin);
        return imptimeBegin;
    }

    /**
     * 返回下一个月的第一天
     *
     * @return 20120401
     */
    public static String nextMonthByDate(String date) {

        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        String imptimeBegin = sdf.format(cal.getTime());
        //      System.out.println(imptimeBegin);
        return imptimeBegin;
    }

    /**
     * 返回当前月的第一天
     *
     * @return 20120101
     */
    public static String getCurrentMonthFirstDayByDate(String date) {

        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }

    /**
     * 获取当天的起始时间 例如：2014-11-25 00:00:00
     *
     * @return long
     */
    public static Long getStartTime() {

        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime().getTime();
    }

    /**
     * 获取当天的结束时间 例如:2014-11-25 23:59:59
     *
     * @return long
     */
    public static Long getEndTime() {

        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime().getTime();
    }

    /**
     * 获取前一天的起始时间
     */
    public static Long getBeforeStartTime() {

        return getStartTime() - DAY_1;
    }

    /**
     * 获取给定时间的前N天时间
     *
     * @param startTime  开始时间
     * @param beforeDays 前N天
     * @return 给定时间的前N天时间
     */
    public static Long getBeforeStartTime(long startTime, int beforeDays) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startTime);
        return calendar.getTime().getTime() - beforeDays * 24 * 60 * 60 * 1000L;
    }

    /**
     * 获取前一天的结束时间
     */
    public static Long getBeforeEndTime() {

        return getEndTime() - DAY_1;
    }

    /**
     * 当前时间转换成UTC ISO8601时间
     *
     * @param date 时间
     * @return UTC ISO8601格式字符串
     */
    public static String formatISO8601(Date date) {

        return ISO_8601_FORMAT.format(date);
    }

    /**
     * UTC时间转换成GMT时间
     *
     * @param date UTC时间字符串
     * @return GMT时间字符串
     */
    public static String formatSecond(String date) {

        try {
            ISO_8601_SECOND_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
            return DATE_TIME_SECOND_FORMAT.format(ISO_8601_SECOND_FORMAT.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * UTC时间转换成GMT时间 秒
     *
     * @param date UTC时间字符串
     * @return GMT时间字符串
     */
    public static String formatUTCToGMTSecond(String date) {

        try {
            ISO_8601_SECOND_FORMAT_1.setTimeZone(TimeZone.getTimeZone("GMT"));
            return DATE_TIME_SECOND_FORMAT.format(ISO_8601_SECOND_FORMAT_1.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * UTC时间转换成GMT时间
     *
     * @param date UTC时间字符串
     * @return Date
     */
    public static Date formatUTCToGMTDate(String date) {

        try {
            ISO_8601_SECOND_FORMAT_1.setTimeZone(TimeZone.getTimeZone("GMT"));
            return ISO_8601_SECOND_FORMAT_1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 精确到秒,并转换成北京标准时间
     *
     * @param date_str 时间字符串
     * @return 精确到秒的时间字符串
     */
    public static String formatMsToSecond(String date_str) {

        L.d(TAG, date_str);
        try {
            DATE_TIME_SECOND_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
            return yearFormat.format(DATE_TIME_SECOND_FORMAT.parse(date_str));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 精确到秒,并转换成北京标准时间
     *
     * @param date_str 时间字符串
     * @return 精确到秒的时间字符串
     */
    public static long formatStringToTime(String date_str) {

        L.d(TAG, date_str);
        try {
            DATE_TIME_SECOND_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
            return DATE_TIME_SECOND_FORMAT.parse(date_str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * ISO 8601格式字符串转换成Date
     *
     * @param iso8601 ISO 8601格式字符串
     * @return Date
     */
    public static String parseISO8601(String iso8601) {

        if (TextUtils.isEmpty(iso8601)) {
            return "";
        }

        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        ISO_8601_SECOND_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return df.format(ISO_8601_SECOND_FORMAT.parse(iso8601));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * ISO 8601格式字符串转换成Date
     *
     * @param iso_8601  ISO 8601格式字符串
     * @param outFormat 指定时间格式
     * @return Date
     */
    public static String parseISO8601(@Nullable String iso_8601, @Nullable String outFormat) {

        if (TextUtils.isEmpty(iso_8601) || TextUtils.isEmpty(outFormat)) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(outFormat);
        try {
            return df.format(ISO8601Utils.parse(iso_8601, new ParsePosition(0)));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 时间字符串转换成指定格式字符串
     *
     * @param dateStr    dateStr
     * @param currFormat 当前格式
     * @param outFormat  输出格式
     * @return String
     */
    public static String parseDateStr(String dateStr, String currFormat, String outFormat) {

        SimpleDateFormat currDf = new SimpleDateFormat(currFormat);
        SimpleDateFormat outDf = new SimpleDateFormat(outFormat);
        try {
            return outDf.format(currDf.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 日期比较 1:大于 0:等于 -1:小于
     *
     * @param date_1      日期
     * @param date_2      日期
     * @param date_format 日期格式
     * @return int
     */
    public static int compareDate(String date_1, String date_2, String date_format) {

        SimpleDateFormat df = new SimpleDateFormat(date_format);
        try {
            return df.parse(date_1).compareTo(df.parse(date_2));
        } catch (ParseException e) {
            return 0;
        }
    }

    public static String getCurrentYMD() {

        return yearDateWithoutFormat.format(new Date());
    }

    /**
     * 获取倒计时
     *
     * @param startTime 开始时间
     * @param endDate   倒计时截止时间
     * @return 倒计时
     */
    public static long getCountdownLong(long startTime, @NonNull String endDate, long clockSyncTime) {

        try {
            long countdownTime =
                    ISO8601Utils.parse(endDate, new ParsePosition(0)).getTime() - startTime - clockSyncTime;
            return countdownTime > 0 ? countdownTime : countdownTime;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getCountdownStr(String planDate) {

        if (TextUtils.isEmpty(planDate)) {
            return "";
        }
        try {
            long countdownTime =
                    (ISO8601Utils.parse(planDate, new ParsePosition(0)).getTime()
                            - System.currentTimeMillis())
                            / SECOND_1;
            if (countdownTime >= 0) {
                if (countdownTime < 60) {
                    return countdownTime + " 秒";
                }
                return countdownTime / 60 + " 分钟";
            } else {
                long absTime = Math.abs(countdownTime);
                if (absTime < 60) {
                    return "超时 " + absTime + " 秒";
                }

                return "超时 " + absTime / 60 + " 分";
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getCountdownStr(@Nullable String planDate, @Nullable String endDate) {

        if (TextUtils.isEmpty(planDate) || TextUtils.isEmpty(endDate)) {
            return "";
        }
        try {
            long countdownTime =
                    (ISO8601Utils.parse(planDate, new ParsePosition(0)).getTime()
                            - ISO8601Utils.parse(endDate, new ParsePosition(0)).getTime())
                            / SECOND_1;
            if (countdownTime >= 0) {
                return "准时送达";
            }

            return "超时送达";

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static long getTime(String date, String dateFormat) {

        SimpleDateFormat df = new SimpleDateFormat(dateFormat, Locale.US);
        df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        try {
            return df.parse(date).getTime();

        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long getTimeChina(String date, String dateFormat) {

        SimpleDateFormat df = new SimpleDateFormat(dateFormat, Locale.CHINA);
        try {
            return df.parse(date).getTime();

        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Date getDateChina(String date, String dateFormat) {

        SimpleDateFormat df = new SimpleDateFormat(dateFormat, Locale.CHINA);
        try {
            return df.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatString(String dateFormat) {

        SimpleDateFormat df = new SimpleDateFormat(dateFormat, Locale.US);
        return df.format(new Date());
    }

    /**
     * 校验时间戳是否大于等于1小时
     *
     * @param cdvSecond 校验倒计时时间
     * @return true/false
     */
    public static boolean isHourFormat(long cdvSecond) {

        return cdvSecond * SECOND_1 >= HOUR_1;
    }

    /**
     * 校验是否重复发送验证码1分钟内（1m） true:重复发送 false:非重复发送
     *
     * @param context 上下文
     * @param mobile  手机号
     * @return true/false
     */
    public static boolean checkRepeatSentVC(Context context, String mobile) {

        Object preTimeObj = SPUtils.get(context, mobile, 0L);
        if (preTimeObj != null) {
            long preTime = Long.parseLong(preTimeObj.toString());
            return System.currentTimeMillis() - preTime <= SECOND_60;
        }
        return false;
    }

    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        return date.getTime();
    }

    /**
     * 获取当前日期之前后的时间
     *
     * @param nDays       -n：前n天； 1：后n天
     * @param currentTime 当前时间
     * @param hourOfDay   指定小时
     * @param minute      指定分钟
     * @param second      指定秒
     * @param millisecond 指定毫秒
     * @return
     */
    public static long getDateLong(
            int nDays, long currentTime, int hourOfDay, int minute, int second, int millisecond) {

        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTimeInMillis(currentTime);
        c.add(Calendar.DATE, nDays);
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        c.set(Calendar.MILLISECOND, millisecond);
        return c.getTimeInMillis();
    }
}

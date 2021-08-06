package com.jay.base_lib.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author zhanghao
 */
public class StringUtils {
    /**
     * 手机号码
     */
    private static Pattern mobile_pattern = Pattern.compile("1[3|5|7|8|][0-9]{9}");// ^(1)\\d{10}$

    /**
     * 国内电话号码
     */
    private static Pattern phone_pattern = Pattern.compile("\\d{3}-\\d{8}|\\d{4}-\\d{7,8}");

    /**
     * 密码
     */
    private static Pattern password_pattern = Pattern.compile("^[0-9A-Za-z]{6,16}$");

    /**
     * 身份证号码
     */
    private static Pattern id_card_pattern = Pattern.compile("^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$");

    /**
     * 金额验证
     */
    private static Pattern amount_pattern = Pattern.compile("^\\d+(\\.\\d+)?$");

    /**
     * 手机号码验证
     *
     * @param phoneNumber 待验证字符串
     * @return 是否手机号
     */
    public static boolean isMobile(String phoneNumber) {

        return mobile_pattern.matcher(phoneNumber).matches();
    }

    /**
     * 电话号码验证（国内）
     *
     * @param phoneNumber 待验证字符串
     * @return 是否固话
     */
    public static boolean isPhone(String phoneNumber) {

        return phone_pattern.matcher(phoneNumber).matches();
    }

    /**
     * 密码验证（密码只能是6-16位的字母和数字的组合）
     *
     * @param password 密码
     * @return 密码格式是否正确
     */
    public static boolean isCorrectWithPassword(String password) {

        return password_pattern.matcher(password).matches();
    }

    /**
     * 身份证号码验证（身份证号码只能是18位的组合）
     *
     * @param idCard 身份证号码
     * @return 身份证格式是否正确
     */
    public static boolean isIdCard(String idCard) {

        return id_card_pattern.matcher(idCard).matches();
    }

    /**
     * 金额验证
     *
     * @param amount 金额
     * @return 金额验证
     */
    public static boolean isAmount(String amount) {

        return amount_pattern.matcher(amount).matches();
    }

    /**
     * 将unicode字符串转换成汉字
     *
     * @param str unicode字符串
     * @return 汉字
     */
    public static String encodingtoStr(String str) {

        if (TextUtils.isEmpty(str)) {
            return "";
        }
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }

    /**
     * 汉字转Unicode
     *
     * @param s 汉字
     * @return unicode字符串
     */
    public static String gbEncoding(final String s) {

        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            str += "\\u" + Integer.toHexString(ch);
        }
        return str;
    }

    /**
     * 字符串中截取数字
     *
     * @param content 字符串
     * @return 截取到的数字字符串
     */
    public static String getNumbers(String content) {

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String number_str = matcher.group();
            //数字字符串长度大于等于4,为验证码
            if (number_str.length() >= 4) {
                return number_str;
            }
        }
        return "";
    }
}

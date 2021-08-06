package com.jay.base_lib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具类-http://blog.csdn.net/xyang81/article/details/7706408 提供验证邮箱、手机号、电话号码、身份证号码、数字等方法
 */
public final class RegexUtils {

    /**
     * 手机号码，中间4位星号替换
     *
     * @param phone 手机号
     * @return
     */
    public static String phoneNoHide(String phone) {
        // 括号表示组，被替换的部分$n表示第n组的内容
        // 正则表达式中，替换字符串，括号的意思是分组，在replace()方法中，
        // 参数二中可以使用$n(n为数字)来依次引用模式串中用括号定义的字串。
        // "(\d{3})\d{4}(\d{4})", "$1****$2"的这个意思就是用括号，
        // 分为(前3个数字)中间4个数字(最后4个数字)替换为(第一组数值，保持不变$1)(中间为*)(第二组数值，保持不变$2)
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 银行卡号，保留最后4位，其他星号替换
     *
     * @param cardId 卡号
     * @return
     */
    public static String cardIdHide(String cardId) {
        // idCard.replaceAll("(\\d{4})\\d{10}(\\w{4})","$1*****$2");
        return cardId.replaceAll("\\d{15}(\\d{3})", "**** **** **** **** $1");
    }

    /**
     * 是否为车牌号（沪A88888）
     */
    public static boolean checkVehicleNo(String vehicleNo) {

        Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{5}$");
        return pattern.matcher(vehicleNo).find();
    }

    /**
     * 验证身份证号码
     *
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIdCard(String idCard) {

        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex, idCard);
    }

    public static boolean checkIdCard18(String idCard) {

        String regex =
                "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}" + "([0-9]|X)$";
        return Pattern.matches(regex, idCard);
    }

    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     *
     * @param mobile 移动、联通、电信运营商的号码段
     *               <p>移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *               、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）
     *               <p>联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）
     *               <p>电信的号段：133、153、180（未启用）、189、177
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) {

        // String regex = "(\\+\\d+)?1[23456789]\\d{9}$";
        String regex = "\\d{11}";
        return Pattern.matches(regex, mobile);
    }

    public static boolean checkMobileWithSpace(String mobile) {

        // String regex = "(\\+\\d+)?1[23456789]\\d{9}$";
        String regex = "\\d{11}";
        return Pattern.matches(regex, mobile);
    }

    /**
     * 验证固定电话号码
     *
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电
     *              <p>
     *              <p>
     *              <p>话号码，如：+8602085588447
     *              <p><b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字， 数字之后是空格分隔的国家（地区）代码。
     *              <p><b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号—— 对不使用地区或城市代码的国家（地区），则省略该组件。
     *              <p><b>电话号码：</b>这包含从 0 到 9 的一个或多个数字
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPhone(String phone) {

        String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
        return Pattern.matches(regex, phone);
    }

    /**
     * 验证Email
     *
     * @param email email地址，格式：zhangsan@sina.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(String email) {

        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }

    /**
     * 验证整数（正整数和负整数）
     *
     * @param digit 一位或多位0-9之间的整数
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDigit(String digit) {

        String regex = "\\-?[1-9]\\d+";
        return Pattern.matches(regex, digit);
    }

    /**
     * 验证整数和浮点数（正负整数和正负浮点数）
     *
     * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDecimals(String decimals) {

        String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
        return Pattern.matches(regex, decimals);
    }

    /**
     * 验证空白字符
     *
     * @param blankSpace 空白字符，包括：空格、\t、\n、\r、\f、\x0B
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBlankSpace(String blankSpace) {

        String regex = "\\s+";
        return Pattern.matches(regex, blankSpace);
    }

    /**
     * 验证中文
     *
     * @param chinese 中文字符
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkChinese(String chinese) {

        String regex = "^[\u4E00-\u9FA5]+$";
        return Pattern.matches(regex, chinese);
    }

    /**
     * 验证日期（年月日）
     *
     * @param birthday 日期，格式：1992-09-03，或1992.09.03
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBirthday(String birthday) {

        String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
        return Pattern.matches(regex, birthday);
    }

    /**
     * 验证URL地址
     *
     * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn
     *            .net:80
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkURL(String url) {

        String regex =
                "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*"
                        + ""
                        + "(\\??"
                        + "(.+=.*)?"
                        + "(&"
                        + ".+=.*)?)?";
        return Pattern.matches(regex, url);
    }

    /**
     * 匹配中国邮政编码
     *
     * @param postcode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPostcode(String postcode) {

        String regex = "[1-9]\\d{5}";
        return Pattern.matches(regex, postcode);
    }

    /**
     * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
     *
     * @param ipAddress IPv4标准地址
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIpAddress(String ipAddress) {

        String regex =
                "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|"
                        + "([1-9](\\d{1,2})"
                        + ""
                        + ""
                        + ""
                        + ""
                        + ""
                        + ""
                        + ""
                        + ""
                        + ""
                        + ""
                        + ""
                        + ""
                        + ""
                        + ""
                        + ""
                        + ""
                        + ""
                        + ""
                        + ""
                        + "?))";
        return Pattern.matches(regex, ipAddress);
    }

    /**
     * 匹配后几位
     *
     * @param str      String
     * @param matchStr Last String
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkLastDigit(String str, String matchStr) {

        String regex = matchStr + "$";

        return Pattern.compile(regex).matcher(str).find();
    }

    /**
     * 匹配是否已某字符串开头
     *
     * @param str      String
     * @param matchStr String
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkFirstDigit(String str, String matchStr) {

        String regex = "^" + matchStr;

        return Pattern.compile(regex).matcher(str).find();
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符
     */
    public static String removeChar(String str) {

        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }

    /**
     * 获取指定位数的整数字符串
     *
     * @param str String
     * @return String
     */
    public static String findDigitStr(String str) {

        String temp = removeChar(str);

        // 获取截取字符串的长度 8／12／13位
        int digit1 = temp.length() > 8 ? (temp.length() > 12 ? 13 : 12) : 8;
        Pattern p = Pattern.compile("\\d{" + digit1 + "}");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return m.group();
        }
        return "";
    }

    /**
     * 校验是否符合临时中转袋格式 临时中转袋:P开头的字符串
     *
     * @return true/false
     */
    public static boolean checkTempPack(String str) {

        return checkFirstDigit(str, "P");
    }

    /**
     * 校验是否符合门店中转袋格式 袋子二维码格式：store_id,pack_no
     *
     * @return true/false
     */
    public static boolean checkPack(String[] strs) {

        return strs != null && strs.length == 2 && strs[0].length() == 24 && strs[1].length() == 8;
    }

    /**
     * 验证商户号
     *
     * @param digit 6位0-9之间的整数
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMerchantCode(String digit) {

        // String regex = "^\\d{4}$";
        // String regex = "^\\d+";
        String regex = "^[A-Za-z0-9]+$";
        return Pattern.matches(regex, digit);
    }

    /**
     * 截取日期
     *
     * @param content 内容，正确的日期格式为yyyy-mm-dd
     * @return 截取后的日期
     */
    public static String findDate(String content) {
        String regex =
                "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        if (m.find()) {
            return m.group();
        }
        return "";
    }
}

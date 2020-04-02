package com.jaydroid.base_lib.utils;

import java.security.MessageDigest;

/**
 * MD5加密工具类
 *
 * @author zhanghao
 * @version 1.0
 */
public class MD5Util {
    /**
     * MD5加密 32位
     *
     * @param str 需要加密的字符串
     * @return 32位的字符串
     */
    public static String md5(String str) {
        byte[] md5Bytes = md5Bytes(str);
        StringBuilder hexValue = new StringBuilder();
        if (md5Bytes != null) {
            for (byte md5Byte : md5Bytes) {
                int val = ((int) md5Byte) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
        }
        return hexValue.toString();
    }

    /**
     * MD5加密后的字节Array
     *
     * @param str 需要加密的字符串
     * @return 加密后的字节Array
     */
    public static byte[] md5Bytes(String str) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        md5.update(str.getBytes());
        return md5.digest();
    }

    /**
     * MD5加密后的字节Array
     *
     * @param bytes 需要加密的字节数组
     * @return 加密后的字节Array
     */
    public static byte[] md5Bytes(byte[] bytes) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        md5.update(bytes);
        return md5.digest();
    }


    /**
     * MD5加密后的字节Array
     *
     * @param bytes 需要加密的字节数组
     * @return 加密后的字节Array
     */
    public static String md5String(byte[] bytes) {
        byte[] md5Bytes = md5Bytes(bytes);
        StringBuilder hexValue = new StringBuilder();
        if (md5Bytes != null) {
            for (byte md5Byte : md5Bytes) {
                int val = ((int) md5Byte) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
        }
        return hexValue.toString();
    }

}

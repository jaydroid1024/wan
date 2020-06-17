package com.jaydroid.base_lib.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * HmacMD5加解密
 * 散列消息鉴别码，基于密钥的Hash算法的认证协议。
 * 消息鉴别码实现鉴别的原理是，用公开函数和密钥产生一个固定长度的值作为认证标识，用这个 标识鉴别消息的完整性。
 * 使用一个密钥生成一个固定大小的小数据块，即MAC，并将其加入到消息中，然后传输。
 * 接收方利用与发送方共享的密钥进行鉴别认证
 *
 * @author zhanghao
 * @version 1.0
 * @date 15/9/21下午4:53
 */
public class HmacMD5Util {
    private static final String HMAC_MD5 = "HmacMD5";

    private HmacMD5Util() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * HmacMD5加密
     * 返回字符串
     *
     * @param src 需要加密的字符串
     * @param key 加密的key
     * @return 加密后的数据
     */
    public static String encryptToString(String src, String key) {
        byte[] encrypt_bytes = encrypt(src, key);
        return encrypt_bytes == null ? null : ConvertUtils.byte2HexString(encrypt_bytes);
    }

    /**
     * HmacMD5加密
     * 返回字节数组
     *
     * @param src 需要加密的字符串
     * @param key 加密的key
     * @return 加密后的数据
     */
    public static byte[] encrypt(String src, String key) {
        //根据key来构建密钥
        SecretKeySpec sk = new SecretKeySpec(key.getBytes(), HMAC_MD5);
        try {
            //生成一个MAC
            Mac mac = Mac.getInstance(sk.getAlgorithm());
            //初始化MAC
            mac.init(sk);
            //加密src并转化成十六进制字符串
            return mac.doFinal(src.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * HmacMD5加密
     * 返回字符串
     *
     * @param src 需要加密的字节数组
     * @param key 加密的key
     * @return 加密后的数据
     */
    public static String encryptToString(byte[] src, String key) {
        byte[] encrypt_bytes = encrypt(src, key);
        return encrypt_bytes == null ? null : ConvertUtils.byte2HexString(encrypt_bytes);
    }

    /**
     * HmacMD5加密
     * 返回字节数组
     *
     * @param src 需要加密的字节数组
     * @param key 加密的key
     * @return 加密后的数据
     */
    public static byte[] encrypt(byte[] src, String key) {
        //根据key来构建密钥
        SecretKeySpec sk = new SecretKeySpec(key.getBytes(), HMAC_MD5);
        try {
            //生成一个MAC
            Mac mac = Mac.getInstance(sk.getAlgorithm());
            //初始化MAC
            mac.init(sk);
            //加密src并转化成十六进制字符串
            return mac.doFinal(src);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }
}

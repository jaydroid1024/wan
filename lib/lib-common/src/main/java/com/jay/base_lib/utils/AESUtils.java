package com.jay.base_lib.utils;

import android.util.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密/解密
 * AES/CBC/PKCS5Padding
 * 算法/模式/填充(补码方式)
 * AES:对称加密(加密和解密需要相同的密钥)
 * CBC模式:需要一个向量iv，可增加加密算法的强度
 * 初始化向量(iv):为了避免在相同的密钥下,使用AES加密相同的明文产生同样的结果
 * 填充: AES在加密明文的时候,需要把明文分为几个区块,每个区块的长度的长度都是128位.
 * 但是并不是每个明文的都可以被区分出完整的区块,因此需对明文做填充.
 *
 * @author zhanghao
 * @version 2.0
 * @date 15/7/26上午10:23
 */
public class AESUtils {
    private static final String TAG = AESUtils.class.getSimpleName();

    /**
     * 加密演算法使用AES
     */
    private static final String ALGORITHM = "AES";

    /**
     * AES使用CBC模式与PKCS5Padding填充
     */
    private static final String TRANSFORMATION = "AES/CBC/PKCS7Padding";

    /**
     * 。PBKDF2WithHmacSHA1算法比MD5算法更安全
     * 它可以同样密码在不同时间生成不同加密Hash
     */
    private static final String PBKDF2 = "PBKDF2WithHmacSHA1";

    /**
     * salt长度
     */
    private static final int SALT_LENGTH = 8;

    /**
     * 密钥长度
     */
    private static final int KEY_LENGTH = 32;

    /**
     * 初始化向量长度
     */
    private static final int IV_LENGTH = 16;

    private AESUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    /**
     * 随即获取长度为8的salt字节数组
     *
     * @return salt字节数组
     */
    private static byte[] getSalt() {
        //密码安全伪随机数生成器
        SecureRandom random = new SecureRandom();
        //定义并初始化salt
        byte[] salt = new byte[SALT_LENGTH];
        //salt赋一个随即值
        random.nextBytes(salt);
        return salt;
    }

    /**
     * 获取长度为(key_len+iv_len)的字节Array(前key_len为密钥 后iv_len为初始化向量)
     *
     * @param key    加密前的key
     * @param salt   用于加密的字节数组
     * @param keyLen 加密后的密钥长度
     * @param ivLen  初始化向量长度
     * @return 密钥初始化向量字节Array
     */
    private static byte[] getKeyIv(String key, byte[] salt, int keyLen, int ivLen) {
        // 将key字符串转换成字节Array
        byte[] keyBytes = key.getBytes();
        // 字节数组的总长度
        int len = keyLen + ivLen;
        // key字节数组和salt合并
        byte[] combinedBytes = ArrayUtil.addAll(keyBytes, salt);
        // 合并后的字符串MD5加密
        byte[] keyIvBytes = MD5Util.md5Bytes(combinedBytes);

        if (keyIvBytes == null) {
            return null;
        }

        byte[] temp = keyIvBytes;
        // while循环  用于补全key_iv字节数组的长度
        while (keyIvBytes.length < len) {
            temp = MD5Util.md5Bytes(ArrayUtil.addAll(temp, combinedBytes));
            keyIvBytes = ArrayUtil.addAll(keyIvBytes, temp);
        }
        return keyIvBytes;
    }

    /**
     * 加密数据
     * Aes-Base64
     * 对aes加密后的数据用base64编码
     *
     * @param src 原数据
     * @param key 加密key
     * @return 加密后的数据
     */
    public static String encryptString(String src, String key) {
        // 生成salt字节数组
        byte[] salt = getSalt();

        // String saltString = byte2HexString(salt);
        // L.d(TAG, "saltByte : " + saltString + "  " + saltString.length());
        // 根据salt生成key和iv的字节数组
        byte[] keyIv = getKeyIv(key, salt, KEY_LENGTH, IV_LENGTH);

        // 获取key字节数组
        byte[] keyByte = ArrayUtil.subarray(keyIv, 0, KEY_LENGTH);
        // String keyString = byte2HexString(keyByte);
        // L.d(TAG, "keyByte : " + keyString + "  " + keyString.length());

        // 获取iv字节数组
        byte[] ivByte = ArrayUtil.subarray(keyIv, KEY_LENGTH, KEY_LENGTH + IV_LENGTH);
        // String ivString = byte2HexString(ivByte);
        // L.d(TAG, "ivByte : " + ivString + "  " + ivString.length());

        //加密
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyByte, ALGORITHM), new IvParameterSpec(ivByte));
            //Base64编码加密后的数据
            return Base64.encodeToString(ArrayUtil.addAll(salt, cipher.doFinal(src.getBytes())), Base64.DEFAULT);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * AES解密数据
     *
     * @param encrypt 加密后的数据
     * @param key     解密key
     * @param flag    是否开启Base64编码
     * @return 解密后的字符串
     */
    public static String decryptString(String encrypt, String key, boolean flag) {
        //将字符串转换成字节数组
        byte[] saltEncryptBytes = encrypt.getBytes();

        //Base64解码
        if (flag) {
            saltEncryptBytes = Base64.decode(saltEncryptBytes, Base64.DEFAULT);
        }

        //获取salt字节数组  8byte
        byte[] saltBytes = ArrayUtil.subarray(saltEncryptBytes, 0, SALT_LENGTH);
        //获取加密字节数组 8byte之后的所有数据
        byte[] encryptBytes = ArrayUtil.subarray(saltEncryptBytes, SALT_LENGTH, saltEncryptBytes.length);
        //根据salt生成key和iv的字节数组
        byte[] keyIv = getKeyIv(key, saltBytes, KEY_LENGTH, IV_LENGTH);
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(ArrayUtil.subarray(keyIv, 0, KEY_LENGTH), ALGORITHM),
                    new IvParameterSpec(ArrayUtil.subarray(keyIv, KEY_LENGTH, KEY_LENGTH + IV_LENGTH)));
            return new String(cipher.doFinal(encryptBytes));
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | NoSuchPaddingException |
                InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * AES解密数据
     *
     * @param encrypt 加密后的数据
     * @param key     解密key
     * @param flag    是否开启Base64编码
     * @return 解密后的字符串
     */
    public static String decryptString(byte[] encrypt, String key, boolean flag) {
        //Base64解码
        if (flag) {
            encrypt = Base64.decode(encrypt, Base64.DEFAULT);
        }
        //获取salt字节数组  8byte
        byte[] saltBytes = ArrayUtil.subarray(encrypt, 0, SALT_LENGTH);
        //获取加密字节数组 8byte之后的所有数据
        byte[] encryptBytes = ArrayUtil.subarray(encrypt, SALT_LENGTH, encrypt.length);
        //根据salt生成key和iv的字节数组
        byte[] keyIv = getKeyIv(key, saltBytes, KEY_LENGTH, IV_LENGTH);
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(ArrayUtil.subarray(keyIv, 0, KEY_LENGTH), ALGORITHM),
                    new IvParameterSpec(ArrayUtil.subarray(keyIv, KEY_LENGTH, KEY_LENGTH + IV_LENGTH)));
            return new String(cipher.doFinal(encryptBytes));
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | NoSuchPaddingException |
                InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * AES解密数据
     *
     * @param encrypt 加密后的数据
     * @param key     解密key
     * @param flag    是否开启Base64编码
     * @return 解密后的字符串
     */
    public static byte[] decryptByte(byte[] encrypt, String key, boolean flag) {
        //Base64解码
        if (flag) {
            encrypt = Base64.decode(encrypt, Base64.DEFAULT);
        }
        //获取salt字节数组  8byte
        byte[] saltBytes = ArrayUtil.subarray(encrypt, 0, SALT_LENGTH);
        //获取加密字节数组 8byte之后的所有数据
        byte[] encryptBytes = ArrayUtil.subarray(encrypt, SALT_LENGTH, encrypt.length);
        //根据salt生成key和iv的字节数组
        byte[] keyIv = getKeyIv(key, saltBytes, KEY_LENGTH, IV_LENGTH);
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(ArrayUtil.subarray(keyIv, 0, KEY_LENGTH), ALGORITHM),
                    new IvParameterSpec(ArrayUtil.subarray(keyIv, KEY_LENGTH, KEY_LENGTH + IV_LENGTH)));
            return cipher.doFinal(encryptBytes);
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | NoSuchPaddingException |
                InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * byte 数组转换为十六进制的字符串
     *
     * @param b 输入需要转换的byte数组
     * @return 返回十六进制 字符串
     */
    private static String byte2HexString(byte[] b) {

        char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] newChar = new char[b.length * 2];
        for (int i = 0; i < b.length; i++) {
            newChar[2 * i] = hex[(b[i] & 0xf0) >> 4];
            newChar[2 * i + 1] = hex[b[i] & 0xf];


        }
        return new String(newChar);
    }

    /**
     * 获取key
     *
     * @param key 字符串key
     * @return 二进制key
     */
    private byte[] getSecretKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //迭代次数
        int iterationCount = 1000;
        //计算加密后的hash长度
        int keyLength = 256;
        int saltLength = keyLength / 8;
        //增加密码破解难度
        byte[] salt = new byte[saltLength];
        KeySpec keySpec = new PBEKeySpec(key.toCharArray(), salt, iterationCount, keyLength);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBKDF2);
        byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();
        L.d(TAG, "key : " + new String(keyBytes));
        return keyBytes;
    }

    /**
     * 获取获取初始化向量(IV)
     *
     * @return 初始化向量
     */
    private IvParameterSpec getIvParameterSpec() throws NoSuchPaddingException, NoSuchAlgorithmException {
        // 随即生成初始化向量
        // SecureRandom random = new SecureRandom();
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        byte[] iv = new byte[cipher.getBlockSize()];
        // random.nextBytes(iv);
        L.d(TAG, "size  = " + cipher.getBlockSize());
        L.d(TAG, "iv = " + new String(iv));
        return new IvParameterSpec(iv);
    }

}

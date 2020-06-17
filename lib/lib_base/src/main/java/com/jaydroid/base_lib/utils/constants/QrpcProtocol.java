package com.jaydroid.base_lib.utils.constants;

/**
 * 客户端支持的数据封装协议类型
 * plain 只是用msgpack进行序列化
 * aes 在plain层的基础上增加AES加密封装
 * aes-base64 同aes，对于加密后的数据再做一次base64编码
 *
 * @author zhanghao
 * @version 1.0
 * @date 15/9/29下午2:14
 */
public class QrpcProtocol {
    public static final String Q_PLAIN = "plain";
    public static final String Q_AES = "aes";
    public static final String Q_AES_BASE64 = "aes-base64";

}

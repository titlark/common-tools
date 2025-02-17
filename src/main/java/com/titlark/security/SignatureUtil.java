package com.titlark.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * 签名/验签工具类
 */
public class SignatureUtil {

    private SignatureUtil() {
    }

    /**
     * 对数据进行验签
     *
     * @param key       签名密钥
     * @param value     签名原文
     * @param signature 签名值
     * @return
     */
    public static boolean verifySignature(String key, String value, String signature) {
        try {
            // 使用 HMAC-SHA256 算法
            byte[] hmacSha256Bytes = hmacSha256(key, value);
            // 使用 Base64 编码签名
            String s = Base64.getEncoder().encodeToString(hmacSha256Bytes);
            return s.equals(signature);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 对参数进行签名
     *
     * @param key   签名密钥
     * @param value 签名原文
     * @return
     */
    public static String signature(String key, String value) {
        try {
            // 使用 HMAC-SHA256 算法
            byte[] hmacSha256Bytes = hmacSha256(key, value);
            // 使用 Base64 编码签名
            return Base64.getEncoder().encodeToString(hmacSha256Bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 手动实现 HMAC-SHA256
     *
     * @param key  密钥
     * @param data 数据
     * @return HMAC-SHA256 结果
     * @throws Exception
     */
    private static byte[] hmacSha256(String key, String data) throws Exception {
        // 创建 HMAC-SHA256 算法实例
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);

        // 使用密钥填充 64 字节
        if (keyBytes.length > 64) {
            keyBytes = md.digest(keyBytes); // 长度超过 64 时，进行一次哈希
        }
        if (keyBytes.length < 64) {
            byte[] temp = new byte[64];
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp; // 填充至 64 字节
        }

        // 内部密钥和外部密钥
        byte[] oKeyPad = new byte[64];
        byte[] iKeyPad = new byte[64];

        for (int i = 0; i < 64; i++) {
            oKeyPad[i] = (byte) (0x5c ^ keyBytes[i]);
            iKeyPad[i] = (byte) (0x36 ^ keyBytes[i]);
        }

        // 计算 HMAC: HMAC = outer hash(inner hash(data))
        md.update(iKeyPad);
        md.update(dataBytes);
        byte[] innerHash = md.digest();

        md.update(oKeyPad);
        md.update(innerHash);
        return md.digest(); // 返回最终的 HMAC
    }
}

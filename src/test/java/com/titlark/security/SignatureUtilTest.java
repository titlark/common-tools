package com.titlark.security;

import junit.framework.TestCase;

/**
 * 签名/验签工具类
 */
public class SignatureUtilTest  extends TestCase {

    private final String KEY = "123";
    private final String plainText = "12312313";

    public void testApiSignature() {
        String signature = SignatureUtil.signature(KEY, plainText);
        System.out.println("signature = " + signature);
    }

    public void testApiVerifySignature() {
        String signature = SignatureUtil.signature(KEY, plainText);
        System.out.println("signature = " + signature);
        boolean verify = SignatureUtil.verifySignature(KEY, plainText, signature);
        System.out.println("verify = " + verify);
    }
}

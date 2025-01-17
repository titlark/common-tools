package com.titlark.utils;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 字节工具类
 */
public class ByteUtil {
    private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

    private ByteUtil() {
    }

    /**
     * 将byte数组转换为16进制数据
     *
     * @param data 字节数组
     * @return 16进制数据
     */
    public static String toHexString(byte[] data) {
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    /**
     * 将byte数组转换为16进制数据
     *
     * @param data 字节数组
     * @return 16进制数据
     */
    public static String toHexString1(byte[] data) {
        return new BigInteger(1, data).toString(16);
    }

    /**
     * 将byte数组转换为16进制数据
     *
     * @param data 字节数组
     * @return 16进制数据
     */
    public static String toHexString2(byte[] data) {
        return new HexBinaryAdapter().marshal(data);
    }

    /**
     * 将byte数组转换为16进制数据
     *
     * @param data 字节数组
     * @return 16进制数据
     */
    public static String toHexString3(byte[] data) {
        return DatatypeConverter.printHexBinary(data);
    }

    /**
     * 将byte数组转换为16进制数据
     *
     * @param data 字节数组
     * @return 16进制数据
     */
    public static String toHexString4(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte byteData : data) {
            String hexString = Integer.toHexString(byteData & 0xFF);
            sb.append(hexString.length() == 1 ? "0" + hexString : hexString);
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 十六进制字符串转byte数组
     *
     * @param hexString 十六进制数据字符串
     * @return
     */
    public static byte[] hexStringToByte(String hexString) {
        return new HexBinaryAdapter().unmarshal(hexString);
    }

    /**
     * 整形转换成网络传输的字节流（字节数组）型数据
     *
     * @param num 一个整型数据
     * @return 4个字节的字节数组
     */
    public static byte[] intToBytes(int num) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (0xff & (num));
        bytes[1] = (byte) (0xff & (num >> 8));
        bytes[2] = (byte) (0xff & (num >> 16));
        bytes[3] = (byte) (0xff & (num >> 24));
        return bytes;
    }

    /**
     * 四个字节的字节数据转换成一个整形数据
     *
     * @param bytes 4个字节的字节数组
     * @return 一个整型数据
     */
    public static int byteToInt(byte[] bytes) {
        int num = 0;
        int temp;
        temp = (0x000000ff & (bytes[0]));
        num = num | temp;
        temp = (0x000000ff & (bytes[1])) << 8;
        num = num | temp;
        temp = (0x000000ff & (bytes[2])) << 16;
        num = num | temp;
        temp = (0x000000ff & (bytes[3])) << 24;
        num = num | temp;
        return num;
    }

    /**
     * 长整形转换成网络传输的字节流（字节数组）型数据
     *
     * @param num 一个长整型数据
     * @return 4个字节的字节数组
     */
    public static byte[] longToBytes(long num) {
        byte[] bytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            bytes[i] = (byte) (0xff & (num >> (i * 8)));
        }
        return bytes;
    }

    /**
     * @param b 将字节数组转long类型 位置为小端
     * @return long类型数据
     */
    public static long byteToLong(byte[] b) {
        long s = 0;
        long s0 = b[0] & 0xff;// 最低位
        long s1 = b[1] & 0xff;
        long s2 = b[2] & 0xff;
        long s3 = b[3] & 0xff;
        long s4 = b[4] & 0xff;// 最低位
        long s5 = b[5] & 0xff;
        long s6 = b[6] & 0xff;
        long s7 = b[7] & 0xff;

        // s0不变
        s1 <<= 8;
        s2 <<= 16;
        s3 <<= 24;
        s4 <<= 8 * 4;
        s5 <<= 8 * 5;
        s6 <<= 8 * 6;
        s7 <<= 8 * 7;
        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
        return s;
    }

    /**
     * 大数字转换字节流（字节数组）型数据
     *
     * @param num bigInteger
     */
    public static byte[] byteConvert32Bytes(BigInteger num) {
        byte[] temp;
        if (num.toByteArray().length == 33) {
            temp = new byte[32];
            System.arraycopy(num.toByteArray(), 1, temp, 0, 32);
        } else if (num.toByteArray().length == 32) {
            temp = num.toByteArray();
        } else {
            temp = new byte[32];
            for (int i = 0; i < 32 - num.toByteArray().length; i++) {
                temp[i] = 0;
            }
            System.arraycopy(num.toByteArray(), 0, temp, 32 - num.toByteArray().length, num.toByteArray().length);
        }
        return temp;
    }

    /**
     * 换字节流（字节数组）型数据转大数字
     *
     * @param bytes 字节流
     */
    public static BigInteger byteConvertInteger(byte[] bytes) {
        if (bytes[0] < 0) {
            byte[] temp = new byte[bytes.length + 1];
            temp[0] = 0;
            System.arraycopy(bytes, 0, temp, 1, bytes.length);
            return new BigInteger(temp);
        }
        return new BigInteger(bytes);
    }

    /**
     * int类型转换小端的byte数组
     *
     * @param i
     * @return byte数组
     */
    public static byte[] intToLittleBytes(int i) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.asIntBuffer().put(i);
        return byteBuffer.array();
    }

    /**
     * 合并多个byte数组
     *
     * @param arrays 多个byte数组
     * @return 合并后的byte数组
     */
    public static byte[] byteMerge(byte[]... arrays) {
        int totalLength = 0;
        for (byte[] array : arrays) {
            if (null != array) {
                totalLength += array.length;
            }
        }
        byte[] result = new byte[totalLength];
        int currentIndex = 0;
        for (byte[] array : arrays) {
            if (null != array) {
                System.arraycopy(array, 0, result, currentIndex, array.length);
                currentIndex += array.length;
            }
        }
        return result;
    }

    /**
     * 截取 byte 数组
     *
     * @param source 源byte数组
     * @param start  截取开始索引位置
     * @param length 要截取的长度
     * @return 截取后的byte数组
     */
    public static byte[] subByte(byte[] source, int start, int length) {
        if (null == source || start < 0 || length < 0 || start + length > source.length) {
            throw new IllegalArgumentException("无效的开始索引或长度");
        }
        byte[] result = new byte[length];
        System.arraycopy(source, start, result, 0, length);
        return result;
    }
}

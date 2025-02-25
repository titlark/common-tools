package com.titlark.mask;

/**
 * 数据脱敏
 */
public class DataMaskingUtil {
    /**
     * 默认脱敏符号：*
     */
    private static String DEFAULT_MASK_SYMBOL = "*";

    private DataMaskingUtil() {
    }

    public static String maskName(String name) {
        if (name == null || name.length() <= 1) return name;
        return name.charAt(0) + maskSymbolRepeat(name.length() - 1);
    }

    public static String maskIdCard(String idCard) {
        if (idCard == null || idCard.length() < 10) return idCard;
        return idCard.substring(0, 6) + maskSymbolRepeat(8) + idCard.substring(idCard.length() - 4);
    }

    /**
     * 根据索引替换脱敏符号
     *
     * @param maskData   待脱敏数据
     * @param startIndex 开始索引
     * @param endIndex   结束索引（不包含）
     * @return
     */
    public static String mask(String maskData, int startIndex, int endIndex) {
        if (maskData == null || maskData.isEmpty()) return maskData;
        if (startIndex == endIndex) return maskData;
        if (startIndex > maskData.length()) startIndex = 0;
        if (endIndex > maskData.length()) endIndex = maskData.length();
        return maskData.substring(0, startIndex) + DEFAULT_MASK_SYMBOL + maskData.substring(endIndex);

    }

    /**
     * 设置脱敏符号
     *
     * @param maskSymbol 脱敏符号
     */
    public static void setDefaultMaskSymbol(String maskSymbol) {
        DataMaskingUtil.DEFAULT_MASK_SYMBOL = maskSymbol;
    }

    /**
     * 获取脱敏符号填充数据
     *
     * @param repeatCount
     * @return
     */
    private static String maskSymbolRepeat(int repeatCount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < repeatCount; i++) {
            sb.append(DEFAULT_MASK_SYMBOL);
        }
        return sb.toString();
    }
}

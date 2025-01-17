package com.titlark.utils;

/**
 * Int 操作相关
 */
public class IntUtil {

    private IntUtil() {
    }

    /**
     * 获取两个数的最大公约数
     * <p>
     * 这是一种用辗转相除法来计算最大公约数的递归函数。
     * <p>
     * 跟我们手算最大公约数的方法不同，这个算法是这样的：
     * <p>
     * 举个简单的例子，a=24，b=18，求a和b的最大公约数；
     * <p>
     * a除以b，得到的余数是6，那么就让a=18，b=6，然后接着往下算；
     * <p>
     * 18除以6，这回余数是0，那么6也就是24和18的最大公约数了。
     * <p>
     * 也就是说，a和b反复相除取余数，直到b=0
     *
     * @param a 数字1
     * @param b 数字2
     * @return 最大公约数
     */
    public static int getGreatestCommonDivisor(int a, int b) {
        while (a % b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return b;
    }

}

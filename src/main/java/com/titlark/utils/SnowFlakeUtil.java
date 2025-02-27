package com.titlark.utils;

/**
 * 雪花算法
 */
public class SnowFlakeUtil {
    /**
     * 起始的时间戳:这个时间戳自己随意获取，比如自己代码的时间戳 2020-12-31 0:0:0
     */
    private final static long START_STMP = 1609344000000L;

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12L; //序列号占用的位数
    private final static long MACHINE_BIT = 5L;  //机器标识占用的位数
    private final static long DATACENTER_BIT = 5L;//数据中心占用的位数

    /**
     * 每一部分的最大值：先进行左移运算，再同-1进行异或运算；异或：相同位置相同结果为0，不同结果为1
     */
    /**
     * 用位运算计算出最大支持的数据中心数量：31
     */
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);

    /**
     * 用位运算计算出最大支持的机器数量：31
     */
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);

    /**
     * 用位运算计算出12位能存储的最大正整数：4095
     */
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */

    /**
     * 机器标志较序列号的偏移量
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;

    /**
     * 数据中心较机器标志的偏移量
     */
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;

    /**
     * 时间戳较数据中心的偏移量
     */
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private static long datacenterId;  //数据中心
    private static long machineId;    //机器标识
    private static long sequence = 0L; //序列号
    private static long lastStmp = -1L;//上一次时间戳

    /**
     * 此处无参构造私有，同时没有给出有参构造，在于避免以下两点问题：
     * 1、私有化避免了通过new的方式进行调用，主要是解决了在for循环中通过new的方式调用产生的id不一定唯一问题问题，因为用于记录上一次时间戳的lastStmp永远无法得到比对；
     * 2、没有给出有参构造在第一点的基础上考虑了一套分布式系统产生的唯一序列号应该是基于相同的参数
     */
    private SnowFlakeUtil() {
    }

    public static long getMachineId() {
        return machineId;
    }

    public static void setMachineId(long machineId) {
        SnowFlakeUtil.machineId = machineId;
    }

    public static long getDatacenterId() {
        return datacenterId;
    }

    public static void setDatacenterId(long datacenterId) {
        SnowFlakeUtil.datacenterId = datacenterId;
    }

    /**
     * 产生下一个ID
     *
     * @return id
     */
    public static synchronized long nextId() {
        // 获取当前时间戳
        long currStmp = getCurrentTimestamp();

        // 如果当前时间戳小于上次时间戳则抛出异常
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }
        // 相同毫秒内
        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                // 获取下一时间的时间戳并赋值给当前时间戳
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }
        // 当前时间戳存档记录，用于下次产生id时对比是否为相同时间戳
        lastStmp = currStmp;

        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT      //数据中心部分
                | machineId << MACHINE_LEFT            //机器标识部分
                | sequence;                            //序列号部分
    }

    /**
     * 获取下一时间的时间戳
     *
     * @return 下一时间戳
     */
    private static long getNextMill() {
        long mill = getCurrentTimestamp();
        while (mill <= lastStmp) {
            mill = getCurrentTimestamp();
        }
        return mill;
    }

    /**
     * 获取当前的时间戳
     *
     * @return 当前时间戳
     */
    private static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }
}

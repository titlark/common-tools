package com.titlark.utils;

import java.lang.reflect.Field;

public class ObjectUtil {
    private ObjectUtil() {
    }

    /**
     * 判断对象是否为null
     *
     * @param object 目标对象类型
     * @return true：为空 false：不为空
     */
    public static boolean isNull(Object object) {
        if (null == object) return true;
        if (object instanceof String) {
            return ((String) object).trim().isEmpty();
        }
        return false;
    }

    /**
     * 判断对象是否不为null
     *
     * @param object 目标对象类型
     * @return true：为空 false：不为空
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * 判断对象是否有一个为空
     *
     * @param objects
     * @return true：为空 false：不为空
     */
    public static boolean isAnyNull(Object... objects) {
        if (null == objects) return true;
        for (Object object : objects) {
            if (isNull(object)) return true;
        }
        return false;
    }

    /**
     * 判断对象是否都不为空
     *
     * @param objects
     * @return true：为空 false：不为空
     */
    public static boolean isNotNull(Object... objects) {
        return !isAnyNull(objects);
    }

    /**
     * 比较两个数组对象是否相等（比较对象里面的元素值）
     *
     * @param src 数组1
     * @param dst 数组2
     * @param <T> 数组对象
     * @return true：相等 false：不相等
     */
    public static <T> boolean isEquals(T[] src, T[] dst) {
        // 类型不一致， false
        if (!src.getClass().getComponentType().toString().equals(dst.getClass().getComponentType().toString())) {
            return false;
        }
        // 类型一致的情况下大小不一致，false
        if (src.length != dst.length) {
            return false;
        }
        // 类型一致的情况下都为空，true
        if (src.length == 0) {
            return true;
        }
        // 类型一致大小一致的情况下，比较每一位的内容，分为基础类型和自定义类型
        if (src[0].getClass().isPrimitive()) {
            for (int i = 0; i < src.length; i++) {
                if (!src[i].toString().equals(dst[i].toString())) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < src.length; i++) {
                // 均为空，true
                if (src[i] == null && dst[i] == null) {
                    continue;
                }
                // 一方为空，false
                if ((src[i] == null && dst[i] != null) || (src[i] != null && dst[i] == null)) {
                    return false;
                }
                // 均不为空，对比每一个字段
                Field[] fields = src[i].getClass().getDeclaredFields();
                for (Field field : fields) {
                    try {
                        field.setAccessible(true);
                        if (!field.get(src[i]).toString().equals(field.get(dst[i]).toString())) {
                            return false;
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            }
        }
        return true;
    }
}

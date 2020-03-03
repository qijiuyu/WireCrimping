package com.bdkj.wirecrimping.util;

/**
 * 参数校验工具类
 * Created by UserBean on 2016/9/26.
 */
public class CheckUtils {

    /**
     * 校验参数不能为Null
     * @param obj
     */
    public static void notNull(Object... obj) {
        notNull(null, obj);
    }

    /**
     * 校验参数不能为Null
     * @param msg 提示信息
     * @param obj
     */
    public static void notNull(String msg, Object... obj) {
        if (!isNotNull(obj)) {
            throw new NullPointerException(msg);
        }
    }

    /**
     * 校验参数是否全部不为null
     * @param obj
     * @return
     */
    public static boolean isNotNull(Object... obj) {

        if (obj == null) {
            return false;
        }

        for (Object o : obj) {
            if (o == null) {
                return false;
            }
        }

        return true;
    }

}

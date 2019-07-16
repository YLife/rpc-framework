package com.yl.util;

/**
 * @description 通用工具类
 *
 * @version v1.1.0
 * @author yanglun
 * @date  2019/7/16 23:16
 * Modification History:
 *   Date           Author          Version            Description
 *-------------------------------------------------------------
 *    2019/7/16      yanglun            v1.0.0              修改原因
 */
public class CommonUtils {

    /**
     * @Function: com.yl.util.CommonUtil::isEmptyArray
     * @description 判断数组是否为空
     *
     * @version v1.1.0
     * @author yanglun
     * @date  2019/7/16 23:16
     * Modification History:
     *   Date           Author          Version            Description
     *-------------------------------------------------------------
     *    2019/7/16      yanglun            v1.0.0              修改原因
     */
    public static boolean isEmptyArray(Object[] objects) {
        return objects == null || objects.length == 0;
    }

    /**
     * @Function: com.yl.util.CommonUtil::isEmptyString
     * @description 判断字符串是否为空
     *
     * @version v1.1.0
     * @author yanglun
     * @date  2019/7/16 23:18
     * Modification History:
     *   Date           Author          Version            Description
     *-------------------------------------------------------------
     *    2019/7/16      yanglun            v1.0.0              修改原因
     */
    public static boolean isEmptyString(String str) {
        return str == null || "".equals(str);
    }
}

package com.zyy.soap.result;

/**
 * Error 帮助类
 *
 * @author zhaoyang
 * @time 2016年09月01日
 * @date 2016年11月1日 修改返回错误信息，以兼容过去的版本；
 */
public class ResultUtil {


    /**
     * 判断是否为错误信息
     *
     * @param data
     * @return
     */
    public static boolean isError(String data) {
        //数据为空时，也为错误数据
        if (null == data || data.length() == 0) {
            return true;
        }
        if (data.startsWith("err-")) {
            return true;
        }

        if (data.startsWith("HTTP request failed")) {
            return true;
        }


        return false;
    }

}

package com.zyy.soap.improved;

import java.util.Map;

/**
 * Rx日志记录
 * Created by zhaoyang on 2017/3/28.
 */

public class RxLog {
    private static final String TAG = "SoapLog ";

    private static boolean DEBUG = false;

    public static void setDEBUG(boolean DEBUG) {
        RxLog.DEBUG = DEBUG;
    }

    public static boolean isDEBUG() {
        return DEBUG;
    }

    public static void log(String namespace,
                           String endPoint,
                           String methodName,
                           Map<String, Object> params, String result) {
        log(namespace, endPoint, methodName, params, result, null);
    }

    public static void log(String namespace,
                           String endPoint,
                           String methodName,
                           Map<String, Object> params, String result, Exception e) {
        if (DEBUG) {
            System.err.println(TAG + "=================开始=====================");
            //如果为调试模式，则打印数据
            System.err.println(TAG + "1.命名空间：" + namespace);
            System.err.println(TAG + "2.访问地址：" + endPoint);
            System.err.println(TAG + "3.接口名称：" + methodName);
            System.err.println(TAG + "4.参数信息：");
            if (null != params && params.size() > 0) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    System.err.println(TAG + "  [" + entry.getKey() + "," + String.valueOf(entry.getValue()) + "]");
                }
            } else {
                System.err.println(TAG + "4.没有参数");
            }
            System.err.println(TAG + "5.返回结果：" + result);
            if (null != e) {
                log(e);
            }
            System.err.println(TAG + "=================结束=====================");


        }
    }

    public static void log(Exception e) {
        if (DEBUG) {
            System.err.println(TAG + "请求数据异常：" + e.getMessage());
            e.printStackTrace();
        }
    }
}

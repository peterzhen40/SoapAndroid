package com.zyy.soap.result;

import com.google.gson.JsonParseException;

import android.text.TextUtils;

import com.zyy.soap.Soap;

import org.json.JSONException;
import org.ksoap2.SoapFault;
import org.ksoap2.SoapFault12;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

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

    /**
     * 是否成功
     * 不可删，兼容旧版本
     *
     * @param data
     * @return
     */
    public static boolean isOk(String data) {
        if (null != data && data.equals("ok")) {
            return true;
        }
        return false;
    }

    /**
     * 不可删，兼容旧版本
     * @param e
     * @return
     */
    public static String convertExceptionToString(Exception e) {
        String suffix = "err-3333:";
        if (e instanceof SocketTimeoutException) {
            return suffix + "网络连接超时";
        } else if (e instanceof XmlPullParserException) {
            return suffix + "XML文件解析失败";
        } else if (e instanceof SoapFault12 || e instanceof SoapFault) {
            return suffix + "服务器出现异常";
        } else if (e instanceof IOException) {
            return suffix + "网络连接异常";
        }

        return suffix + e.getLocalizedMessage();
    }

    /**
     * 没有数据
     * 不可删，兼容旧版本
     * @return
     */
    public static String convertNoData() {
        String suffix = "err-3333:";
        return suffix + "没有数据";
    }

    /**
     * 解析返回错误信息
     * 不可删，兼容旧版本
     * @param data
     * @return
     */
    public static String getError(String data, Soap.SYSTEM system) {
        if (null == data || data.length() == 0) {
            return "err-4444:返回数据为空";
        }
        String err = "";
        if (data.startsWith("err-0000")) {
            return data; //服务器错误返回所有的信息
        } else {
            try {
                return data.split(":")[1];
            } catch (Exception e) {
                // 兼容以前的版本
                if (!Soap.SYSTEM.JDYZB.equals(system)) {
                    //民爆类项目
                    if (ResultState.mErrorMap.containsKey(data)) {
                        return ResultState.getErrorState(data);
                    } else {
                        return "解析错误,请联系管理员";
                    }
                } else {
                    //剧毒类项目
                    if (ResultStateForJDYzb.errMap.containsKey(data)) {
                        return ResultStateForJDYzb.errMap.get(data);
                    } else {
                        return data;
                    }
                }
            }
        }
    }

    /** =======================================新的错误处理======================================================== */

    /**
     * 民爆系统的错误处理
     * @param e
     * @return
     */
    public static String getErrorMsgForMB(Throwable e) {
        return getErrorMessage(e, Soap.SYSTEM.MINBAO);
    }

    /**
     * 剧毒易制爆系统的错误处理
     * @param e
     * @return
     */
    public static String getErrorMsgForJDYZB(Throwable e) {
        return getErrorMessage(e, Soap.SYSTEM.JDYZB);
    }

    /**
     * 新增 错误处理
     *
     * @param e
     * @param system 民爆系统还是剧毒易制爆系统
     * @return
     */
    private static String getErrorMessage(Throwable e, Soap.SYSTEM system) {
        if (e != null) {
            if (e instanceof HttpException) {
                HttpException httpException = (HttpException) e;
                switch (httpException.code()) {
                    case 403:
                        return "403:用户过期了，请重新登录";
                    case 401:
                        return "401:未授权";
                    case 404:
                        return "404:网页不存在";
                    case 405:
                        return "405:方法不被允许";
                    case 408:
                        return "408:等待服务器返回文件超时";
                    default:
                        return httpException.message();
                }
            } else if (e instanceof UnknownHostException) {
                return "连接失败";
            } else if (e instanceof JSONException
                    || e instanceof JsonParseException) {
                return "Json解析失败";
            } else if (e instanceof SocketTimeoutException) {
                return "连接超时";
            } else {
                String message = e.getMessage();
                return getErrorCodeMsg(message, system);
            }
        }
        return "";
    }

    /**
     * 新增 错误码处理
     *
     * @param errorStr
     * @param system
     * @return
     */
    public static String getErrorCodeMsg(String errorStr, Soap.SYSTEM system) {
        if (TextUtils.isEmpty(errorStr)) {
            return "错误信息为空";
        }

        //err开头
        if (errorStr.startsWith("err")) {
            //形式：err-4444:返回数据为空，返回后一段
            String errorMsg = "";
            try {
                errorMsg = errorStr.split(":")[1];
            } catch (Exception e) {
                e.printStackTrace();
                //没有后一段，errorStr即错误码err-4444
                if (system == Soap.SYSTEM.MINBAO) {
                    if (ResultState.mErrorMap.containsKey(errorStr)) {
                        errorMsg = ResultState.mErrorMap.get(errorStr);
                    } else {
                        errorMsg = "未知错误：" + errorStr;
                    }
                } else if (system == Soap.SYSTEM.JDYZB) {
                    if (ResultStateForJDYzb.errMap.containsKey(errorStr)) {
                        errorMsg = ResultStateForJDYzb.errMap.get(errorStr);
                    } else {
                        errorMsg = "未知错误：" + errorStr;
                    }
                }
            }
            return errorMsg;
        } else {
            //不是error开头，直接返回
            return errorStr;
        }
    }

}

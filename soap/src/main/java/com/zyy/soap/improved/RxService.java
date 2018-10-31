package com.zyy.soap.improved;

import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.OkHttpTransportSE;
import org.ksoap2.transport.ServiceConnection;

import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * RxCallWebService Created by zhaoyang on 2017/3/28.
 */

public class RxService {

    /**
     * 实际请求，加入client,加入剧毒/民爆系统的判断不同 okhttp3.8.1崩溃，弃用 client
     */
    @Deprecated
    public static String call(String namespace, String endPoint, String methodName,
                              Map<String, Object> params, OkHttpClient client, boolean isNewSystem, int timeout)
            throws Exception {
        if (null == client) {
            return call(namespace, endPoint, methodName, params, isNewSystem, timeout);
        } else {
            try {
                SoapPrimitive resultObject = null;
                // 指定WebService的命名空间和调用的方法名
                SoapObject request = new SoapObject(namespace, methodName);

                // 设置需调用WebService接口需要传入的两个参数
                if (params != null && !params.isEmpty()) {
                    for (Map.Entry<String, Object> e : params.entrySet()) {
                        if (e.getValue() instanceof byte[]) {
                            byte[] d = (byte[]) e.getValue();
                            String data = Base64.encode(d);
                            request.addProperty(e.getKey(), data);
                        } else {
                            request.addProperty(e.getKey(), e.getValue());
                        }
                    }
                }
                // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.bodyOut = request;
                // 设置是否调用的是dotNet开发的WebService
                envelope.dotNet = false;
                OkHttpTransportSE ht = new OkHttpTransportSE(client, null, endPoint,
                        ServiceConnection.DEFAULT_TIMEOUT, -1);
                if (!isNewSystem) {
                    ht.call(namespace + methodName, envelope);
                } else {
                    ht.call(null, envelope);
                }
                if (envelope.getResponse() != null) {
                    resultObject = (SoapPrimitive) envelope.getResponse();
                }
                String resultStr = (null == resultObject ? "" : resultObject.toString());
                if (null == resultStr || resultStr.length() == 0) {
                    resultStr = "返回结果为空";
                }
                RxLog.log(namespace, endPoint, methodName, params, resultStr);
                return resultStr;
            } catch (Exception e) {
                String returnStr = e.getMessage();
                RxLog.log(namespace, endPoint, methodName, params, returnStr, e);
                throw e;
            }
        }
    }

    public static String call(String namespace, String endPoint, String methodName,
                              Map<String, Object> params, boolean isNewSystem, int timeout) {

        try {
            SoapPrimitive resultObject = null;
            // 指定WebService的命名空间和调用的方法名
            SoapObject request = new SoapObject(namespace, methodName);

            // 设置需调用WebService接口需要传入的两个参数
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, Object> e : params.entrySet()) {
                    if (e.getValue() instanceof byte[]) {
                        byte[] d = (byte[]) e.getValue();
                        String data = Base64.encode(d);
                        request.addProperty(e.getKey(), data);
                    } else {
                        request.addProperty(e.getKey(), e.getValue());
                    }
                }
            }
            // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;

            // 设置是否调用的是dotNet开发的WebService
            envelope.dotNet = false;

            HttpTransportSE ht = new HttpTransportSE(endPoint, timeout);
            if (!isNewSystem) {
                ht.call(namespace + methodName, envelope);
            } else {
                ht.call(null, envelope);
            }
            if (envelope.getResponse() != null) {
                resultObject = (SoapPrimitive) envelope.getResponse();
            }
            String resultStr = (null == resultObject ? "" : resultObject.toString());
            if (null == resultStr || resultStr.length() == 0) {
                resultStr = "返回结果为空";
            }
            RxLog.log(namespace, endPoint, methodName, params, resultStr);
            return resultStr;
        } catch (Exception e) {
            String returnStr = e.getMessage();
            RxLog.log(namespace, endPoint, methodName, params, returnStr, e);
            return returnStr;
        }
    }

}

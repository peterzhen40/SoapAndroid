package com.zyy.soap.improved;

import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;

import java.util.Map;

import okhttp3.HttpUrl;

/**
 * RxCallWebService
 * Created by zhaoyang on 2017/3/28.
 */

public class RxService {

    /**
     * 不在此捕获错误，直接抛出
     * @param namespace
     * @param endPoint
     * @param methodName
     * @param params
     * @param isNewSystem
     * @param timeout
     * @return
     * @throws Exception
     */
    public static String call(String namespace, String endPoint, String methodName, Map<String, Object> params,
                              boolean isNewSystem, int timeout, boolean isHttps) throws Exception {

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

        //新增Https支持
        if (isHttps) {
            //host + ":" + port + file = endPoint
            //https://192.168.11.109:7001/cxf/cbswWebService
            HttpUrl httpUrl = HttpUrl.parse(endPoint);
            String host = httpUrl.host();
            int port = httpUrl.port();
            String file = httpUrl.encodedPath();
            HttpsTransportSE ht = new HttpsTransportSE(host, port, file, timeout);
            if (!isNewSystem) {
                ht.call(namespace + methodName, envelope);
            } else {
                ht.call(null, envelope);
            }
        } else {
            //增加超时
            HttpTransportSE ht = new HttpTransportSE(endPoint, timeout);
            if (!isNewSystem) {
                ht.call(namespace + methodName, envelope);
            } else {
                ht.call(null, envelope);
            }
        }

        if (envelope.getResponse() != null) {
            resultObject = (SoapPrimitive) envelope.getResponse();
        }
        String resultStr = (null == resultObject ? "" : resultObject.toString());
        RxLog.log(namespace, endPoint, methodName, params, resultStr);
        return resultStr;
    }

}

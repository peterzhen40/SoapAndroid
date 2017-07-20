package com.zyy.soap.impl;



import com.zyy.soap.result.ResultUtil;

import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;

import static com.zyy.soap.service.SoapService.DEBUG;


/**
 * Created by zhaoyang on 2016/12/17.
 */

public class SoapRun {

    public static final String TAG = "SoapService";

    public static String callWebservice2(String namespace, String endPoint, String methodName,
                                         Map<String, Object> params) {
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

            HttpTransportSE ht = new HttpTransportSE(endPoint);

            ht.call(namespace + methodName, envelope);
            if (envelope.getResponse() != null) {
                resultObject = (SoapPrimitive) envelope.getResponse();
            } else {
                resultObject = null;
            }
            String resultStr = (null == resultObject ? "" : resultObject.toString());

            if (null != resultStr && resultStr.length() > 0) {
                return resultStr;
            } else {
                return ResultUtil.convertNoData();
            }
        } catch (Exception e) {
            System.err.println("=================有异常导致结束=====================" + e.toString());
            return ResultUtil.convertExceptionToString(e);
        }
    }

    public static SoapPrimitive callWebservice(String namespace, String endPoint, String methodName,
                                               Map<String, Object> params)
            throws SocketTimeoutException, SoapFault, IOException, XmlPullParserException {
        if (DEBUG) {
            //如果为调试模式，则打印数据
            System.err.println("1.命名空间：" + namespace);
            System.err.println("2.访问地址：" + endPoint);
            System.err.println("3.接口名称：" + methodName);
            System.err.println("4.参数信息：");

            if (null != params && params.size() > 0) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    System.err.println("\t[" + entry.getKey() + "，" + String.valueOf(entry.getValue()) + "]");
                }
            } else {
                System.err.println("没有参数");
            }

        }
        SoapPrimitive resultObject = null;
        // 指定WebService的命名空间和调用的方法名
        SoapObject request = new SoapObject(namespace, methodName);

        // 设置需调用WebService接口需要传入的两个参数
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> e : params.entrySet()) {
                if (e.getValue() instanceof byte[]) {
                    byte[] d = (byte[]) e.getValue();
                    String data = new String(Base64.encode(d));
                    request.addProperty(e.getKey(), data);
                } else {
                    request.addProperty(e.getKey().toString(), e.getValue());
                }
            }
        }
        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = request;

        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = false;

        HttpTransportSE ht = new HttpTransportSE(endPoint);
        // issue: The given SOAPAction does not match an operation.' faultactor:
        // 'null' detail: null
        // ht.call(namespace + methodName, envelope);
        /**
         * 对应新框架 2016-0901（目前深圳民爆）
         */
        ht.call(namespace + methodName, envelope);

        if (envelope.getResponse() != null) {
            resultObject = (SoapPrimitive) envelope.getResponse();
        } else {
            resultObject = null;
        }
        if (DEBUG) {
            String resultStr = resultObject.toString();
            if (null != resultStr) {
                System.err.println("5.返回结果: " + resultStr);
            }
        }
        return resultObject;
    }

    public static SoapPrimitive callWebservice(String namespace, String endPoint, String methodName,
                                               Map<String, Object> params, int timeout)
            throws SocketTimeoutException, SoapFault, IOException, XmlPullParserException {
        if (DEBUG) {
            //如果为调试模式，则打印数据
            System.err.println("1.命名空间：" + namespace);
            System.err.println("2.访问地址：" + endPoint);
            System.err.println("3.接口名称：" + methodName);
            System.err.println("4.参数信息：");

            if (null != params && params.size() > 0) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    System.err.println("Key=" + entry.getKey() + "\tValue=" + String.valueOf(entry.getValue()));
                }
            } else {
                System.err.println("没有参数");
            }

        }
        SoapPrimitive resultObject = null;
        // 指定WebService的命名空间和调用的方法名
        SoapObject request = new SoapObject(namespace, methodName);

        // 设置需调用WebService接口需要传入的两个参数
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> e : params.entrySet()) {
                if (e.getValue() instanceof byte[]) {
                    byte[] d = (byte[]) e.getValue();
                    String data = new String(Base64.encode(d));
                    request.addProperty(e.getKey(), data);
                } else {
                    request.addProperty(e.getKey().toString(), e.getValue());
                }
            }
        }
        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = request;

        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = false;

        HttpTransportSE ht = new HttpTransportSE(endPoint, timeout);
        // issue: The given SOAPAction does not match an operation.' faultactor:
        // 'null' detail: null
        // ht.call(namespace + methodName, envelope);
        /**
         * 对应新框架 2016-0901（目前深圳民爆）
         */
        ht.call(namespace + methodName, envelope);
        if (envelope.getResponse() != null) {
            resultObject = (SoapPrimitive) envelope.getResponse();
        } else {
            resultObject = null;
        }
        if (DEBUG) {
            String resultStr = resultObject.toString();
            if (null != resultStr) {
                System.err.println("5.返回结果: " + resultStr);
            }
        }
        return resultObject;
    }
}

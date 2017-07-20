package com.zyy.soap.service;

/**
 * Soap调用的配置
 * Created by zhaoyang on 2016/12/17.
 */

public class SoapService {

    public static boolean DEBUG = false;

    public String defaultNameSpace = "http://dao.ws.cbsw.cn/";

    private String baseUrl = "http://14.23.69.2:1051/cxf/cbswWebService/";
    private static SoapService instantce;

    public static SoapService getInstance() {
        if (null == instantce) {
            instantce = new SoapService();
        } else {
            synchronized (instantce) {
                if (null == instantce) {
                    instantce = new SoapService();
                }
            }
        }
        return instantce;
    }

    public void setDEBUG(boolean DEBUG) {
        this.DEBUG = DEBUG;
    }

    public static void init(String baseUrl, boolean DEBUG) {
        SoapService service = SoapService.getInstance();
        service.setBaseUrl(baseUrl);
        service.setDEBUG(DEBUG);
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public String getNameSpace() {
        return defaultNameSpace;
    }


}

package com.zyy.soap;

/**
 * Soap调用的配置
 * Created by zhaoyang on 2016/12/17.
 */

public class SoapService {

    public static boolean DEBUG;

    public String defaultNameSpace = "";

    private String baseUrl = "";
    private static SoapService instantce;

    public static SoapService getInstance() {
        if (null == instantce) {
            instantce = new SoapService();
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

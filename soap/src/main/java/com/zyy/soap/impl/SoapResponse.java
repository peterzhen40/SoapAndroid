package com.zyy.soap.impl;


import com.zyy.soap.interfaces.ISoapResponse;

/**
 * Created by zhaoyang on 2016/12/17.
 */

public class SoapResponse<T> implements ISoapResponse {

    private T t;

    public void setResponse(T t) {
        this.t = t;
    }

    @Override
    public T getResponse() {
        return t;
    }
}

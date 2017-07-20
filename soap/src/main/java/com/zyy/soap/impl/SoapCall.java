package com.zyy.soap.impl;



import com.zyy.soap.interfaces.Callback;
import com.zyy.soap.interfaces.ISoapCall;
import com.zyy.soap.interfaces.ISoapRequest;
import com.zyy.soap.interfaces.ISoapResponse;

import java.io.IOException;



/**
 * Created by zhaoyang on 2016/12/17.
 */

public class SoapCall<T> implements ISoapCall<T> {

    private ISoapRequest request;
    private ISoapResponse response;


    public void init(ISoapRequest request, ISoapResponse response) {
        this.request = request;
        this.response = response;
    }

    public void setRequest(SoapRequest request) {
        this.request = request;
    }

    public void setResponse(SoapResponse response) {
        this.response = response;
    }

    @Override
    public ISoapResponse<T> execute() throws IOException {
        String result = SoapRun.callWebservice2(request.getNameSpace(),
                request.getEndPoint(), request.getMethodName(), request.getParams());
        SoapResponse<String> response = new SoapResponse<>();
        response.setResponse(result);
        return response;
    }

    @Override
    public void enqueue(Callback<T> callback) {

    }
}

package com.zyy.soap.invocationhandlers;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.zyy.soap.interfaces.ISoapRequest;
import com.zyy.soap.service.SoapService;
import com.zyy.soap.utils.AnnonationsUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class SoapInvocationHandler implements InvocationHandler {

    private Class<?> target;

    public SoapInvocationHandler() {
        super();
    }

    public SoapInvocationHandler(Class<?> target) {
        super();
        this.target = target;
    }


    @Override
    public Observable<?> invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        ISoapRequest mSoapRequest = AnnonationsUtil.transformInvokeToRequest(target, method, args
                , SoapService.getInstance().getBaseUrl());
        //SoapCall<?> call = AnnonationsUtil.transformInvokeToCall(target, method);
        //call.init(mSoapRequest, new SoapResponse());
        final Type returnType = method.getGenericReturnType();
        Observable<?> observable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                String result = "hello,World";
                TypeAdapter<?> adapter = new Gson().getAdapter(TypeToken.get(returnType));
                emitter.onNext(adapter.fromJson(result));
            }
        }).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io());
        return observable;
    }

    public static <T> T newMapperProxy(Class<T> mapperInterface) {
        ClassLoader classLoader = mapperInterface.getClassLoader();
        Class<?>[] interfaces = new Class[]{mapperInterface};
        SoapInvocationHandler proxy = new SoapInvocationHandler(mapperInterface);
        return (T) Proxy.newProxyInstance(classLoader, interfaces, proxy);
    }

} 
package com.zyy.soap;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import com.zyy.soap.factory.DefaultCallFactory;
import com.zyy.soap.factory.ICallFactory;
import com.zyy.soap.improved.RxLog;
import com.zyy.soap.improved.RxService;
import com.zyy.soap.interfaces.ISoapRequest;
import com.zyy.soap.result.ResultUtil;
import com.zyy.soap.utils.AnnonationsUtil;
import com.zyy.soap.utils.Utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;


/**
 * 用于Webservice protocol
 * <example>
 * Soap soap =new Soap.Builder() .baseUrl("http://127.0.0.1") .create(); Api api = soap.create(Api.class);
 * api.login("username","password","name") ..... ......
 * </example>
 * Created by zhaoyang on 2017/1/19.
 */
@SuppressWarnings("unchecked")
public final class Soap {

    private String baseUrl = "";
    private ICallFactory callFactory;
    private boolean isNewSoap;
    private int timeout;
    private boolean isHttps;
    private SYSTEM builderSystem = SYSTEM.MINBAO;

    public enum SYSTEM {MINBAO, JDYZB}

    public Soap(String baseUrl, ICallFactory callFactory, boolean isNewSoap, int timeout, boolean isHttps) {
        this.baseUrl = baseUrl;
        this.callFactory = callFactory;
        this.isNewSoap = isNewSoap;
        this.timeout = timeout;
        this.isHttps = isHttps;
    }

    public static void setDEBUG(boolean DEBUG) {
        RxLog.setDEBUG(DEBUG);
    }

    public static final class Builder {
        private String baseUrl;
        private ICallFactory callFactory = DefaultCallFactory.create();
        private boolean isNew = false;//是不是新系统（jdk1.8）
        private int timeout = 1000 * 10;
        private boolean isHttps = false;
        private SYSTEM builderSystem = SYSTEM.MINBAO;

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder isNewSoap(boolean isNew) {
            this.isNew = isNew;
            if (isNew) {
                this.builderSystem = SYSTEM.JDYZB;
            } else {
                this.builderSystem = SYSTEM.MINBAO;
            }
            return this;
        }

        public Builder system(SYSTEM sys) {
            this.builderSystem = sys;
            this.isNew = sys == SYSTEM.JDYZB;
            return this;
        }


        public Builder callFactory(ICallFactory callFactory) {
            this.callFactory = callFactory;
            return this;
        }

        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder isHttps(boolean isHttps) {
            this.isHttps = isHttps;
            return this;
        }

        public Soap build() {
            Soap soap = new Soap(baseUrl, callFactory, isNew, timeout, isHttps);
            soap.builderSystem = this.builderSystem;
            return soap;
        }
    }

    /**
     * 基本的URL
     */

    public <T> T create(final Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, final Method method, final Object... args)
                            throws Throwable {
                        // If the method is a method from Object then defer to normal invocation.
                        if (method.getDeclaringClass() == Object.class) {
                            return method.invoke(this, args);
                        }
                        if (callFactory == null) {
                            throw new IllegalArgumentException("call facotry must be instantce");
                        }
                        Class<?> responseClass = method.getReturnType();
                        if (responseClass == Observable.class) {
                            return callFactory.convert(createObservable(service, method, baseUrl, args));
                        } else if (responseClass == Flowable.class) {
                            return callFactory.convert(createFloawable(service, method, baseUrl, args));
                        } else if (responseClass == String.class) {
                            ISoapRequest mSoapRequest = AnnonationsUtil.
                                    transformInvokeToRequest(service, method, args, baseUrl);
                            return RxService.call(
                                    mSoapRequest.getNameSpace(),
                                    mSoapRequest.getEndPoint(),
                                    mSoapRequest.getMethodName(),
                                    mSoapRequest.getParams(), isNewSoap, timeout, isHttps);
                        } else {
                            throw new IllegalArgumentException("unknown return type,you must use Observable,Flowable," +
                                    "String");
                        }
                    }
                });
    }

    private Observable createObservable(final Class<?> service, final Method method,
                                        final String baseUrl, final Object... args) {
        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                Type responseType = Utils.getCallResponseType(method.getGenericReturnType());
                try {
                    ISoapRequest mSoapRequest = AnnonationsUtil.
                            transformInvokeToRequest(service, method, args, baseUrl);
                    String result = RxService.call(
                            mSoapRequest.getNameSpace(),
                            mSoapRequest.getEndPoint(),
                            mSoapRequest.getMethodName(),
                            mSoapRequest.getParams(), isNewSoap, timeout, isHttps);
                    if (!ResultUtil.isError(result)) {
                        Gson gson = new Gson();
                        if (responseType != String.class) {
                            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(responseType));
                            if (!emitter.isDisposed()) {
                                emitter.onNext(adapter.fromJson(result));
                            }
                        } else {
                            if (!emitter.isDisposed()) {
                                emitter.onNext(result);
                            }
                        }
                        if (!emitter.isDisposed()) {
                            emitter.onComplete();
                        }

                    } else {
                        if (!emitter.isDisposed()) {
                            emitter.onError(new Throwable(result));
                            RxLog.log(mSoapRequest.getNameSpace(), mSoapRequest.getEndPoint(),
                                    mSoapRequest.getMethodName(),
                                    mSoapRequest.getParams(), result, new Exception(ResultUtil.getErrorCodeMsg(result
                                            , builderSystem)));
                        }
                    }
                } catch (Exception e) {
                    if (!emitter.isDisposed()) {
                        emitter.onError(e);
                        try {
                            ISoapRequest mSoapRequest = AnnonationsUtil.
                                    transformInvokeToRequest(service, method, args, baseUrl);
                            RxLog.log(mSoapRequest.getNameSpace(), mSoapRequest.getEndPoint(),
                                    mSoapRequest.getMethodName(),
                                    mSoapRequest.getParams(), "", e);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            RxLog.log(e1);
                        }
                    }
                }
            }
        });
    }

    Flowable createFloawable(final Class<?> service, final Method method,
                             final String baseUrl, final Object... args) {
        return Flowable.create(new FlowableOnSubscribe() {
            @Override
            public void subscribe(@NonNull FlowableEmitter emitter) throws Exception {
                Type responseType = Utils.getCallResponseType(method.getGenericReturnType());
                try {
                    ISoapRequest mSoapRequest = AnnonationsUtil.
                            transformInvokeToRequest(service, method, args, baseUrl);
                    String result = RxService.call(
                            mSoapRequest.getNameSpace(),
                            mSoapRequest.getEndPoint(),
                            mSoapRequest.getMethodName(),
                            mSoapRequest.getParams(), isNewSoap, timeout, isHttps);
                    if (!ResultUtil.isError(result)) {
                        Gson gson = new Gson();
                        if (responseType != String.class) {
                            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(responseType));
                            if (!emitter.isCancelled()) {
                                emitter.onNext(adapter.fromJson(result));
                            }
                        } else {
                            if (!emitter.isCancelled()) {
                                emitter.onNext(result);
                            }
                        }
                        if (!emitter.isCancelled()) {
                            emitter.onComplete();
                        }

                    } else {
                        if (!emitter.isCancelled()) {
                            emitter.onError(new Throwable(result));
                            RxLog.log(mSoapRequest.getNameSpace(), mSoapRequest.getEndPoint(),
                                    mSoapRequest.getMethodName(),
                                    mSoapRequest.getParams(), result, new Exception(ResultUtil.getErrorCodeMsg(result
                                            , builderSystem)));
                        }
                    }
                } catch (Exception e) {
                    //在此捕获异常
                    if (!emitter.isCancelled()) {
                        emitter.onError(e);
                        try {
                            ISoapRequest mSoapRequest = AnnonationsUtil.
                                    transformInvokeToRequest(service, method, args, baseUrl);
                            RxLog.log(mSoapRequest.getNameSpace(), mSoapRequest.getEndPoint(),
                                    mSoapRequest.getMethodName(),
                                    mSoapRequest.getParams(), "", e);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            RxLog.log(e1);
                        }
                    }
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}




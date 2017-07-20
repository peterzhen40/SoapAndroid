package com.zyy.soap.improved;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zyy.soap.Soap;
import com.zyy.soap.result.ResultUtil;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


/**
 * Created by zhaoyang on 2017/3/14.
 */

public class RxApiUtil {


    @SuppressWarnings({"unchecked"})
    public static Observable get(final String methodName,
                                 final LinkedHashMap<String, Object> params, final TypeToken typeToken) {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) {
                try {
                    String namespace = Soap.NAMESPACE;
                    String endpoint = Soap.ENDPOINT;
                    String data = RxService.call(namespace, endpoint, methodName, params);
                    if (ResultUtil.isError(data)) {
                        throw new Exception(ResultUtil.getError(data));
                    }
                    Type returnType = typeToken.getType();
                    Gson gson = new Gson();
                    if (!e.isDisposed()) {
                        e.onNext(gson.fromJson(data, returnType));
                        e.onComplete();
                    }
                } catch (Exception exception) {
                    if (!e.isDisposed()) {
                        e.onError(exception);
                    }
                }
            }
        });
    }

    @SuppressWarnings({"unchecked"})
    public static Observable get(final String methodName,
                                 final LinkedHashMap<String, Object> params, final Type type) {
        return (Observable<?>) Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) {
                try {
                    String namespace =Soap.NAMESPACE;
                    String endpoint = Soap.ENDPOINT;
                    String data = RxService.call(namespace, endpoint, methodName, params);
                    if (ResultUtil.isError(data)) {
                        throw new Exception(ResultUtil.getError(data));
                    }
                    Gson gson = new Gson();
                    if (!e.isDisposed()) {
                        if (type != String.class) {
                            e.onNext(gson.fromJson(data, type));
                        } else {
                            e.onNext(data);
                        }
                        e.onComplete();
                    }

                } catch (Exception exception) {
                    if (!e.isDisposed()) {
                        e.onError(exception);
                    }
                }
            }
        });
    }

    @SuppressWarnings({"unchecked"})
    public static Observable post(final String methodName,
                                  final LinkedHashMap<String, Object> params) {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) {
                try {
                    String namespace = Soap.NAMESPACE;
                    String endpoint = Soap.ENDPOINT;
                    String data = RxService.call(namespace, endpoint, methodName, params);
                    if (ResultUtil.isError(data)) {
                        throw new Exception(ResultUtil.getError(data));
                    }
                    if (ResultUtil.isOk(data)) {
                        if (!e.isDisposed()) {
                            e.onNext(data);
                        }
                    }
                    if (!e.isDisposed()) {
                        e.onComplete();
                    }
                } catch (Exception exception) {
                    if (!e.isDisposed()) {
                        e.onError(exception);
                    }
                }
            }
        });
    }

}

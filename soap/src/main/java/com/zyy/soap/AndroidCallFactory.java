package com.zyy.soap;


import com.zyy.soap.factory.ICallFactory;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 配置Factory可以配置一些对Observable的配置
 *
 * @author zhaoyang
 */
public class AndroidCallFactory implements ICallFactory {

    public static AndroidCallFactory create() {
        return new AndroidCallFactory();
    }

    @Override
    public Observable convert(Observable observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
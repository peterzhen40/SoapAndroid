package com.zyy.soap.factory;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Java App ,Android App use different Factory
 * Created by zhaoyang on 2017/3/31.
 */

public interface ICallFactory {


    Observable convert(Observable observable);

    Flowable convert(Flowable flowable);
}

package zhaoyang.soap;

import com.zyy.soap.AndroidCallFactory;
import com.zyy.soap.Soap;

/**
 * Soap使用相关的定义均在此，其他的网络请求功能可以移除
 * Created by zhaoyang on 2017/3/31.
 */

public class Api {

    public static ILoginService createLogin() {
        Soap soap = new Soap.Builder()
                .baseUrl("http://14.23.69.2:1076")
                .callFactory(AndroidCallFactory.create())
                .build();
        return soap.create(ILoginService.class);
    }
}
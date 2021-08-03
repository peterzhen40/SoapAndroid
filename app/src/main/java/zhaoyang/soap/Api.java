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
                .baseUrl(MainActivity.BASE_URL)
                .system(Soap.SYSTEM.JDYZB)
                .timeout(20*1000)
                .callFactory(AndroidCallFactory.create())
                .build();
        return soap.create(ILoginService.class);
    }

    public static KtxService ktxService() {
        Soap soap = new Soap.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .system(Soap.SYSTEM.JDYZB)
                .timeout(20*1000)
                .callFactory(AndroidCallFactory.create())
                .build();
        try {
            return soap.create(KtxService.class);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
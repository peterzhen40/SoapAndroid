package zhaoyang.soap;


import com.zyy.soap.annonations.WebParam;
import com.zyy.soap.annonations.WebService;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * 广州民爆
 * WSDL不显示参数名,类型及返回值类型时,
 * 需增加webservice interface和webservice impl的@webservice注解的targetNamespace属性。同时接口方法参数前@WebParam注解。
 * <p>
 * 如果使用KSoap2Util等SOAP工具调用,注意保持参数顺序一致.
 *
 * @author Administrator
 */
@WebService(targetNamespace = "http://dao.ws.cbsw.cn/",
        targetEndPoint = "cxf/loginService")
public interface ILoginService {
    /**
     * @param username 用户名称
     * @param password 密码
     * @param shebei   设备 1为手机app,2为平板
     * @return {"compNo":"唯一sn","compName":"单位名","compId":"单位id","compBm":"单位区域编码","userMobile":"用户电话","userSex":"男"
     * "userRealName":"用户实名","userId":"用户ID","compNature":"单位类型,可以用方法判断是否公安","userType":"用户类型,1系统管理员2单位管理员3普通用户4爆破项目人员"}或err-xxxx
     */
    Flowable<UserInfo> login(@WebParam(name = "username") String username,
                             @WebParam(name = "password") String password,
                             @WebParam(name = "shebei") String shebei);

    /**
     * 退出登录
     *
     * @param username 用户名
     * @param shebei   设备表示码:1为手机app,2为平板,3苹果
     * @return "ok" 或 "err-"
     */
    String logout(@WebParam(name = "username") String username, @WebParam(name = "shebei") String shebei);
}
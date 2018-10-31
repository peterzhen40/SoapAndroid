package zhaoyang.soap;


import com.zyy.soap.annonations.WebParam;
import com.zyy.soap.annonations.WebService;

import io.reactivex.Flowable;

/**
 * 当前类注释:
 * Author: zhenyanjun
 * Date  : 2018/3/7 14:41
 */
@WebService(targetNamespace = "http://dao.ws.cbsw.cn/", targetEndPoint = "cxf/cbswWebService")
public interface ICommonService {

    /**
     *
     * @param username 用户名称
     * @param password	密码
     * @param shebei	设备 1为手机app,2为平板
     * @return {"compNo":"唯一sn","compName":"单位名","compId":"单位id","compBm":"单位区域编码","userMobile":"用户电话","userSex":"男"
     * "userRealName":"用户实名","userId":"用户ID","compNature":"单位类型,可以用方法判断是否公安","userType":"用户类型,1系统管理员2单位管理员3普通用户4爆破项目人员"}或err-xxxx
     */
    Flowable<UserInfo> login(
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password,
            @WebParam(name = "shebei") String shebei);
    /**
     * 退出登录
     * @param username 用户名
     * @param shebei 设备表示码:1为手机app,2为平板
     * @return "ok" 或 "err-"
     */
    Flowable<String> logout(
            @WebParam(name = "username") String username,
            @WebParam(name = "shebei") String shebei);

    /**
     * 获取最新版本
     * @return
     * {"qzUpdate":是否强制更新0否1是,"versionNum":"版本号","versionName":"版本名称","versionAddress":"版本下载地址","versionExplain":"版本说明"}
     * {"qzUpdate":0,"versionNum":"2","versionName":"2.0","versionAddress":"http://www.baidu.com/ggggggg","versionExplain":"dsafadsgsg"}
     */
    Flowable<String> getNewVersion();

    /**=======================================道路运输========================================================*/

}

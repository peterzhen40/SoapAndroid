package zhaoyang.soap

import com.zyy.soap.annonations.WebParam
import com.zyy.soap.annonations.WebService
import io.reactivex.Flowable
import retrofit2.Call

/**
 * 当前类注释:
 * @author zhenyanjun
 * @date   2021/8/3 14:30
 */
@WebService(targetNamespace = "http://dao.ws.cbsw.cn/", targetEndPoint = "cxf/cbswWebService")
interface KtxService {

    suspend fun login(
        @WebParam(name = "username") username: String?,
        @WebParam(name = "password") password: String?,
        @WebParam(name = "shebei") shebei: String?
    ): String

    //fun login(
    //    @WebParam(name = "username") username: String?,
    //    @WebParam(name = "password") password: String?,
    //    @WebParam(name = "shebei") shebei: String?
    //): Flowable<UserInfo>
}
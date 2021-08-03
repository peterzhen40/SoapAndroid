package zhaoyang.soap

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.xsj.crasheye.Crasheye
import com.zyy.soap.Soap
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * 当前类注释:
 * @author zhenyanjun
 * @date   2021/8/3 14:16
 */
class KotlinTestActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    val BASE_URL = MainActivity.BASE_URL

    override fun onCreate(savedInstanceState: Bundle?) {

        //testplus
        Crasheye.init(this, "eab1f0f0")
        Soap.setDEBUG(BuildConfig.DEBUG)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
        val etAddress = findViewById<EditText>(R.id.et_address)
        val etUsername = findViewById<EditText>(R.id.et_username)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val button = findViewById<Button>(R.id.button)

        etAddress.setText(BASE_URL)
        etUsername.setText("test")
        etPassword.setText("88888")


        button.setOnClickListener {

            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            launch(Dispatchers.IO) {
                try {
                    val result = Api.ktxService()
                            .login(username, password, "1")

                    val userInfo = Gson().fromJson(result, UserInfo::class.java)
                    Log.d("test", userInfo.toString())

                    val result2 = Api.ktxService().getBpjhListForPeisong(userInfo.getCompNo(), "","","","","","")
                    Log.d("test", result2.toString())
                } catch (e: Exception) {
                    withContext(Dispatchers.Main){
                        textView.text = e.message
                    }
                    Log.e("test", e.message, e)
                }
            }

            //Api.ktxService().login(username, password, "1")
            //    .subscribeOn(Schedulers.io())
            //    .subscribe(object : Subscriber<UserInfo>{
            //        override fun onSubscribe(s: Subscription?) {
            //        }
            //
            //        override fun onNext(t: UserInfo?) {
            //
            //        }
            //
            //        override fun onError(t: Throwable?) {
            //        }
            //
            //        override fun onComplete() {
            //        }
            //
            //    })
        }

    }
}
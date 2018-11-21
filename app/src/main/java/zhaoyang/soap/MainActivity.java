package zhaoyang.soap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zyy.soap.Soap;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static String BASE_URL = "http://192.168.11.109:7001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Soap.setDEBUG(BuildConfig.DEBUG);
        setContentView(R.layout.activity_main);
        final TextView textView = findViewById(R.id.textView);
        final EditText etAddress = findViewById(R.id.et_address);
        final EditText etUsername = findViewById(R.id.et_username);
        final EditText etPassword = findViewById(R.id.et_password);
        etAddress.setText(BASE_URL);
        etUsername.setText("test");
        etPassword.setText("88888");

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = etAddress.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (!TextUtils.isEmpty(address) && isValidUrl(address)) {
                    BASE_URL = address;
                    if ( !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                        Api.commonService().login(username, password, "1")
                                .subscribe(new Subscriber<UserInfo>() {
                                    @Override
                                    public void onSubscribe(Subscription s) {
                                        Log.d(TAG, "onSubscribe: ");
                                        s.request(Long.MAX_VALUE);
                                    }

                                    @Override
                                    public void onNext(UserInfo userInfo) {
                                        textView.setText(userInfo.toString());
                                        Log.d(TAG, "onNext: " + userInfo.toString());
                                    }

                                    @Override
                                    public void onError(Throwable t) {
                                        textView.setText(t.getMessage());
                                        Log.d(TAG, "onError: ", t);
                                    }

                                    @Override
                                    public void onComplete() {
                                        Log.d(TAG, "onComplete: ");
                                    }
                                });
                    }

                }

            }
        });
    }

    public static boolean isValidUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        if (Patterns.WEB_URL.matcher(url).matches()) {
            try {
                new Retrofit.Builder()
                        .baseUrl(url)
                        .build();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}

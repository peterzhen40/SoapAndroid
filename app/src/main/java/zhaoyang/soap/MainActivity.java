package zhaoyang.soap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.zyy.soap.Soap;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Soap.setDEBUG(BuildConfig.DEBUG);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api.createLogin()
                        .login("csyyx1", "88888", "1").subscribeWith(new Subscriber<UserInfo>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe: ");
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        Log.d(TAG, "onNext: " + userInfo.toString());
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });

            }
        });
    }
}

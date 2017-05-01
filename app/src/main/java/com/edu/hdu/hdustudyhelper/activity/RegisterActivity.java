package com.edu.hdu.hdustudyhelper.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.edu.hdu.hdustudyhelper.R;
import com.edu.hdu.hdustudyhelper.model.ResultReturn;
import com.edu.hdu.hdustudyhelper.network.NetWork;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {

    private Button register;
    private Subscription subscription;
    private String name = "Tetrewr";
    private String email = "123@qq.com";
    private String password = "qwerty321";
    private String contact = "bsdfasd";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(name, email, password, contact);
            }
        });
    }
    Observer<ResultReturn> observer = new Observer<ResultReturn>() {
        @Override
        public void onCompleted() {
            //Toast.makeText(LoginActivity.this,"onCompleted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Throwable e) {
//            dismissLoadingViewwdingView();
            Toast.makeText(RegisterActivity.this, "onError:" + e.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(ResultReturn result) {
//            dismissLoadingView();
            Toast.makeText(RegisterActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            if (result.isSuccess()) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        }
    };

    private void registerUser(final String name, final String email,
                              final String password, final String contact) {
        subscription = NetWork.getRegisterApi()
                .register(name, email, contact, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

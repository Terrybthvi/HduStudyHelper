package com.edu.hdu.hdustudyhelper.network;

/**
 * Created by leiqi on 2017/4/30.
 */

import retrofit2.Converter;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetWork {
    private static LoginApi loginApi;
    private static RegisterApi registerApi;
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private static String BASE_URL = "http://bthvi.cn/";

    public static LoginApi getloginApi() {
        if (loginApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(httpclient.getOkHttpClient())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            loginApi = retrofit.create(LoginApi.class);
        }
        return loginApi;
    }


    public static RegisterApi getRegisterApi() {
        if (registerApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(httpclient.getOkHttpClient())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            registerApi = retrofit.create(RegisterApi.class);
        }
        return registerApi;
    }

}


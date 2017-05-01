package com.edu.hdu.hdustudyhelper.network;

import com.edu.hdu.hdustudyhelper.model.ResultReturn;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by leiqi on 2017/4/30.
 */

public interface LoginApi {
    @FormUrlEncoded
    @POST("/LoginServer/login.php")
    Observable<ResultReturn> login(@Field("email") String email,
                                   @Field("password") String password);
}

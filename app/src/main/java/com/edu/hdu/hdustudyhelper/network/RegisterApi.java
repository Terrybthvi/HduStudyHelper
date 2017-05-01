package com.edu.hdu.hdustudyhelper.network;

import com.edu.hdu.hdustudyhelper.model.ResultReturn;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by leiqi on 2017/4/30.
 */

public interface RegisterApi {
    @FormUrlEncoded
    @POST("/HduStudyApp/RxLoginAndRegister/LoginServer/register.php")
    Observable<ResultReturn> register(@Field("name") String name,
                                      @Field("email") String email,
                                      @Field("contact") String contact,
                                      @Field("password") String password);
}

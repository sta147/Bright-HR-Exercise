package com.kayani.brighthr.login.network;

import com.kayani.brighthr.login.entity.UserDataEntity;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NetworkService {

    @FormUrlEncoded
    @POST("api/Account/PostValidateUser")
    Call<UserDataEntity> doLogin(@Field("username") String username,
                                 @Field("password") String password);

    @DELETE("api/Account/Logout")
    Call<Void> doLogout();

}

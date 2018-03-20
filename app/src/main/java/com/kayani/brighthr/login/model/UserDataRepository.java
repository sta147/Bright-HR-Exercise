package com.kayani.brighthr.login.model;

import com.kayani.brighthr.login.entity.UserDataEntity;
import com.kayani.brighthr.login.entity.UserEntity;
import com.kayani.brighthr.login.network.NetworkService;

import java.net.HttpURLConnection;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@Singleton
public class UserDataRepository {

    @Inject
    public UserDataRepository() {
    }

    public void login(final UserEntity userEntity, final LoginUseCase.CallBack loginUseCase, Retrofit retrofit) {

        Call<UserDataEntity> users = retrofit.create(NetworkService.class).doLogin(userEntity.getUsername(), userEntity.getPassword());
        users.enqueue(new Callback<UserDataEntity>() {

            @Override
            public void onResponse(Call<UserDataEntity> call, Response<UserDataEntity> response) {
                loginUseCase.onResult(response.body(), response.code());
            }

            @Override
            public void onFailure(Call<UserDataEntity> call, Throwable t) {
                loginUseCase.onResult(null, HttpURLConnection.HTTP_BAD_METHOD);
            }
        });
    }
}
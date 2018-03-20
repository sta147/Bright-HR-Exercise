package com.kayani.brighthr.login.interactor;


import android.util.Log;

import com.kayani.brighthr.login.entity.UserEntity;
import com.kayani.brighthr.login.model.LoginUseCase;
import com.kayani.brighthr.login.model.UserDataRepository;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class LoginUser implements LoginUseCase {

    Retrofit retrofit;
    private UserDataRepository usersRepository;
    private UserEntity userEntity;

    @Inject
    public LoginUser(UserEntity userEntity, Retrofit retrofit, UserDataRepository _usersRepository){
        this.usersRepository = _usersRepository;
        this.retrofit = retrofit;
        this.userEntity = userEntity;
    }

    @Override
    public void execute(CallBack callBack) {
        this.usersRepository.login(userEntity, callBack, retrofit);
        Log.d("#","Executing use case");
    }

}
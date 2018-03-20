package com.kayani.brighthr.login.network;

import android.content.Context;

import com.kayani.brighthr.login.entity.UserDataEntity;
import com.kayani.brighthr.login.entity.UserEntity;

public class SessionController {
    private Context mContext;
    private NetworkService mNetworkService;

    public SessionController() {
//        mContext = context;
    }

    private synchronized NetworkService getNetworkService() {
        return NetworkFactory.getApiService();
    }

    public void doLogin(final UserEntity userEntity, final NetworkListener<UserDataEntity> listener) {
        getNetworkService()
                .doLogin(userEntity.getUsername(), userEntity.getPassword())
                .enqueue(listener);
    }
}

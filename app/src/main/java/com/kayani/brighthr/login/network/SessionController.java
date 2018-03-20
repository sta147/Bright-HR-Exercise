package com.kayani.brighthr.login.network;

import com.kayani.brighthr.login.entity.UserDataEntity;
import com.kayani.brighthr.login.entity.UserEntity;

public class SessionController {

    public SessionController() {
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

package com.kayani.brighthr.login.network.wrapper;

import com.kayani.brighthr.login.entity.UserEntity;

public class UserWrapper {

    private UserEntity user;

    public UserWrapper(UserEntity user) {
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}

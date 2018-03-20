package com.kayani.brighthr.login.model;

import com.kayani.brighthr.login.entity.UserDataEntity;

/**
 * Implements the login use case
 */
public interface LoginUseCase {

    void execute(CallBack callBack);

    interface CallBack {
        void onResult(UserDataEntity usersDataEntity, int errorCode);

        void onFailure(int errorCode);
    }
}

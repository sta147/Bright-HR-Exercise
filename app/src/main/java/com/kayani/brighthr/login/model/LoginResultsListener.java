package com.kayani.brighthr.login.model;

import com.kayani.brighthr.login.entity.UserDataEntity;

/**
 * Results listener companion to {@link LoginUseCase}
 */
public interface LoginResultsListener {

    /**
     * Success callback
     * @param userDataEntity
     */
    void onLoginSuccess(UserDataEntity userDataEntity);

    /**
     * Network error callback
     */
    void onNetworkError(int errorCode);

    /**
     * Validate error callback
     */
    void onValidationError();

    /**
     * Logging util method, just for debugging
     *
     * @param desc description
     */
    void logThreadState(String desc);
}

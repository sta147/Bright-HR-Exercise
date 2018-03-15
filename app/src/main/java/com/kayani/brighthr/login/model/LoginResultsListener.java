package com.kayani.brighthr.login.model;

/**
 * Results listener companion to {@link LoginUseCase}
 *
 */
public interface LoginResultsListener {

    /**
     * Success callback
     */
    void onLoginSuccess();

    /**
     * Network error callback
     */
    void onNetworkError();

    /**
     * Validate error callback
     */
    void onValidationError();

    /**
     * Logging util method, just for debugging
     * @param desc description
     */
    void logThreadState(String desc);
}

package com.kayani.brighthr.login;

import android.app.Application;

import com.kayani.brighthr.login.dagger.DaggerLoginComponent;
import com.kayani.brighthr.login.dagger.LoginComponent;

/**
 * Sample app to try out code and APIs.
 *
 */
public class LoginApplication extends Application {
    LoginComponent loginComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        loginComponent = DaggerLoginComponent.builder().build();
    }

    public LoginComponent getLoginComponent() {
        return loginComponent;
    }

}

package com.kayani.brighthr.login.dagger;

import com.kayani.brighthr.login.ui.login.view.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dagger Login Component
 *
 */
@Singleton
@Component(modules = LoginModule.class)
public interface LoginComponent {

    void inject(LoginActivity loginActivity);

}

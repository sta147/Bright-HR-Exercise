package com.kayani.brighthr.login.dagger;

import com.kayani.brighthr.login.model.LoginUseCase;
import com.kayani.brighthr.login.ui.login.presenter.LoginPresenter;
import com.kayani.brighthr.login.ui.login.presenter.LoginPresenterImpl;
import com.kayani.brighthr.login.util. AndroidLogger;
import com.kayani.brighthr.login.util.Logger;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger LoginModule
 *
 */
@Module
class LoginModule {

    @Provides static LoginUseCase provideLoginUseCase() {
        return new LoginUseCase();
    }


    @Provides static LoginPresenter provideLoginPresenter(LoginUseCase loginUseCase, Logger logger) {
        return new LoginPresenterImpl(loginUseCase, logger);
    }

    @Provides static Logger provideLogger() {
        return new AndroidLogger();
    }

}

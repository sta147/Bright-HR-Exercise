package com.kayani.brighthr.login.dagger;

import com.kayani.brighthr.login.model.UserDataRepository;
import com.kayani.brighthr.login.ui.login.presenter.LoginPresenter;
import com.kayani.brighthr.login.ui.login.presenter.LoginPresenterImpl;
import com.kayani.brighthr.login.util.AndroidLogger;
import com.kayani.brighthr.login.util.Logger;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger LoginModule
 *
 */
@Module
class LoginModule {

    @Provides static UserDataRepository provideLoginUseCase() {
        return new UserDataRepository();
    }


    @Provides static LoginPresenter provideLoginPresenter(UserDataRepository usersRepository, Logger logger) {
        return new LoginPresenterImpl(usersRepository, logger);
    }

    @Provides static Logger provideLogger() {
        return new AndroidLogger();
    }

}
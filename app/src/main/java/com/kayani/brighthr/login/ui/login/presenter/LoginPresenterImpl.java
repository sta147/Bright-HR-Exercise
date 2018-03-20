package com.kayani.brighthr.login.ui.login.presenter;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import com.kayani.brighthr.login.entity.UserDataEntity;
import com.kayani.brighthr.login.entity.UserEntity;
import com.kayani.brighthr.login.model.LoginResultsListener;
import com.kayani.brighthr.login.model.UserDataRepository;
import com.kayani.brighthr.login.network.NetworkListener;
import com.kayani.brighthr.login.network.ResultListener;
import com.kayani.brighthr.login.network.SessionController;
import com.kayani.brighthr.login.ui.login.view.LoginView;
import com.kayani.brighthr.login.util.Logger;

import java.net.HttpURLConnection;

import javax.inject.Inject;

/**
 * Basic {@link LoginPresenter} and {@link LoginResultsListener}, updates the {@link LoginView}
 * with login results.
 */
public class LoginPresenterImpl implements LoginPresenter, LoginResultsListener {
    private static final String LOG_TAG = "LoginPresenterImpl";

    @VisibleForTesting
    LoginView loginView;
    @VisibleForTesting
    boolean mLoginStarted;
    @VisibleForTesting
    Result mLoginResult;
    private UserDataRepository mUsersRepository;
    private String mEmail;
    private String mPassword;
    private Logger mLogger;

    /**
     * Create a new Login Presenter
     *
     * @param usersRepository
     */
    @Inject
    public LoginPresenterImpl(UserDataRepository usersRepository, Logger logger) {
        this.mUsersRepository = usersRepository;
        this.mLogger = logger;
    }

    /**
     * @param loginView View to update based on login results.
     */
    public void attachView(LoginView loginView) {
        mLogger.log(LOG_TAG, "Attaching view: " + loginView + ", to: " + this);
        this.loginView = loginView;
        loginView.setEmail(mEmail);
        loginView.setPassword(mPassword);

        if (!mLoginStarted) {
            mLogger.log(LOG_TAG, "login not started");
            loginView.hideProgress();
            return;
        }

        if (mLoginResult == null) {
            mLogger.log(LOG_TAG, "login started, no result present");
            loginView.showProgress();
            return;
        }

        mLogger.log(LOG_TAG, "login started, " + mLoginResult + " was present.");
        switch (mLoginResult) {
            case SUCCESS:
                onLoginSuccess(null);
                break;
            case NETWORK_ERROR:
                onNetworkError(HttpURLConnection.HTTP_FORBIDDEN);
                break;
            case VALIDATION_ERROR:
                onValidationError();
                break;
        }
    }

    public void detachView() {
        mLogger.log(LOG_TAG, "Detaching view: " + loginView + " from " + this);
        this.loginView = null;
    }

    @Override
    public void doLogin(String email, String password) {
        this.mEmail = email;
        this.mPassword = password;
        mLoginStarted = false;
        mLoginResult = null;

        if (Strings.isNullOrEmpty(email)) {
            loginView.showEmailRequiredError();
        } else if (Strings.isNullOrEmpty(password)) {
            loginView.showPasswordRequiredError();
        } else if (!isEmailValid(email)) {
            loginView.showEmailInvalidError();
        } else if (!isPasswordValid(password)) {
            loginView.showPasswordInvalidError();
        } else {
            mLoginStarted = true;
            final UserEntity userEntity = new UserEntity();
            userEntity.setUsername(email);
            userEntity.setPassword(password);

            final SessionController sessionController = new SessionController();
            sessionController
                    .doLogin(userEntity, new NetworkListener<>(new ResultListener<UserDataEntity>() {
                        @Override
                        public void onResult(UserDataEntity data, int resultCode) {
                            if (resultCode == HttpURLConnection.HTTP_OK) {
                                mLogger.log(LOG_TAG, "Logged in ");
                                onLoginSuccess(data);
                            } else {
                                onNetworkError(resultCode);
                            }
                            loginView.hideProgress();
                        }
                    }));
            loginView.showProgress();
        }
    }

    @Override
    public void bypassLogin() {
        loginView.navigateToLandingPage(null);
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    //region LoginResultsListener
    @Override
    public void onLoginSuccess(UserDataEntity data) {
        mLogger.log(LOG_TAG, "login success, view: " + loginView);
        mLoginResult = Result.SUCCESS;
        if (loginView != null) {
            loginView.navigateToLandingPage(data);
        }
    }

    @Override
    public void onNetworkError(int errorCode) {
        mLogger.log(LOG_TAG, "network error, view: " + loginView);
        mLoginResult = Result.NETWORK_ERROR;
        if (loginView != null) {
            loginView.hideProgress();
            loginView.showNetworkError(errorCode);
        }
    }

    @Override
    public void onValidationError() {
        mLogger.log(LOG_TAG, "validation error, view: " + loginView);
        mLoginResult = Result.VALIDATION_ERROR;
        if (loginView != null) {
            loginView.hideProgress();
            loginView.showValidationError();
        }
    }

    @Override
    public void logThreadState(String desc) {
        mLogger.log(LOG_TAG, "Thread: " + Thread.currentThread().getId() + ": " + desc);
    }

    enum Result {
        SUCCESS,
        NETWORK_ERROR,
        VALIDATION_ERROR
    }
    //endregion
}
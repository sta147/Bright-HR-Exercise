package com.kayani.brighthr.login.ui.login.presenter;

import com.kayani.brighthr.login.model.LoginUseCase;
import com.kayani.brighthr.login.ui.login.view.LoginView;
import com.kayani.brighthr.login.util.Logger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterImplShould {
    @Mock
    private LoginView loginView;
    @Mock
    private LoginUseCase loginUseCase;
    @Mock
    private Logger logger;
    private LoginPresenterImpl loginPresenter;
    private String VALID_EMAIL = "test@gmail.com";
    private String VALID_PASSWORD = "abcde";

    @Before
    public void setUp() {
        loginPresenter = new LoginPresenterImpl(loginUseCase, logger);
        loginPresenter.attachView(loginView);

        // Discard interactions with loginView during setup, so they don't throw off the counts for other methods under test
        reset(loginView);
    }

    @Test
    public void testAttachViewSetsViewEmailAndPassword() {
        loginPresenter.doLogin(VALID_EMAIL, VALID_PASSWORD);
        loginPresenter.attachView(loginView);
        verify(loginView).setEmail(VALID_EMAIL);
        verify(loginView).setPassword(VALID_PASSWORD);
    }

    @Test
    public void testAttachViewWithLoginNotStartedHidesProgressOnView() {
        loginPresenter.attachView(loginView);
        verify(loginView).hideProgress();
    }

    @Test
    public void testAttachViewWithLoginStartedAndNotCompletedShowsProgressOnView() {
        loginPresenter.mLoginStarted = true;
        loginPresenter.attachView(loginView);
        verify(loginView).showProgress();
    }

    @Test
    public void testAttachViewWithLoginStartedAndCompletedSuccessfullyNavigatesToLandingPage() {
        loginPresenter.mLoginStarted = true;
        loginPresenter.mLoginResult = LoginPresenterImpl.Result.SUCCESS;
        loginPresenter.attachView(loginView);
        verify(loginView).navigateToLandingPage(null);
    }

    @Test
    public void testAttachViewWithLoginStartedAndCompletedWithNetworkErrorHidesProgressViewAndShowsNetworkErrorOnView() {
        loginPresenter.mLoginStarted = true;
        loginPresenter.mLoginResult = LoginPresenterImpl.Result.NETWORK_ERROR;
        loginPresenter.attachView(loginView);
        verify(loginView).hideProgress();
        verify(loginView).showNetworkError();
    }

    @Test
    public void testAttachViewWithLoginStartedAndCompletedWithValidationErrorHidesProgressViewAndShowsValidationErrorOnView() {
        loginPresenter.mLoginStarted = true;
        loginPresenter.mLoginResult = LoginPresenterImpl.Result.VALIDATION_ERROR;
        loginPresenter.attachView(loginView);
        verify(loginView).hideProgress();
        verify(loginView).showValidationError();
    }

    @Test
    public void testDetachViewSetsViewToNull() {
        loginPresenter.detachView();
        assertNull(loginPresenter.loginView);
    }

    @Test
    public void testDoLoginWithNullEmailShowsEmailRequiredErrorOnView() {
        loginPresenter.doLogin(null, "password");
        verify(loginView).showEmailRequiredError();
    }

    @Test
    public void testDoLoginWithNullPasswordShowsPasswordRequiredErrorOnView() {
        loginPresenter.doLogin("email", null);
        verify(loginView).showPasswordRequiredError();
    }

    @Test
    public void testDoLoginWithInvalidEmailShowsInvalidEmailErrorOnView() {
        String INVALID_EMAIL = "tt_gmail.com";
        loginPresenter.doLogin(INVALID_EMAIL, VALID_PASSWORD);
        verify(loginView).showEmailInvalidError();
    }

    @Test
    public void testDoLoginWithInvalidPasswordShowsInvalidPasswordErrorOnView() {
        String INVALID_PASSWORD = "abcd";
        loginPresenter.doLogin(VALID_EMAIL, INVALID_PASSWORD);
        verify(loginView).showPasswordInvalidError();
    }

    @Test
    public void testDoLoginWithValidEmailAndPasswordDelegatesToUseCaseAndShowsProgressOnView() {
        loginPresenter.doLogin(VALID_EMAIL, VALID_PASSWORD);
        verify(loginUseCase).doLogin(VALID_EMAIL, VALID_PASSWORD, loginPresenter);
        verify(loginView).showProgress();
    }

    @Test
    public void testBypassLoginNavigatesToLandingPage() {
        loginPresenter.bypassLogin();
        verify(loginView).navigateToLandingPage(null);
    }

    @Test
    public void testOnLoginSuccessNavigatesToLandingPageOnSuccess() {
        loginPresenter.onLoginSuccess();
        verify(loginView).navigateToLandingPage(null);
    }

    @Test
    public void testOnNetworkErrorHidesProgressAndShowsNetworkErrorOnView() {
        loginPresenter.onNetworkError();
        verify(loginView).hideProgress();
        verify(loginView).showNetworkError();
    }

    @Test
    public void testOnValidationErrorHidesProgressAndShowsValidationErrorOnView() {
        loginPresenter.onValidationError();
        verify(loginView).hideProgress(); //
        verify(loginView).showValidationError();
    }
}

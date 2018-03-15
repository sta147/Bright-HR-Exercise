package com.kayani.brighthr.login.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.schedulers.Schedulers;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LoginUseCaseShould {
    private String userOneEmail = "foo@example.com";
    private String userOnePassword = "hello";
    private LoginUseCase loginUseCase;

    @Mock private LoginResultsListener resultsListener;


    @Before
    public void setUp() {
        loginUseCase = new LoginUseCase(Schedulers.immediate(), Schedulers.immediate());
    }

    @Test
    public void testDoLoginWithValidEmailPasswordCallsSuccessOnResultsListener() {
        loginUseCase.doLogin(userOneEmail, userOnePassword, resultsListener);
        verify(resultsListener).onLoginSuccess();
    }

    @Test
    public void testDoLoginWithWrongPasswordGivesError() {
        loginUseCase.doLogin(userOneEmail, "wrong", resultsListener);
        verify(resultsListener).onValidationError();
    }

    @Test
    public void testDoLoginWithUnknownEmailGivesError() {
        loginUseCase.doLogin("invalidUser", userOnePassword, resultsListener);
        verify(resultsListener).onValidationError();
    }

}

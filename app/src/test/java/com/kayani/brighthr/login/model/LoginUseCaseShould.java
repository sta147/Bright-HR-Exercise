package com.kayani.brighthr.login.model;

import com.kayani.brighthr.login.entity.UserEntity;
import com.kayani.brighthr.login.network.ResultListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.schedulers.Schedulers;

@RunWith(MockitoJUnitRunner.class)
public class LoginUseCaseShould {
    private String userOneEmail = "c_emp1@grr.la";
    private String userOnePassword = "123456";
    private LoginUseCase loginUseCase;
    private UserDataRepository userDataRepository;
    private UserEntity userEntity;

    @Mock
    private ResultListener resultsListener;

    @Mock
    private LoginUseCase.CallBack mResultsListener;


    @Before
    public void setUp() {
        loginUseCase = new LoginUseCase(Schedulers.immediate(), Schedulers.immediate());
//        userEntity.setUsername(userOneEmail);
//        userEntity.setPassword(userOnePassword);
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

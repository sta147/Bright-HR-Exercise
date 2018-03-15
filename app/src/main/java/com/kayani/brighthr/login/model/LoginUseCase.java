package com.kayani.brighthr.login.model;

import com.google.common.annotations.VisibleForTesting;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Implements the login use case - here we merely simulate network access and check credentials
 * against an in-memory list.
 *
 */
public class LoginUseCase {

    private final Scheduler subscribeOnScheduler;
    private final Scheduler observeOnScheduler;

    @VisibleForTesting
    LoginUseCase(Scheduler subscribeOnScheduler, Scheduler observeOnScheduler) {
        this.subscribeOnScheduler = subscribeOnScheduler;
        this.observeOnScheduler = observeOnScheduler;
    }

    public LoginUseCase() {
        this(Schedulers.newThread(), AndroidSchedulers.mainThread());
    }

    /**
     * Execute the login use case
     *
     * @param email           Email for login
     * @param password        Password for login
     * @param resultsListener Results listener for login
     */
    public void doLogin(final String email, final String password, final LoginResultsListener resultsListener) {
        rx.Observable<Boolean> myObservable = Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                resultsListener.logThreadState("fromCallable");
                return new UserRepository().loginUser(email, password);
            }});

        myObservable
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean b) {
                        resultsListener.logThreadState("success callback");
                        if(b) {
                            resultsListener.onLoginSuccess();
                        } else {
                            resultsListener.onValidationError();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        resultsListener.logThreadState("error callback");
                        resultsListener.onNetworkError();
                    }
                });
    }
}

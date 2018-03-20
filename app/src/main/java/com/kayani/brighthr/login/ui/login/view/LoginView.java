package com.kayani.brighthr.login.ui.login.view;

import com.kayani.brighthr.login.entity.UserDataEntity;

/**
 * View for the login screen. Informs the user of any errors, progress, and navigates to the
 * landing page upon success.
 *
 */
public interface LoginView {

    void setEmail(String email);

    void setPassword(String password);

    void showEmailRequiredError();

    void showPasswordRequiredError();

    void showPasswordInvalidError();

    void showEmailInvalidError();

    void showNetworkError(int errorCode);

    void showValidationError();

    void showProgress();

    void hideProgress();

    void navigateToLandingPage(UserDataEntity data);
}

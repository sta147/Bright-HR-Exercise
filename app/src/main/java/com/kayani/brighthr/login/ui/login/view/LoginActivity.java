package com.kayani.brighthr.login.ui.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kayani.brighthr.login.LoginApplication;
import com.kayani.brighthr.login.R;
import com.kayani.brighthr.login.entity.UserDataEntity;
import com.kayani.brighthr.login.ui.landing.LandingPageActivity;
import com.kayani.brighthr.login.ui.login.presenter.LoginPresenter;

import java.net.HttpURLConnection;

import javax.inject.Inject;

/**
 * An email/password login screen.
 */
public class LoginActivity extends AppCompatActivity implements LoginView {

    @Inject
    LoginPresenter mLoginPresenter;

    private EditText mEmailView;
    private EditText mPasswordView;
    private TextInputLayout mPasswordLayout;
    private View mProgressView;
    private View mFormView;
    private Button mSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((LoginApplication) getApplication()).getLoginComponent().inject(this);

        setContentView(R.layout.activity_login);

        mEmailView = (EditText) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mEmailView.addTextChangedListener(getWatcher());
        mPasswordView.addTextChangedListener(getWatcher());

        mPasswordLayout = findViewById(R.id.password_layout);

        mSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    @NonNull
    private TextWatcher getWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // only show signin button if both email and password are not empty
                final boolean isSignInEnabled = !TextUtils.isEmpty(mEmailView.getText().toString())
                        && !TextUtils.isEmpty(mPasswordView.getText().toString());
                mSignInButton.setEnabled(isSignInEnabled);
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mLoginPresenter.attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mLoginPresenter.detachView();
    }

    private void attemptLogin() {
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        mLoginPresenter.doLogin(email, password);
    }

    //region LoginView methods
    @Override
    public void setEmail(String email) {
        mEmailView.setText(email);
    }

    @Override
    public void setPassword(String password) {
        mPasswordView.setText(password);
    }

    @Override
    public void showEmailRequiredError() {
        mEmailView.setError(getString(R.string.error_field_required));
        mEmailView.requestFocus();
    }

    @Override
    public void showPasswordRequiredError() {
        mPasswordLayout.setError(getString(R.string.error_field_required));
        mPasswordView.requestFocus();
    }

    @Override
    public void showPasswordInvalidError() {
        mPasswordLayout.setError(getString(R.string.error_invalid_password));
        mPasswordView.requestFocus();
    }

    @Override
    public void showEmailInvalidError() {
        mEmailView.setError(getString(R.string.error_invalid_email));
        mEmailView.requestFocus();
    }

    @Override
    public void navigateToLandingPage(UserDataEntity data) {
        mFormView.setVisibility(View.GONE);
        mProgressView.setVisibility(View.GONE);

        final Intent landingPageIntent = new Intent(this, LandingPageActivity.class);
        landingPageIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        landingPageIntent.putExtra(LandingPageActivity.EXTRA_COMPANY_TIMEZONE_NAME, data.getCompanyTimeZoneName());
        startActivity(landingPageIntent);
    }

    @Override
    public void showNetworkError(int errorCode) {
        if (errorCode == HttpURLConnection.HTTP_FORBIDDEN) {
            //403
            mPasswordLayout.setError(getString(R.string.error_forbidden));
        } else {
            //Other errors
            mPasswordLayout.setError(getString(R.string.error_network));
        }
        mPasswordView.requestFocus();
    }

    @Override
    public void showValidationError() {
        mPasswordLayout.setError(getString(R.string.error_incorrect_password));
        mPasswordView.requestFocus();
    }

    @Override
    public void showProgress() {
        mProgressView.setVisibility(View.VISIBLE);
        mFormView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mProgressView.setVisibility(View.GONE);
        mFormView.setVisibility(View.VISIBLE);
    }
    //endregion

}


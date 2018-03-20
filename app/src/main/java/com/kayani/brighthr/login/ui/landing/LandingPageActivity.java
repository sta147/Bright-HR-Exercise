package com.kayani.brighthr.login.ui.landing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.kayani.brighthr.login.R;
import com.kayani.brighthr.login.ui.login.view.LoginActivity;

/**
 * Landing page for app, displayed after a successful login.
 */
public class LandingPageActivity extends AppCompatActivity {
    public static final String EXTRA_COMPANY_TIMEZONE_NAME = "EXTRA_COMPANY_TIMEZONE_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        final String companyTimezoneName = getIntent().getParcelableExtra(EXTRA_COMPANY_TIMEZONE_NAME);
        final TextView welcomeText = findViewById(R.id.success);
        if (!TextUtils.isEmpty(companyTimezoneName)) {
            welcomeText.setText(getString(R.string.welcome_message, companyTimezoneName));
        }

        final Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> {
            // Since we don't maintain login state, we just go to the login page
            final Intent loginIntent = new Intent(LandingPageActivity.this, LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(loginIntent);
        });
    }
}

package com.kayani.brighthr.login.ui.landing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.kayani.brighthr.login.R;
import com.kayani.brighthr.login.ui.login.view.LoginActivity;

/**
 * Landing page for app, displayed after a successful login.
 */
public class LandingPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        final Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Since we don't maintain login state, we just go to the login page
                final Intent loginIntent = new Intent(LandingPageActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }
}

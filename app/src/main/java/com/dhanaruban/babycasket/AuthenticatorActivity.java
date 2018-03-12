package com.dhanaruban.babycasket;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.amazonaws.mobile.auth.ui.SignInUI;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

/**
 * Created by dhanaruban on 20/02/18.
 */

public class AuthenticatorActivity extends Activity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        Log.i(LOG_TAG,this.toString());
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                SignInUI signin = (SignInUI) AWSMobileClient.getInstance()
                        .getClient(AuthenticatorActivity.this, SignInUI.class);
                signin.login(AuthenticatorActivity.this, MainActivity.class).execute();
            }
        }).execute();
    }
}

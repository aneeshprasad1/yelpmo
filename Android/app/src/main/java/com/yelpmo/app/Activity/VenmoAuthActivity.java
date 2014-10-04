package com.yelpmo.app.Activity;

import android.net.Uri;
import android.os.Bundle;

/**
 * Created by Corey on 10/4/14.
 */
public class VenmoAuthActivity extends BaseActivity {

    private String ACCESS_TOKEN_PARAMETER = "access_token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleAuthToken();
    }

    private void handleAuthToken() {
        Uri data = getIntent().getData();
        String token = data.getQueryParameter(ACCESS_TOKEN_PARAMETER);

    }
}

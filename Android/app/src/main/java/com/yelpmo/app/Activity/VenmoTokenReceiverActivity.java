package com.yelpmo.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.squareup.otto.Produce;
import com.yelpmo.app.Event.VenmoAuthorizedEvent;
import com.yelpmo.app.Preferences.UserDetails;

/**
 * Created by Corey on 10/4/14.
 */
public class VenmoTokenReceiverActivity extends BaseActivity {

    private String ACCESS_TOKEN_PARAMETER = "access_token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleAuthToken();
    }

    private void handleAuthToken() {
        Uri data = getIntent().getData();
        String token = data.getQueryParameter(ACCESS_TOKEN_PARAMETER);
        UserDetails.setHasVenmo(true);
        UserDetails.setVenmoToken(token);
        Intent iStartMainActivity = new Intent(this, MainActivity.class);
        iStartMainActivity.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(iStartMainActivity);
        finish();
    }

    @Produce
    public VenmoAuthorizedEvent notifyVenmoAuthorized() {
        return new VenmoAuthorizedEvent();
    }
}

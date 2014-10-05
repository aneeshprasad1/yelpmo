package com.yelpmo.app.Venmo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Corey on 10/4/14.
 */
public class Venmo {

    private static String AUTHORIZATION_URL = "https://api.venmo.com/v1/oauth/authorize?client_id=2004" +
            "&scope=make_payments%20access_profile&response_type=token";

    public static void makeAuthorizationRequest(Activity activity) {
        Intent iCreateAuthorizationRequest = new Intent(Intent.ACTION_VIEW);
        iCreateAuthorizationRequest.setData(Uri.parse(AUTHORIZATION_URL));
        activity.startActivity(iCreateAuthorizationRequest);
    }

}

package com.yelpmo.app.Activity;

import android.app.Activity;

import com.yelpmo.app.BusProvider;

/**
 * Created by Corey on 10/3/14.
 */
public class BaseActivity extends Activity {

    @Override
    protected void onStart() {
        super.onStart();
        BusProvider.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        BusProvider.unregister(this);
    }
}

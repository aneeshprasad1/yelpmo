package com.yelpmo.app.Fragment;

import android.app.Fragment;

import com.yelpmo.app.BusProvider;

/**
 * Created by Corey on 10/3/14.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.unregister(this);
    }
}

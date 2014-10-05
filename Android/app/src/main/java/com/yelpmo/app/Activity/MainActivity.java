package com.yelpmo.app.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.squareup.otto.Subscribe;
import com.yelpmo.app.Constants.ParseInfo;
import com.yelpmo.app.Event.UserLoggedInEvent;
import com.yelpmo.app.Event.UserSignedUpEvent;
import com.yelpmo.app.Event.VenmoAuthorizedEvent;
import com.yelpmo.app.Fragment.LogInFragment;
import com.yelpmo.app.Fragment.MealsFragment;
import com.yelpmo.app.Fragment.SignUpFragment;
import com.yelpmo.app.Preferences.UserDetails;
import com.yelpmo.app.R;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initParse();
        authorizeUser();
    }

    private void authorizeUser() {
        if(UserDetails.isSignedIn()) {
            initMealsFragment();
        } else if (UserDetails.hasLogIn()) {
            initLogInFragment();
        } else {
            initSignUpFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                break;
            case R.id.action_new_meal:
                launchCameraForReceipt();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initMealsFragment() {
        MealsFragment mealsFrag = new MealsFragment();
        getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, mealsFrag)
                .commit();
    }

    private void initSignUpFragment() {
        SignUpFragment signUpFrag = new SignUpFragment();
        getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, signUpFrag)
                .commit();
    }

    private void initLogInFragment() {
        LogInFragment logInFrag = new LogInFragment();
        getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, logInFrag)
                .commit();
    }

    private void requestAuthorizeVenmo() {
        Intent iAuthorizeVenmo = new Intent(this, VenmoAuthActivity.class);
        startActivity(iAuthorizeVenmo);
    }

    private static final int CAMERA_REQUEST_CODE = 4200;

    private void launchCameraForReceipt() {
        Intent iLaunchCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(iLaunchCamera, CAMERA_REQUEST_CODE);
    }

    private void handleCameraResponse(Intent data) {
        Bitmap receiptPhoto = (Bitmap) data.getExtras().get("data");
        //OCR this!
    }

    private void initParse() {
        Parse.initialize(this, ParseInfo.APPLICATION_ID, ParseInfo.CLIENT_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case CAMERA_REQUEST_CODE:
                handleCameraResponse(data);
                break;
        }
    }

    @Subscribe
    public void onUserSignedUp(UserSignedUpEvent event) {
        initMealsFragment();
        if(!UserDetails.hasVenmo()) {
            requestAuthorizeVenmo();
        }
    }

    @Subscribe
    public void onUserLoggedIn(UserLoggedInEvent event) {
        initMealsFragment();
    }

    @Subscribe
    public void onVenmoAuthorized(VenmoAuthorizedEvent event) {
        //Show thx message
    }
}

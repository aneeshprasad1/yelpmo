package com.yelpmo.app.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.yelpmo.app.Constants.ParseInfo;
import com.yelpmo.app.Fragment.LogInFragment;
import com.yelpmo.app.Fragment.MealsFragment;
import com.yelpmo.app.Fragment.SignUpFragment;
import com.yelpmo.app.Preferences.UserDetails;
import com.yelpmo.app.R;

import java.util.List;


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
        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Meal");
        query.whereEqualTo("owner", currentUser);
        Log.d("user", currentUser.toString());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                Log.d("meals", parseObjects.toString());
                Log.d("meals", Integer.toString(parseObjects.size()));
                MealsFragment mealsFrag = new MealsFragment();
                getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, mealsFrag)
                        .commit();
            }
        });

    }

    public void initSignUpFragment() {
        SignUpFragment signUpFrag = new SignUpFragment();
        getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, signUpFrag)
                .commit();
    }

    public void initLogInFragment() {
        LogInFragment logInFrag = new LogInFragment();
        getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, logInFrag)
                .commit();
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
}

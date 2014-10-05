package com.yelpmo.app.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.parse.Parse;
import com.yelpmo.app.Application.App;
import com.yelpmo.app.Constants.ParseInfo;
import com.yelpmo.app.Fragment.LogInFragment;
import com.yelpmo.app.Fragment.MealsFragment;
import com.yelpmo.app.Fragment.SignUpFragment;
import com.yelpmo.app.OCR.OcrManager;
import com.yelpmo.app.Preferences.UserDetails;
import com.yelpmo.app.R;

import java.io.IOException;


public class MainActivity extends BaseActivity {

    private static Uri receiptPhotoUri;

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

    private static final int CAMERA_REQUEST_CODE = 4200;

    private void launchCameraForReceipt() {
        Intent iLaunchCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        receiptPhotoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new ContentValues());
        iLaunchCamera.putExtra(MediaStore.EXTRA_OUTPUT, receiptPhotoUri);
        startActivityForResult(iLaunchCamera, CAMERA_REQUEST_CODE);
    }

    private void handleCameraResponse() throws IOException {
        final Bitmap receiptPhoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), receiptPhotoUri);
        if(receiptPhoto == null) {
            return;
        }
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    TessBaseAPI baseAPI = new TessBaseAPI();
                    try {
                        baseAPI.init(OcrManager.getTesseractDataPath(App.getInstance()), "eng");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    baseAPI.setImage(receiptPhoto);
                    String text = baseAPI.getUTF8Text();
                    Log.d("RECEIPT", text);
                    baseAPI.end();
                }
            });
            thread.start();

    }

    private void initParse() {
        Parse.initialize(this, ParseInfo.APPLICATION_ID, ParseInfo.CLIENT_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case CAMERA_REQUEST_CODE:
                try {
                    handleCameraResponse();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}

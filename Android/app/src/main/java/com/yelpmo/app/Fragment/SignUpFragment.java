package com.yelpmo.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.yelpmo.app.Activity.MainActivity;
import com.yelpmo.app.BusProvider;
import com.yelpmo.app.Event.UserSignedUpEvent;
import com.yelpmo.app.Preferences.UserDetails;
import com.yelpmo.app.R;

/**
 * Created by Corey on 10/4/14.
 */
public class SignUpFragment extends BaseFragment implements View.OnClickListener {

    EditText etUsername, etPassword, etEmailAddress;
    Button btnSignUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_signup, null);
        return initializeView(contentView);
    }

    private View initializeView(View view) {
        etUsername = (EditText) view.findViewById(R.id.et_signup_username);
        etPassword = (EditText) view.findViewById(R.id.et_signup_password);
        etEmailAddress = (EditText) view.findViewById(R.id.et_signup_email);
        btnSignUp = (Button) view.findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(this);
        return view;
    }

    private void signUp() {
        String userName = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String emailAddress = etEmailAddress.getText().toString();
        ParseUser user = new ParseUser();
        user.setUsername(userName);
        user.setPassword(password);
        user.setEmail(emailAddress);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    UserDetails.setHasLogIn(true);
                    UserDetails.setSignedIn(true);
                    BusProvider.getBus().post(new UserSignedUpEvent());
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_signup:
                signUp();
                break;
        }
    }
}

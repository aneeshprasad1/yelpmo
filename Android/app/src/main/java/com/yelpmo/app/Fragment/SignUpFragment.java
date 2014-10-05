package com.yelpmo.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.yelpmo.app.Activity.MainActivity;
import com.yelpmo.app.R;

/**
 * Created by Corey on 10/4/14.
 */
public class SignUpFragment extends BaseFragment implements View.OnClickListener {

    EditText etUsername, etPassword, etEmailAddress;
    Button btnSignUp;
    TextView linkToSignIn;

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
        linkToSignIn = (TextView) view.findViewById(R.id.login_link);
        linkToSignIn.setOnClickListener(this);
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
                    Log.d("yeah", "working");
                    ((MainActivity) getActivity()).initMealsFragment();
                } else {
                    Log.d("boo", e.toString());
                }
            }
        });

    }

    private void switchToLogin() {
        ((MainActivity) getActivity()).initLogInFragment();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_signup:
                signUp();
                break;
            case R.id.login_link:
                switchToLogin();
                break;
        }
    }
}

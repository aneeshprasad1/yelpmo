package com.yelpmo.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseUser;
import com.yelpmo.app.Activity.MainActivity;
import com.yelpmo.app.R;

/**
 * Created by aneeshprasad on 10/4/14.
 */
public class LogInFragment extends BaseFragment implements View.OnClickListener{

    EditText etUsername, etPassword;
    Button btnLogIn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_login, null);
        return initializeView(contentView);
    }

    private View initializeView(View view) {
        etUsername = (EditText) view.findViewById(R.id.et_login_username);
        etPassword = (EditText) view.findViewById(R.id.et_login_password);
        btnLogIn = (Button) view.findViewById(R.id.btn_login);
        btnLogIn.setOnClickListener(this);
        return view;
    }


    private void logIn() {
        String userName = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        ParseUser.logInInBackground(userName, password, new LogInCallback() {
            public void done(ParseUser user, com.parse.ParseException e) {
                if (user != null) {
                    Log.d("yeah", "working"); // Hooray! The user is logged in.
                    ((MainActivity) getActivity()).initMealsFragment();
                } else {
                    Log.d("boo", e.toString()); // Signup failed. Look at the ParseException to see what happened.
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_login:
                logIn();
                break;
        }
    }
}

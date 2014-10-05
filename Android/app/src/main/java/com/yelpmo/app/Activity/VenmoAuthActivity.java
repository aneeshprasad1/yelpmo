package com.yelpmo.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yelpmo.app.R;
import com.yelpmo.app.Venmo.Venmo;

/**
 * Created by Corey on 10/4/14.
 */
public class VenmoAuthActivity extends BaseActivity implements View.OnClickListener {

    private Button btnLinkVenmo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venmo_auth);
        initializeView();
    }

    private void initializeView() {
        btnLinkVenmo = (Button) findViewById(R.id.btn_link_venmo);
       // btnLinkVenmo.setOnClickListener(this);
    }

    private void linkVenmo() {
        Venmo.makeAuthorizationRequest(this);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_link_venmo:
                linkVenmo();

                break;
        }
    }
}

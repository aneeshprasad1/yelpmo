package com.yelpmo.app.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.yelpmo.app.Constants.ParseInfo;
import com.yelpmo.app.Fragment.MealsFragment;
import com.yelpmo.app.R;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initParse();
        initMealsFragment();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initMealsFragment() {
        MealsFragment mealsFrag = new MealsFragment();
        getFragmentManager().beginTransaction().add(mealsFrag, "mealsFrag")
                .commit();
    }

    private void initParse() {
        Parse.initialize(this, ParseInfo.APPLICATION_ID, ParseInfo.CLIENT_ID);
    }
}

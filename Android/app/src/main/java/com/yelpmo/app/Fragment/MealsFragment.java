package com.yelpmo.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yelpmo.app.R;

/**
 * Created by Corey on 10/3/14.
 */
public class MealsFragment extends BaseFragment implements View.OnClickListener {

    private ListView lvMeals;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_meals, null);
        return initializeView(contentView);
    }

    private View initializeView(View view) {
        lvMeals = (ListView) view.findViewById(R.id.lv_meals_history);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {

        }
    }
}

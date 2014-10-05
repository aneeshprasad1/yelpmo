package com.yelpmo.app;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by Corey on 10/3/14.
 */
public class BusProvider {

    public static Bus BUS = new Bus(ThreadEnforcer.ANY);

    public static Bus getBus() {
        return BUS;
    }

    public static void register(Object object) {
        BUS.register(object);
    }

    public static void unregister(Object object) {
        BUS.unregister(object);
    }

}

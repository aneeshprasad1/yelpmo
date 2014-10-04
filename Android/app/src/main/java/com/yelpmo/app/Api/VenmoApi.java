package com.yelpmo.app.Api;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Corey on 10/4/14.
 */
public class VenmoApi {

    private static VenmoApiInterface venmoApiInterface;
    private static String API_ENDPOINT = "https://api.venmo.com/v1/";
    private static String CLIENT_ID = "2004";

    public static VenmoApiInterface getApiInterface() {
        if(venmoApiInterface == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_ENDPOINT)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();

            venmoApiInterface = restAdapter.create(VenmoApiInterface.class);
        }
        return venmoApiInterface;
    }


    public interface VenmoApiInterface {

        @FormUrlEncoded
        @POST("/payments/")
        void requestPayment(@Field("access_token") String accessToken,
                            @Field("email") String email, @Field("amount") int amount,
                            @Field("note") String note, Callback<Object> response);

    }
}

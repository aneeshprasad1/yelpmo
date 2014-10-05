package com.yelpmo.app.Objects;

import com.parse.ParseUser;

/**
 * Created by nishadsingh on 10/4/14.
 */
public class OtherUser {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String objectId;

    public OtherUser(ParseUser parseUser){
        this.firstName = parseUser.getString("firstName");
        this.lastName = parseUser.getString("lastName");
        this.phoneNumber = parseUser.getString("phoneNumber");
        this.objectId = parseUser.getObjectId();
    }


    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}

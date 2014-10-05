package com.yelpmo.app.Objects;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userName;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String objectId;
    private ParseUser parseUser;

    private ArrayList<Meal> meals;

    public User(ParseUser parseUser){
        this.parseUser = parseUser;
        this.userName = parseUser.getUsername();
        this.firstName = parseUser.getString("firstName");
        this.lastName = parseUser.getString("lastName");
        this.phoneNumber = parseUser.getString("phoneNumber");
        this.email = parseUser.getEmail();
        this.objectId = parseUser.getObjectId();

    }

    public ArrayList<Meal> getMeals(){
        if(meals==null){
            getMealsFromDb();
        }
        return meals;
    }

    public void addMeal(Meal meal){
        getMeals().add(meal);
        meal.getParseObject().put("owner", parseUser);
        meal.getParseObject().saveInBackground();
    }

    public void getMealsFromDb(){
        ParseQuery<ParseObject> mealQuery = ParseQuery.getQuery("Meal");
        mealQuery.whereEqualTo("owner", ParseUser.getCurrentUser());
        mealQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    objectsWereRetrievedSuccessfully(parseObjects);
                } else {
                    //error
                }
            }
        });
    }

    public void objectsWereRetrievedSuccessfully(List<ParseObject> parseObjects){
        this.meals = new ArrayList<Meal>();
        for (ParseObject parseObject: parseObjects){
            meals.add(new Meal(parseObject, this));
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public ParseUser getParseUser() {
        return parseUser;
    }

    public void setParseUser(ParseUser parseUser) {
        this.parseUser = parseUser;
    }


}

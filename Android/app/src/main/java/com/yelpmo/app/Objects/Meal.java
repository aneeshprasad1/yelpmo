package com.yelpmo.app.Objects;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Meal {
    private String name;
    private ParseObject parseObject;
    private User owner;
    private String objectId;
    private ArrayList<Item> items;

    //constructed when called by User's getMeals()
    public Meal(ParseObject parseObject, User user){
        this.owner = user;
        this.parseObject = parseObject;
        this.name = parseObject.getString("name");
        this.objectId = parseObject.getObjectId();
    }

    public void addItem(Item item){
        getItems().add(item);
        item.getParseObject().put("meal", parseObject);
        item.getParseObject().saveInBackground();
    }

    //constructed when actually created in application
    public Meal(String name, User user){
        this.owner = user;
        this.name = name;
        this.parseObject = new ParseObject("Meal");
        parseObject.put("name", name);
        parseObject.put("owner", user.getParseUser());
        parseObject.saveInBackground();
    }

    public ArrayList<Item> getItems(){
        if(items==null){
            getItemsFromDb();
        }
        return items;
    }

    public void getItemsFromDb(){
        ParseQuery<ParseObject> itemQuery = ParseQuery.getQuery("Item");
        itemQuery.whereEqualTo("meal", parseObject);
        itemQuery.findInBackground(new FindCallback<ParseObject>() {
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
        this.items = new ArrayList<Item>();
        for (ParseObject parseObject: parseObjects){
            items.add(new Item(parseObject, this));
        }
    }

    public String getObjectId() {
        if(objectId==null){
            this.objectId = parseObject.getObjectId();
        }
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParseObject getParseObject() {
        return parseObject;
    }

    public void setParseObject(ParseObject parseObject) {
        this.parseObject = parseObject;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }


}

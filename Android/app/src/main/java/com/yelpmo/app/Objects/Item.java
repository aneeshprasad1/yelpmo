package com.yelpmo.app.Objects;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class Item {
    private OtherUser owner;
    private Meal meal;
    private static final String ITEM_TYPE = "Item";
    private double price;
    private ParseObject parseObject;
    private String objectId;
    private String name;

    //Constructors

    //To be called when creating an item:
    public Item(String name, double price){
        this.name = name;
        this.price = price;
        this.parseObject = new ParseObject("Item");
        parseObject.put("name", name);
        parseObject.put("price", price);
        parseObject.saveInBackground();
    }

    public Item(ParseObject parseObject, Meal meal){
        this.meal = meal;
        this.objectId = parseObject.getObjectId();
        this.price = parseObject.getDouble("price");
        this.name = parseObject.getString("name");
        this.parseObject = parseObject;
        this.owner = new OtherUser(parseObject.getParseUser("owner"));
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        parseObject.put("name", name);
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public OtherUser getOwner() {
        return owner;
    }

    public void setOwner(OtherUser owner) {
        this.owner = owner;
        parseObject.put("owner", owner.getObjectId());
        parseObject.saveInBackground();
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public static String getItemType() {
        return ITEM_TYPE;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        parseObject.put("price", price);
        this.price = price;
    }

    public ParseObject getParseObject() {
        return parseObject;
    }

    public void setParseObject(ParseObject parseObject) {
        this.parseObject = parseObject;
    }

}

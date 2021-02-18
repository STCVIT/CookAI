package com.stcvit.cookai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class IngredientsPost implements Serializable {


    @SerializedName("foodItems")
    @Expose
    private String foodItems;


    public String getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(String foodItems) {
        this.foodItems = foodItems;
    }

    public IngredientsPost(String foodItems) {
        this.foodItems = foodItems;
    }
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("ingredients")
    @Expose
    private String ingredients;
    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("missing")
    @Expose
    private String missing;
    @SerializedName("imgurl")
    @Expose
    private String imgurl;
    @SerializedName("cuisine")
    @Expose
    private String cuisine;
    @SerializedName("instructions")
    @Expose
    private String instructions;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getMissing() {
        return missing;
    }

    public void setMissing(String missing) {
        this.missing = missing;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public IngredientsPost(String title, String ingredients, Integer time, String imgurl, String cuisine, String instructions) {
        this.title = title;
        this.ingredients = ingredients;
        this.time = time;
        this.imgurl = imgurl;
        this.cuisine = cuisine;
        this.instructions = instructions;
    }
}

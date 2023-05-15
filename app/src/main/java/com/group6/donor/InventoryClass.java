package com.group6.donor;

import com.google.gson.annotations.SerializedName;

public class InventoryClass {

    @SerializedName("ItemID")
    private int ItemID;
    @SerializedName("ItemName")
    private String ItemName;
    @SerializedName("Quantity")
    private String Quantity;
    @SerializedName("Description")
    private String Description;
    @SerializedName("Expiration")
    private String Expiration;
    @SerializedName("picture")
    private String picture;
//    @SerializedName("love")
//    private Boolean love;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int donorID) {
        this.ItemID = ItemID;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String ItemName) { this.ItemName = ItemName; }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String Quantity) {
        this.Quantity = Quantity;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) { this.Description = Description; }

    public String getExpiration() {
        return Expiration;
    }

    public void setExpiration(String Expiration) {
        this.Expiration = Expiration;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

//    public Boolean getLove() {
//        return love;
//    }
//
//    public void setLove(Boolean love) {
//        this.love = love;
//    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}

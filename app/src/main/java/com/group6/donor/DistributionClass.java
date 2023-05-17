package com.group6.donor;

import com.google.gson.annotations.SerializedName;

public class DistributionClass {

    @SerializedName("DistributionID")
    private int DistributionID;
    @SerializedName("Recipient")
    private String Recipient;
    @SerializedName("DistributionLocation")
    private String DistributionLocation;
    @SerializedName("Quantity")
    private String Quantity;
    @SerializedName("Notes")
    private String Notes;
    @SerializedName("Status")
    private int Status;
    @SerializedName("DistributionDate")
    private String DistributionDate;
    @SerializedName("picture")
    private String picture;
//    @SerializedName("love")
//    private Boolean love;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

    public int getDistributionID() { return DistributionID; }

    public void setDistributionID(int DistributionID) {
        this.DistributionID = DistributionID;
    }

    public String getRecipient() {
        return Recipient;
    }

    public void setRecipient(String Recipient) { this.Recipient = Recipient; }

    public String getDistributionLocation() {
        return DistributionLocation;
    }

    public void setDistributionLocation(String DistributionLocation) { this.DistributionLocation = DistributionLocation; }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String Notes) {
        this.Notes = Notes;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String Quantity) {
        this.Quantity = Quantity;
    }

    public int getStatus() { return Status; }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getDistributionDate() {
        return DistributionDate;
    }

    public void setDistributionDate(String DistributionDate) { this.DistributionDate = DistributionDate; }

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

package com.group6.donor;

import com.google.gson.annotations.SerializedName;

public class DonorsClass {

    @SerializedName("DonorID")
    private int DonorID;
    @SerializedName("name")
    private String name;
    @SerializedName("Address")
    private String Address;
    @SerializedName("ContactNumber")
    private String ContactNumber;
    @SerializedName("gender")
    private int gender;
    @SerializedName("birth")
    private String birth;
    @SerializedName("picture")
    private String picture;
    @SerializedName("love")
    private Boolean love;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

    public int getDonorID() {
        return DonorID;
    }

    public void setDonorID(int donorID) {
        this.DonorID = donorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.ContactNumber = contactNumber;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Boolean getLove() {
        return love;
    }

    public void setLove(Boolean love) {
        this.love = love;
    }

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

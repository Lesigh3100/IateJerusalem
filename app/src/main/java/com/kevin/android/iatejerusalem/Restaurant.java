package com.kevin.android.iatejerusalem;

public class Restaurant {

    private int mImageId;
    private String mRestaurantName;
    private String mRestaurantAddress;
    private String mHours;
    private String mPhoneNumber;
    private String mRestaurantType;
    private String mWebsite;
    private static final int NO_IMAGE_PROVIDED = -1;
    private double mRestaurantDistance;
    private int mBackgroundColor = -1;


    public Restaurant(String mRestaurantName, String mRestaurantAddress, String mHours, String mPhoneNumber, String mWebsite, String mRestaurantType) {
        this.mRestaurantName = mRestaurantName;
        this.mRestaurantAddress = mRestaurantAddress;
        this.mHours = mHours;
        this.mPhoneNumber = mPhoneNumber;
        this.mRestaurantType = mRestaurantType;
        this.mWebsite = mWebsite;
    }

    public Restaurant(int mImageId, String mRestaurantName, String mRestaurantAddress, String mHours, String mPhoneNumber, String mWebsite, String mRestaurantType) {
        this.mImageId = mImageId;
        this.mRestaurantName = mRestaurantName;
        this.mRestaurantAddress = mRestaurantAddress;
        this.mHours = mHours;
        this.mPhoneNumber = mPhoneNumber;
        this.mRestaurantType = mRestaurantType;
        this.mWebsite = mWebsite;
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public void setBackgroundColor(int mBackgroundColor) {
        this.mBackgroundColor = mBackgroundColor;
    }

    public double getRestaurantDistance() {
        return mRestaurantDistance;
    }

    public void setRestaurantDistance(double mRestaurantDistance) {
        this.mRestaurantDistance = mRestaurantDistance;
    }

    public boolean hasImage() {
        return mImageId != NO_IMAGE_PROVIDED;
    }


    public int getImageId() {
        return mImageId;
    }


    public String getWebsite() {
        return mWebsite;
    }


    public String getRestaurantType() {
        return mRestaurantType;
    }


    public String getRestaurantName() {
        return mRestaurantName;
    }


    public String getRestaurantAddress() {
        return mRestaurantAddress;
    }


    public String getHours() {
        return mHours;
    }


    public String getPhoneNumber() {
        return mPhoneNumber;
    }


}

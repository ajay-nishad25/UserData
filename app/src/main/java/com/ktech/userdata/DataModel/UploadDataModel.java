package com.ktech.userdata.DataModel;

public class UploadDataModel {

    private String mUserImage, mName, mEmail, mMobileNo, mRikshawNo, mGender, mAddress;

    public UploadDataModel() {
        //Empty constructor
    }

    public UploadDataModel(String mUserImage, String mName, String mEmail, String mMobileNo, String mRikshawNo, String mGender, String mAddress) {
        this.mUserImage = mUserImage;
        this.mName = mName;
        this.mEmail = mEmail;
        this.mMobileNo = mMobileNo;
        this.mRikshawNo = mRikshawNo;
        this.mGender = mGender;
        this.mAddress = mAddress;
    }

    public String getmUserImage() {
        return mUserImage;
    }

    public void setmUserImage(String mUserImage) {
        this.mUserImage = mUserImage;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmMobileNo() {
        return mMobileNo;
    }

    public void setmMobileNo(String mMobileNo) {
        this.mMobileNo = mMobileNo;
    }

    public String getmRikshawNo() {
        return mRikshawNo;
    }

    public void setmRikshawNo(String mRikshawNo) {
        this.mRikshawNo = mRikshawNo;
    }

    public String getmGender() {
        return mGender;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }
}

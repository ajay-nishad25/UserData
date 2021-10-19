package com.ktech.userdata.DataModel;

public class UploadDataModel {

    private String mUserImageUrl,mName, mEmail, mMobileNo, mRikshawNo, mAddress;

    public UploadDataModel() {
        //Empty constructor
    }

    public UploadDataModel(String mUserImageUrl,String mName, String mEmail, String mMobileNo, String mRikshawNo, String mAddress) {
        this.mUserImageUrl = mUserImageUrl;
        this.mName = mName;
        this.mEmail = mEmail;
        this.mMobileNo = mMobileNo;
        this.mRikshawNo = mRikshawNo;
        this.mAddress = mAddress;
    }

    public String getmUserImageUrl() {
        return mUserImageUrl;
    }

    public void setmUserImageUrl(String mUserImageUrl) {
        this.mUserImageUrl = mUserImageUrl;
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

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }
}

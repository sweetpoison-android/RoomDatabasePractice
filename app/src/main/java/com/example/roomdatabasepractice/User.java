package com.example.roomdatabasepractice;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

@PrimaryKey(autoGenerate = true)
    public int uid;

@ColumnInfo(name = "User Image", typeAffinity = ColumnInfo.BLOB)
public byte[] imageUrl;

@ColumnInfo(name = "User Name")
    public String userName;

@ColumnInfo(name = "Mobile No")
    public String mobileNo;

    public User(int uid, String userName, String mobileNo, byte[] imageUrl) {
        this.uid = uid;
        this.userName = userName;
        this.mobileNo = mobileNo;
        this.imageUrl = imageUrl;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public byte[] getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(byte[] imageUrl) {
        this.imageUrl = imageUrl;
    }
}

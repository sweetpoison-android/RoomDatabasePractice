package com.example.roomdatabasepractice;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertData(User user);

    @Query("SELECT EXISTS(SELECT * FROM User WHERE `Mobile No` = :mobileNo)")
    Boolean isMobileNoExists(String mobileNo);

    @Query("SELECT * FROM User")
    List<User> getAllUser();

    @Query("DELETE FROM USER WHERE `User Name` = :userName" )
    void deleteByUserName(String userName);
}

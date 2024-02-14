package com.ifbaiano.powermap.factory;

import android.database.Cursor;

import com.ifbaiano.powermap.model.User;

public class UserFactory {

    public static User createByCursor(Cursor cursor){
        return new User(
                cursor.getString(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("name")),
                cursor.getString(cursor.getColumnIndexOrThrow("email")),
                cursor.getString(cursor.getColumnIndexOrThrow("password")),
                cursor.getString(cursor.getColumnIndexOrThrow("imgPath")),
                cursor.getInt(cursor.getColumnIndexOrThrow("isAdmin")) == 1,
                null,
                null
        );
    }
}

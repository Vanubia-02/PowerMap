package com.ifbaiano.powermap.factory;

import android.database.Cursor;

import com.ifbaiano.powermap.model.Car;
import com.ifbaiano.powermap.model.Schedule;

public class CarFactory {

    public static Car createByCursor(Cursor cursor){
        return new Car(
                cursor.getString(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("name")),
                null
        );
    }
}

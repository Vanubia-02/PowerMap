package com.ifbaiano.powermap.factory;

import android.database.Cursor;

import com.ifbaiano.powermap.model.EletricCarModel;

public class EletricCarModelFactory {
    public static EletricCarModel createByCursor(Cursor cursor){
        return new EletricCarModel(
                cursor.getString(cursor.getColumnIndexOrThrow("id")),
                cursor.getInt(cursor.getColumnIndexOrThrow("year")),
                cursor.getString(cursor.getColumnIndexOrThrow("pathImg")),
                cursor.getFloat(cursor.getColumnIndexOrThrow("energyConsumption"))
        );
    }
}

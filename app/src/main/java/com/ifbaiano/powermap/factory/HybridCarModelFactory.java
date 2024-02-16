package com.ifbaiano.powermap.factory;

import android.database.Cursor;

import com.ifbaiano.powermap.model.EletricCarModel;
import com.ifbaiano.powermap.model.HybridCarModel;

public class HybridCarModelFactory {
    public static HybridCarModel createByCursor(Cursor cursor){
        return new HybridCarModel(
                cursor.getString(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("name")),
                cursor.getInt(cursor.getColumnIndexOrThrow("year")),
                cursor.getString(cursor.getColumnIndexOrThrow("pathImg")),
                cursor.getFloat(cursor.getColumnIndexOrThrow("energyConsumption")),
                cursor.getFloat(cursor.getColumnIndexOrThrow("fuelConsumption"))
        );
    }
}

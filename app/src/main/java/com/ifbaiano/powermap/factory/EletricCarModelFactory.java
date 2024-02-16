package com.ifbaiano.powermap.factory;

import android.content.Context;
import android.database.Cursor;

import com.ifbaiano.powermap.activity.carModel.AddCarModelActivity;
import com.ifbaiano.powermap.model.EletricCarModel;

import java.util.Objects;

public class EletricCarModelFactory {
    public static EletricCarModel createByCursor(Cursor cursor){
        return new EletricCarModel(
                cursor.getString(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("name")),
                cursor.getInt(cursor.getColumnIndexOrThrow("year")),
                cursor.getString(cursor.getColumnIndexOrThrow("pathImg")),
                cursor.getFloat(cursor.getColumnIndexOrThrow("energyConsumption"))
        );
    }


}

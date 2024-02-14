package com.ifbaiano.powermap.dao.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ifbaiano.powermap.connection.SqliteConnection;
import com.ifbaiano.powermap.dao.contracts.HybridCarModelDao;
import com.ifbaiano.powermap.model.EletricCarModel;
import com.ifbaiano.powermap.model.HybridCarModel;

import java.util.ArrayList;

public class HybridCarModelDaoSqlite implements HybridCarModelDao {
    private final SqliteConnection conn;
    private SQLiteDatabase db;
    private final String TABLE_NAME = "car_models";
    private final String FIND_ONE_QUERY = "SELECT * FROM "+ this.TABLE_NAME +" WHERE id = ?";
    private final String FIND_ALL_QUERY =  "SELECT * FROM "+ this.TABLE_NAME;
    private final String FIND_BY_CAR_QUERY =  "SELECT * FROM "+ this.TABLE_NAME + " WHERE cars_id = ?";

    public HybridCarModelDaoSqlite(Context ctx) {
        this.conn = new SqliteConnection(ctx);
    }


    @Override
    public HybridCarModel add(HybridCarModel carModel, String carId) {
        this.db = this.conn.getWritableDatabase();
        ContentValues values = this.makeContentValues(carModel);
        values.put("cars_id", carId);

        long id = db.insert(this.TABLE_NAME, null, values);
        carModel.setId(Long.toString(id));

        return  (id > 0) ? carModel :  null;
    }

    @Override
    public HybridCarModel edit(HybridCarModel carModel) {
        this.db = this.conn.getWritableDatabase();

        return (this.db.update(this.TABLE_NAME, this.makeContentValues(carModel), "id = ?",  new String[]{ carModel.getId() }) > 0) ? carModel : null;
    }

    @Override
    public Boolean remove(HybridCarModel carModel) {
        this.db = this.conn.getWritableDatabase();
        return db.delete(this.TABLE_NAME, "id = ?", new String[]{ carModel.getId()}) > 0;
    }

    @Override
    public HybridCarModel findOne(String id) {
        this.db = this.conn.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(this.FIND_ONE_QUERY, new String[]{id});

        if(cursor.moveToFirst()){
            return new HybridCarModel(
                    cursor.getString(cursor.getColumnIndexOrThrow("id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("year")),
                    cursor.getString(cursor.getColumnIndexOrThrow("pathImg")),
                    cursor.getFloat(cursor.getColumnIndexOrThrow("energyConsumption")),
                    cursor.getFloat(cursor.getColumnIndexOrThrow("fuelConsumption"))

            );
        }

        return null;
    }

    @Override
    public ArrayList<HybridCarModel> findAll() {
        this.db = this.conn.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(this.FIND_ALL_QUERY, null);
        return this.makeCarModelList(cursor);
    }

    @Override
    public ArrayList<HybridCarModel> findByCarId(String id) {
        this.db = this.conn.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(this.FIND_BY_CAR_QUERY, new String[]{ id });
        return this.makeCarModelList(cursor);
    }

    public ContentValues makeContentValues(HybridCarModel carModel){
        ContentValues values = new ContentValues();
        values.put("year", carModel.getYear());
        values.put("pathImg", carModel.getPathImg());
        values.put("energyConsumption", carModel.getEnergyConsumption());
        values.put("fuelConsumption", carModel.getFuelConsumption());

        return values;
    }

    public ArrayList<HybridCarModel> makeCarModelList(Cursor cursor){
        ArrayList<HybridCarModel> carModelList = new ArrayList<>();
        while(cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
            String pathImg = cursor.getString(cursor.getColumnIndexOrThrow("pathImg"));
            Integer year = cursor.getInt(cursor.getColumnIndexOrThrow("year"));
            Float energyConsumption = cursor.getFloat(cursor.getColumnIndexOrThrow("energyConsumption"));
            Float fuelConsumption = cursor.getFloat(cursor.getColumnIndexOrThrow("fuelConsumption"));

            HybridCarModel carModel = new HybridCarModel(id, year, pathImg, energyConsumption, fuelConsumption);
            carModelList.add(carModel);
        }
        return carModelList.size() > 0 ? carModelList : null;
    }
}

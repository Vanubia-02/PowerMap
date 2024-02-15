package com.ifbaiano.powermap.dao.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ifbaiano.powermap.connection.SqliteConnection;
import com.ifbaiano.powermap.dao.contracts.DataCallback;
import com.ifbaiano.powermap.dao.contracts.EletricCarModelDao;
import com.ifbaiano.powermap.factory.EletricCarModelFactory;
import com.ifbaiano.powermap.model.EletricCarModel;

import java.util.ArrayList;

public class EletricCarModelDaoSqlite implements EletricCarModelDao {
    private final SqliteConnection conn;
    private SQLiteDatabase db;
    private final String TABLE_NAME = "car_models";
    private final String FIND_ONE_QUERY = "SELECT * FROM "+ this.TABLE_NAME +" WHERE id = ?";
    private final String FIND_ALL_QUERY =  "SELECT * FROM "+ this.TABLE_NAME;
    private final String FIND_BY_CAR_QUERY =  "SELECT * FROM "+ this.TABLE_NAME + " WHERE cars_id = ?";

    public EletricCarModelDaoSqlite(Context ctx) {
        this.conn = new SqliteConnection(ctx);
    }

    @Override
    public EletricCarModel add(EletricCarModel carModel, String carId) {
        this.db = this.conn.getWritableDatabase();
        ContentValues values = this.makeContentValues(carModel);
        values.put("cars_id", carId);

        long id = db.insert(this.TABLE_NAME, null, values);
        carModel.setId(Long.toString(id));

        return  (id > 0) ? carModel :  null;
    }

    @Override
    public EletricCarModel edit(EletricCarModel carModel) {
        this.db = this.conn.getWritableDatabase();

        return (this.db.update(this.TABLE_NAME, this.makeContentValues(carModel), "id = ?",  new String[]{ carModel.getId() }) > 0) ? carModel : null;
    }

    @Override
    public Boolean remove(EletricCarModel carModel) {
        this.db = this.conn.getWritableDatabase();
        return db.delete(this.TABLE_NAME, "id = ?", new String[]{ carModel.getId()}) > 0;
    }

    @Override
    public EletricCarModel findOne(String id) {
        this.db = this.conn.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(this.FIND_ONE_QUERY, new String[]{id});

        if(cursor.moveToFirst()){
            return EletricCarModelFactory.createByCursor(cursor);
        }

        return null;
    }

    @Override
    public void findAll(DataCallback<EletricCarModel> callback) {
        try {
            this.db = this.conn.getWritableDatabase();
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(this.FIND_ALL_QUERY, null);
            callback.onDataLoaded(this.makeCarModelList(cursor));
        }
        catch (Exception e){
            callback.onError("Não foi possível listar os modelos de carros eletrícos!");
        }

    }

    @Override
    public void findByCarId(String id, DataCallback<EletricCarModel> callback) {
        try {
            this.db = this.conn.getWritableDatabase();
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(this.FIND_BY_CAR_QUERY, new String[]{ id });
            callback.onDataLoaded(this.makeCarModelList(cursor));
        }
        catch (Exception e){
            callback.onError("Não foi possível listar os modelos deste carro!");
        }
    }

    public ContentValues makeContentValues(EletricCarModel carModel){
        ContentValues values = new ContentValues();
        values.put("year", carModel.getYear());
        values.put("pathImg", carModel.getPathImg());
        values.put("energyConsumption", carModel.getEnergyConsumption());

        return values;
    }

    public ArrayList<EletricCarModel> makeCarModelList(Cursor cursor){
        ArrayList<EletricCarModel> carModelList = new ArrayList<>();
        while(cursor.moveToNext()) {
            carModelList.add( EletricCarModelFactory.createByCursor(cursor));
        }
        return carModelList.size() > 0 ? carModelList : null;
    }
}

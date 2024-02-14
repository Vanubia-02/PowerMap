package com.ifbaiano.powermap.dao.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ifbaiano.powermap.connection.SqliteConnection;
import com.ifbaiano.powermap.dao.contracts.EletricCarModelDao;
import com.ifbaiano.powermap.model.EletricCarModel;

import java.util.ArrayList;

public class EletricModelCarDaoSqlite implements EletricCarModelDao {
    private final SqliteConnection conn;
    private SQLiteDatabase db;
    private final String TABLE_NAME = "car_models";
    private final String FIND_ONE_QUERY = "SELECT * FROM "+ this.TABLE_NAME +" WHERE id = ?";
    private final String FIND_ALL_QUERY =  "SELECT * FROM "+ this.TABLE_NAME;
    private final String FIND_BY_CAR_QUERY =  "SELECT * FROM "+ this.TABLE_NAME + " WHERE cars_id = ?";

    public EletricModelCarDaoSqlite(Context ctx) {
        this.conn = new SqliteConnection(ctx);
    }

    @Override
    public EletricCarModel add(EletricCarModel carModel) {
        return null;
    }

    @Override
    public EletricCarModel edit(EletricCarModel carModel) {
        return null;
    }

    @Override
    public Boolean remove(EletricCarModel carModel) {
        return null;
    }

    @Override
    public EletricCarModel findOne(String id) {
        return null;
    }

    @Override
    public ArrayList<EletricCarModel> findAll() {
        return null;
    }

    @Override
    public ArrayList<EletricCarModel> findByCarId(String id) {
        return null;
    }
}

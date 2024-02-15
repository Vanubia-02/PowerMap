package com.ifbaiano.powermap.dao.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.ifbaiano.powermap.connection.SqliteConnection;
import com.ifbaiano.powermap.dao.contracts.DataCallback;
import com.ifbaiano.powermap.dao.contracts.UserDao;
import com.ifbaiano.powermap.factory.UserFactory;
import com.ifbaiano.powermap.model.User;

import java.util.ArrayList;

public class UserDaoSqlite implements UserDao {

    private final SqliteConnection conn;
    private SQLiteDatabase db;
    private final String TABLE_NAME = "users";
    private final String FIND_ONE_QUERY = "SELECT * FROM "+ this.TABLE_NAME +" WHERE id = ?";
    private final String FIND_ALL_QUERY = "SELECT * FROM " + this.TABLE_NAME;
    private final String FIND_ALL_FILTER_QUERY = "SELECT * FROM "+ this.TABLE_NAME +" WHERE isAdmin = ? ";
    private final String ADMIN = "1";
    private final String CLIENT = "0";


    public UserDaoSqlite(Context ctx) {
        this.conn = new SqliteConnection(ctx);
    }
    @Override
    public User add(User user) {
        this.db = this.conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("imgPath", user.getImgpath());
        values.put("isAdmin", user.isAdmin());
        values.put("password", user.getPassword());

        long id = db.insert(this.TABLE_NAME, null, values);
        user.setId(Long.toString(id));

        return  (id > 0) ? user :  null;
    }

    @Override
    public User edit(User user) {
        this.db = this.conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("imgPath", user.getImgpath());
        values.put("isAdmin", user.isAdmin());

        return (this.db.update(this.TABLE_NAME, values, "id = ?",  new String[]{ user.getId() }) > 0) ? user : null;

    }

    @Override
    public Boolean remove(User user) {
        this.db = this.conn.getWritableDatabase();
        return db.delete(this.TABLE_NAME, "id = ?", new String[]{user.getId()}) > 0;
    }

    @Override
    public  User findOne(String id) {
        this.db = this.conn.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(this.FIND_ONE_QUERY, new String[]{id});

        if(cursor.moveToFirst()){
            return UserFactory.createByCursor(cursor);
        }

        return null;
    }

    @Override
    public void findAll(DataCallback<User> callback) {
       try {
           this.db = this.conn.getWritableDatabase();
           @SuppressLint("Recycle") Cursor cursor = db.rawQuery(this.FIND_ALL_QUERY, null);
           callback.onDataLoaded(this.makeUserList(cursor));
       }
       catch (Exception e){
           callback.onError("Não foi possível listar todos os usuários!");
       }
    }

    @Override
    public void findAllClients(DataCallback<User> callback) {
        try {
            this.db = this.conn.getWritableDatabase();
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(this.FIND_ALL_FILTER_QUERY, new String[]{ this.CLIENT } );
            callback.onDataLoaded(this.makeUserList(cursor));
        }
        catch (Exception e){
            callback.onError("Não foi possível listar os clientes!");
        }
    }

    @Override
    public void findAllAdmins(DataCallback<User> callback) {
        try {
            this.db = this.conn.getWritableDatabase();
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(this.FIND_ALL_FILTER_QUERY,  new String[]{ this.ADMIN });
            callback.onDataLoaded(this.makeUserList(cursor));
        }
        catch (Exception e){
            callback.onError("Não foi possível listar os administradores!");
        }
    }

    @Override
    public Boolean changePassword(User user, String password) {
        this.db = this.conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("password", password);

        return this.db.update(this.TABLE_NAME, values, "id = ?", new String[]{user.getId()}) > 0;
    }
    public ArrayList<User> makeUserList(Cursor cursor){
        ArrayList<User> userList = new ArrayList<>();
        while(cursor.moveToNext()) {
            userList.add(UserFactory.createByCursor(cursor));
        }
        return userList.size() > 0 ? userList : null;
    }
}
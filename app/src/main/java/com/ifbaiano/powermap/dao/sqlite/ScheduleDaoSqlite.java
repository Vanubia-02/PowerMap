package com.ifbaiano.powermap.dao.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;

import com.ifbaiano.powermap.connection.SqliteConnection;
import com.ifbaiano.powermap.dao.contracts.ScheduleDao;
import com.ifbaiano.powermap.model.Schedule;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ScheduleDaoSqlite implements ScheduleDao {
    private final SqliteConnection conn;
    private SQLiteDatabase db;
    private final String TABLE_NAME = "schedules";
    private final String FIND_ONE_QUERY = "SELECT * FROM "+ this.TABLE_NAME +" WHERE id = ?";
    private final String FIND_ALL_QUERY =  "SELECT * FROM "+ this.TABLE_NAME;
    private final String FIND_BY_CLIENT_QUERY =  "SELECT * FROM "+ this.TABLE_NAME + " WHERE users_id = ?";


    public ScheduleDaoSqlite(Context ctx) {
        this.conn = new SqliteConnection(ctx);
    }

    @Override
    public Schedule add(Schedule schedule, String userId) {
        this.db = this.conn.getWritableDatabase();
        ContentValues values = this.makeContentValues(schedule);
        values.put("users_id", userId);

        long id = db.insert(this.TABLE_NAME, null, values);
        schedule.setId(Long.toString(id));

        return  (id > 0) ? schedule :  null;
    }

    @Override
    public Schedule edit(Schedule schedule) {
        this.db = this.conn.getWritableDatabase();
        return (this.db.update(this.TABLE_NAME, this.makeContentValues(schedule), "id = ?",  new String[]{ schedule.getId() }) > 0) ? schedule : null;
    }

    @Override
    public Boolean remove(Schedule schedule) {
        this.db = this.conn.getWritableDatabase();
        return db.delete(this.TABLE_NAME, "id = ?", new String[]{schedule.getId()}) > 0;
    }

    @Override
    public Schedule findOne(String id) {
        this.db = this.conn.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(this.FIND_ONE_QUERY, new String[]{id});


        if(cursor.moveToFirst()){
            return new Schedule(
                    cursor.getString(cursor.getColumnIndexOrThrow("id")),
                    this.castStringToDate(
                            cursor.getString(cursor.getColumnIndexOrThrow("date"))
                    ),
                    cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("dayOfWeek")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("repetition"))
            );
        }

        return null;
    }

    @Override
    public ArrayList<Schedule> findAll() {
        this.db = this.conn.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(this.FIND_ALL_QUERY, null);
        return this.makeScheduleList(cursor);
    }

    @Override
    public ArrayList<Schedule> findByUserId(String id) {
        this.db = this.conn.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(this.FIND_BY_CLIENT_QUERY, new String[]{ id });
        return this.makeScheduleList(cursor);
    }

    public ArrayList<Schedule> makeScheduleList(Cursor cursor){
        ArrayList<Schedule> scheduleList = new ArrayList<>();
        while(cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
            Date date = this.castStringToDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            Integer dayOfWeek = cursor.getInt(cursor.getColumnIndexOrThrow("dayOfWeek"));
            Integer repetition = cursor.getInt(cursor.getColumnIndexOrThrow("repetition"));

            scheduleList.add(new Schedule(id, date, description, dayOfWeek, repetition));
        }
        return scheduleList.size() > 0 ? scheduleList : null;
    }

    public ContentValues makeContentValues(Schedule schedule){
        ContentValues values = new ContentValues();
        values.put("date", schedule.getDate().getTime());
        values.put("description", schedule.getDescription());
        values.put("dayOfWeek", schedule.getDayOfWeek());
        values.put("repetition", schedule.getRepetition());
        return values;
    }

    public Date castStringToDate(String dateString){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            return null;
        }

        return date;
    }
}

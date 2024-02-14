package com.ifbaiano.powermap.factory;

import android.database.Cursor;
import android.icu.text.SimpleDateFormat;

import com.ifbaiano.powermap.model.Schedule;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class ScheduleFactory {

    public static Schedule createByCursor(Cursor cursor){
        return new Schedule(
                cursor.getString(cursor.getColumnIndexOrThrow("id")),
                castStringToDate(
                        cursor.getString(cursor.getColumnIndexOrThrow("date"))
                ),
                cursor.getString(cursor.getColumnIndexOrThrow("description")),
                cursor.getInt(cursor.getColumnIndexOrThrow("dayOfWeek")),
                cursor.getInt(cursor.getColumnIndexOrThrow("repetition"))
        );
    }

    private static Date castStringToDate(String dateString){

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

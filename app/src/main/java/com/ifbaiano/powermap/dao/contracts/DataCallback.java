package com.ifbaiano.powermap.dao.contracts;

import androidx.appcompat.app.AppCompatActivity;

import com.ifbaiano.powermap.activity.MainActivity;

import java.util.ArrayList;

public interface DataCallback<T> {
    void onDataLoaded(ArrayList<T> items);
    void onError(String errorMessage);
}

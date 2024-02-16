package com.ifbaiano.powermap.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.ifbaiano.powermap.R;
import com.ifbaiano.powermap.dao.contracts.CarDao;
import com.ifbaiano.powermap.dao.contracts.DataCallback;
import com.ifbaiano.powermap.dao.contracts.EletricCarModelDao;
import com.ifbaiano.powermap.dao.contracts.UserDao;
import com.ifbaiano.powermap.dao.firebase.EletricCarModelDaoFirebase;
import com.ifbaiano.powermap.dao.firebase.UserDaoFirebase;
import com.ifbaiano.powermap.dao.sqlite.CarDaoSqlite;
import com.ifbaiano.powermap.model.EletricCarModel;
import com.ifbaiano.powermap.model.User;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
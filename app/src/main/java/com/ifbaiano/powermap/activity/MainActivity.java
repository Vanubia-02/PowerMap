package com.ifbaiano.powermap.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.ifbaiano.powermap.R;
import com.ifbaiano.powermap.dao.contracts.CarDao;
import com.ifbaiano.powermap.dao.sqlite.CarDaoSqlite;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
//        CarDao carDao = new CarDaoSqlite(getApplicationContext());


//        carDao.findAll(callback);
//
//        CarDao carDao = new CarDaoFirebase();
//
//        Car newCar = new Car("", "Corolla", null);
//        Car saveCar = carDao.add(newCar, "userId123");
//        if(saveCar != null){
//            Log.d(TAG, "Found car: " + saveCar.getId() + " " + saveCar.getName());
//        }
//        else{
//            Log.d(TAG, "Não salvou");
//        }
//
//
////
//        newCar.setName("Teste");
//        carDao.edit(newCar);
//
//        carDao.remove(newCar);
//
//        String carId = "carId123";
//        Car foundCar = carDao.findOne(carId);
//        if (foundCar != null) {
//            Log.d(TAG, "Found car: " + foundCar.getId() + " " + foundCar.getName());
//        } else {
//            Log.d(TAG, "Car with ID " + carId + " not found.");
//        }
////
//        carDao.findAll(new FirebaseCallback<Car>() {
//            @Override
//            public void onDataLoaded(ArrayList<Car> items) {
//                for (Car car : items) {
//                    Log.d(TAG, "Found car: " + car.getId());
//                }
//            }
//
//            @Override
//            public void onError() {
//                Log.d(TAG, "nenhum carro encontrado");
//            }
//        });
//
//
//
//        String userId = "userId123";
//        ArrayList<Car> userCars = carDao.findByUserId(userId);
//        for (Car car : userCars) {
//            assert foundCar != null;
//            Log.d(TAG, "Found car: " + foundCar.getId() + " " + foundCar.getName());
//        }
    }
}
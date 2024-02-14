package com.ifbaiano.powermap.dao.contracts;

import com.ifbaiano.powermap.model.Car;

import java.util.ArrayList;

public interface CarDao {
    Car add(Car car);
    Car edit(Car car);
    Boolean remove(Car car);
    ArrayList<Car> findOne(String id);
    ArrayList<Car>  findAll();
}

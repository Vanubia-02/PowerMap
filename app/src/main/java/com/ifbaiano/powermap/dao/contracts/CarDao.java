package com.ifbaiano.powermap.dao.contracts;

import com.ifbaiano.powermap.model.Car;
import com.ifbaiano.powermap.model.User;

import java.util.ArrayList;

public interface CarDao {
    Car add(Car car);
    Car edit(Car car);
    Boolean remove(Car car);
    Car findOne(String id);
    ArrayList<Car>  findAll();
    ArrayList<Car>  findByUserId(String id);

}

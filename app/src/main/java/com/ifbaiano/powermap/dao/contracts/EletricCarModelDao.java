package com.ifbaiano.powermap.dao.contracts;

import com.ifbaiano.powermap.model.EletricCarModel;

import java.util.ArrayList;

public interface EletricCarModelDao {
    EletricCarModel add(EletricCarModel carModel);
    EletricCarModel edit(EletricCarModel carModel);
    Boolean remove(EletricCarModel carModel);
    ArrayList<EletricCarModel> findOne(String id);
    ArrayList<EletricCarModel>  findAll();
}
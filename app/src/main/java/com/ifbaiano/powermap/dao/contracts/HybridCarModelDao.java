package com.ifbaiano.powermap.dao.contracts;


import com.ifbaiano.powermap.model.HybridCarModel;

import java.util.ArrayList;

public interface HybridCarModelDao {
    HybridCarModel add(HybridCarModel carModel);
    HybridCarModel edit(HybridCarModel carModel);
    Boolean remove(HybridCarModel carModel);
    ArrayList<HybridCarModel> findOne(String id);
    ArrayList<HybridCarModel>  findAll();

}

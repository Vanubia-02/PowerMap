package com.ifbaiano.powermap.dao.contracts;


import com.ifbaiano.powermap.model.EletricCarModel;
import com.ifbaiano.powermap.model.HybridCarModel;

import java.util.ArrayList;

public interface HybridCarModelDao {
    HybridCarModel add(HybridCarModel carModel, String carId);
    HybridCarModel edit(HybridCarModel carModel);
    Boolean remove(HybridCarModel carModel);
    HybridCarModel findOne(String id);
    ArrayList<HybridCarModel> findAll();
    ArrayList<HybridCarModel> findByCarId(String id);

}

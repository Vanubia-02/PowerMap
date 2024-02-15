package com.ifbaiano.powermap.dao.contracts;


import com.ifbaiano.powermap.model.EletricCarModel;
import com.ifbaiano.powermap.model.HybridCarModel;

import java.util.ArrayList;

public interface HybridCarModelDao {
    HybridCarModel add(HybridCarModel carModel, String carId);
    HybridCarModel edit(HybridCarModel carModel);
    Boolean remove(HybridCarModel carModel);
    HybridCarModel findOne(String id);
    void findAll(DataCallback<HybridCarModel> callback);
    void findByCarId(String id, DataCallback<HybridCarModel> callback);

}

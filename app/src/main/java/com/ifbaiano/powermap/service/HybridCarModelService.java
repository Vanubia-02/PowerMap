package com.ifbaiano.powermap.service;

import com.ifbaiano.powermap.dao.contracts.HybridCarModelDao;
import com.ifbaiano.powermap.dao.firebase.FirebaseStorageDao;
import com.ifbaiano.powermap.model.EletricCarModel;
import com.ifbaiano.powermap.model.HybridCarModel;

public class HybridCarModelService {
    HybridCarModelDao dao;

    public HybridCarModelService(HybridCarModelDao dao) {
        this.dao = dao;
    }

    public boolean add(HybridCarModel carModel, String carId, byte[] imgByte){
        carModel.setPathImg(new FirebaseStorageDao().add(imgByte));
        return this.dao.add(carModel, carId) != null;
    }

    public HybridCarModelDao getDao() {
        return dao;
    }

    public void setDao(HybridCarModelDao dao) {
        this.dao = dao;
    }
}

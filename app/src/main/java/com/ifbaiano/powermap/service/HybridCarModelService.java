package com.ifbaiano.powermap.service;

import com.ifbaiano.powermap.dao.contracts.HybridCarModelDao;
import com.ifbaiano.powermap.dao.contracts.StorageDao;
import com.ifbaiano.powermap.model.EletricCarModel;
import com.ifbaiano.powermap.model.HybridCarModel;

import java.util.ArrayList;

public class HybridCarModelService {
    private HybridCarModelDao dao;
    private final StorageDao storageDao;
    public HybridCarModelService(HybridCarModelDao dao, StorageDao storageDao) {
        this.dao = dao;
        this.storageDao = storageDao;
    }

    public boolean add(HybridCarModel carModel, String carId, byte[] imgByte){
        carModel.setPathImg(storageDao.add(imgByte, "hybrid_car_models"));
        return this.dao.add(carModel, carId) != null;
    }
    public ArrayList<HybridCarModel> listAll(){
        return this.dao.findAll();
    }

    public HybridCarModelDao getDao() {
        return dao;
    }

    public void setDao(HybridCarModelDao dao) {
        this.dao = dao;
    }
}

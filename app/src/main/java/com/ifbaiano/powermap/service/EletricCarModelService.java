package com.ifbaiano.powermap.service;

import com.ifbaiano.powermap.dao.contracts.EletricCarModelDao;
import com.ifbaiano.powermap.dao.contracts.StorageDao;
import com.ifbaiano.powermap.model.EletricCarModel;

public class EletricCarModelService {
    private EletricCarModelDao dao;
    private final StorageDao storageDao;

    public EletricCarModelService(EletricCarModelDao dao, StorageDao storageDao) {
        this.dao = dao;
        this.storageDao = storageDao;
    }

    public boolean add(EletricCarModel carModel, String carId, byte[] imgByte){
       carModel.setPathImg(storageDao.add(imgByte, "eletric_car_models"));
       return this.dao.add(carModel, carId) != null;
    }


    public EletricCarModelDao getDao() {
        return dao;
    }

    public void setDao(EletricCarModelDao dao) {
        this.dao = dao;
    }
}

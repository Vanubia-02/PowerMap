package com.ifbaiano.powermap.service;

import android.util.Log;

import com.ifbaiano.powermap.dao.contracts.EletricCarModelDao;
import com.ifbaiano.powermap.dao.firebase.FirebaseStorageDao;
import com.ifbaiano.powermap.model.EletricCarModel;

public class EletricCarModelService {
    private EletricCarModelDao dao;

    public EletricCarModelService(EletricCarModelDao dao) {
        this.dao = dao;
    }

    public boolean add(EletricCarModel carModel, String carId, byte[] imgByte){
       carModel.setPathImg(new FirebaseStorageDao().add(imgByte));
       return this.dao.add(carModel, carId) != null;
    }


    public EletricCarModelDao getDao() {
        return dao;
    }

    public void setDao(EletricCarModelDao dao) {
        this.dao = dao;
    }
}

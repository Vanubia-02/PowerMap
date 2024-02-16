package com.ifbaiano.powermap.model;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class EletricCarModel extends CarModel {
    private Float energyConsumption;

    public EletricCarModel() {
        super();
    }

    public EletricCarModel(String id, String name, Integer year, String pathImg, Float energyConsumption) {
        super(id,name, year, pathImg);
        this.energyConsumption = energyConsumption;
    }


    public Float getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(Float energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

}

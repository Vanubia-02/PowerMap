package com.ifbaiano.powermap.model;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class HybridCarModel extends EletricCarModel {
    private Float fuelConsumption;

    public HybridCarModel(String id, String name, Integer year, String pathImg, Float energyConsumption, Float fuelConsumption) {
        super(id, name, year, pathImg, energyConsumption);
        this.fuelConsumption = fuelConsumption;
    }

    public HybridCarModel(Float fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public HybridCarModel() {
    }

    public Float getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(Float fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }


}

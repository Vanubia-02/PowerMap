package com.ifbaiano.powermap.model;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class EletricCarModel extends CarModel {
    private Float energyConsumption;

    public Float getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(Float energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

}

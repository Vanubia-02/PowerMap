package com.ifbaiano.powermap.model;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

abstract public class CarModel implements  Serializable{

    @Exclude
    private String id;
    private Integer year;
    private String pathImg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPathImg() {
        return pathImg;
    }

    public void setPathImg(String pathImg) {
        this.pathImg = pathImg;
    }

}
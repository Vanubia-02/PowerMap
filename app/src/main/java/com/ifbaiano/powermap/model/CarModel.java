package com.ifbaiano.powermap.model;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

abstract public class CarModel implements  Serializable {

    @Exclude
    private String id;
    private String name;
    private Integer year;
    private String pathImg;

    public CarModel(String id, String name, Integer year, String pathImg) {
        this.id = id;
        this.year = year;
        this.name = name;
        this.pathImg = pathImg;
    }

    public CarModel() {
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

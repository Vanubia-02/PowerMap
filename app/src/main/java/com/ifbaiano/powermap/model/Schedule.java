package com.ifbaiano.powermap.model;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.Date;

public class Schedule implements  Serializable{
    @Exclude
    private String id;
    private Date date;
    private String description;
    private Integer dayOfWeek, repetition;

    public Schedule(String id, Date date, String description, Integer dayOfWeek, Integer repetition) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.repetition = repetition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getRepetition() {
        return repetition;
    }

    public void setRepetition(Integer repetition) {
        this.repetition = repetition;
    }



}

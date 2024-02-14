package com.ifbaiano.powermap.dao.contracts;

import com.ifbaiano.powermap.model.Schedule;

import java.util.ArrayList;

public interface ScheduleDao {
    Schedule add(Schedule schedule);
    Schedule edit(Schedule schedule);
    Boolean remove(Schedule schedule);
    ArrayList<Schedule> findOne(String id);
    ArrayList<Schedule>  findAll();
    
}

package com.ifbaiano.powermap.dao.contracts;

import com.ifbaiano.powermap.model.Schedule;
import com.ifbaiano.powermap.model.User;

import java.util.ArrayList;

public interface ScheduleDao {
    Schedule add(Schedule schedule, String userId);
    Schedule edit(Schedule schedule);
    Boolean remove(Schedule schedule);
    Schedule findOne(String id);
    ArrayList<Schedule>  findAll();
    ArrayList<Schedule>  findByUserId(String id);

}

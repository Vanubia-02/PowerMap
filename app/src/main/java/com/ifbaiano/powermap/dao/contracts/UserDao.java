package com.ifbaiano.powermap.dao.contracts;

import com.ifbaiano.powermap.model.User;

import java.util.ArrayList;

public interface UserDao{
    User add(User user);
    User edit(User user);
    Boolean remove(User user);
     User findOne(String id);
    ArrayList<User>  findAll();
    ArrayList<User>  findAllClients();
    ArrayList<User>  findAllAdmins();
    Boolean changePassword(User user, String password);

}

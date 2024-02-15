package com.ifbaiano.powermap.dao.contracts;

import com.ifbaiano.powermap.model.User;

import java.util.ArrayList;

import javax.annotation.Nullable;

public interface UserDao{
    User add(User user);
    User edit(User user);
    Boolean remove(User user);
     User findOne(String id);
    void  findAll(DataCallback<User> callback);
    void findAllClients(DataCallback<User> callback);
    void  findAllAdmins(DataCallback<User> callback);
    Boolean changePassword(User user, String password);

}

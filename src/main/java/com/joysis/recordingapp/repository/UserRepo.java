package com.joysis.recordingapp.repository;

import com.joysis.recordingapp.model.User;

import java.util.List;

public interface UserRepo {
    List<User> getAllUsers();

    boolean registerUser(String username, String password);

    User login(String username, String password);

    boolean deleteUser(int id);
}

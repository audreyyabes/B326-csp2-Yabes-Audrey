package com.joysis.recordingapp.service;

import com.joysis.recordingapp.model.User;
import com.joysis.recordingapp.repository.UserRepo;

import java.util.List;

public class UserServiceImpl implements UserService{
    private final UserRepo userRepo; // Composition

    // Constructor injection
    public UserServiceImpl(UserRepo userRepository) {
        this.userRepo = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.getAllUsers();
    }

    @Override
    public boolean registerUser(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Username is required.");
            return false;
        }
        if (password == null || password.length() < 6) {
            System.out.println("Password must be at least 6 characters.");
            return false;
        }
        return userRepo.registerUser(username.trim(), password);
    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null
                || password.isEmpty()) {
            System.out.println("Username and password are required.");
            return null;
        }
        return userRepo.login(username.trim(), password);
    }

    @Override
    public boolean deleteUser(int id) {
        if (id <= 0) {
            System.out.println("Invalid user ID.");
            return false;
        }
        return userRepo.deleteUser(id);
    }
}

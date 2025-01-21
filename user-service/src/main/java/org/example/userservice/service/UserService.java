package org.example.userservice.service;

import org.example.userservice.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long id);

    User saveUser(User user);

    void deleteUserById(Long id);

    void lockUser(Long id);

    void unlockUser(Long id);
}


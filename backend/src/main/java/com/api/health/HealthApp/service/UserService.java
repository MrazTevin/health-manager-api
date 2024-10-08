package com.api.health.HealthApp.service;

import com.api.health.HealthApp.dto.UserDTO;
import com.api.health.HealthApp.entities.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    UserDTO getUserById(Long id);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    List<UserDTO> getAllUsers();
}

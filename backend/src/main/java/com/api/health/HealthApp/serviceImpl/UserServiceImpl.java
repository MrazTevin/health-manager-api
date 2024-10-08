package com.api.health.HealthApp.serviceImpl;

import com.api.health.HealthApp.dto.UserDTO;
import com.api.health.HealthApp.entities.User;
import com.api.health.HealthApp.repository.UserRepository;
import com.api.health.HealthApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        User savedUser = userRepository.save(user);
        return new UserDTO(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null ? new UserDTO(user.getId(), user.getUsername(), user.getEmail()) : null;
    }

    @Override
    public User updateUser(Long id, User user) {
        return null;
    }


    public UserDTO updateUser(Long id, UserDTO userDTO) {
        UserDTO existingUser = getUserById(id);
        User currentUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(userDTO.getUsername());
            existingUser.setEmail(userDTO.getEmail());
            User updatedUser = userRepository.save(currentUser);
            return new UserDTO(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getEmail());
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
    }
}

package com.api.health.HealthApp.dto;

public class UserDTO {
    private Long id;
    private String username;

    private String email;

    public UserDTO(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}

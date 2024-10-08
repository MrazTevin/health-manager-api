package com.api.health.HealthApp.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name="insurances")
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String country;

    private BigDecimal annualPrice;

    @ElementCollection
    private List<Long> hospitalsCovered; // List of Hospital IDs

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

package com.api.health.HealthApp.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@Table(name = "premium_calculations")
public class PremiumCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "insurance_id", nullable = false)
    private Insurance insurance;

    private Integer monthsCovered;

    private BigDecimal calculatedPremium;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}

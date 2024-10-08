package com.api.health.HealthApp.repository;

import com.api.health.HealthApp.entities.PremiumCalculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiumCalculationRepository extends JpaRepository<PremiumCalculation, Long> {
}

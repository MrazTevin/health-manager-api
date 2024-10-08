package com.api.health.HealthApp.repository;

import com.api.health.HealthApp.entities.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

    Optional<Insurance> findByName(String name);
    List<Insurance> findByCountry(String country);


}

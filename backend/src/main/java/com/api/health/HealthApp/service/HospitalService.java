package com.api.health.HealthApp.service;
import com.api.health.HealthApp.entities.Hospital;

import java.util.List;


public interface HospitalService {
    List<Hospital> getAllHospitals();
    Hospital getHospitalById(Long id);
}

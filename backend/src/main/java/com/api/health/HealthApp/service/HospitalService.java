package com.api.health.HealthApp.service;
import com.api.health.HealthApp.entities.Hospital;

import java.util.List;


public interface HospitalService {
    Hospital createHospital(Hospital hospital);
    List<Hospital> getAllHospitals();

    Hospital updateHospital(Long id, Hospital hospital);
    Hospital getHospitalById(Long id);

    void deleteHospital(Long id);
}

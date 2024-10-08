package com.api.health.HealthApp.service;
import com.api.health.HealthApp.entities.Insurance;

import java.util.List;


public interface InsuranceService {
    Insurance createInsurance(Insurance insurance);
    Insurance getInsuranceById(Long id);
    Insurance updateInsurance(Long id, Insurance insurance);
    void deleteInsurance(Long id);
    List<Insurance> getAllInsurances();
}

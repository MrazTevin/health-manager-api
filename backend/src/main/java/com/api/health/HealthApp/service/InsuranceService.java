package com.api.health.HealthApp.service;
import com.api.health.HealthApp.dto.InsuranceDTO;
import com.api.health.HealthApp.entities.Insurance;

import java.util.List;


public interface InsuranceService {
    InsuranceDTO createInsurance(InsuranceDTO insuranceDTO);

    InsuranceDTO getInsuranceById(Long id);
    Insurance updateInsurance(Long id, Insurance insurance);

    InsuranceDTO updateInsurance(Long id, InsuranceDTO insuranceDTO);

    void deleteInsurance(Long id);
    List<InsuranceDTO> getAllInsurances();
}

package com.api.health.HealthApp.serviceImpl;

import com.api.health.HealthApp.entities.Insurance;
import com.api.health.HealthApp.repository.InsuranceRepository;
import com.api.health.HealthApp.service.PremiumCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PremiumCalculationServiceImpl implements PremiumCalculationService {
    @Autowired
    private InsuranceRepository insuranceRepository;

    @Override
    public BigDecimal calculatePremium(Long insuranceId, Integer monthsCovered) {
        Insurance insurance = insuranceRepository.findById(insuranceId).orElse(null);
        if (insurance != null) {
            return insurance.getAnnualPrice().multiply(BigDecimal.valueOf(monthsCovered)).divide(BigDecimal.valueOf(12), 2, BigDecimal.ROUND_HALF_UP);
        }
        return BigDecimal.ZERO;
    }


}

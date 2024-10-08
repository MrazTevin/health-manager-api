package com.api.health.HealthApp.service;

import java.math.BigDecimal;

public interface PremiumCalculationService {
    BigDecimal calculatePremium(Long insuranceId, Integer monthsCovered);

}

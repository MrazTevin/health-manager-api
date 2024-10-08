package com.api.health.HealthApp.controller;


import com.api.health.HealthApp.dto.PremiumRequest;
import com.api.health.HealthApp.service.PremiumCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/premiums")
@RequiredArgsConstructor
public class PremiumCalculatorRequest {

    private final PremiumCalculationService premiumCalculationService;
    @PostMapping("/calculate")
    public ResponseEntity<BigDecimal> calculatePremium(@RequestBody PremiumRequest request) {
        BigDecimal premium = premiumCalculationService.calculatePremium(request.getInsuranceId(), request.getMonthsCovered());
        return ResponseEntity.ok(premium);
    }

}

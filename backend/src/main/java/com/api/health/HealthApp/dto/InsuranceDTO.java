package com.api.health.HealthApp.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

public class InsuranceDTO {
    private Long id;
    @Getter
    private String name;
    @Getter
    private String country;
    @Getter
    private BigDecimal annualPrice;
    @Getter
    private List<String> hospitalsCovered;

    public InsuranceDTO(Long id, String name, String country, BigDecimal annualPrice, List<String> hospitalsCovered) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.annualPrice = annualPrice;
        this.hospitalsCovered = hospitalsCovered;
    }

}

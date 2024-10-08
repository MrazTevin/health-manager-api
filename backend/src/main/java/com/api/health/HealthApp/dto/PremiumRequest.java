package com.api.health.HealthApp.dto;

public class PremiumRequest {
    private Long insuranceId; // ID of the insurance
    private Integer monthsCovered; // Number of months to calculate premium for


    public PremiumRequest(Long insuranceId, Integer monthsCovered) {
        this.insuranceId = insuranceId;
        this.monthsCovered = monthsCovered;
    }


    public Long getInsuranceId() {
        return insuranceId;
    }

    public Integer getMonthsCovered() {
        return monthsCovered;
    }

    // Setters (optional)
    public void setInsuranceId(Long insuranceId) {
        this.insuranceId = insuranceId;
    }

    public void setMonthsCovered(Integer monthsCovered) {
        this.monthsCovered = monthsCovered;
    }
}

package com.api.health.HealthApp.dto;

import java.time.LocalDateTime;

public class ReportDTO {
    private Long id;
    private Long userId;
    private Long insuranceId;
    private String reportData;
    private LocalDateTime generatedAt;


    public ReportDTO(Long id, Long userId, Long insuranceId, String reportData, LocalDateTime generatedAt) {
        this.id = id;
        this.userId = userId;
        this.insuranceId = insuranceId;
        this.reportData = reportData;
        this.generatedAt = generatedAt;
    }
}

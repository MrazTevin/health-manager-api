package com.api.health.HealthApp.service;
import com.api.health.HealthApp.dto.ReportDTO;
import com.api.health.HealthApp.entities.Report;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;


public interface ReportService {
    Report generateReport(Long userId, Long insuranceId) throws JsonProcessingException;
    List<ReportDTO> getReportsByUserId(Long userId);

}

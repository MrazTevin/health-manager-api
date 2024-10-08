package com.api.health.HealthApp.service;
import com.api.health.HealthApp.entities.Report;

import java.util.List;


public interface ReportService {
    Report generateReport(Long userId, Long insuranceId);
    List<Report> getReportsByUserId(Long userId);

}

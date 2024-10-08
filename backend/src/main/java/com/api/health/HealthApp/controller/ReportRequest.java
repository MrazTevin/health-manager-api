package com.api.health.HealthApp.controller;


import com.api.health.HealthApp.dto.ReportDTO;
import com.api.health.HealthApp.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
public class ReportRequest {

    private final ReportService reportService;

    @GetMapping("/all")
    public ResponseEntity<List<ReportDTO>> getReportsByUserId(@RequestParam Long userId) {
        List<ReportDTO> reports = reportService.getReportsByUserId(userId);
        return ResponseEntity.ok(reports);
    }

    @PostMapping("/create")
    public ResponseEntity<ReportDTO> generateReport(@RequestParam Long userId, @RequestParam Long insuranceId) {
        try {
            ReportDTO report = reportService.generateReport(userId, insuranceId);
            return report != null ? ResponseEntity.ok(report) : ResponseEntity.badRequest().body(new ReportDTO(null, null, null, "Reports couldn't be generated", null));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ReportDTO(null, null, null, "Error processing the report.", null));
        }
    }




}

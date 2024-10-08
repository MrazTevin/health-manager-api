package com.api.health.HealthApp.serviceImpl;

import com.api.health.HealthApp.dto.InsuranceDTO;
import com.api.health.HealthApp.dto.ReportDTO;
import com.api.health.HealthApp.dto.UserDTO;
import com.api.health.HealthApp.entities.Hospital;
import com.api.health.HealthApp.entities.Insurance;
import com.api.health.HealthApp.entities.Report;
import com.api.health.HealthApp.entities.User;
import com.api.health.HealthApp.exceptions.ResourceNotFoundException;
import com.api.health.HealthApp.repository.HospitalRepository;
import com.api.health.HealthApp.repository.InsuranceRepository;
import com.api.health.HealthApp.repository.ReportRepository;
import com.api.health.HealthApp.repository.UserRepository;
import com.api.health.HealthApp.service.PremiumCalculationService;
import com.api.health.HealthApp.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private PremiumCalculationService premiumCalculationService;

    @Autowired
    private HospitalRepository hospitalRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ReportDTO generateReport(Long userId, Long insuranceId) throws JsonProcessingException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Insurance insurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance not found with id: " + insuranceId));

        if (user != null && insurance != null) {
            Report report = new Report();
            report.setUser(user);
            report.setInsurance(insurance);
            report.setGeneratedAt(LocalDateTime.now());


            BigDecimal calculatedPremium = premiumCalculationService.calculatePremium(insuranceId, 12);
            ReportData.PremiumCalculations.Breakdown breakdown = new ReportData.PremiumCalculations.Breakdown(
                    calculatedPremium.divide(BigDecimal.valueOf(12), 2, BigDecimal.ROUND_HALF_UP), // Monthly
                    calculatedPremium.divide(BigDecimal.valueOf(4), 2, BigDecimal.ROUND_HALF_UP),  // Quarterly
                    calculatedPremium // Annual
            );

            List<String> hospitalNames = insurance.getHospitalsCovered().stream()
                    .map(this::getHospitalNameById)
                    .collect(Collectors.toList());

            ReportData reportData = new ReportData(
                    new UserDTO(user.getId(), user.getUsername(), user.getEmail()),
                    new InsuranceDTO(insurance.getId(), insurance.getName(), insurance.getCountry(), insurance.getAnnualPrice(), hospitalNames),
                    new ReportData.PremiumCalculations(calculatedPremium, breakdown)
            );

            report.setReportData(objectMapper.writeValueAsString(reportData));

            ReportDTO reportDTO = new ReportDTO(report.getId(), user.getId(), insurance.getId(), report.getReportData(), report.getGeneratedAt());

            reportRepository.save(report);

            return reportDTO;
        }
        return null;
    }

    public String getHospitalNameById(Long id) {
        Optional<Hospital> hospitalOptional = hospitalRepository.findById(id);
        return hospitalOptional.map(Hospital::getName).orElse(null);
    }

    @Override
    public List<ReportDTO> getReportsByUserId(Long userId) {
        List<Report> reports = reportRepository.findByUserId(userId);
        return reports.stream()
                .map(report -> new ReportDTO(report.getId(), report.getUser().getId(), report.getInsurance().getId(), report.getReportData(), report.getGeneratedAt()))
                .collect(Collectors.toList());
    }

    private static class ReportData {
        private UserDTO user;
        private InsuranceDTO insurance;
        private PremiumCalculations premiumCalculations;
        private LocalDateTime generatedAt;

        public ReportData(UserDTO user, InsuranceDTO insurance, PremiumCalculations premiumCalculations) {
            this.user = user;
            this.insurance = insurance;
            this.premiumCalculations = premiumCalculations;
            this.generatedAt = LocalDateTime.now();
        }


        public UserDTO getUser() {
            return user;
        }

        public void setUser(UserDTO user) {
            this.user = user;
        }

        public InsuranceDTO getInsurance() {
            return insurance;
        }

        public void setInsurance(InsuranceDTO insurance) {
            this.insurance = insurance;
        }

        public PremiumCalculations getPremiumCalculations() {
            return premiumCalculations;
        }

        public void setPremiumCalculations(PremiumCalculations premiumCalculations) {
            this.premiumCalculations = premiumCalculations;
        }

        public LocalDateTime getGeneratedAt() {
            return generatedAt;
        }

        public void setGeneratedAt(LocalDateTime generatedAt) {
            this.generatedAt = generatedAt;
        }

        // Nested class for premium calculations
        public static class PremiumCalculations {
            private BigDecimal calculatedPremium;
            private Breakdown breakdown;

            public PremiumCalculations(BigDecimal calculatedPremium, Breakdown breakdown) {
                this.calculatedPremium = calculatedPremium;
                this.breakdown = breakdown;
            }


            public BigDecimal getCalculatedPremium() {
                return calculatedPremium;
            }

            public void setCalculatedPremium(BigDecimal calculatedPremium) {
                this.calculatedPremium = calculatedPremium;
            }

            public Breakdown getBreakdown() {
                return breakdown;
            }

            public void setBreakdown(Breakdown breakdown) {
                this.breakdown = breakdown;
            }

            public static class Breakdown {
                private BigDecimal monthly;
                private BigDecimal quarterly;
                private BigDecimal annual;

                public Breakdown(BigDecimal monthly, BigDecimal quarterly, BigDecimal annual) {
                    this.monthly = monthly;
                    this.quarterly = quarterly;
                    this.annual = annual;
                }

                // Getters and Setters
                public BigDecimal getMonthly() {
                    return monthly;
                }

                public void setMonthly(BigDecimal monthly) {
                    this.monthly = monthly;
                }

                public BigDecimal getQuarterly() {
                    return quarterly;
                }

                public void setQuarterly(BigDecimal quarterly) {
                    this.quarterly = quarterly;
                }

                public BigDecimal getAnnual() {
                    return annual;
                }

                public void setAnnual(BigDecimal annual) {
                    this.annual = annual;
                }
            }
        }

    }
}

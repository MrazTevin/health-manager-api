package com.api.health.HealthApp.controller;


import com.api.health.HealthApp.dto.InsuranceDTO;
import com.api.health.HealthApp.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/insurance")
@RequiredArgsConstructor
public class InsuranceRequest {

    private final InsuranceService insuranceService;

    @PostMapping("/create")
    public ResponseEntity<InsuranceDTO> createInsurance(@RequestBody InsuranceDTO insuranceDTO) {
        InsuranceDTO createdInsurance = insuranceService.createInsurance(insuranceDTO);
        return new ResponseEntity<>(createdInsurance, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<InsuranceDTO>> getAllInsurances() {
        List<InsuranceDTO> insurances = insuranceService.getAllInsurances();
        return ResponseEntity.ok(insurances);
    }

    @GetMapping("specific/{id}")
    public ResponseEntity<InsuranceDTO> getInsuranceById(@PathVariable Long id) {
        InsuranceDTO insuranceDTO = insuranceService.getInsuranceById(id);
        return insuranceDTO != null ? ResponseEntity.ok(insuranceDTO) : ResponseEntity.notFound().build();
    }


    @PutMapping("edit/{id}")
    public ResponseEntity<InsuranceDTO> updateInsurance(@PathVariable Long id, @RequestBody InsuranceDTO insuranceDTO) {
        InsuranceDTO updatedInsurance = insuranceService.updateInsurance(id, insuranceDTO);
        return updatedInsurance != null ? ResponseEntity.ok(updatedInsurance) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteInsurance(@PathVariable Long id) {
        insuranceService.deleteInsurance(id);
        return ResponseEntity.noContent().build();
    }


}

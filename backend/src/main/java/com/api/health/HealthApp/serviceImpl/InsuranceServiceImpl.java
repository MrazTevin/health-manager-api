package com.api.health.HealthApp.serviceImpl;

import com.api.health.HealthApp.dto.InsuranceDTO;
import com.api.health.HealthApp.entities.Hospital;
import com.api.health.HealthApp.entities.Insurance;
import com.api.health.HealthApp.repository.HospitalRepository;
import com.api.health.HealthApp.repository.InsuranceRepository;
import com.api.health.HealthApp.service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InsuranceServiceImpl implements InsuranceService {
    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public InsuranceDTO createInsurance(InsuranceDTO insuranceDTO) {
        Insurance insurance = new Insurance();
        insurance.setName(insuranceDTO.getName());
        insurance.setCountry(insuranceDTO.getCountry());
        insurance.setAnnualPrice(insuranceDTO.getAnnualPrice());


        List<String> inputHospitals = insuranceDTO.getHospitalsCovered();
        List<Long> hospitalIds = new ArrayList<>();

        if (inputHospitals != null && !inputHospitals.isEmpty()) {

            hospitalIds = inputHospitals.stream()
                    .map(this::getHospitalIdByName)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        insurance.setHospitalsCovered(hospitalIds);

        Insurance savedInsurance = insuranceRepository.save(insurance);

        List<String> hospitalNames = new ArrayList<>();

        if (savedInsurance.getHospitalsCovered() != null) {
            hospitalNames = savedInsurance.getHospitalsCovered().stream()
                    .map(this::getHospitalNameById)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        return new InsuranceDTO(savedInsurance.getId(), savedInsurance.getName(), savedInsurance.getCountry(), savedInsurance.getAnnualPrice(), hospitalNames);
    }

    public Long getHospitalIdByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        Optional<Hospital> hospitalOptional = hospitalRepository.findByName(name);
        return hospitalOptional.map(Hospital::getId).orElse(null);
    }


    public String getHospitalNameById(Long id) {
        Optional<Hospital> hospitalOptional = hospitalRepository.findById(id);
        return hospitalOptional.map(Hospital::getName).orElse(null);
    }


    @Override
    public InsuranceDTO getInsuranceById(Long id) {
        Insurance insurance = insuranceRepository.findById(id).orElse(null);

        List<String> hospitalNames = insurance.getHospitalsCovered().stream()
                .map(this::getHospitalNameById)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new InsuranceDTO(insurance.getId(), insurance.getName(), insurance.getCountry(), insurance.getAnnualPrice(), hospitalNames);
    }

    @Override
    public Insurance updateInsurance(Long id, Insurance insurance) {
        return null;
    }

    @Override
    public InsuranceDTO updateInsurance(Long id, InsuranceDTO insuranceDTO) {
        Insurance existingInsurance = insuranceRepository.findById(id).orElse(null);
        if (existingInsurance != null) {
            existingInsurance.setName(insuranceDTO.getName());
            existingInsurance.setCountry(insuranceDTO.getCountry());
            existingInsurance.setAnnualPrice(insuranceDTO.getAnnualPrice());

            List<Long> hospitalIds = insuranceDTO.getHospitalsCovered().stream()
                    .map( name -> getHospitalIdByName(String.valueOf(name)))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            existingInsurance.setHospitalsCovered(hospitalIds);


            Insurance updatedInsurance = insuranceRepository.save(existingInsurance);

            List<String> hospitalNames = updatedInsurance.getHospitalsCovered().stream()
                    .map(this::getHospitalNameById)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            return new InsuranceDTO(updatedInsurance.getId(), updatedInsurance.getName(), updatedInsurance.getCountry(), updatedInsurance.getAnnualPrice(), hospitalNames);
        }
        return null;
    }

    @Override
    public void deleteInsurance(Long id) {
        insuranceRepository.deleteById(id);
    }

    @Override
    public List<InsuranceDTO> getAllInsurances() {
        return insuranceRepository.findAll().stream()
                .map(insurance -> {
                    List<String> hospitalNames = insurance.getHospitalsCovered().stream()
                            .map(this::getHospitalNameById)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());

                    return new InsuranceDTO(insurance.getId(), insurance.getName(), insurance.getCountry(), insurance.getAnnualPrice(), hospitalNames);

                } )
                .collect(Collectors.toList());
    }

}

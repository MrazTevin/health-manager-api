package com.api.health.HealthApp.serviceImpl;

import com.api.health.HealthApp.entities.Hospital;
import com.api.health.HealthApp.repository.HospitalRepository;
import com.api.health.HealthApp.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    @Override
    public Hospital getHospitalById(Long id) {
        return hospitalRepository.findById(id).orElse(null);
    }


    public Long getHospitalIdByName(String name) {
        Optional<Hospital> hospital = hospitalRepository.findByName(name);
        return hospital.map(Hospital::getId).orElse(null);

    }

    public Hospital createHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    public Hospital updateHospital(Long id, Hospital hospital) {
        if (hospitalRepository.existsById(id)) {
            hospital.setId(id);
            return hospitalRepository.save(hospital);
        }
        return null;
    }

    public void deleteHospital(Long id) {
        hospitalRepository.deleteById(id);
    }
}

package com.HealthCare.HealthCare.service;

import com.HealthCare.HealthCare.dto.PatientDto;
import com.HealthCare.HealthCare.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import com.HealthCare.HealthCare.mapper.PatientMapper;
import com.HealthCare.HealthCare.model.Patient;
import org.springframework.stereotype.Service;
import com.HealthCare.HealthCare.repository.PatientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public List<PatientDto> getAllPatients(){
        List<Patient> patients = patientRepository.getAll();
        return patients.stream()
                .map(patientMapper::toDto)
                .toList();
    }

    public PatientDto createPatient(PatientDto patientDto){
        Patient patient = patientMapper.toEntity(patientDto);
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toDto(savedPatient);
    }

    public PatientDto modifyPatient(PatientDto patientDto){
        if(!patientRepository.existsById(patientDto.getId())){
            throw new ResourceNotFoundException("Patient non trouvable");
        }
        Patient saved = patientRepository.save(patientMapper.toEntity(patientDto));
        return patientMapper.toDto(saved);
    }

    public String deleteById(Long id) {
        if (!patientRepository.existsById(id)){
            throw new ResourceNotFoundException("Patient non trouvable");
        }

        patientRepository.deleteById(id);
        return "Patient deleted";
    }

    public PatientDto getById(long id){
        Patient  patient = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient non trouvable"));
        return patientMapper.toDto(patient);
    }
}

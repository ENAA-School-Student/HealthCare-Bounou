package com.HealthCare.HealthCare.service;

import com.HealthCare.HealthCare.dto.PatientDto;
import com.HealthCare.HealthCare.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.HealthCare.HealthCare.mapper.PatientMapper;
import com.HealthCare.HealthCare.model.Patient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.HealthCare.HealthCare.repository.PatientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Cacheable(value = "patients")
    public Page<PatientDto> getAllPatients(int page , int size , String orderBy , String orderDir){

        Sort sort = orderDir.equalsIgnoreCase("desc")
                ? Sort.by(orderBy).descending()
                : Sort.by(orderBy).ascending();

        Pageable pageable = PageRequest.of(page ,size ,sort);
        return patientRepository.findAll(pageable).map(patientMapper::toDto);
    }

    @CacheEvict(value = "patients" , allEntries = true)
    public PatientDto createPatient(PatientDto patientDto){
        Patient patient = patientMapper.toEntity(patientDto);
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toDto(savedPatient);
    }

    @CacheEvict(value = "patients" , allEntries = true)
    public PatientDto modifyPatient(PatientDto patientDto){
        if(!patientRepository.existsById(patientDto.getId())){
            throw new ResourceNotFoundException("Patient non trouvable");
        }
        Patient saved = patientRepository.save(patientMapper.toEntity(patientDto));
        return patientMapper.toDto(saved);
    }

    @CacheEvict(value = "patients" , allEntries = true)
    public String deleteById(Long id) {
        if (!patientRepository.existsById(id)){
            throw new ResourceNotFoundException("Patient non trouvable");
        }

        patientRepository.deleteById(id);
        return "Patient deleted";
    }

    @Cacheable(value = "patients" , key = "#id")
    public PatientDto getById(long id){
        Patient  patient = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient non trouvable"));
        return patientMapper.toDto(patient);
    }

    @Cacheable( value = "patients" , key = "#name + '-' + #page + '-' + #size")
    public Page<PatientDto> getByName(@Valid String name, int page , int size) {
        Pageable pageable = PageRequest.of(page, size);
        return patientRepository.findByNomLike(name , pageable).map(patientMapper::toDto);
    }
}

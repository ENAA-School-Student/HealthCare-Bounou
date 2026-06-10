package com.HealthCare.HealthCare.service;

import com.HealthCare.HealthCare.dto.DossierMedicalDto;
import com.HealthCare.HealthCare.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import com.HealthCare.HealthCare.mapper.DossierMedicalMapper;
import com.HealthCare.HealthCare.model.DossierMedical;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.HealthCare.HealthCare.repository.DossierMedicalRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DossierMedicalService {
    private final DossierMedicalRepository dossierMedicalRepository;
    private final DossierMedicalMapper dossierMedicalMapper;

    @CacheEvict(value = "dossiers", allEntries = true)
    public DossierMedicalDto createDossierMedical(DossierMedicalDto dossierMedicalDto){
        if (dossierMedicalDto.getDateCreation() == null){
            dossierMedicalDto.setDateCreation(LocalDate.now());
        }
        DossierMedical dossierMedical = dossierMedicalRepository.save(dossierMedicalMapper.toEntity(dossierMedicalDto));
        return dossierMedicalMapper.toDto(dossierMedical);
    }

    @Cacheable(value = "dossiers", key = "'list-' + #page + '-' + #size + '-' + #orderBy + '-' + #orderDir")
    public Page<DossierMedicalDto> getAll(int page , int size , String orderBy , String orderDir){
        Sort sort = orderDir.equalsIgnoreCase("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy).ascending();
        Pageable pageable = PageRequest.of(page,size,sort);
        return dossierMedicalRepository.findAll(pageable).map(dossierMedicalMapper::toDto);
    }

    @Cacheable(value = "dossiers", key = "'id-' + #id")
    public DossierMedicalDto getById(long id){
        DossierMedical dossierMedical = dossierMedicalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Dossier medical non trouvable"));
        return dossierMedicalMapper.toDto(dossierMedical);
    }

    @CacheEvict(value = "dossiers", allEntries = true)
    public DossierMedicalDto modifyDossierMedical(DossierMedicalDto dossierMedicalDto){
        if(!dossierMedicalRepository.existsById(dossierMedicalDto.getId())){
            throw new ResourceNotFoundException("Dossier medical non trouvable");
        }
        DossierMedical saved = dossierMedicalRepository.save(dossierMedicalMapper.toEntity(dossierMedicalDto));
        return dossierMedicalMapper.toDto(saved);
    }

    @CacheEvict(value = "dossiers", allEntries = true)
    public DossierMedicalDto addDiagnostic(long id ,String diag){
        DossierMedical dossierMedical = dossierMedicalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Dossier medical non trouvable"));
        dossierMedical.setDiagnostic(diag);
        dossierMedicalRepository.save(dossierMedical);
        return dossierMedicalMapper.toDto(dossierMedical);
    }

    @CacheEvict(value = "dossiers", allEntries = true)
    public DossierMedicalDto addObservation(long id , String observation){
        DossierMedical dossierMedical = dossierMedicalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Dossier medical non trouvable"));
        dossierMedical.setObservation(observation);
        dossierMedicalRepository.save(dossierMedical);
        return dossierMedicalMapper.toDto(dossierMedical);
    }
}
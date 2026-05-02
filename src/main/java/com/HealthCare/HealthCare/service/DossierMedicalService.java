package com.HealthCare.HealthCare.service;

import com.HealthCare.HealthCare.dto.DossierMedicalDto;
import com.HealthCare.HealthCare.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import com.HealthCare.HealthCare.mapper.DossierMedicalMapper;
import com.HealthCare.HealthCare.model.DossierMedical;
import org.springframework.stereotype.Service;
import com.HealthCare.HealthCare.repository.DossierMedicalRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DossierMedicalService {
    private final DossierMedicalRepository dossierMedicalRepository;
    private final DossierMedicalMapper dossierMedicalMapper;

    public DossierMedicalDto createDossierMedical(DossierMedicalDto dossierMedicalDto){
        if (dossierMedicalDto.getDateCreation() == null){
            dossierMedicalDto.setDateCreation(LocalDate.now());
        }
        DossierMedical dossierMedical = dossierMedicalRepository.save(dossierMedicalMapper.toEntity(dossierMedicalDto));
        return dossierMedicalMapper.toDto(dossierMedical);
    }

    public DossierMedicalDto getById(long id){
        DossierMedical dossierMedical = dossierMedicalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Dossier medical non trouvable"));
        return dossierMedicalMapper.toDto(dossierMedical);
    }

    public DossierMedicalDto modifyDossierMedical(DossierMedicalDto dossierMedicalDto){
        if(!dossierMedicalRepository.existsById(dossierMedicalDto.getId())){
            throw new ResourceNotFoundException("Dossier medical non trouvable");
        }
        DossierMedical saved = dossierMedicalRepository.save(dossierMedicalMapper.toEntity(dossierMedicalDto));
        return dossierMedicalMapper.toDto(saved);
    }

    public DossierMedicalDto addDiagnostic(long id ,String diag){
        DossierMedical dossierMedical = dossierMedicalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Dossier medical non trouvable"));
        dossierMedical.setDiagnostic(diag);
        dossierMedicalRepository.save(dossierMedical);
        return dossierMedicalMapper.toDto(dossierMedical);
    }

    public DossierMedicalDto addObservation(long id , String observation){
        DossierMedical dossierMedical = dossierMedicalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Dossier medical non trouvable"));
        dossierMedical.setObservation(observation);
        dossierMedicalRepository.save(dossierMedical);
        return dossierMedicalMapper.toDto(dossierMedical);
    }

}

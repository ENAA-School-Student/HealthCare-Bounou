package com.HealthCare.HealthCare.mapper;

import com.HealthCare.HealthCare.dto.DossierMedicalDto;
import com.HealthCare.HealthCare.model.DossierMedical;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DossierMedicalMapper {
    DossierMedicalDto toDto(DossierMedical dossierMedical);
    DossierMedical toEntity(DossierMedicalDto dossierMedicalDto);
}

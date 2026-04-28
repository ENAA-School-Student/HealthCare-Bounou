package com.HealthCare.HealthCare.mapper;

import com.HealthCare.HealthCare.dto.PatientDto;
import com.HealthCare.HealthCare.model.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientDto toDto(Patient patient);
    Patient toEntity(PatientDto patientDto);
}

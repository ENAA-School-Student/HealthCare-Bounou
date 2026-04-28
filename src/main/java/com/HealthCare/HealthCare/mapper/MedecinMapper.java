package com.HealthCare.HealthCare.mapper;

import com.HealthCare.HealthCare.dto.MedecinDto;
import com.HealthCare.HealthCare.model.Medecin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedecinMapper {
    MedecinDto toDto(Medecin medecin);
    Medecin toEntity(MedecinDto medecinDto);
}

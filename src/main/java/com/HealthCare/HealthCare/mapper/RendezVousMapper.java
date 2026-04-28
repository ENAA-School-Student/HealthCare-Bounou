package com.HealthCare.HealthCare.mapper;

import com.HealthCare.HealthCare.dto.RendezVousDto;
import com.HealthCare.HealthCare.model.RendezVous;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RendezVousMapper {
    RendezVousDto toDto(RendezVous rendezVous);
    RendezVous toEntity(RendezVousDto rendezVousDto);
}

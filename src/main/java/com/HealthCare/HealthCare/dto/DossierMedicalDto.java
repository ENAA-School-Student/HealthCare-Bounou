package com.HealthCare.HealthCare.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DossierMedicalDto {
    private long id;
    private String diagnostic;
    private String observation;
    private LocalDate dateCreation;
    private long patientId;
    private long medecinId;
}

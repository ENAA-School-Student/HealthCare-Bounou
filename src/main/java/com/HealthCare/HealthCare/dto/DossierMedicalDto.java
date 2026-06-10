package com.HealthCare.HealthCare.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class DossierMedicalDto implements Serializable {
    private long id;
    private String diagnostic;
    private String observation;
    private LocalDate dateCreation;
    private long patientId;
    private long medecinId;
}

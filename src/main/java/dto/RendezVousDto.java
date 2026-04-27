package dto;

import enums.StatusRendezVous;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RendezVousDto {
    private long id;
    @NotNull(message = "Le champ 'dateRendezVous' est obligatoire")
    @Future(message = "Le champ 'dateRendezVous' doit être une date future")
    private LocalDateTime dateRendezVous;

    @NotNull(message = "Le champ 'status' est obligatoire")
    @Pattern(regexp = "^(En_ATTENTE|TERMINE|CONFIRME|ANNULE)$", message = "Le champ 'status' doit être l'une des valeurs suivantes : En_ATTENTE, TERMINE, CONFIRME, ANNULE")
    private StatusRendezVous status;

    @NotNull(message = "Le champ 'patientId' est obligatoire")
    private long patientId;

    @NotNull(message = "Le champ 'medecinId' est obligatoire")
    private long medecinId;

    @NotNull(message = "Le champ 'dossierMedicalId' est obligatoire")
    private long dossierMedicalId;


}

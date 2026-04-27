package dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDto {
    private long id;
    @NotNull(message = "Le champ 'nom' est obligatoire")
    private String nom;
    @NotNull(message = "Le champ 'prenom' est obligatoire")
    private String prenom;
    @NotNull(message = "Le champ 'email' est obligatoire")
    @Email(message = "Le champ 'email' doit être une adresse email valide")
    private String email;
    @NotNull(message = "Le champ 'dateNaissance' est obligatoire")
    @Past(message = "Le champ 'dateNaissance' doit être une date passée")
    private LocalDate dateNaissance;
    @NotNull(message = "Le champ 'telephone' est obligatoire")
    @Size(min = 10, max = 12, message = "Le champ 'telephone' doit être entre 10 et 12 caractères")
    private String telephone;
}

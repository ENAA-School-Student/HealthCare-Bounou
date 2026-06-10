
package com.HealthCare.HealthCare.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class MedecinDto implements Serializable {
    private long id;
    @NotNull(message = "Le champ 'nom' est obligatoire")
    private String nom;
    @NotNull(message = "Le champ 'prenom' est obligatoire")
    private String specialite;
    @NotNull(message = "Le champ 'email' est obligatoire")
    @Email(message = "Le champ 'email' doit être une adresse email valide")
    private String email;
    @NotNull(message = "Le champ 'telephone' est obligatoire")
    @Positive(message = "Le champ 'telephone' doit être un nombre positif")
    @Size(min = 10, max = 12, message = "Le champ 'telephone' doit être entre 10 et 12 caractères")
    private String telephone;

}


package dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MedecinDto {
    private long id;
    @NotNull(message = "Le champ 'nom' est obligatoire")
    private String nom;
    @NotNull(message = "Le champ 'prenom' est obligatoire")
    private String specialite;
    @NotNull(message = "Le champ 'email' est obligatoire")
    private String email;
    @NotNull(message = "Le champ 'telephone' est obligatoire")
    @Positive(message = "Le champ 'telephone' doit être un nombre positif")
    @Size(min = 10, max = 12, message = "Le champ 'telephone' doit être entre 10 et 12 caractères")
    private int telephone;

}

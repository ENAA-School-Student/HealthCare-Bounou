package dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DossierMedicalDto {
    private int id;
    @NotNull(message = "Le champ 'diagnostic' est obligatoire")
    private String diagnostic;
}

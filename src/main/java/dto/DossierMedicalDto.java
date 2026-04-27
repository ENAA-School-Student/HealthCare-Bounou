package dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DossierMedicalDto {
    private long id;
    private String diagnostic;
    private String observation;
}

package mapper;

import dto.DossierMedicalDto;
import model.DossierMedical;

public interface DossierMedicalMapper {
    DossierMedicalDto toDo(DossierMedical dossierMedical);
    DossierMedical toEntity(DossierMedicalDto dossierMedicalDto);
}

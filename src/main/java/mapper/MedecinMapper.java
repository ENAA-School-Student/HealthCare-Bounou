package mapper;

import dto.MedecinDto;
import model.Medecin;

public interface MedecinMapper {
    MedecinDto toDo(Medecin medecin);
    Medecin toEntity(MedecinDto medecinDto);
}

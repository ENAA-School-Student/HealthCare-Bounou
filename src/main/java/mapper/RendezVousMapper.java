package mapper;

import dto.RendezVousDto;
import model.RendezVous;

public interface RendezVousMapper {
    RendezVousDto toDo(RendezVous rendezVous);
    RendezVous toEntity(RendezVousDto rendezVousDto);
}

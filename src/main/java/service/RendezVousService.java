
package service;

import dto.RendezVousDto;
import enums.StatusRendezVous;
import lombok.RequiredArgsConstructor;
import mapper.RendezVousMapper;
import model.RendezVous;
import org.springframework.stereotype.Service;
import repository.RendezVousRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RendezVousService {
    private final RendezVousRepository rendezVousRepository;
    private final RendezVousMapper rendezVousMapper;

    public RendezVousDto createRendezVous(RendezVousDto rendezVousDto){
        RendezVous rendezVous = rendezVousRepository.save(rendezVousMapper.toEntity(rendezVousDto));
        return rendezVousMapper.toDo(rendezVous);
    }

    public RendezVousDto getById(long id){
        RendezVous rendezVous = rendezVousRepository.findById(id).orElseThrow(() -> new RuntimeException("Rendez vous non trouvable"));
        return rendezVousMapper.toDo(rendezVous);
    }

    public List<RendezVousDto> getAll(){
        List<RendezVous> rendezVous = rendezVousRepository.findAll();
        return rendezVous.stream().map(rendezVousMapper::toDo).toList();
    }

    public RendezVousDto modify(RendezVousDto rendezVousDto){
        if (!rendezVousRepository.existsById(rendezVousDto.getId())){
            throw new RuntimeException("Rendez vous non trouvable");
        }
        RendezVous rendezVous = rendezVousRepository.save(rendezVousMapper.toEntity(rendezVousDto));
        return rendezVousMapper.toDo(rendezVous);
    }

    public RendezVousDto annulerRdv(long id){
        RendezVous rendezVous = rendezVousRepository.findById(id).orElseThrow(() -> new RuntimeException("Rendez vous non trouvable"));
        rendezVous.setStatus(StatusRendezVous.ANNULE);
        return rendezVousMapper.toDo(rendezVousRepository.save(rendezVous));
    }



}

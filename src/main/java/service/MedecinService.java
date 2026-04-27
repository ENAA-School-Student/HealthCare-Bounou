package service;

import dto.MedecinDto;
import lombok.RequiredArgsConstructor;
import mapper.MedecinMapper;
import model.Medecin;
import org.springframework.stereotype.Service;
import repository.MedecinRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedecinService {
    private final MedecinRepository medecinRepository;
    private final MedecinMapper medecinMapper;

    public String deleteById(long id){
        if (!medecinRepository.existsById(id)){
            throw new RuntimeException("Medecin non trouvable");
        }
        medecinRepository.deleteById(id);
        return "Medecin deleted";
    }

    public MedecinDto getById(long id){
        Medecin medecin = medecinRepository.findById(id).orElseThrow(() -> new RuntimeException("Medecin non trouvable"));
        return medecinMapper.toDo(medecin);
    }

    public List<MedecinDto> getAll(){
        List<Medecin> medecins = medecinRepository.findAll();
        return medecins.stream().map(medecinMapper::toDo).toList();
    }

    public MedecinDto addMedecin(MedecinDto medecinDto)
    {
        Medecin medecin = medecinMapper.toEntity(medecinDto);
        Medecin savedMedecin = medecinRepository.save(medecin);
        return medecinMapper.toDo(savedMedecin);
    }


}

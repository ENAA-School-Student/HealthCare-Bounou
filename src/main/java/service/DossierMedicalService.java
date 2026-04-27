package service;

import dto.DossierMedicalDto;
import lombok.RequiredArgsConstructor;
import mapper.DossierMedicalMapper;
import model.DossierMedical;
import org.springframework.stereotype.Service;
import repository.DossierMedicalRepository;

@Service
@RequiredArgsConstructor
public class DossierMedicalService {
    private final DossierMedicalRepository dossierMedicalRepository;
    private final DossierMedicalMapper dossierMedicalMapper;

    public DossierMedicalDto createDossierMedical(DossierMedicalDto dossierMedicalDto){
        DossierMedical dossierMedical = dossierMedicalRepository.save(dossierMedicalMapper.toEntity(dossierMedicalDto));
        return dossierMedicalMapper.toDo(dossierMedical);
    }

    public DossierMedicalDto getById(long id){
        DossierMedical dossierMedical = dossierMedicalRepository.findById(id).orElseThrow(() -> new RuntimeException("Dossier medical non trouvable"));
        return dossierMedicalMapper.toDo(dossierMedical);
    }

    public DossierMedicalDto modifyDossierMedical(DossierMedicalDto dossierMedicalDto){
        if(!dossierMedicalRepository.existsById(dossierMedicalDto.getId())){
            throw new RuntimeException("Dossier medical non trouvable");
        }
        DossierMedical saved = dossierMedicalRepository.save(dossierMedicalMapper.toEntity(dossierMedicalDto));
        return dossierMedicalMapper.toDo(saved);
    }

    public DossierMedicalDto addDiagnostic(long id ,String diag){
        DossierMedical dossierMedical = dossierMedicalRepository.findById(id).orElseThrow(() -> new RuntimeException("Dossier medical non trouvable"));
        dossierMedical.setDiagnostic(diag);
        dossierMedicalRepository.save(dossierMedical);
        return dossierMedicalMapper.toDo(dossierMedical);
    }

    public DossierMedicalDto addObservation(long id , String observation){
        DossierMedical dossierMedical = dossierMedicalRepository.findById(id).orElseThrow(() -> new RuntimeException("Dossier medical non trouvable"));
        dossierMedical.setObservation(observation);
        dossierMedicalRepository.save(dossierMedical);
        return dossierMedicalMapper.toDo(dossierMedical);
    }

}

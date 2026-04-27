package service;

import dto.PatientDto;
import lombok.RequiredArgsConstructor;
import mapper.PatientMapper;
import model.Patient;
import org.springframework.stereotype.Service;
import repository.PatientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public List<PatientDto> getAllPatients(){
        List<Patient> patients = patientRepository.getAll();
        return patients.stream()
                .map(patientMapper::toDo)
                .toList();
    }

    public PatientDto createPatient(PatientDto patientDto){
        Patient patient = patientMapper.toEntity(patientDto);
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toDo(savedPatient);
    }

    public PatientDto modifyPatient(PatientDto patientDto){
        if(!patientRepository.existsById(patientDto.getId())){
            throw new RuntimeException("Patient non trouvable");
        }
        Patient saved = patientRepository.save(patientMapper.toEntity(patientDto));
        return patientMapper.toDo(saved);
    }

    public String deleteById(Long id)
    {
        if (!patientRepository.existsById(id)){
            throw new RuntimeException("Patient non trouvable");
        }

        patientRepository.deleteById(id);
        return "Patient deleted";
    }

    public PatientDto getById(long id){
        Patient  patient = patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient non trouvable"));
        return patientMapper.toDo(patient);
    }
}

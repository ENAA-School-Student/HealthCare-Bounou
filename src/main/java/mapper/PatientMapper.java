package mapper;

import dto.PatientDto;
import model.Patient;

public interface PatientMapper {
    PatientDto toDo(Patient patient);
    Patient toEntity(PatientDto patientDto);
}

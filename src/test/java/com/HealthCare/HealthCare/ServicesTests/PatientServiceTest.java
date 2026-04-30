package com.HealthCare.HealthCare.ServicesTests;

import com.HealthCare.HealthCare.dto.PatientDto;
import com.HealthCare.HealthCare.mapper.PatientMapper;
import com.HealthCare.HealthCare.model.Patient;
import com.HealthCare.HealthCare.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class PatientServiceTest {
    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientMapper patientMapper;

    @Test
    void testSavedPatient(){
        Patient patient = new Patient();
        patient.setNom("Marouane");
        patient.setPrenom("Bounou");
        patient.setEmail("Marouane@gmail.com");
        patient.setDateNaissance(LocalDate.parse("1990-01-01"));
        patient.setRendezVousList(List.of());

        PatientDto patientSavedDto = patientService.createPatient(patientMapper.toDto(patient));
        assertNotNull(patientSavedDto);
        assertEquals(patientMapper.toEntity(patientSavedDto).getClass() , patient.getClass());
        assertEquals(patientSavedDto.getEmail() , patient.getEmail());
    }

}

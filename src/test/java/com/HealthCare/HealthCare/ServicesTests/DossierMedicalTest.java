package com.HealthCare.HealthCare.ServicesTests;

import com.HealthCare.HealthCare.dto.DossierMedicalDto;
import com.HealthCare.HealthCare.dto.MedecinDto;
import com.HealthCare.HealthCare.dto.PatientDto;
import com.HealthCare.HealthCare.mapper.DossierMedicalMapper;
import com.HealthCare.HealthCare.mapper.MedecinMapper;
import com.HealthCare.HealthCare.mapper.PatientMapper;
import com.HealthCare.HealthCare.model.DossierMedical;
import com.HealthCare.HealthCare.model.Medecin;
import com.HealthCare.HealthCare.model.Patient;
import com.HealthCare.HealthCare.service.DossierMedicalService;
import com.HealthCare.HealthCare.service.MedecinService;
import com.HealthCare.HealthCare.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class DossierMedicalTest {

    @Autowired
    private DossierMedicalService dossierMedicalService;
    @Autowired
    private DossierMedicalMapper dossierMedicalMapper;
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private MedecinService medecinService;
    @Autowired
    private MedecinMapper medecinMapper;

    @Test
    void addObservationAndDiagnosticTest(){
        Patient patient = new Patient();
        patient.setNom("Marouane");
        patient.setPrenom("Bounou");
        patient.setEmail("Marouane@gmail.com");
        patient.setDateNaissance(LocalDate.parse("1990-01-01"));
        patient.setRendezVousList(List.of());

        Medecin medecin = new Medecin();
        medecin.setEmail("test@gmail.com");
        medecin.setNom("Test");
        medecin.setTelephone("1234567890");
        medecin.setSpecialite("Dentist");
        medecin.setRendezVousList(List.of());

        PatientDto savedPatientDto = patientService.createPatient(patientMapper.toDto(patient));
        MedecinDto savedMedecinDto = medecinService.addMedecin(medecinMapper.toDto(medecin));

        DossierMedical dossierMedical = new DossierMedical();
        dossierMedical.setPatientId(savedPatientDto.getId());
        dossierMedical.setMedecinId(savedMedecinDto.getId());
        dossierMedical.setDateCreation(LocalDate.now());
        dossierMedical.setRendezVousList(List.of());

        DossierMedicalDto dossierMedicalDto = dossierMedicalService.createDossierMedical(dossierMedicalMapper.toDto(dossierMedical));

        DossierMedicalDto savedDiag = dossierMedicalService.addDiagnostic(dossierMedicalDto.getId(),"Test diag");

        assertNotNull(savedDiag);
        assertEquals("Test diag", savedDiag.getDiagnostic());

        DossierMedicalDto savedObs = dossierMedicalService.addObservation(dossierMedicalDto.getId(),"Test obs");

        assertNotNull(savedObs);
        assertEquals("Test obs", savedObs.getObservation());
    }

}

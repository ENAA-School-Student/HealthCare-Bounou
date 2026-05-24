package com.HealthCare.HealthCare.ServicesTests;

import com.HealthCare.HealthCare.dto.DossierMedicalDto;
import com.HealthCare.HealthCare.dto.MedecinDto;
import com.HealthCare.HealthCare.dto.PatientDto;
import com.HealthCare.HealthCare.dto.RendezVousDto;
import com.HealthCare.HealthCare.enums.StatusRendezVous;
import com.HealthCare.HealthCare.mapper.DossierMedicalMapper;
import com.HealthCare.HealthCare.mapper.MedecinMapper;
import com.HealthCare.HealthCare.mapper.PatientMapper;
import com.HealthCare.HealthCare.mapper.RendezVousMapper;
import com.HealthCare.HealthCare.model.DossierMedical;
import com.HealthCare.HealthCare.model.Medecin;
import com.HealthCare.HealthCare.model.Patient;
import com.HealthCare.HealthCare.service.DossierMedicalService;
import com.HealthCare.HealthCare.service.MedecinService;
import com.HealthCare.HealthCare.service.PatientService;
import com.HealthCare.HealthCare.service.RendezVousService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class RendezVousServiceTest {

    @Autowired
    private RendezVousService rendezVousService;
    @Autowired
    private RendezVousMapper rendezVousMapper;
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private MedecinService medecinService;
    @Autowired
    private MedecinMapper medecinMapper;
    @Autowired
    private DossierMedicalService dossierMedicalService;
    @Autowired
    private DossierMedicalMapper dossierMedicalMapper;

    RendezVousDto getRendeVous(){
        Patient patient = new Patient();
        patient.setDateNaissance(LocalDate.parse("1990-01-01"));
        patient.setPrenom("Marouane");
        patient.setNom("Bounou");
        patient.setEmail("test@gmail.com");
        patient.setTelephone("1234567890");
        patient.setRendezVousList(List.of());
        PatientDto savedPatientDto = patientService.createPatient(patientMapper.toDto(patient));

        Medecin medecin = new Medecin();
        medecin.setEmail("test@gmail.com");
        medecin.setNom("Test");
        medecin.setTelephone("1234567890");
        medecin.setSpecialite("Dentist");
        medecin.setRendezVousList(List.of());
        MedecinDto savedMedecinDto = medecinService.addMedecin(medecinMapper.toDto(medecin));

        DossierMedical dossierMedical = new DossierMedical();
        dossierMedical.setPatientId(savedPatientDto.getId());
        dossierMedical.setMedecinId(savedMedecinDto.getId());
        dossierMedical.setObservation("Test observation");
        dossierMedical.setDiagnostic("Test diagnostic");
        dossierMedical.setDateCreation(LocalDate.now());
        dossierMedical.setRendezVousList(List.of());
        DossierMedicalDto savedDossierMedicalDto = dossierMedicalService.createDossierMedical(dossierMedicalMapper.toDto(dossierMedical));

        RendezVousDto rendezVous = new RendezVousDto();
        rendezVous.setPatientId(savedPatientDto.getId());
        rendezVous.setMedecinId(savedMedecinDto.getId());
        rendezVous.setDossierMedicalId(savedDossierMedicalDto.getId());
        rendezVous.setDateRendezVous(LocalDateTime.of(2026,6,1,10,30));
        rendezVous.setStatus(StatusRendezVous.En_ATTENTE);
        return rendezVousService.createRendezVous(rendezVous);
    }

    @Test
    void testCreateRendezVous(){
        RendezVousDto savedRendezVousDto = getRendeVous();
        assertNotNull(savedRendezVousDto);
        assertEquals(savedRendezVousDto.getDateRendezVous(), LocalDateTime.of(2026,6,1,10,30));
    }

    @Test
    void testAnnulerRdv(){
        RendezVousDto savedRendezVousDto = getRendeVous();
        RendezVousDto canceldRdv = rendezVousService.annulerRdv(savedRendezVousDto.getId());
        assertNotNull(canceldRdv);
        assertEquals(StatusRendezVous.ANNULE , canceldRdv.getStatus());
    }

    @Test
    void testGetById(){
        RendezVousDto savedRendezVousDto = getRendeVous();
        RendezVousDto foundRendezVousDto = rendezVousService.getById(savedRendezVousDto.getId());
        assertNotNull(foundRendezVousDto);
        assertEquals(savedRendezVousDto.getDateRendezVous(), foundRendezVousDto.getDateRendezVous());
    }

     @Test
     void testGetAll(){
         RendezVousDto savedRendezVousDto = getRendeVous();
          Page<RendezVousDto> rendezVousDtos = rendezVousService.getAll(0,10,"dateRendezVous","asc");
          assertNotNull(rendezVousDtos);
          assertTrue(rendezVousDtos.getTotalElements() >= 1);
          assertTrue(rendezVousDtos.getContent().stream().anyMatch(rendezVousDto -> rendezVousDto.getId() == savedRendezVousDto.getId()));
     }

     @Test
     void testGetByStatus(){
         RendezVousDto savedRendezVousDto = getRendeVous();
         Page<RendezVousDto> rendezVousDtos = rendezVousService.getByStatus("En_ATTENTE", 0, 10, "dateRendezVous", "asc");

         assertNotNull(rendezVousDtos);
         assertTrue(rendezVousDtos.getContent().stream().anyMatch(rendezVousDto -> rendezVousDto.getId() == savedRendezVousDto.getId()));
     }

     @Test
     void testGetByDate(){
         RendezVousDto savedRendezVousDto = getRendeVous();
         Page<RendezVousDto> rendezVousDtos = rendezVousService.getByDate("2026-06-01", 0, 10, "dateRendezVous", "asc");

         assertNotNull(rendezVousDtos);
         assertTrue(rendezVousDtos.getContent().stream().anyMatch(rendezVousDto -> rendezVousDto.getId() == savedRendezVousDto.getId()));
     }



}

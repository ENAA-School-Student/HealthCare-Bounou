package com.HealthCare.HealthCare.ServicesTests;

import com.HealthCare.HealthCare.dto.MedecinDto;
import com.HealthCare.HealthCare.mapper.MedecinMapper;
import com.HealthCare.HealthCare.model.Medecin;
import com.HealthCare.HealthCare.service.MedecinService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class MedecinServiceTest {

    @Autowired
    private MedecinService medecinService;
    @Autowired
    private MedecinMapper medecinMapper;

    @Test
    void testCreateMedecin(){
        Medecin medecin = new Medecin();
        medecin.setEmail("test@gmail.com");
        medecin.setNom("Test");
        medecin.setTelephone("1234567890");
        medecin.setSpecialite("Dentist");
        medecin.setRendezVousList(List.of());

        MedecinDto savedMedecinDto = medecinService.addMedecin(medecinMapper.toDto(medecin));
        assertNotNull(savedMedecinDto);
        assertEquals(savedMedecinDto.getEmail(), medecin.getEmail());
    }

    @Test
    void testDeleteAndCreateMedecin(){
        Medecin medecin = new Medecin();
        medecin.setEmail("test@gmail.com");
        medecin.setNom("Test");
        medecin.setTelephone("1234567890");
        medecin.setSpecialite("Dentist");
        medecin.setRendezVousList(List.of());

        MedecinDto savedMedecinDto = medecinService.addMedecin(medecinMapper.toDto(medecin));
        medecinService.deleteById(savedMedecinDto.getId());

        MedecinDto deletedMedecin = medecinService.getById(savedMedecinDto.getId());
        assertNull(deletedMedecin);
    }

}

package com.HealthCare.HealthCare.controller;

import com.HealthCare.HealthCare.dto.DossierMedicalDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.HealthCare.HealthCare.mapper.DossierMedicalMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.HealthCare.HealthCare.service.DossierMedicalService;

@RestController
@RequestMapping("/dossier-medical")
@RequiredArgsConstructor
public class DossierMedcialController {
    private final DossierMedicalService dossierMedicalService;
    private final DossierMedicalMapper dossierMedicalMapper;

    @GetMapping("/{id}")
    public ResponseEntity<DossierMedicalDto> get(@PathVariable long id) {
        return ResponseEntity.ok(dossierMedicalService.getById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<DossierMedicalDto> post(@RequestBody DossierMedicalDto dossierMedicalDto) {
        return ResponseEntity.ok(dossierMedicalService.createDossierMedical(dossierMedicalDto));
    }

    @PutMapping("/update")
    public ResponseEntity<DossierMedicalDto> update(@RequestBody DossierMedicalDto dossierMedicalDto){
        return ResponseEntity.ok(dossierMedicalService.modifyDossierMedical(dossierMedicalDto));
    }

    @PostMapping("/add/diagnostic/{id}")
    public ResponseEntity<DossierMedicalDto> addDiagnostic(@Valid @PathVariable long id , @RequestBody String diagnostic) {
        return ResponseEntity.ok(dossierMedicalService.addDiagnostic(id,diagnostic));
    }

    @PostMapping("/add/observation/{id}")
    public ResponseEntity<DossierMedicalDto> addObservation(@Valid @PathVariable long id , @RequestBody String observation) {
        return ResponseEntity.ok(dossierMedicalService.addObservation(id,observation));
    }
}

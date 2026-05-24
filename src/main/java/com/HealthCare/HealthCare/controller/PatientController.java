package com.HealthCare.HealthCare.controller;

import com.HealthCare.HealthCare.dto.PatientDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.HealthCare.HealthCare.mapper.PatientMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.HealthCare.HealthCare.service.PatientService;

import java.util.List;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    private final PatientMapper patientMapper;

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatient(@Valid @PathVariable long id){
        PatientDto patientDto = patientService.getById(id);
        return ResponseEntity.ok(patientDto);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<Page<PatientDto>> getPatientByName(@Valid @PathVariable String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Page<PatientDto> patientDto = patientService.getByName(name  ,page , size);
        return ResponseEntity.ok(patientDto);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<PatientDto>> getAllPatient(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        Page<PatientDto> patientDtos = patientService.getAllPatients(page,size,sortBy,sortDir);
        return ResponseEntity.ok(patientDtos);
    }

    @PostMapping("/add")
    public ResponseEntity<PatientDto> createPatient(@Valid @RequestBody PatientDto patientDto){
        PatientDto created = patientService.createPatient(patientDto);
        return new ResponseEntity<>(created,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<PatientDto> updatePatient(@Valid @RequestBody PatientDto patientDto){
        PatientDto updated = patientService.modifyPatient(patientDto);
        return new ResponseEntity<>(updated,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePatient(@Valid @PathVariable long id){
        patientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

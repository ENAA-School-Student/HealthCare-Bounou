package com.HealthCare.HealthCare.controller;

import com.HealthCare.HealthCare.dto.MedecinDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.HealthCare.HealthCare.mapper.MedecinMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.HealthCare.HealthCare.service.MedecinService;

import java.util.List;

@RestController
@RequestMapping("/medecin")
@RequiredArgsConstructor
public class MedecinController {
    private final MedecinService medecinService;
    private final MedecinMapper medecinMapper;

    @GetMapping("/{id}")
    public ResponseEntity<MedecinDto> getById(@Valid @Param("id") long id){
        return ResponseEntity.ok(medecinService.getById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MedecinDto>> getAll(){
        return ResponseEntity.ok(medecinService.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<MedecinDto> createNew(@Valid @RequestBody MedecinDto medecinDto){
        MedecinDto created = medecinService.addMedecin(medecinDto);
        return new ResponseEntity<>(created , HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<MedecinDto> update(@Valid @RequestBody MedecinDto medecinDto){
        MedecinDto updated = medecinService.update(medecinDto);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        return ResponseEntity.ok(medecinService.deleteById(id));
    }

}

package com.HealthCare.HealthCare.controller;

import com.HealthCare.HealthCare.dto.RendezVousDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.HealthCare.HealthCare.mapper.RendezVousMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.HealthCare.HealthCare.service.RendezVousService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rendez-vous")
public class RendezVousController {
    private final RendezVousService rendezVousService;
    private final RendezVousMapper rendezVousMapper;

    @GetMapping("/{id}")
    public ResponseEntity<RendezVousDto> getById(@PathVariable long id) {
        return ResponseEntity.ok(rendezVousService.getById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<RendezVousDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir

    ){
        return ResponseEntity.ok(rendezVousService.getAll(page , size , sortBy , sortDir));
    }

    @GetMapping("/search/date/{date}")
    public ResponseEntity<Page<RendezVousDto>> getByDate(
            @Valid @PathVariable String date ,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        return ResponseEntity.ok(rendezVousService.getByDate(date , page , size , sortBy , sortDir));
    }

    @GetMapping("/search/status/{status}")
    public ResponseEntity<Page<RendezVousDto>> getByStatus(
            @Valid @PathVariable String status ,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        return ResponseEntity.ok(rendezVousService.getByStatus(status , page , size , sortBy , sortDir));
    }

    @PostMapping("/create")
    public ResponseEntity<RendezVousDto> create(@Valid @RequestBody RendezVousDto rendezVousDto){
        return ResponseEntity.ok(rendezVousService.createRendezVous(rendezVousDto));
    }

    @PutMapping("/update")
    public ResponseEntity<RendezVousDto> update(@Valid @RequestBody RendezVousDto rendezVousDto) {
        return ResponseEntity.ok(rendezVousService.modify(rendezVousDto));
    }

    @PutMapping("/{id}/annuler")
    public ResponseEntity<RendezVousDto> annulerRdv(@PathVariable long id){
        return ResponseEntity.ok(rendezVousService.annulerRdv(id));
    }

    @PutMapping("/{id}/confirmer")
    public ResponseEntity<RendezVousDto> cinfirmerRdv(@PathVariable long id){
        return ResponseEntity.ok(rendezVousService.confirmeRdv(id));
    }

    @GetMapping("/all/medecin/{nom}")
    public ResponseEntity<List<RendezVousDto>> getAllLikeMedecin(@Valid @PathVariable String nom){
        return ResponseEntity.ok(rendezVousService.getAllLikeMedecin(nom));
    }

    @GetMapping("/all/patient/{nom}")
    public ResponseEntity<List<RendezVousDto>> getAllLikePatient(@Valid @PathVariable String nom){
        return ResponseEntity.ok(rendezVousService.getAllLikePation(nom));
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<RendezVousDto>> getPatientById(@PathVariable long id){
        return ResponseEntity.ok(rendezVousService.getByPatientId(id));
    }

    @GetMapping("/medecin/{id}")
    public ResponseEntity<List<RendezVousDto>> getMedecinById(@PathVariable long id){
        return ResponseEntity.ok(rendezVousService.getByMedecinId(id));
    }

}

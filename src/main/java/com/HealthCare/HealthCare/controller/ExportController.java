package com.HealthCare.HealthCare.controller;

import com.HealthCare.HealthCare.service.DossierMedicalService;
import com.HealthCare.HealthCare.service.RendezVousService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/exports")
@RequiredArgsConstructor
public class ExportController {

    private final DossierMedicalService dossierMedicalService;
    private final RendezVousService rendezVousService;

    @GetMapping("/dossier/{id}/pdf")
    public ResponseEntity<Resource> downloadDossierPdf(@PathVariable long id) {
        ByteArrayInputStream stream = dossierMedicalService.downloadDossierPdf(id);
        InputStreamResource resource = new InputStreamResource(stream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=dossier_medical_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @GetMapping("/dossier/{id}/rapport")
    public ResponseEntity<Resource> downloadRapportPdf(@PathVariable long id) {
        ByteArrayInputStream stream = dossierMedicalService.downloadRapportSimple(id);
        InputStreamResource resource = new InputStreamResource(stream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rapport_simple_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @GetMapping("/patient/{patientId}/rendezvous/excel")
    public ResponseEntity<Resource> downloadRendezVousExcel(@PathVariable long patientId) {
        ByteArrayInputStream stream = rendezVousService.downloadPatientRendezVousExcel(patientId);
        InputStreamResource resource = new InputStreamResource(stream);

        String excelContentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=patient_" + patientId + "_rendezvous.xlsx")
                .contentType(MediaType.parseMediaType(excelContentType))
                .body(resource);
    }
}
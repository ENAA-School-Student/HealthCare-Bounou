
package com.HealthCare.HealthCare.service;

import com.HealthCare.HealthCare.dto.RendezVousDto;
import com.HealthCare.HealthCare.enums.StatusRendezVous;
import com.HealthCare.HealthCare.repository.DossierMedicalRepository;
import com.HealthCare.HealthCare.repository.MedecinRepository;
import com.HealthCare.HealthCare.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import com.HealthCare.HealthCare.mapper.RendezVousMapper;
import com.HealthCare.HealthCare.model.RendezVous;
import org.springframework.stereotype.Service;
import com.HealthCare.HealthCare.repository.RendezVousRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RendezVousService {
    private final RendezVousRepository rendezVousRepository;
    private final RendezVousMapper rendezVousMapper;
    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;
    private final DossierMedicalRepository dossierMedicalRepository;

    public RendezVousDto createRendezVous(RendezVousDto rendezVousDto){
        RendezVous rendezVous = rendezVousMapper.toEntity(rendezVousDto);
        rendezVous.setPatient(patientRepository.findById(rendezVousDto.getPatientId()).orElseThrow(() -> new RuntimeException("Patient non trouvable")));
        rendezVous.setMedecin(medecinRepository.findById(rendezVousDto.getMedecinId()).orElseThrow(()-> new RuntimeException("Medecin non trouvable")));
        rendezVous.setDossierMedical(dossierMedicalRepository.findById(rendezVousDto.getDossierMedicalId()).orElseThrow(() -> new RuntimeException("Dossier medical non trouvable")));
        RendezVous saved = rendezVousRepository.save(rendezVous);
        RendezVousDto savedDto = rendezVousMapper.toDto(saved);
        savedDto.setPatientId(saved.getPatient().getId());
        savedDto.setMedecinId(saved.getMedecin().getId());
        savedDto.setDossierMedicalId(saved.getDossierMedical().getId());
        return savedDto;
    }

    public RendezVousDto getById(long id){
        RendezVous rendezVous = rendezVousRepository.findById(id).orElseThrow(() -> new RuntimeException("Rendez vous non trouvable"));
        RendezVousDto rendezVousDto = rendezVousMapper.toDto(rendezVous);
        rendezVousDto.setMedecinId(rendezVous.getMedecin().getId());
        rendezVousDto.setPatientId(rendezVous.getPatient().getId());
        rendezVousDto.setDossierMedicalId(rendezVous.getDossierMedical().getId());
        return rendezVousDto;
    }

    public List<RendezVousDto> getAll(){
        List<RendezVous> rendezVous = rendezVousRepository.findAll();
        List<RendezVousDto> rendezVousDtos = rendezVous.stream().map(rendezVousMapper::toDto).toList();
        for (int i = 0; i < rendezVous.size(); i++) {
            rendezVousDtos.get(i).setMedecinId(rendezVous.get(i).getMedecin().getId());
            rendezVousDtos.get(i).setPatientId(rendezVous.get(i).getPatient().getId());
            rendezVousDtos.get(i).setDossierMedicalId(rendezVous.get(i).getDossierMedical().getId());
        }
        return rendezVousDtos;
    }

    public RendezVousDto modify(RendezVousDto rendezVousDto){
        if (!rendezVousRepository.existsById(rendezVousDto.getId())){
            return null;
        }
        RendezVous rendezVous1 = rendezVousMapper.toEntity(rendezVousDto);

        rendezVous1.setPatient(patientRepository.findById(rendezVousDto.getPatientId()).orElseThrow(() -> new RuntimeException("Patient non trouvable")));
        rendezVous1.setMedecin(medecinRepository.findById(rendezVousDto.getMedecinId()).orElseThrow(()-> new RuntimeException("Medecin non trouvable")));
        rendezVous1.setDossierMedical(dossierMedicalRepository.findById(rendezVousDto.getDossierMedicalId()).orElseThrow(() -> new RuntimeException("Dossier medical non trouvable")));

        RendezVous rendezVous = rendezVousRepository.save(rendezVous1);

        RendezVousDto rendezVousDto1 = rendezVousMapper.toDto(rendezVous);

        rendezVousDto1.setMedecinId(rendezVous.getMedecin().getId());
        rendezVousDto1.setPatientId(rendezVous.getPatient().getId());
        rendezVousDto1.setDossierMedicalId(rendezVous.getDossierMedical().getId());

        return rendezVousDto1;
    }

    public RendezVousDto annulerRdv(long id){
        RendezVous rendezVous = rendezVousRepository.findById(id).orElseThrow(() -> new RuntimeException("Rendez vous non trouvable"));
        rendezVous.setStatus(StatusRendezVous.ANNULE);
        RendezVousDto rendezVousDto = rendezVousMapper.toDto(rendezVousRepository.save(rendezVous));
        rendezVousDto.setMedecinId(rendezVous.getMedecin().getId());
        rendezVousDto.setPatientId(rendezVous.getPatient().getId());
        rendezVousDto.setDossierMedicalId(rendezVous.getDossierMedical().getId());
        return rendezVousDto;
    }

    public List<RendezVousDto> getAllLikePation(String nom){
        List<RendezVous> rendezVous = rendezVousRepository.findAllLikePation(nom);
        if (rendezVous.isEmpty()){
            return List.of();
        }
        List<RendezVousDto> rendezVousDtos = rendezVous.stream().map(rendezVousMapper::toDto).toList();
        for (int i = 0; i < rendezVous.size(); i++) {
            rendezVousDtos.get(i).setMedecinId(rendezVous.get(i).getMedecin().getId());
            rendezVousDtos.get(i).setPatientId(rendezVous.get(i).getPatient().getId());
            rendezVousDtos.get(i).setDossierMedicalId(rendezVous.get(i).getDossierMedical().getId());
        }
        return rendezVousDtos;
    }

    public List<RendezVousDto> getAllLikeMedecin(String nom){
        List<RendezVous> rendezVous = rendezVousRepository.findAllLikeMedecin(nom);
        if (rendezVous.isEmpty()) {
            return List.of();
        }
        List<RendezVousDto> rendezVousDtos = rendezVous.stream().map(rendezVousMapper::toDto).toList();
        for (int i = 0; i < rendezVous.size(); i++) {
            rendezVousDtos.get(i).setMedecinId(rendezVous.get(i).getMedecin().getId());
            rendezVousDtos.get(i).setPatientId(rendezVous.get(i).getPatient().getId());
            rendezVousDtos.get(i).setDossierMedicalId(rendezVous.get(i).getDossierMedical().getId());
        }
        return rendezVousDtos;
    }

    public List<RendezVousDto> getByPatientId(long id){
        List<RendezVous> rendezVous = rendezVousRepository.findByPatientId(id);
        if (rendezVous.isEmpty()){
            return List.of();
        }
        List<RendezVousDto> rendezVousDtos = rendezVous.stream().map(rendezVousMapper::toDto).toList();
        for (int i = 0; i < rendezVous.size(); i++) {
            rendezVousDtos.get(i).setMedecinId(rendezVous.get(i).getMedecin().getId());
            rendezVousDtos.get(i).setPatientId(rendezVous.get(i).getPatient().getId());
            rendezVousDtos.get(i).setDossierMedicalId(rendezVous.get(i).getDossierMedical().getId());
        }
        return rendezVousDtos;
    }

    public List<RendezVousDto> getByMedecinId(long id){
        List<RendezVous> rendezVous = rendezVousRepository.findByMedecinId(id);
        if (rendezVous.isEmpty()){
            return List.of();
        }
        List<RendezVousDto> rendezVousDtos = rendezVous.stream().map(rendezVousMapper::toDto).toList();
        for (int i = 0; i < rendezVous.size(); i++) {
            rendezVousDtos.get(i).setMedecinId(rendezVous.get(i).getMedecin().getId());
            rendezVousDtos.get(i).setPatientId(rendezVous.get(i).getPatient().getId());
            rendezVousDtos.get(i).setDossierMedicalId(rendezVous.get(i).getDossierMedical().getId());
        }
        return rendezVousDtos;
    }

}

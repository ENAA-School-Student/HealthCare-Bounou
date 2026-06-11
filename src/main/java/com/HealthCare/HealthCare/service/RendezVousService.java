package com.HealthCare.HealthCare.service;

import com.HealthCare.HealthCare.dto.DossierMedicalDto;
import com.HealthCare.HealthCare.dto.RendezVousDto;
import com.HealthCare.HealthCare.enums.StatusRendezVous;
import com.HealthCare.HealthCare.mapper.DossierMedicalMapper;
import com.HealthCare.HealthCare.repository.DossierMedicalRepository;
import com.HealthCare.HealthCare.repository.MedecinRepository;
import com.HealthCare.HealthCare.repository.PatientRepository;
import com.HealthCare.HealthCare.utils.ExcelGeneratorUtil;
import lombok.RequiredArgsConstructor;
import com.HealthCare.HealthCare.mapper.RendezVousMapper;
import com.HealthCare.HealthCare.model.RendezVous;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.HealthCare.HealthCare.repository.RendezVousRepository;
import com.HealthCare.HealthCare.exception.ResourceNotFoundException;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RendezVousService {
    private final RendezVousRepository rendezVousRepository;
    private final RendezVousMapper rendezVousMapper;
    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;
    private final DossierMedicalRepository dossierMedicalRepository;
    private final DossierMedicalService dossierMedicalService;
    private final DossierMedicalMapper dossierMedicalMapper;

    public ByteArrayInputStream downloadPatientRendezVousExcel(long patientId) {
        List<RendezVousDto> rdvList = getByPatientId(patientId);
        return ExcelGeneratorUtil.generateRendezVousExcel(rdvList);
    }

    private Pageable buildPageable(int page, int size, String orderBy, String orderDir) {
        Sort sort = orderDir.equalsIgnoreCase("desc")
                ? Sort.by(orderBy).descending()
                : Sort.by(orderBy).ascending();
        return PageRequest.of(page, size, sort);
    }

    private RendezVousDto toDtoWithRelations(RendezVous rendezVous) {
        RendezVousDto rendezVousDto = rendezVousMapper.toDto(rendezVous);
        rendezVousDto.setMedecinId(rendezVous.getMedecin().getId());
        rendezVousDto.setPatientId(rendezVous.getPatient().getId());
        if (rendezVous.getDossierMedical() != null) {
            rendezVousDto.setDossierMedicalId(rendezVous.getDossierMedical().getId());
        }
        return rendezVousDto;
    }

    private StatusRendezVous parseStatus(String status) {
        String normalized = status.trim();
        return List.of(StatusRendezVous.values()).stream()
                .filter(value -> value.name().equalsIgnoreCase(normalized))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Statut de rendez-vous invalide : " + status));
    }

    @CacheEvict(value = "rendezvous", allEntries = true)
    public RendezVousDto createRendezVous(RendezVousDto rendezVousDto){
        RendezVous rendezVous = rendezVousMapper.toEntity(rendezVousDto);
        rendezVous.setPatient(patientRepository.findById(rendezVousDto.getPatientId()).orElseThrow(() -> new ResourceNotFoundException("Patient non trouvable")));
        rendezVous.setMedecin(medecinRepository.findById(rendezVousDto.getMedecinId()).orElseThrow(()-> new ResourceNotFoundException("Medecin non trouvable")));
        if (rendezVousDto.getDossierMedicalId() != null){
            rendezVous.setDossierMedical(dossierMedicalRepository.findById(rendezVousDto.getDossierMedicalId()).orElseThrow(() -> new ResourceNotFoundException("Dossier medical non trouvable")));
        }else {
            rendezVous.setDossierMedical(null);
        }
        RendezVous saved = rendezVousRepository.save(rendezVous);
        RendezVousDto savedDto = rendezVousMapper.toDto(saved);
        savedDto.setPatientId(saved.getPatient().getId());
        savedDto.setMedecinId(saved.getMedecin().getId());
        if (saved.getDossierMedical() != null){
            savedDto.setDossierMedicalId(saved.getDossierMedical().getId());
        }
        return savedDto;
    }

    @Cacheable(value = "rendezvous", key = "'id-' + #id")
    public RendezVousDto getById(long id){
        RendezVous rendezVous = rendezVousRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rendez vous non trouvable"));
        RendezVousDto rendezVousDto = rendezVousMapper.toDto(rendezVous);
        rendezVousDto.setMedecinId(rendezVous.getMedecin().getId());
        rendezVousDto.setPatientId(rendezVous.getPatient().getId());
        if (rendezVous.getDossierMedical() != null){
            rendezVousDto.setDossierMedicalId(rendezVous.getDossierMedical().getId());
        }else{
            rendezVousDto.setDossierMedicalId(1L);
        }
        return rendezVousDto;
    }

    @Cacheable(value = "rendezvous", key = "'list-' + #page + '-' + #size + '-' + #orderBy + '-' + #orderDir")
    public Page<RendezVousDto> getAll(int page , int size , String orderBy , String orderDir){
        Pageable pageable = buildPageable(page, size, orderBy, orderDir);
        return rendezVousRepository.findAll(pageable)
                .map(this::toDtoWithRelations);
    }

    @Cacheable(value = "rendezvous", key = "'date-' + #date + '-' + #page + '-' + #size + '-' + #orderBy + '-' + #orderDir")
    public Page<RendezVousDto> getByDate(String date , int page , int size , String orderBy , String orderDir){
        LocalDate parsedDate = LocalDate.parse(date);
        Pageable pageable = buildPageable(page, size, orderBy, orderDir);
        return rendezVousRepository.findByDateBetween(parsedDate.atStartOfDay(), parsedDate.plusDays(1).atStartOfDay(), pageable)
                .map(this::toDtoWithRelations);
    }

    @Cacheable(value = "rendezvous", key = "'status-' + #status + '-' + #page + '-' + #size + '-' + #orderBy + '-' + #orderDir")
    public Page<RendezVousDto> getByStatus(String status , int page , int size , String orderBy , String orderDir){
        Pageable pageable = buildPageable(page, size, orderBy, orderDir);
        return rendezVousRepository.findByStatus(parseStatus(status), pageable)
                .map(this::toDtoWithRelations);
    }

    @CacheEvict(value = "rendezvous", allEntries = true)
    public RendezVousDto modify(RendezVousDto rendezVousDto){
        if (!rendezVousRepository.existsById(rendezVousDto.getId())){
            return null;
        }
        RendezVous rendezVous1 = rendezVousMapper.toEntity(rendezVousDto);

        rendezVous1.setPatient(patientRepository.findById(rendezVousDto.getPatientId()).orElseThrow(() -> new ResourceNotFoundException("Patient non trouvable")));
        rendezVous1.setMedecin(medecinRepository.findById(rendezVousDto.getMedecinId()).orElseThrow(()-> new ResourceNotFoundException("Medecin non trouvable")));
        rendezVous1.setDossierMedical(dossierMedicalRepository.findById(rendezVousDto.getDossierMedicalId()).orElseThrow(() -> new ResourceNotFoundException("Dossier medical non trouvable")));

        RendezVous rendezVous = rendezVousRepository.save(rendezVous1);

        RendezVousDto rendezVousDto1 = rendezVousMapper.toDto(rendezVous);

        rendezVousDto1.setMedecinId(rendezVous.getMedecin().getId());
        rendezVousDto1.setPatientId(rendezVous.getPatient().getId());
        if (rendezVous.getDossierMedical() != null){
            rendezVousDto1.setDossierMedicalId(rendezVous.getDossierMedical().getId());
        }
        return rendezVousDto1;
    }

    @CacheEvict(value = "rendezvous", allEntries = true)
    public RendezVousDto annulerRdv(long id){
        RendezVous rendezVous = rendezVousRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rendez vous non trouvable"));
        rendezVous.setStatus(StatusRendezVous.ANNULE);
        RendezVousDto rendezVousDto = rendezVousMapper.toDto(rendezVousRepository.save(rendezVous));
        rendezVousDto.setMedecinId(rendezVous.getMedecin().getId());
        rendezVousDto.setPatientId(rendezVous.getPatient().getId());
        if (rendezVous.getDossierMedical() != null){
            rendezVousDto.setDossierMedicalId(rendezVous.getDossierMedical().getId());
        }
        return rendezVousDto;
    }

    @CacheEvict(value = "rendezvous", allEntries = true)
    public RendezVousDto confirmeRdv(long id){
        RendezVous rendezVous = rendezVousRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rendez vous non trouvable"));
        rendezVous.setStatus(StatusRendezVous.CONFIRME);
        RendezVousDto rendezVousDto;
        if (rendezVous.getDossierMedical() != null){
            rendezVousDto = rendezVousMapper.toDto(rendezVousRepository.save(rendezVous));
            rendezVousDto.setMedecinId(rendezVous.getMedecin().getId());
            rendezVousDto.setPatientId(rendezVous.getPatient().getId());
            rendezVousDto.setDossierMedicalId(rendezVous.getDossierMedical().getId());
        }else {
            DossierMedicalDto dossierMedicalDto1 = new DossierMedicalDto();
            dossierMedicalDto1.setPatientId(rendezVous.getPatient().getId());
            dossierMedicalDto1.setMedecinId(rendezVous.getMedecin().getId());
            dossierMedicalDto1.setDateCreation(LocalDate.now());
            DossierMedicalDto saved = dossierMedicalService.createDossierMedical(dossierMedicalDto1);
            rendezVous.setDossierMedical(dossierMedicalMapper.toEntity(saved));
            rendezVousDto = rendezVousMapper.toDto(rendezVousRepository.save(rendezVous));
            rendezVousDto.setDossierMedicalId(saved.getId());
            rendezVousDto.setMedecinId(rendezVous.getMedecin().getId());
            rendezVousDto.setPatientId(rendezVous.getPatient().getId());
        }
        return rendezVousDto;
    }

    @Cacheable(value = "rendezvous", key = "'like-patient-' + #nom")
    public List<RendezVousDto> getAllLikePation(String nom){
        List<RendezVous> rendezVous = rendezVousRepository.findAllLikePation(nom);
        if (rendezVous.isEmpty()){
            return List.of();
        }
        List<RendezVousDto> rendezVousDtos = rendezVous.stream().map(rendezVousMapper::toDto).toList();
        for (int i = 0; i < rendezVous.size(); i++) {
            rendezVousDtos.get(i).setMedecinId(rendezVous.get(i).getMedecin().getId());
            rendezVousDtos.get(i).setPatientId(rendezVous.get(i).getPatient().getId());
            if (rendezVous.get(i).getDossierMedical() != null){
                rendezVousDtos.get(i).setDossierMedicalId(rendezVous.get(i).getDossierMedical().getId());
            }
        }
        return rendezVousDtos;
    }

    @Cacheable(value = "rendezvous", key = "'like-medecin-' + #nom")
    public List<RendezVousDto> getAllLikeMedecin(String nom){
        List<RendezVous> rendezVous = rendezVousRepository.findAllLikeMedecin(nom);
        if (rendezVous.isEmpty()) {
            return List.of();
        }
        List<RendezVousDto> rendezVousDtos = rendezVous.stream().map(rendezVousMapper::toDto).toList();
        for (int i = 0; i < rendezVous.size(); i++) {
            rendezVousDtos.get(i).setMedecinId(rendezVous.get(i).getMedecin().getId());
            rendezVousDtos.get(i).setPatientId(rendezVous.get(i).getPatient().getId());
            if (rendezVous.get(i).getDossierMedical() != null){
                rendezVousDtos.get(i).setDossierMedicalId(rendezVous.get(i).getDossierMedical().getId());
            }
        }
        return rendezVousDtos;
    }

    @Cacheable(value = "rendezvous", key = "'patient-id-' + #id")
    public List<RendezVousDto> getByPatientId(long id){
        List<RendezVous> rendezVous = rendezVousRepository.findByPatientId(id);
        if (rendezVous.isEmpty()){
            return List.of();
        }
        List<RendezVousDto> rendezVousDtos = rendezVous.stream().map(rendezVousMapper::toDto).toList();
        for (int i = 0; i < rendezVous.size(); i++) {
            rendezVousDtos.get(i).setMedecinId(rendezVous.get(i).getMedecin().getId());
            rendezVousDtos.get(i).setPatientId(rendezVous.get(i).getPatient().getId());
            if (rendezVous.get(i).getDossierMedical() != null){
                rendezVousDtos.get(i).setDossierMedicalId(rendezVous.get(i).getDossierMedical().getId());
            }
        }
        return rendezVousDtos;
    }

    @Cacheable(value = "rendezvous", key = "'medecin-id-' + #id")
    public List<RendezVousDto> getByMedecinId(long id){
        List<RendezVous> rendezVous = rendezVousRepository.findByMedecinId(id);
        if (rendezVous.isEmpty()){
            return List.of();
        }
        List<RendezVousDto> rendezVousDtos = rendezVous.stream().map(rendezVousMapper::toDto).toList();
        for (int i = 0; i < rendezVous.size(); i++) {
            rendezVousDtos.get(i).setMedecinId(rendezVous.get(i).getMedecin().getId());
            rendezVousDtos.get(i).setPatientId(rendezVous.get(i).getPatient().getId());
            if (rendezVous.get(i).getDossierMedical() != null){
                rendezVousDtos.get(i).setDossierMedicalId(rendezVous.get(i).getDossierMedical().getId());
            }
        }
        return rendezVousDtos;
    }
}
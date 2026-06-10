package com.HealthCare.HealthCare.service;

import com.HealthCare.HealthCare.dto.MedecinDto;
import com.HealthCare.HealthCare.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import com.HealthCare.HealthCare.mapper.MedecinMapper;
import com.HealthCare.HealthCare.model.Medecin;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.HealthCare.HealthCare.repository.MedecinRepository;

@Service
@RequiredArgsConstructor
public class MedecinService {
    private final MedecinRepository medecinRepository;
    private final MedecinMapper medecinMapper;

    @CacheEvict(value = "medecins" , allEntries = true)
    public String deleteById(long id){
        if (!medecinRepository.existsById(id)){
            throw new ResourceNotFoundException("Medecin non trouvable");
        }
        medecinRepository.deleteById(id);
        return "Medecin deleted";
    }

    @Cacheable(value = "medecins" , key = "'id-' + #id")
    public MedecinDto getById(long id){
        Medecin medecin = medecinRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Medecin non trouvable"));
        return medecinMapper.toDto(medecin);
    }

    @Cacheable(value = "medecins", key = "'list-' + #page + '-' + #size + '-' + #orderBy + '-' + #orderDir")
    public Page<MedecinDto> getAll(int page , int size , String orderBy , String orderDir){
        Sort sort = orderDir.equalsIgnoreCase("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy).ascending();
        Pageable pageable = PageRequest.of(page,size,sort);
        return medecinRepository.findAll(pageable).map(medecinMapper::toDto);
    }

    @Cacheable(value = "medecins" , key = "'specialite-' + #specialite + '-' + #page + '-' + #size")
    public Page<MedecinDto> getMedecinBySpecialite(String specialite , int page  , int size ){
        Pageable pageable = PageRequest.of(page,size);
        return medecinRepository.findBySpecialite(specialite,pageable).map(medecinMapper::toDto);
    }

    @CacheEvict(value = "medecins" , allEntries = true)
    public MedecinDto addMedecin(MedecinDto medecinDto) {
        Medecin medecin = medecinMapper.toEntity(medecinDto);
        Medecin savedMedecin = medecinRepository.save(medecin);
        return medecinMapper.toDto(savedMedecin);
    }

    @CacheEvict(value = "medecins" , allEntries = true)
    public MedecinDto update(MedecinDto medecinDto){
        Medecin medecin = medecinRepository.save(medecinMapper.toEntity(medecinDto));
        return medecinMapper.toDto(medecin);
    }
}
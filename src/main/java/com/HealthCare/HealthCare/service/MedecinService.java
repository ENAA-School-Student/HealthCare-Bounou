package com.HealthCare.HealthCare.service;

import com.HealthCare.HealthCare.dto.MedecinDto;
import com.HealthCare.HealthCare.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import com.HealthCare.HealthCare.mapper.MedecinMapper;
import com.HealthCare.HealthCare.model.Medecin;
import org.springframework.stereotype.Service;
import com.HealthCare.HealthCare.repository.MedecinRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedecinService {
    private final MedecinRepository medecinRepository;
    private final MedecinMapper medecinMapper;

    public String deleteById(long id){
        if (!medecinRepository.existsById(id)){
            throw new ResourceNotFoundException("Medecin non trouvable");
        }
        medecinRepository.deleteById(id);
        return "Medecin deleted";
    }

    public MedecinDto getById(long id){
        Medecin medecin = medecinRepository.findById(id).orElse(null);
        return medecinMapper.toDto(medecin);
    }

    public List<MedecinDto> getAll(){
        List<Medecin> medecins = medecinRepository.findAll();
        return medecins.stream().map(medecinMapper::toDto).toList();
    }

    public MedecinDto addMedecin(MedecinDto medecinDto)
    {
        Medecin medecin = medecinMapper.toEntity(medecinDto);
        Medecin savedMedecin = medecinRepository.save(medecin);
        return medecinMapper.toDto(savedMedecin);
    }

    public MedecinDto update(MedecinDto medecinDto){
        Medecin medecin = medecinRepository.save(medecinMapper.toEntity(medecinDto));
        return medecinMapper.toDto(medecin);
    }


}

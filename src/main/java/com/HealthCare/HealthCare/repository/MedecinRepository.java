package com.HealthCare.HealthCare.repository;

import com.HealthCare.HealthCare.model.Medecin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    @Query("select m from Medecin m where m.specialite like %:specialite%")
    Page<Medecin> findBySpecialite(String specialite, Pageable pageable);
}

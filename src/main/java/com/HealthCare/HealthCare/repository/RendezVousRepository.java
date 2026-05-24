package com.HealthCare.HealthCare.repository;

import com.HealthCare.HealthCare.enums.StatusRendezVous;
import com.HealthCare.HealthCare.model.RendezVous;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    @Query("select r from RendezVous r where r.patient.nom like %:nom%")
    public List<RendezVous> findAllLikePation(@Param("nom") String nom);

    @Query("select r from RendezVous r where r.medecin.nom LIKE %:nom%")
    public List<RendezVous> findAllLikeMedecin(@Param("nom") String nom);

    @Query("select r from RendezVous r where r.patient.id = :id")
    public List<RendezVous> findByPatientId(@Param("id") long id);

    @Query("select r from RendezVous r where r.medecin.id = :id")
    public List<RendezVous> findByMedecinId(@Param("id") long id);

    @Query("select r from RendezVous r where r.dateRendezVous >= :startOfDay and r.dateRendezVous < :endOfDay")
    Page<RendezVous> findByDateBetween(
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay,
            Pageable pageable
    );

    @Query("select r from RendezVous r where r.status = :status")
    Page<RendezVous> findByStatus(@Param("status") StatusRendezVous status, Pageable pageable);
}

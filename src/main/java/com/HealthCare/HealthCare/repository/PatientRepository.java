package com.HealthCare.HealthCare.repository;

import com.HealthCare.HealthCare.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("select p from Patient p where p.nom like %:nom%")
    Page<Patient> findByNomLike(String nom , Pageable pageable);
}

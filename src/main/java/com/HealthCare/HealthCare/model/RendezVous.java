package com.HealthCare.HealthCare.model;

import com.HealthCare.HealthCare.enums.StatusRendezVous;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
@Table(name = "rendez_vous")
public class RendezVous {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;

    private LocalDateTime dateRendezVous;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusRendezVous status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "dossier_medical_id")
    private DossierMedical dossierMedical;

    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private Medecin medecin;
}

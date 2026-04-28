package com.HealthCare.HealthCare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "dossier_medical")
public class DossierMedical {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;

    private String diagnostic;
    private String observation;
    private LocalDate dateCreation;

    private long patientId;
    private long medecinId;

    @OneToMany(mappedBy = "dossierMedical", cascade = CascadeType.ALL)
    private List<RendezVous> rendezVousList;
}

package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "agentatelier")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentAtelier implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idagentatelier")
    private Integer idagentatelier;
    
    @Column(name = "etat", length = 20)
    private String etat;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpersonne")
    private Personne personne;
    
    @OneToMany(mappedBy = "agentAtelier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rv> rendezVous;
    
    @OneToMany(mappedBy = "agentAtelier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HoraireAgent> horaires;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdagentatelier() { return idagentatelier; }
    public void setIdagentatelier(Integer idagentatelier) { this.idagentatelier = idagentatelier; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public Personne getPersonne() { return personne; }
    public void setPersonne(Personne personne) { this.personne = personne; }
    public List<Rv> getRendezvous() { return rendezVous; }
    public void setRendezvous(List<Rv> rendezVous) { this.rendezVous = rendezVous; }
    public List<HoraireAgent> getHoraires() { return horaires; }
    public void setHoraires(List<HoraireAgent> horaires) { this.horaires = horaires; }
}

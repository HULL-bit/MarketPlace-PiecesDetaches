package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "horaire_agent")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoraireAgent implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idhoraire")
    private Integer idhoraire;
    
    @Column(name = "jour", length = 20)
    private String jour;
    
    @Column(name = "heuredebut", length = 10)
    private String heuredebut;
    
    @Column(name = "heurefin", length = 10)
    private String heurefin;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idagentatelier")
    private AgentAtelier agentAtelier;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdhoraire() { return idhoraire; }
    public void setIdhoraire(Integer idhoraire) { this.idhoraire = idhoraire; }
    public String getJour() { return jour; }
    public void setJour(String jour) { this.jour = jour; }
    public String getHeuredebut() { return heuredebut; }
    public void setHeuredebut(String heuredebut) { this.heuredebut = heuredebut; }
    public String getHeurefin() { return heurefin; }
    public void setHeurefin(String heurefin) { this.heurefin = heurefin; }
    public AgentAtelier getAgentatelier() { return agentAtelier; }
    public void setAgentatelier(AgentAtelier agentAtelier) { this.agentAtelier = agentAtelier; }
}

package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "rv")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rv implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrv")
    private Integer idrv;
    
    @Column(name = "date", length = 20)
    private String date;
    
    @Column(name = "heure", length = 10)
    private String heure;
    
    @Column(name = "motif", length = 500)
    private String motif;
    
    @Column(name = "etat", length = 20)
    private String etat;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idclient")
    private Client client;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idagentatelier")
    private AgentAtelier agentAtelier;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdrv() { return idrv; }
    public void setIdrv(Integer idrv) { this.idrv = idrv; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getHeure() { return heure; }
    public void setHeure(String heure) { this.heure = heure; }
    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public AgentAtelier getAgentatelier() { return agentAtelier; }
    public void setAgentatelier(AgentAtelier agentAtelier) { this.agentAtelier = agentAtelier; }
}

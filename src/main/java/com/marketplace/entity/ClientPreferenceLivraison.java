package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "client_preference_livraison")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientPreferenceLivraison implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprefliv")
    private Integer idprefliv;
    
    @Column(name = "jours", length = 100)
    private String jours;
    
    @Column(name = "heuredebut", length = 10)
    private String heuredebut;
    
    @Column(name = "heurefin", length = 10)
    private String heurefin;
    
    @Column(name = "adresse", columnDefinition = "TEXT")
    private String adresse;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idclient")
    private Client client;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdprefliv() { return idprefliv; }
    public void setIdprefliv(Integer idprefliv) { this.idprefliv = idprefliv; }
    public String getJours() { return jours; }
    public void setJours(String jours) { this.jours = jours; }
    public String getHeuredebut() { return heuredebut; }
    public void setHeuredebut(String heuredebut) { this.heuredebut = heuredebut; }
    public String getHeurefin() { return heurefin; }
    public void setHeurefin(String heurefin) { this.heurefin = heurefin; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
}

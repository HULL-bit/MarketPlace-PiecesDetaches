package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "client_boutique")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ClientBoutiqueId.class)
public class ClientBoutique implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idclient")
    private Client client;
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idboutique")
    private Boutique boutique;
    
    @Column(name = "dateabonnement", length = 20)
    private String dateabonnement;
    
    @Column(name = "etat", length = 20)
    private String etat;

    // Getters and Setters (generated for Eclipse compatibility)
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public Boutique getBoutique() { return boutique; }
    public void setBoutique(Boutique boutique) { this.boutique = boutique; }
    public String getDateabonnement() { return dateabonnement; }
    public void setDateabonnement(String dateabonnement) { this.dateabonnement = dateabonnement; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
}

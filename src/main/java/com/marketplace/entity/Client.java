package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idclient")
    private Integer idclient;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpersonne", unique = true, nullable = false)
    private Personne personne;
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Panier> paniers;
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Commande> commandes;
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ClientBoutique> clientBoutiques;
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AvisPiece> avisPieces;
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rv> rendezVous;
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MonHoraireLivraison> horairesLivraison;
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ClientPreferenceLivraison> preferencesLivraison;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdclient() { return idclient; }
    public void setIdclient(Integer idclient) { this.idclient = idclient; }
    public Personne getPersonne() { return personne; }
    public void setPersonne(Personne personne) { this.personne = personne; }
    public List<Panier> getPaniers() { return paniers; }
    public void setPaniers(List<Panier> paniers) { this.paniers = paniers; }
    public List<Commande> getCommandes() { return commandes; }
    public void setCommandes(List<Commande> commandes) { this.commandes = commandes; }
    public List<ClientBoutique> getClientboutiques() { return clientBoutiques; }
    public void setClientboutiques(List<ClientBoutique> clientBoutiques) { this.clientBoutiques = clientBoutiques; }
    public List<AvisPiece> getAvispieces() { return avisPieces; }
    public void setAvispieces(List<AvisPiece> avisPieces) { this.avisPieces = avisPieces; }
    public List<Rv> getRendezvous() { return rendezVous; }
    public void setRendezvous(List<Rv> rendezVous) { this.rendezVous = rendezVous; }
    public List<MonHoraireLivraison> getHoraireslivraison() { return horairesLivraison; }
    public void setHoraireslivraison(List<MonHoraireLivraison> horairesLivraison) { this.horairesLivraison = horairesLivraison; }
    public List<ClientPreferenceLivraison> getPreferenceslivraison() { return preferencesLivraison; }
    public void setPreferenceslivraison(List<ClientPreferenceLivraison> preferencesLivraison) { this.preferencesLivraison = preferencesLivraison; }
}

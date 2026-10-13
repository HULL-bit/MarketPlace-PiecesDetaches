package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "boutique")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Boutique implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idboutique")
    private Integer idboutique;
    
    @Column(name = "libelle", nullable = false, length = 300)
    private String libelle;
    
    @Column(name = "date", length = 20)
    private String date;
    
    @Column(name = "datemaj", length = 20)
    private String datemaj;
    
    @Column(name = "nombre", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer nombre = 0;
    
    @Column(name = "creepar")
    private Integer creepar;
    
    @Column(name = "tel")
    private Long tel;
    
    @Column(name = "mobile1")
    private Long mobile1;
    
    @Column(name = "mobile2")
    private Long mobile2;
    
    @Column(name = "mobile3")
    private Long mobile3;
    
    @Column(name = "qrcode", length = 500)
    private String qrcode;
    
    @Column(name = "modepaielivraison", length = 200)
    private String modepaielivraison;
    
    @Column(name = "email", length = 150)
    private String email;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "logo", length = 500)
    private String logo;
    
    @Column(name = "banniere", length = 500)
    private String banniere;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "etat", length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'ACTIF'")
    @Builder.Default
    private String etat = "ACTIF";
    
    @OneToMany(mappedBy = "boutique", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Emplacement> emplacements;
    
    @OneToMany(mappedBy = "boutique", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Piece> pieces;
    
    @OneToMany(mappedBy = "boutique", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Stock> stocks;
    
    @OneToMany(mappedBy = "boutique", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ClientBoutique> clientBoutiques;
    
    @OneToMany(mappedBy = "boutique", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Promotion> promotions;
    
    @OneToMany(mappedBy = "boutique", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ZoneLivraison> zonesLivraison;
    
    @OneToMany(mappedBy = "boutique", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LivreurBoutique> livreurBoutiques;
    
    @OneToMany(mappedBy = "boutique", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Manager> managers;
    
    @OneToMany(mappedBy = "boutique", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SeuilLivraisonPanier> seuilsLivraison;

    // Getters and Setters (manual for Eclipse compatibility)
    public Integer getIdboutique() { return idboutique; }
    public void setIdboutique(Integer idboutique) { this.idboutique = idboutique; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getDatemaj() { return datemaj; }
    public void setDatemaj(String datemaj) { this.datemaj = datemaj; }
    public Integer getNombre() { return nombre; }
    public void setNombre(Integer nombre) { this.nombre = nombre; }
    public Integer getCreepar() { return creepar; }
    public void setCreepar(Integer creepar) { this.creepar = creepar; }
    public Long getTel() { return tel; }
    public void setTel(Long tel) { this.tel = tel; }
    public Long getMobile1() { return mobile1; }
    public void setMobile1(Long mobile1) { this.mobile1 = mobile1; }
    public Long getMobile2() { return mobile2; }
    public void setMobile2(Long mobile2) { this.mobile2 = mobile2; }
    public Long getMobile3() { return mobile3; }
    public void setMobile3(Long mobile3) { this.mobile3 = mobile3; }
    public String getQrcode() { return qrcode; }
    public void setQrcode(String qrcode) { this.qrcode = qrcode; }
    public String getModepaielivraison() { return modepaielivraison; }
    public void setModepaielivraison(String modepaielivraison) { this.modepaielivraison = modepaielivraison; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }
    public String getBanniere() { return banniere; }
    public void setBanniere(String banniere) { this.banniere = banniere; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public List<Emplacement> getEmplacements() { return emplacements; }
    public void setEmplacements(List<Emplacement> emplacements) { this.emplacements = emplacements; }
    public List<Piece> getPieces() { return pieces; }
    public void setPieces(List<Piece> pieces) { this.pieces = pieces; }
    public List<Stock> getStocks() { return stocks; }
    public void setStocks(List<Stock> stocks) { this.stocks = stocks; }
    public List<ClientBoutique> getClientBoutiques() { return clientBoutiques; }
    public void setClientBoutiques(List<ClientBoutique> clientBoutiques) { this.clientBoutiques = clientBoutiques; }
    public List<Promotion> getPromotions() { return promotions; }
    public void setPromotions(List<Promotion> promotions) { this.promotions = promotions; }
    public List<ZoneLivraison> getZonesLivraison() { return zonesLivraison; }
    public void setZonesLivraison(List<ZoneLivraison> zonesLivraison) { this.zonesLivraison = zonesLivraison; }
    public List<LivreurBoutique> getLivreurBoutiques() { return livreurBoutiques; }
    public void setLivreurBoutiques(List<LivreurBoutique> livreurBoutiques) { this.livreurBoutiques = livreurBoutiques; }
    public List<Manager> getManagers() { return managers; }
    public void setManagers(List<Manager> managers) { this.managers = managers; }
    public List<SeuilLivraisonPanier> getSeuilsLivraison() { return seuilsLivraison; }
    public void setSeuilsLivraison(List<SeuilLivraisonPanier> seuilsLivraison) { this.seuilsLivraison = seuilsLivraison; }
}

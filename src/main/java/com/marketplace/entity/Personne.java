package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "personne")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Personne implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpersonne")
    private Integer idpersonne;
    
    @Column(name = "prenom", length = 100)
    private String prenom;
    
    @Column(name = "nom", nullable = false, length = 100)
    private String nom;
    
    @Column(name = "sexe", length = 10)
    private String sexe;
    
    @Column(name = "datenaiss")
    private java.time.LocalDate datenaiss;
    
    @Column(name = "lieunaiss", length = 200)
    private String lieunaiss;
    
    @Column(name = "codepostal")
    private Integer codepostal;
    
    @Column(name = "adresse", columnDefinition = "TEXT")
    private String adresse;
    
    @Column(name = "email", unique = true, nullable = false, length = 150)
    private String email;
    
    @Column(name = "email2", length = 150)
    private String email2;
    
    @Column(name = "email3", length = 150)
    private String email3;
    
    @Column(name = "tel")
    private Long tel;
    
    @Column(name = "tel2")
    private Long tel2;
    
    @Column(name = "tel3")
    private Long tel3;
    
    @Column(name = "codesecret")
    private Integer codesecret;
    
    @Column(name = "role", length = 50)
    private String role;
    
    @Column(name = "etat", length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'ACTIF'")
    @Builder.Default
    private String etat = "ACTIF";
    
    @Column(name = "session", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer session = 0;
    
    @Column(name = "matrimoniale", length = 50)
    private String matrimoniale;
    
    @Column(name = "login", unique = true, nullable = false, length = 100)
    private String login;
    
    @Column(name = "motpasse", nullable = false)
    private String motpasse;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @Column(name = "date_maj")
    private LocalDateTime dateMaj;
    
    @OneToOne(mappedBy = "personne", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Client client;
    
    @OneToOne(mappedBy = "personne", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Livreur livreur;
    
    @OneToOne(mappedBy = "personne", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Manager manager;
    
    @OneToOne(mappedBy = "personne", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AgentAtelier agentAtelier;
    
    @OneToMany(mappedBy = "personne", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LogActionSecurity> logs;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        dateMaj = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        dateMaj = LocalDateTime.now();
    }

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdpersonne() { return idpersonne; }
    public void setIdpersonne(Integer idpersonne) { this.idpersonne = idpersonne; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }
    public java.time.LocalDate getDatenaiss() { return datenaiss; }
    public void setDatenaiss(java.time.LocalDate datenaiss) { this.datenaiss = datenaiss; }
    public String getLieunaiss() { return lieunaiss; }
    public void setLieunaiss(String lieunaiss) { this.lieunaiss = lieunaiss; }
    public Integer getCodepostal() { return codepostal; }
    public void setCodepostal(Integer codepostal) { this.codepostal = codepostal; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getEmail2() { return email2; }
    public void setEmail2(String email2) { this.email2 = email2; }
    public String getEmail3() { return email3; }
    public void setEmail3(String email3) { this.email3 = email3; }
    public Long getTel() { return tel; }
    public void setTel(Long tel) { this.tel = tel; }
    public Long getTel2() { return tel2; }
    public void setTel2(Long tel2) { this.tel2 = tel2; }
    public Long getTel3() { return tel3; }
    public void setTel3(Long tel3) { this.tel3 = tel3; }
    public Integer getCodesecret() { return codesecret; }
    public void setCodesecret(Integer codesecret) { this.codesecret = codesecret; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public Integer getSession() { return session; }
    public void setSession(Integer session) { this.session = session; }
    public String getMatrimoniale() { return matrimoniale; }
    public void setMatrimoniale(String matrimoniale) { this.matrimoniale = matrimoniale; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getMotpasse() { return motpasse; }
    public void setMotpasse(String motpasse) { this.motpasse = motpasse; }
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    public LocalDateTime getDatemaj() { return dateMaj; }
    public void setDatemaj(LocalDateTime dateMaj) { this.dateMaj = dateMaj; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public Livreur getLivreur() { return livreur; }
    public void setLivreur(Livreur livreur) { this.livreur = livreur; }
    public Manager getManager() { return manager; }
    public void setManager(Manager manager) { this.manager = manager; }
    public AgentAtelier getAgentatelier() { return agentAtelier; }
    public void setAgentatelier(AgentAtelier agentAtelier) { this.agentAtelier = agentAtelier; }
    public List<LogActionSecurity> getLogs() { return logs; }
    public void setLogs(List<LogActionSecurity> logs) { this.logs = logs; }
}

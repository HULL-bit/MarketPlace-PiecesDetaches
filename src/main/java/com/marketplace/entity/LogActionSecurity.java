package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "log_action_security")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogActionSecurity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idlogaction")
    private Integer idlogaction;
    
    @Column(name = "action", length = 500)
    private String action;
    
    @Column(name = "date", length = 20)
    private String date;
    
    @Column(name = "etat", length = 20)
    private String etat;
    
    @Column(name = "tablebd", length = 100)
    private String tablebd;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpersonne")
    private Personne personne;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdlogaction() { return idlogaction; }
    public void setIdlogaction(Integer idlogaction) { this.idlogaction = idlogaction; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public String getTablebd() { return tablebd; }
    public void setTablebd(String tablebd) { this.tablebd = tablebd; }
    public Personne getPersonne() { return personne; }
    public void setPersonne(Personne personne) { this.personne = personne; }
}

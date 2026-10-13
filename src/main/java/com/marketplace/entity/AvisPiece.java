package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "avis_piece")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvisPiece implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idavispiece")
    private Integer idavispiece;

    @Column(name = "libelle", length = 200)
    private String libelle;

    @Column(name = "code")
    private Integer code;

    @Column(name = "ref", length = 100)
    private String ref;

    @Column(name = "date", length = 20)
    private String date;

    @Column(name = "datemaj", length = 20)
    private String datemaj;

    @Column(name = "etat", length = 20)
    private String etat;

    @Column(name = "creepar")
    private Integer creepar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpiece")
    private Piece piece;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idclient")
    private Client client;

    @OneToMany(mappedBy = "avisPiece", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ValeurAvis> valeurs;
}

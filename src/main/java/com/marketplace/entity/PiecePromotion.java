package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "piece_promotion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(PiecePromotionId.class)
public class PiecePromotion implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpiece")
    private Piece piece;
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpromo")
    private Promotion promotion;

    // Getters and Setters (generated for Eclipse compatibility)
    public Piece getPiece() { return piece; }
    public void setPiece(Piece piece) { this.piece = piece; }
    public Promotion getPromotion() { return promotion; }
    public void setPromotion(Promotion promotion) { this.promotion = promotion; }
}

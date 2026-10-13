package com.marketplace.entity;

import lombok.*;
import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PiecePromotionId implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer piece;
    private Integer promotion;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getPiece() { return piece; }
    public void setPiece(Integer piece) { this.piece = piece; }
    public Integer getPromotion() { return promotion; }
    public void setPromotion(Integer promotion) { this.promotion = promotion; }
}

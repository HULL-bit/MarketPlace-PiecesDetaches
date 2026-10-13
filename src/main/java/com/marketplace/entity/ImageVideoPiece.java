package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "image_video_piece")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageVideoPiece implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idimage")
    private Integer idimage;
    
    @Column(name = "type", length = 20)
    private String type;
    
    @Column(name = "url", length = 500)
    private String url;
    
    @Column(name = "ordre", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer ordre = 0;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpiece")
    private Piece piece;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdimage() { return idimage; }
    public void setIdimage(Integer idimage) { this.idimage = idimage; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public Integer getOrdre() { return ordre; }
    public void setOrdre(Integer ordre) { this.ordre = ordre; }
    public Piece getPiece() { return piece; }
    public void setPiece(Piece piece) { this.piece = piece; }
}

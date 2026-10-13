package com.marketplace.entity;

import lombok.*;
import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ClientBoutiqueId implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer client;
    private Integer boutique;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getClient() { return client; }
    public void setClient(Integer client) { this.client = client; }
    public Integer getBoutique() { return boutique; }
    public void setBoutique(Integer boutique) { this.boutique = boutique; }
}

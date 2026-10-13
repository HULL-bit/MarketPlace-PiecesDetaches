package com.marketplace.web;

import com.marketplace.entity.CategoriePiece;
import com.marketplace.entity.Piece;
import com.marketplace.repository.PrixPieceRepository;
import com.marketplace.service.PieceService;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Filtrage et projection des pièces pour la page catalogue publique.
 */
public final class CataloguePageSupport {

    private CataloguePageSupport() {
    }

    public static List<Piece> filterPieces(List<Piece> source, String q, String categorieParam) {
        return filterPieces(source, q, categorieParam, null);
    }

    public static List<Piece> filterPieces(List<Piece> source, String q, String categorieParam, String boutiqueParam) {
        List<Piece> result = source;
        if (q != null && !q.isBlank()) {
            String needle = q.trim().toLowerCase(Locale.ROOT);
            result = result.stream()
                    .filter(p -> matchesQuery(p, needle))
                    .collect(Collectors.toList());
        }
        if (categorieParam != null && !categorieParam.isBlank()) {
            String param = categorieParam.trim();
            if (param.matches("\\d+")) {
                int id = Integer.parseInt(param);
                result = result.stream()
                        .filter(p -> p.getCategoriepiece() != null
                                && id == p.getCategoriepiece().getIdcatpiece())
                        .collect(Collectors.toList());
            } else {
                String cat = param.toLowerCase(Locale.ROOT);
                result = result.stream()
                        .filter(p -> matchesCategory(p, cat))
                        .collect(Collectors.toList());
            }
        }
        if (boutiqueParam != null && !boutiqueParam.isBlank() && boutiqueParam.trim().matches("\\d+")) {
            int idboutique = Integer.parseInt(boutiqueParam.trim());
            result = result.stream()
                    .filter(p -> p.getBoutique() != null && idboutique == p.getBoutique().getIdboutique())
                    .collect(Collectors.toList());
        }
        return result;
    }

    public static List<Piece> availableWithRelations(PieceService pieceService) {
        return pieceService.findAllWithBoutiqueAndCategorie().stream()
                .filter(p -> p.getEtat() != null && "DISPONIBLE".equalsIgnoreCase(p.getEtat().trim()))
                .collect(Collectors.toList());
    }

    public static List<Map<String, Object>> toCatalogueItems(List<Piece> pieces, PrixPieceRepository prixRepo) {
        return pieces.stream().map(p -> {
            Map<String, Object> row = new HashMap<>();
            row.put("idpiece", p.getIdpiece());
            row.put("libelle", p.getLibelle());
            row.put("description", p.getDescription());
            row.put("ref", p.getRef());
            row.put("etat", p.getEtat());
            row.put("prix", GuestCartSupport.resolvePrice(p.getIdpiece(), prixRepo));
            if (p.getCategoriepiece() != null) {
                row.put("categorie", p.getCategoriepiece().getLibelle());
            } else {
                row.put("categorie", "");
            }
            row.put("boutique", p.getBoutique() != null ? p.getBoutique().getLibelle() : "");
            return row;
        }).collect(Collectors.toList());
    }

    private static boolean matchesQuery(Piece p, String needle) {
        return (p.getLibelle() != null && p.getLibelle().toLowerCase(Locale.ROOT).contains(needle))
                || (p.getRef() != null && p.getRef().toLowerCase(Locale.ROOT).contains(needle))
                || (p.getDescription() != null && p.getDescription().toLowerCase(Locale.ROOT).contains(needle));
    }

    private static boolean matchesCategory(Piece p, String cat) {
        if (p.getCategoriepiece() == null) {
            return false;
        }
        CategoriePiece c = p.getCategoriepiece();
        String lib = c.getLibelle() != null ? c.getLibelle().toLowerCase(Locale.ROOT) : "";
        String ref = c.getRef() != null ? c.getRef().toLowerCase(Locale.ROOT) : "";
        return lib.contains(cat) || ref.contains(cat) || cat.contains(lib);
    }
}

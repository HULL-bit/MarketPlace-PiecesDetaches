package com.marketplace.controller;

import com.marketplace.entity.Piece;
import com.marketplace.repository.PrixPieceRepository;
import com.marketplace.service.BoutiqueService;
import com.marketplace.service.CategoriePieceService;
import com.marketplace.service.PieceService;
import com.marketplace.web.CataloguePageSupport;
import com.marketplace.web.GuestCartSupport;
import com.marketplace.web.LocaleSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class PublicCatalogueController {

    private final PieceService pieceService;
    private final CategoriePieceService categoriePieceService;
    private final BoutiqueService boutiqueService;
    private final PrixPieceRepository prixPieceRepository;
    private final LocaleResolver localeResolver;

    public PublicCatalogueController(PieceService pieceService,
                                   CategoriePieceService categoriePieceService,
                                   BoutiqueService boutiqueService,
                                   PrixPieceRepository prixPieceRepository,
                                   LocaleResolver localeResolver) {
        this.pieceService = pieceService;
        this.categoriePieceService = categoriePieceService;
        this.boutiqueService = boutiqueService;
        this.prixPieceRepository = prixPieceRepository;
        this.localeResolver = localeResolver;
    }

    @GetMapping("/catalogue")
    public String catalogue(@RequestParam(required = false) String q,
                            @RequestParam(required = false) String categorie,
                            @RequestParam(required = false) String marque,
                            @RequestParam(required = false) String boutique,
                            Model model,
                            HttpServletRequest request,
                            HttpSession session) {
        LocaleSupport.applyLangSwitch(request, localeResolver, model.asMap(), "/catalogue");

        List<Piece> available = CataloguePageSupport.availableWithRelations(pieceService);
        List<Piece> filtered = CataloguePageSupport.filterPieces(available, q, categorie, boutique);
        List<Map<String, Object>> items = CataloguePageSupport.toCatalogueItems(filtered, prixPieceRepository);

        model.addAttribute("cataloguePieces", items);
        model.addAttribute("categories", categoriePieceService.findAll());
        model.addAttribute("boutiques", boutiqueService.findAll());
        model.addAttribute("searchQuery", q != null ? q : "");
        model.addAttribute("selectedCategorie", categorie != null ? categorie : "");
        model.addAttribute("selectedMarque", marque != null ? marque : "");
        model.addAttribute("selectedBoutique", boutique != null ? boutique : "");
        model.addAttribute("resultCount", items.size());
        model.addAttribute("cartCount", GuestCartSupport.countItems(session));

        return "forward:/public/catalogue.xhtml";
    }
}

package com.marketplace.controller;

import com.marketplace.entity.Piece;
import com.marketplace.repository.PrixPieceRepository;
import com.marketplace.service.PieceService;
import com.marketplace.web.GuestCartSupport;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/panier")
public class PublicPanierController {

    private final PieceService pieceService;
    private final PrixPieceRepository prixPieceRepository;

    public PublicPanierController(PieceService pieceService, PrixPieceRepository prixPieceRepository) {
        this.pieceService = pieceService;
        this.prixPieceRepository = prixPieceRepository;
    }

    @GetMapping
    public String panier(Model model, HttpSession session) {
        model.addAttribute("cartLines", GuestCartSupport.getLines(session));
        model.addAttribute("cartTotal", GuestCartSupport.totalFcfa(session));
        model.addAttribute("cartCount", GuestCartSupport.countItems(session));
        model.addAttribute("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return "forward:/public/panier.xhtml";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Integer idpiece,
                            @RequestParam(defaultValue = "1") int quantite,
                            @RequestParam(required = false) String redirect,
                            HttpSession session,
                            RedirectAttributes ra) {
        Piece piece = pieceService.findById(idpiece).orElse(null);
        if (piece == null || !"DISPONIBLE".equalsIgnoreCase(
                piece.getEtat() != null ? piece.getEtat().trim() : "")) {
            ra.addFlashAttribute("cartError", "Pièce indisponible.");
        } else {
            GuestCartSupport.addPiece(session, piece, Math.max(1, quantite), prixPieceRepository);
            ra.addFlashAttribute("cartSuccess", "Pièce ajoutée au panier.");
        }
        String target = (redirect != null && redirect.startsWith("/")) ? redirect : "/catalogue";
        return "redirect:" + target;
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam Integer idpiece,
                             @RequestParam int quantite,
                             HttpSession session) {
        GuestCartSupport.updateQty(session, idpiece, quantite);
        return "redirect:/panier";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Integer idpiece, HttpSession session) {
        GuestCartSupport.remove(session, idpiece);
        return "redirect:/panier";
    }

    @GetMapping("/commander")
    public String commander(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getName())) {
            return "redirect:/login?redirect=/panier/commander";
        }
        return "redirect:/client/panier";
    }
}

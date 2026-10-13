package com.marketplace.controller;

import com.marketplace.entity.Piece;
import com.marketplace.service.PieceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final PieceService pieceService;

    public ClientController(PieceService pieceService) {
        this.pieceService = pieceService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return "forward:/client/dashboard.xhtml";
    }

    @GetMapping("/catalogue")
    public String catalogue(Model model) {
        List<Piece> pieces = pieceService.findAvailable();
        model.addAttribute("pieces", pieces);
        model.addAttribute("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return "forward:/client/catalogue.xhtml";
    }

    @GetMapping("/panier")
    public String panier(Model model) {
        model.addAttribute("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return "forward:/client/panier.xhtml";
    }

    @GetMapping("/commandes")
    public String commandes(Model model) {
        model.addAttribute("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return "forward:/client/commandes.xhtml";
    }

    @GetMapping("/compte")
    public String compte(Model model) {
        model.addAttribute("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return "forward:/client/compte.xhtml";
    }
}

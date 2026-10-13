package com.marketplace.service.impl;

import com.marketplace.dto.AdminDashboardSnapshot;
import com.marketplace.entity.Boutique;
import com.marketplace.entity.Commande;
import com.marketplace.entity.Personne;
import com.marketplace.entity.Piece;
import com.marketplace.service.AdminDashboardService;
import com.marketplace.service.BoutiqueService;
import com.marketplace.service.CategoriePieceService;
import com.marketplace.service.CommandeService;
import com.marketplace.service.PersonneService;
import com.marketplace.service.PieceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final PersonneService personneService;
    private final BoutiqueService boutiqueService;
    private final PieceService pieceService;
    private final CommandeService commandeService;
    private final CategoriePieceService categoriePieceService;

    public AdminDashboardServiceImpl(PersonneService personneService,
                                       BoutiqueService boutiqueService,
                                       PieceService pieceService,
                                       CommandeService commandeService,
                                       CategoriePieceService categoriePieceService) {
        this.personneService = personneService;
        this.boutiqueService = boutiqueService;
        this.pieceService = pieceService;
        this.commandeService = commandeService;
        this.categoriePieceService = categoriePieceService;
    }

    @Override
    @Transactional(readOnly = true)
    public AdminDashboardSnapshot buildSnapshot() {
        List<Personne> allUsers = personneService.findAll();
        long activeUsers = allUsers.stream().filter(u -> "ACTIF".equals(u.getEtat())).count();

        long nbClients = countRole(allUsers, "CLIENT");
        long nbAdmins = countRole(allUsers, "ADMIN");
        long nbVendeurs = allUsers.stream()
                .filter(u -> "BOUTIQUE".equals(u.getRole()) || "MANAGER".equals(u.getRole()) || "MAGASINIER".equals(u.getRole()))
                .count();
        long nbAgentsAtelier = allUsers.stream()
                .filter(u -> "AGENT_ATELIER".equals(u.getRole()) || "MECANICIEN".equals(u.getRole()))
                .count();
        long nbLivreurs = countRole(allUsers, "LIVREUR");

        List<Boutique> boutiques = boutiqueService.findAll();
        long activeBoutiques = boutiques.stream().filter(b -> "ACTIF".equals(b.getEtat())).count();

        List<Piece> pieces = pieceService.findAll();
        long piecesDisponibles = pieces.stream()
                .filter(p -> p.getEtat() == null || "DISPONIBLE".equalsIgnoreCase(p.getEtat()))
                .count();

        List<Commande> commandes = commandeService.findAll();
        Map<String, Long> commandesParEtat = commandes.stream()
                .collect(Collectors.groupingBy(c -> c.getEtat() != null ? c.getEtat() : "INCONNU", Collectors.counting()));

        long enAttente = commandeService.countByEtat("EN_ATTENTE");
        long livrees = commandeService.countByEtat("LIVREE");
        long commandesAujourdhui = countCommandesInLastDays(commandes, 1);
        long commandes7Jours = countCommandesInLastDays(commandes, 7);

        Integer ca = commandeService.calculateTotalRevenue();
        long caLong = ca != null ? ca.longValue() : 0L;
        long ageMoins18 = allUsers.stream().map(this::computeAge).filter(a -> a != null && a < 18).count();
        long age18a25 = allUsers.stream().map(this::computeAge).filter(a -> a != null && a >= 18 && a <= 25).count();
        long age26a40 = allUsers.stream().map(this::computeAge).filter(a -> a != null && a >= 26 && a <= 40).count();
        long age41Plus = allUsers.stream().map(this::computeAge).filter(a -> a != null && a >= 41).count();

        List<Personne> recentUsers = allUsers.stream()
                .sorted(Comparator
                        .comparing(Personne::getDateCreation, Comparator.nullsLast(Comparator.naturalOrder()))
                        .reversed())
                .limit(8)
                .collect(Collectors.toList());

        List<Commande> recentCommandes = commandes.stream()
                .sorted(Comparator
                        .comparing(Commande::getIdcommande, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(8)
                .collect(Collectors.toList());

        AdminDashboardSnapshot snapshot = new AdminDashboardSnapshot();
        snapshot.setTotalUsers(allUsers.size());
        snapshot.setActiveUsers(activeUsers);
        snapshot.setInactiveUsers(allUsers.size() - activeUsers);
        snapshot.setNbClients(nbClients);
        snapshot.setNbAdmins(nbAdmins);
        snapshot.setNbVendeurs(nbVendeurs);
        snapshot.setNbAgentsAtelier(nbAgentsAtelier);
        snapshot.setNbLivreurs(nbLivreurs);
        snapshot.setTotalBoutiques(boutiques.size());
        snapshot.setActiveBoutiques(activeBoutiques);
        snapshot.setTotalPieces(pieces.size());
        snapshot.setPiecesDisponibles(piecesDisponibles);
        snapshot.setTotalCategories(categoriePieceService.findAll().size());
        snapshot.setTotalCommandes(commandes.size());
        snapshot.setCommandesEnAttente(enAttente);
        snapshot.setCommandesLivrees(livrees);
        snapshot.setCommandesAujourdhui(commandesAujourdhui);
        snapshot.setCommandes7Jours(commandes7Jours);
        snapshot.setChiffreAffairesFcfa(caLong);
        snapshot.setAgeMoins18(ageMoins18);
        snapshot.setAge18a25(age18a25);
        snapshot.setAge26a40(age26a40);
        snapshot.setAge41Plus(age41Plus);
        snapshot.setCommandesParEtat(commandesParEtat);
        snapshot.setRecentUsers(recentUsers);
        snapshot.setRecentCommandes(recentCommandes);
        return snapshot;
    }

    private static long countRole(List<Personne> users, String role) {
        return users.stream().filter(u -> Objects.equals(role, u.getRole())).count();
    }

    private Integer computeAge(Personne p) {
        if (p.getDatenaiss() == null) {
            return null;
        }
        return Period.between(p.getDatenaiss(), LocalDate.now()).getYears();
    }

    private long countCommandesInLastDays(List<Commande> commandes, int days) {
        LocalDate now = LocalDate.now();
        LocalDate from = now.minusDays(days - 1L);
        return commandes.stream()
                .map(c -> parseCommandeDate(c.getDate()))
                .filter(Objects::nonNull)
                .filter(d -> !d.isBefore(from) && !d.isAfter(now))
                .count();
    }

    private LocalDate parseCommandeDate(String date) {
        if (date == null || date.isBlank()) {
            return null;
        }
        List<DateTimeFormatter> patterns = List.of(
                DateTimeFormatter.ISO_LOCAL_DATE,
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd")
        );
        for (DateTimeFormatter pattern : patterns) {
            try {
                return LocalDate.parse(date.trim(), pattern);
            } catch (DateTimeParseException ignored) {
                // try next format
            }
        }
        return null;
    }
}

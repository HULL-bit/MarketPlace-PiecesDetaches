package com.marketplace.dto;

import com.marketplace.entity.Commande;
import com.marketplace.entity.Personne;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Vue agrégée du tableau de bord admin (données issues des services métier).
 */
public class AdminDashboardSnapshot {

    private long totalUsers;
    private long activeUsers;
    private long inactiveUsers;

    private long nbClients;
    private long nbAdmins;

    private long nbVendeurs;
    private long nbAgentsAtelier;
    private long nbLivreurs;

    private long totalBoutiques;
    private long activeBoutiques;

    private long totalPieces;
    private long piecesDisponibles;

    private long totalCategories;

    private long totalCommandes;
    private long commandesEnAttente;
    private long commandesLivrees;
    private long commandesAujourdhui;
    private long commandes7Jours;
  
    private long chiffreAffairesFcfa;

    private long ageMoins18;
    private long age18a25;
    private long age26a40;
    private long age41Plus;

    /** Libellé état -> nombre (aperçu pipeline) */
    private Map<String, Long> commandesParEtat = Collections.emptyMap();

    private List<Personne> recentUsers = Collections.emptyList();

    private List<Commande> recentCommandes = Collections.emptyList();

    public AdminDashboardSnapshot() {
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(long activeUsers) {
        this.activeUsers = activeUsers;
    }

    public long getInactiveUsers() {
        return inactiveUsers;
    }

    public void setInactiveUsers(long inactiveUsers) {
        this.inactiveUsers = inactiveUsers;
    }

    public long getNbClients() {
        return nbClients;
    }

    public void setNbClients(long nbClients) {
        this.nbClients = nbClients;
    }

    public long getNbAdmins() {
        return nbAdmins;
    }

    public void setNbAdmins(long nbAdmins) {
        this.nbAdmins = nbAdmins;
    }

    public long getNbVendeurs() {
        return nbVendeurs;
    }

    public void setNbVendeurs(long nbVendeurs) {
        this.nbVendeurs = nbVendeurs;
    }

    public long getNbAgentsAtelier() {
        return nbAgentsAtelier;
    }

    public void setNbAgentsAtelier(long nbAgentsAtelier) {
        this.nbAgentsAtelier = nbAgentsAtelier;
    }

    public long getNbLivreurs() {
        return nbLivreurs;
    }

    public void setNbLivreurs(long nbLivreurs) {
        this.nbLivreurs = nbLivreurs;
    }

    public long getTotalBoutiques() {
        return totalBoutiques;
    }

    public void setTotalBoutiques(long totalBoutiques) {
        this.totalBoutiques = totalBoutiques;
    }

    public long getActiveBoutiques() {
        return activeBoutiques;
    }

    public void setActiveBoutiques(long activeBoutiques) {
        this.activeBoutiques = activeBoutiques;
    }

    public long getTotalPieces() {
        return totalPieces;
    }

    public void setTotalPieces(long totalPieces) {
        this.totalPieces = totalPieces;
    }

    public long getPiecesDisponibles() {
        return piecesDisponibles;
    }

    public void setPiecesDisponibles(long piecesDisponibles) {
        this.piecesDisponibles = piecesDisponibles;
    }

    public long getTotalCategories() {
        return totalCategories;
    }

    public void setTotalCategories(long totalCategories) {
        this.totalCategories = totalCategories;
    }

    public long getTotalCommandes() {
        return totalCommandes;
    }

    public void setTotalCommandes(long totalCommandes) {
        this.totalCommandes = totalCommandes;
    }

    public long getCommandesEnAttente() {
        return commandesEnAttente;
    }

    public void setCommandesEnAttente(long commandesEnAttente) {
        this.commandesEnAttente = commandesEnAttente;
    }

    public long getCommandesLivrees() {
        return commandesLivrees;
    }

    public void setCommandesLivrees(long commandesLivrees) {
        this.commandesLivrees = commandesLivrees;
    }

    public long getCommandesAujourdhui() {
        return commandesAujourdhui;
    }

    public void setCommandesAujourdhui(long commandesAujourdhui) {
        this.commandesAujourdhui = commandesAujourdhui;
    }

    public long getCommandes7Jours() {
        return commandes7Jours;
    }

    public void setCommandes7Jours(long commandes7Jours) {
        this.commandes7Jours = commandes7Jours;
    }

    public long getChiffreAffairesFcfa() {
        return chiffreAffairesFcfa;
    }

    public void setChiffreAffairesFcfa(long chiffreAffairesFcfa) {
        this.chiffreAffairesFcfa = chiffreAffairesFcfa;
    }

    public long getAgeMoins18() {
        return ageMoins18;
    }

    public void setAgeMoins18(long ageMoins18) {
        this.ageMoins18 = ageMoins18;
    }

    public long getAge18a25() {
        return age18a25;
    }

    public void setAge18a25(long age18a25) {
        this.age18a25 = age18a25;
    }

    public long getAge26a40() {
        return age26a40;
    }

    public void setAge26a40(long age26a40) {
        this.age26a40 = age26a40;
    }

    public long getAge41Plus() {
        return age41Plus;
    }

    public void setAge41Plus(long age41Plus) {
        this.age41Plus = age41Plus;
    }

    public Map<String, Long> getCommandesParEtat() {
        return commandesParEtat;
    }

    public void setCommandesParEtat(Map<String, Long> commandesParEtat) {
        this.commandesParEtat = commandesParEtat;
    }

    public List<Personne> getRecentUsers() {
        return recentUsers;
    }

    public void setRecentUsers(List<Personne> recentUsers) {
        this.recentUsers = recentUsers;
    }

    public List<Commande> getRecentCommandes() {
        return recentCommandes;
    }

    public void setRecentCommandes(List<Commande> recentCommandes) {
        this.recentCommandes = recentCommandes;
    }
}

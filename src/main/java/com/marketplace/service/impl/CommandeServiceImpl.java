package com.marketplace.service.impl;

import com.marketplace.entity.Client;
import com.marketplace.entity.Commande;
import com.marketplace.repository.ClientRepository;
import com.marketplace.repository.CommandeRepository;
import com.marketplace.service.CommandeService;
import com.marketplace.util.QRCodeGenerator;
import com.marketplace.util.SecurityLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommandeServiceImpl implements CommandeService {
    
    private final CommandeRepository commandeRepository;
    private final ClientRepository clientRepository;
    private final QRCodeGenerator qrCodeGenerator;
    private final SecurityLogger securityLogger;

    public CommandeServiceImpl(CommandeRepository commandeRepository, ClientRepository clientRepository,
                               QRCodeGenerator qrCodeGenerator, SecurityLogger securityLogger) {
        this.commandeRepository = commandeRepository;
        this.clientRepository = clientRepository;
        this.qrCodeGenerator = qrCodeGenerator;
        this.securityLogger = securityLogger;
    }

    
    @Override
    public Commande save(Commande commande) {
        Commande saved = commandeRepository.save(commande);
        generateQRCode(saved.getIdcommande());
        securityLogger.logAction("CREATE", "commande", saved.getIdcommande().toString());
        return saved;
    }
    
    @Override
    public Commande update(Commande commande) {
        Commande updated = commandeRepository.save(commande);
        securityLogger.logAction("UPDATE", "commande", updated.getIdcommande().toString());
        return updated;
    }
    
    @Override
    public void delete(Integer id) {
        commandeRepository.deleteById(id);
        securityLogger.logAction("DELETE", "commande", id.toString());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Commande> findById(Integer id) {
        return commandeRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Commande> findAll() {
        return commandeRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Commande> findByClient(Integer idclient) {
        return commandeRepository.findByClientIdclient(idclient);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Commande> findByEtat(String etat) {
        return commandeRepository.findByEtat(etat);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Commande> findByLivreur(Integer idlivreur) {
        return commandeRepository.findByLivreur(idlivreur);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Long countByEtat(String etat) {
        return commandeRepository.countByEtat(etat);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Integer calculateTotalRevenue() {
        return commandeRepository.calculateTotalRevenue();
    }
    
    @Override
    public void updateEtat(Integer id, String etat) {
        commandeRepository.findById(id).ifPresent(c -> {
            c.setEtat(etat);
            commandeRepository.save(c);
            securityLogger.logAction("UPDATE_ETAT", "commande", id + " -> " + etat);
        });
    }
    
    @Override
    public void generateQRCode(Integer id) {
        commandeRepository.findById(id).ifPresent(commande -> {
            String qrCodePath = qrCodeGenerator.generateQRCode("CMD_" + commande.getIdcommande(), 300);
            commande.setQrcode(qrCodePath);
            commandeRepository.save(commande);
        });
    }

    @Override
    public Commande createMinimalForAdmin(Integer idclient, Integer nombrearticle, String etat, String date,
                                          String modelivraison) {
        Client client = clientRepository.findById(idclient)
                .orElseThrow(() -> new IllegalArgumentException("Client inconnu: " + idclient));
        Commande c = new Commande();
        c.setClient(client);
        c.setNombrearticle(nombrearticle != null ? nombrearticle : 0);
        c.setEtat(etat != null && !etat.isBlank() ? etat.trim() : "EN_ATTENTE");
        String d = date != null && !date.isBlank() ? date.trim()
                : LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        c.setDate(d);
        if (modelivraison != null && !modelivraison.isBlank()) {
            c.setModelivraison(modelivraison.trim());
        }
        return save(c);
    }

    @Override
    public void updateFromAdmin(Integer idcommande, String etat, Integer nombrearticle, String modelivraison, String date) {
        Commande c = commandeRepository.findById(idcommande)
                .orElseThrow(() -> new IllegalArgumentException("Commande introuvable: " + idcommande));
        if (etat != null && !etat.isBlank()) {
            c.setEtat(etat.trim());
        }
        if (nombrearticle != null) {
            c.setNombrearticle(nombrearticle);
        }
        if (modelivraison != null) {
            c.setModelivraison(modelivraison.isBlank() ? null : modelivraison.trim());
        }
        if (date != null && !date.isBlank()) {
            c.setDate(date.trim());
        }
        c.setDatemaj(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        commandeRepository.save(c);
        securityLogger.logAction("UPDATE_ADMIN", "commande", idcommande.toString());
    }
}

package com.marketplace.service;

import com.marketplace.entity.Client;
import java.util.List;
import java.util.Optional;

public interface ClientService {
    Client save(Client client);
    Client update(Client client);
    void delete(Integer id);
    Optional<Client> findById(Integer id);
    List<Client> findAll();
    Optional<Client> findByPersonneId(Integer idpersonne);
    List<Client> findByNomContaining(String nom);
    boolean existsByEmail(String email);
}

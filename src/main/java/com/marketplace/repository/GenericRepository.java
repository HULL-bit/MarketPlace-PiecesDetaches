package com.marketplace.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface GenericRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    // Pas de @Query ici : Spring Data délègue à GenericRepositoryImpl
    // qui gère le cas où l'entité n'a pas de champ "etat"
    List<T> findAllActive();

    Page<T> findAllActive(Pageable pageable);

    long countActive();

    List<T> search(String search);

    Optional<T> findById(ID id);

    List<T> findAllById(Iterable<ID> ids);

    boolean existsById(ID id);

    long count();

    void deleteById(ID id);

    void delete(T entity);

    void deleteAll(Iterable<? extends T> entities);

    void deleteAll();
}

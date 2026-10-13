package com.marketplace.repository.impl;

import com.marketplace.repository.GenericRepository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Transactional
public class GenericRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
        implements GenericRepository<T, ID> {

    private final EntityManager entityManager;
    private final Class<T> entityClass;
    private final boolean hasEtat;

    public GenericRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.entityClass = entityInformation.getJavaType();
        this.hasEtat = Arrays.stream(entityClass.getDeclaredFields())
                .anyMatch(f -> f.getName().equals("etat"));
    }

    @Override
    public List<T> findAllActive() {
        if (!hasEtat) {
            return findAll();
        }
        String query = "SELECT e FROM " + entityClass.getSimpleName()
                + " e WHERE e.etat = 'ACTIF' OR e.etat = 'DISPONIBLE'";
        return entityManager.createQuery(query, entityClass).getResultList();
    }

    @Override
    public Page<T> findAllActive(Pageable pageable) {
        if (!hasEtat) {
            return findAll(pageable);
        }
        String query = "SELECT e FROM " + entityClass.getSimpleName()
                + " e WHERE e.etat = 'ACTIF' OR e.etat = 'DISPONIBLE'";
        List<T> results = entityManager.createQuery(query, entityClass)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        String countQuery = "SELECT COUNT(e) FROM " + entityClass.getSimpleName()
                + " e WHERE e.etat = 'ACTIF' OR e.etat = 'DISPONIBLE'";
        long total = entityManager.createQuery(countQuery, Long.class).getSingleResult();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public long countActive() {
        if (!hasEtat) {
            return count();
        }
        String query = "SELECT COUNT(e) FROM " + entityClass.getSimpleName()
                + " e WHERE e.etat = 'ACTIF' OR e.etat = 'DISPONIBLE'";
        return entityManager.createQuery(query, Long.class).getSingleResult();
    }

    @Override
    public List<T> search(String search) {
        String query = "SELECT e FROM " + entityClass.getSimpleName()
                + " e WHERE CAST(e.id as string) LIKE :search";
        return entityManager.createQuery(query, entityClass)
                .setParameter("search", "%" + search + "%")
                .getResultList();
    }
}

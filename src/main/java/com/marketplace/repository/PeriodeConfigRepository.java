package com.marketplace.repository;

import com.marketplace.entity.PeriodeConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodeConfigRepository extends GenericRepository<PeriodeConfig, Integer> {
    List<PeriodeConfig> findByTypeOrderByOrdreAsc(String type);

    long countByType(String type);
}


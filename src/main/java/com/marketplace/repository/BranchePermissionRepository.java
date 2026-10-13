package com.marketplace.repository;

import com.marketplace.entity.BranchePermission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchePermissionRepository extends GenericRepository<BranchePermission, Integer> {

    @Query("SELECT COALESCE(MAX(b.code), 0) FROM BranchePermission b")
    Integer findMaxCode();
}

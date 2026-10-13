package com.marketplace.repository;

import com.marketplace.entity.Permission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends GenericRepository<Permission, Integer> {

    List<Permission> findByBrancheIdbranche(Integer idbranche);

    List<Permission> findByEtat(String etat);

    @Query("SELECT COALESCE(MAX(p.code), 0) FROM Permission p WHERE p.branche.code = :branchCode")
    Integer findMaxCodeByBranchCode(@Param("branchCode") Integer branchCode);
}

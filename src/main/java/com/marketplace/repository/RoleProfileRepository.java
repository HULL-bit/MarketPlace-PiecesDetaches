package com.marketplace.repository;

import com.marketplace.entity.RoleProfile;
import com.marketplace.entity.RoleProfileId;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleProfileRepository extends GenericRepository<RoleProfile, RoleProfileId> {
    List<RoleProfile> findByProfileIdprofile(String idprofile);

    List<RoleProfile> findByProfileIdprofileAndEtat(String idprofile, String etat);

    void deleteByProfile_Idprofile(String idprofile);
}


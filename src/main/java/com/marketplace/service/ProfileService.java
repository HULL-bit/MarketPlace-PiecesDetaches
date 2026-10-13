package com.marketplace.service;

import com.marketplace.entity.Profile;
import java.util.List;

public interface ProfileService {
    
    List<Profile> findAll();
    
    Profile findById(String id);
    
    Profile save(Profile profile);
    
    void delete(String id);
}

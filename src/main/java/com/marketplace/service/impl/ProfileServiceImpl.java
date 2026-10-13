package com.marketplace.service.impl;

import com.marketplace.entity.Profile;
import com.marketplace.repository.ProfileRepository;
import com.marketplace.service.ProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Profile findById(String id) {
        return profileRepository.findById(id).orElse(null);
    }

    @Override
    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public void delete(String id) {
        profileRepository.deleteById(id);
    }
}

package com.racha.rachadev.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.racha.rachadev.models.Authority;
import com.racha.rachadev.repositories.AuthorityRepository;

@Service
public class AuthorityService {
    
    @Autowired
    private AuthorityRepository authorityRepository;

    public Authority save(Authority authority) {
        return authorityRepository.save(authority);
    }

    public Optional<Authority> findById(String id) {
        return authorityRepository.findById(id);
    }
}

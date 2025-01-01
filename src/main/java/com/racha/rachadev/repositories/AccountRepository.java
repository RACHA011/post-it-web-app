package com.racha.rachadev.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.racha.rachadev.models.Account;

@Repository
public interface AccountRepository extends MongoRepository <Account, String>{

    Optional<Account> findTopByOrderByIdDesc();
    
    Optional<Account> findByEmailIgnoreCase(String email);
    
    Optional<Account> findByPasswordResetToken(String passwordResetToken);
}
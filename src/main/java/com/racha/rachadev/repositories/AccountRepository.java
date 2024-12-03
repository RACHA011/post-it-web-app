package com.racha.rachadev.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.racha.rachadev.models.Account;

@Repository
public interface AccountRepository extends JpaRepository <Account, Long>{
    Optional<Account> findByEmailIgnoreCase(String email);
    
    Optional<Account> findByPasswordResetToken(String passwordResetToken);
}
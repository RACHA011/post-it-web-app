package com.racha.rachadev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.racha.rachadev.models.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

} 

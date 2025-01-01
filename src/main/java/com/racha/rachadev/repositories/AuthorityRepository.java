package com.racha.rachadev.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.racha.rachadev.models.Authority;

@Repository
public interface AuthorityRepository extends MongoRepository<Authority, String> {

} 

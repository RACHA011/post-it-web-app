package com.racha.rachadev.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.racha.rachadev.models.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

}

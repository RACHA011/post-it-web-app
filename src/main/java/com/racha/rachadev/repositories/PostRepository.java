package com.racha.rachadev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.racha.rachadev.models.Post;

@Repository
public interface PostRepository extends JpaRepository <Post, Long>{
    
    
}

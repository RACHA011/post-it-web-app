package com.racha.rachadev.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.racha.rachadev.models.Post;
import com.racha.rachadev.services.PostService;


@RestController
@RequestMapping("/api/v1")
public class HomeRestController {
    @Autowired
    private PostService postService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public List<Post> getPosts() {
        logger.debug("This is a test debug log");
        return postService.findAll();
    }


}




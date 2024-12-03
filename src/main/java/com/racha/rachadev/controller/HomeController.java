package com.racha.rachadev.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.racha.rachadev.models.Post;
import com.racha.rachadev.services.PostService;


@Controller
public class HomeController {
    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int per_page,
            @RequestParam(defaultValue = "createdAt") String sort_by,
            Model model) {
        
        PageRequest pageable = PageRequest.of(page, per_page, Sort.by(sort_by));
        Page<Post> postPage = postService.findAll(pageable);
        
        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postPage.getTotalPages());
        model.addAttribute("sortBy", sort_by);
        model.addAttribute("perPage", per_page);


        // List<Post> posts = postService.findAll();
        // model.addAttribute("posts", posts);
        
        return "home_views/home";
    }


}



package com.racha.rachadev.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.racha.rachadev.models.Account;
import com.racha.rachadev.models.Post;
import com.racha.rachadev.services.AccountService;
import com.racha.rachadev.services.PostService;

import jakarta.validation.Valid;




@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable("id") Long id, Model model, Principal principal) {

        Optional<Post> optionalPost = postService.getById(id);
        String authUser = "email";

        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);

            // get username of current logged in session user
            // String authUserName = SecurityContextHolder.getContext().getAuthentication().getAuthent();

            if (principal != null) {
                authUser = principal.getName();
            }
            if (authUser.equals(post.getAccount().getEmail())) {
                model.addAttribute("isOwner", true);
            } else {
                model.addAttribute("isOwner", false);
            }

            return "post_views/post";
        } else {
            return "404";
         }

    }


    @GetMapping("/post/add")
    public String addPost(Model model,Principal principal) {
        String authUser = "email";
        if (principal != null) {
            authUser = principal.getName();
        }
        Optional<Account> optionalAccount = accountService.findOneByEmail(authUser);
        if (optionalAccount.isPresent()) {
            Post post = new Post();
            post.setAccount(optionalAccount.get());
            model.addAttribute("post", post);
            return "post_views/post_add";
        } else {
            return "redirect:/";
        }

    }

    @PostMapping("/post/add")
    @PreAuthorize("isAuthenticated()")
    public String addPostHandler(@Valid @ModelAttribute Post post, BindingResult result ,Principal principal) {
        String AuthUser = "email";
        if (result.hasErrors()) {
            return "post_views/post_add";
        } 
        if(principal != null) {
            AuthUser = principal.getName();
        }
        if (post.getAccount().getEmail().compareToIgnoreCase(AuthUser) < 0) {
            return "redirect:/?error";
        }
        postService.save(post);
        
        return "redirect:/post/" + post.getId();
    }
    
    @GetMapping("/post/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String getPostForEdit(@PathVariable Long id, Model model) {
        Optional<Post> optionalPost = postService.getById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "post_views/post_edit";
        } else {
            return "404";
        }

    }
    
    @PostMapping("/post/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String updatePost(@Valid @ModelAttribute Post post, BindingResult result, @PathVariable Long id) {
        Optional<Post> optionalPost = postService.getById(id);

        if (result.hasErrors()) {
            return "post_views/post_edit";
        } 

        if(optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();
            existingPost.setTitle(post.getTitle());
            existingPost.setBody(post.getBody());
            postService.save(existingPost);
        }

        return "redirect:/post/" + post.getId();
    }

    @GetMapping("/post/{id}/delete")
    @PreAuthorize("isAuthenticated()")
    public String deletePost(@PathVariable Long id) {
        Optional<Post> optionalPost = postService.getById(id);
        

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            postService.delete(post);
            return "redirect:/";
        } else {
            
            return "redirect:/?error";
        }

        
    }
}

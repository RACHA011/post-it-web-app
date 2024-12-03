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


/*
 * @GetMapping("/")
    public String home(Model model, 
    @RequestParam(required = false, name = "sort_by", defaultValue = "createdAt") String sort_by,
    @RequestParam(required = false, name = "per_page", defaultValue = "2") String per_page,
    @RequestParam(required = false, name = "page", defaultValue = "1") String page
    ) {

        Page<Post> posts_on_page = postService.findAll(Integer.parseInt(page) - 1, Integer.parseInt(per_page), sort_by);

        int total_pages = posts_on_page.getTotalPages();

        List<Integer> pages = new ArrayList<Integer>();

        if(total_pages > 0) {
            pages = IntStream.range(0, total_pages - 1)
            .boxed().collect(Collectors.toList());
        }
        
        List<String> links = new ArrayList<String>();

        if (pages != null) {
            for (int link: pages) {
                String active = "";
                if(link == posts_on_page.getNumber()) {
                    active = "active";
                }
                String _temp_link = "/?per_page=" + per_page + "&page=" + (link + 1) + "&sort_by=" + sort_by;
                links.add("<li class=\"page-item " + active +"\"><a href=\"" + _temp_link + "\" class=\"page-link\">" + (link + 1) +"</a></li>");
            }
            model.addAttribute("links", links);
        }
        model.addAttribute("posts", posts_on_page);  
        
 * 
 */


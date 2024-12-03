package com.racha.rachadev.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorsController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(Model model) {
        // Add custom error message or details to the model if needed
        return "error_views/error"; // Ensure you have a Thymeleaf template named error.html in templates/error_views
    }

    @RequestMapping("/access-denied")
    public String handleAccessDenied(Model model) {
        // Add custom access denied message or details to the model if needed
        return "error_views/access-denied"; // Ensure you have a Thymeleaf template named access-denied.html in templates/error_views
    }

}

package com.racha.rachadev.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.racha.rachadev.models.Account;
import com.racha.rachadev.services.AccountService;
import com.racha.rachadev.services.EmailService;
import com.racha.rachadev.util.email.EmailDetaill;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private EmailService emailService;

    @Value("${spring.mvc.static-path-pattern}")
    private String Photo_prifix;

    @Value("${password.token.reset.timeout.minutes}")
    private Long password_token_timeout;

    @Value("${site.domain}")
    private String site_domain;

    @GetMapping("/register")
    public String register(Model model) {

        Account account = new Account();
        model.addAttribute("account", account);
        return "account_views/register";
    }

    @PostMapping("/register")
    public String register_user(@Valid @ModelAttribute("account") Account account, BindingResult result) {
        if (result.hasErrors()) {
            return "account_views/register";
        }

        accountService.save(account);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "account_views/login";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model, Principal principal) {
        String authUser = "email";
        if (principal != null) {
            authUser = principal.getName();
        }
        Optional<Account> optionalAccount = accountService.findOneByEmail(authUser);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            model.addAttribute("account", account);
            model.addAttribute("photo", account.getImageData());
            return "account_views/profile";
        } else {
            return "redirect:/?error";
        }
    }

    @PostMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profileHandler(@Valid @ModelAttribute Account account,
            BindingResult bindingResult,
            Principal principal,
            HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "account_views/profile";
        }
        String authUser = "email";
        if (principal != null) {
            authUser = principal.getName();
        }
        Optional<Account> optionalAccount = accountService.findOneByEmail(authUser);
        if (optionalAccount.isPresent()) {
            Account account_by_id = accountService.findById(account.getId()).get();
            account_by_id.setAge(account.getAge());
            account_by_id.setDate_of_birth(account.getDate_of_birth());
            account_by_id.setFirstName(account.getFirstName());
            account_by_id.setGender(account.getGender());
            account_by_id.setLastName(account.getLastName());
            account_by_id.setPassword(account.getPassword());

            accountService.save(account_by_id);

            // Invalidate session and clear security context
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            SecurityContextHolder.clearContext();

            return "redirect:/";
        } else {
            return "redirect:/?error";
        }

    }

    @PostMapping("/update_photo")
    @PreAuthorize("isAuthenticated()")
    public String update_photo(@RequestParam("file") MultipartFile file,
            RedirectAttributes attributes,
            Principal principal) {
        if (file.isEmpty()) {
            attributes.addFlashAttribute("error", "No file Uploaded");
            return "redirect:/profile";
        } else {

            // String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            try {

                byte[] imageData = file.getBytes();

                // int length = 10;
                // boolean useLetters = true;
                // boolean useNumbers = true;
                // String generatedString = RandomStringUtils.random(length, useLetters,
                // useNumbers);
                // String final_photo_name = generatedString + fileName;
                // String absoluteFileLocation = AppUtil.getUploadPath(final_photo_name);

                // Path path = Paths.get(absoluteFileLocation);
                // Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                // attributes.addFlashAttribute("message", "You successfully uploaded");

                String authUser = "email";
                if (principal != null) {
                    authUser = principal.getName();
                }
                Optional<Account> optionalAccount = accountService.findOneByEmail(authUser);
                if (optionalAccount.isPresent()) {
                    // Account account = optionalAccount.get();
                    // Account accountById = accountService.findById(account.getId()).get();
                    // String relativeFileLocation = Photo_prifix.replace("**", "uploads/" +
                    // final_photo_name);

                    // account.setPhoto(relativeFileLocation);
                    // accountService.save(accountById);

                    // return "redirect:/profile";

                    Account account = optionalAccount.get();
                    account.setImageData("data:image/png;base64," + Base64.getEncoder().encodeToString(imageData));// Photo_prifix.replace("**",
                    // "uploads/" +
                    // final_photo_name));
                    accountService.save(account);

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                    }

                    return "redirect:/profile";
                } else {
                    return "redirect:/profile?error";
                }

            } catch (Exception e) {
                attributes.addFlashAttribute("error", "An error occurred while uploading the file");
                return "redirect:/profile";
            }
        }

    }

    @GetMapping("/test")
    public String test(Model model) {
        return "test";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        return "account_views/forgot_password";
    }

    @PostMapping("/reset-password")
    public String reset_Password(@RequestParam("email") String _email, RedirectAttributes attributes, Model model) {
        Optional<Account> optionalAccount = accountService.findOneByEmail(_email);
        if (optionalAccount.isPresent()) {
            Account account = accountService.findById(optionalAccount.get().getId()).get();
            String reset_token = UUID.randomUUID().toString();
            account.setPasswordResetToken(reset_token);
            account.setPassword_reset_token_expiry(LocalDateTime.now().plusMinutes(password_token_timeout));
            accountService.save(account);
            String reset_message = "This is the reset password link: " + site_domain + "change-password?token="
                    + reset_token;
            // send reset password reset link
            EmailDetaill emailDetails = new EmailDetaill(account.getEmail(), reset_message, "Reset Password Rachadev");
            if (emailService.sendSimpleEmail(emailDetails) == false) {
                attributes.addFlashAttribute("error", "error while sending email, contact admin");
                return "redirect:/forgot-password";
            }

            attributes.addFlashAttribute("message", "Password reset email sent successfully");
            return "redirect:/login";
        } else {
            attributes.addFlashAttribute("error", "No user found with the email supplied");
            return "redirect:/forgot-password";
        }
    }

    @GetMapping("/change-password")
    public String changePasswordString(Model model, @RequestParam("token") String token,
            RedirectAttributes attributes) {
        Optional<Account> optinal_account = accountService.findByToken(token);

        if (token.equals("")) {
            attributes.addFlashAttribute("error", "Invalid Token");
            return "redirect:/forgot-password";
        }

        if (optinal_account.isPresent()) {
            Account account = accountService.findById(optinal_account.get().getId()).get();
            LocalDateTime now = LocalDateTime.now();

            if (now.isAfter(optinal_account.get().getPassword_reset_token_expiry())) {
                attributes.addFlashAttribute("error", "Token Expired");
                return "redirect:/forgot-password";
            }

            model.addAttribute("account", account);
            return "account_views/change_password";
        } else {
            attributes.addFlashAttribute("error", "Invalid token");
            return "redirect:/forgot-password";
        }

    }

    @PostMapping("/change-password")
    public String postChangePassword(@ModelAttribute Account account, RedirectAttributes attributes) {
        Account account_by_id = accountService.findById(account.getId()).get();
        account_by_id.setPassword(account.getPassword());
        account_by_id.setPasswordResetToken("");
        accountService.save(account_by_id);

        attributes.addFlashAttribute("message", "Password updated successfully");

        return "redirect:/login";
    }

}

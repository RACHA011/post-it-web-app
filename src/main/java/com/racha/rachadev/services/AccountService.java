package com.racha.rachadev.services;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.racha.rachadev.models.Account;
import com.racha.rachadev.models.Authority;
import com.racha.rachadev.repositories.AccountRepository;
import com.racha.rachadev.util.constants.Roles;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Value("${spring.mvc.static-path-pattern}")
    private String Photo_prifix;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authorityService;

    public Account save(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        if (account.getRole() == null) {
            account.setRole(Roles.User.getRole());
        }
        if (account.getImageData() == null) {
            // String path = Photo_prifix.replace("**", "images/user.png");

            try {
                // System.out.println("Resolved Path: " + new
                // File("src/main/resources/static/images/user.png").getAbsolutePath());
                byte[] imageData = Files.readAllBytes(new File("src/main/resources/static/images/user.png").toPath());
                account.setImageData("data:image/png;base64," + Base64.getEncoder().encodeToString(imageData));
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        // Generate a new ID if not provided
        if (account.getId() == null || account.getId().isEmpty()) {
            // Check if the email already exists
            if (accountRepository.findByEmailIgnoreCase(account.getEmail()).isPresent()) {
                throw new RuntimeException("Email already exists");
            } 
            Optional<Account> maxIdOpt = findMaxId();
            String newidString = maxIdOpt.map(Account::getId).orElse("0");
            Long newId = Long.parseLong(newidString) + 1;
            account.setId(String.valueOf(newId));
        }

        return accountRepository.save(account);
    }
    public Optional<Account> findMaxId() {
        return accountRepository.findTopByOrderByIdDesc();
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findByEmailIgnoreCase(email);

        if (!optionalAccount.isPresent()) {
            throw new UsernameNotFoundException("Account not found: " + email);
        }
        Account account = optionalAccount.get();

        List<GrantedAuthority> grantedAuthority = new ArrayList<>();

        grantedAuthority.add(new SimpleGrantedAuthority(account.getRole()));

        for (String _auth : account.getAuthorityIds()) {
            Optional<Authority> authority = authorityService.findById(_auth);
            if (!authority.isPresent()) {
                throw new UsernameNotFoundException("Authority not found for ID: " + _auth);
            }
            grantedAuthority.add(new SimpleGrantedAuthority(authority.get().getName()));
        }

        return new User(account.getEmail(), account.getPassword(), grantedAuthority);
    }

    public Optional<Account> findOneByEmail(String email) {
        return accountRepository.findByEmailIgnoreCase(email);
    }

    public Optional<Account> findById(String id) {
        return accountRepository.findById(id);
    }

    public Optional<Account> findByToken(String token) {
        return accountRepository.findByPasswordResetToken(token);
    }
}

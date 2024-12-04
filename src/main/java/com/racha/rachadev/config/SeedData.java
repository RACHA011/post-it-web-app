package com.racha.rachadev.config;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.racha.rachadev.models.Account;
import com.racha.rachadev.models.Authority;
import com.racha.rachadev.services.AccountService;
import com.racha.rachadev.services.AuthorityService;
import com.racha.rachadev.util.constants.Privillages;
import com.racha.rachadev.util.constants.Roles;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorityService authorityService;

    @Override
    public void run(String... args) throws Exception {

        for (Privillages auth : Privillages.values()) {

            Authority authority = new Authority();
            authority.setId(auth.getId());
            authority.setName(auth.getPrivillage());
            authorityService.save(authority);
        }

        Account accounto1 = new Account();

        accounto1.setEmail("ratshalingwaadivhaho106@gmail.com");
        accounto1.setPassword("pass435");
        accounto1.setFirstName("Adivhaho");
        accounto1.setLastName("Ratshalingwa");
        accounto1.setAge(22);
        accounto1.setDate_of_birth(LocalDate.parse("2003-01-13"));
        accounto1.setGender("Male");
        accounto1.setRole(Roles.ADMIN.getRole());

        Set<Authority> authorities = new HashSet<>();
        authorityService.findById(Privillages.RESET_ANY_USER_PASSWORD.getId()).ifPresent(authorities::add);
        authorityService.findById(Privillages.ACCESS_ADMIN_PANEL.getId()).ifPresent(authorities::add);
        accounto1.setAuthorities(authorities);

        accountService.save(accounto1);

    }
}

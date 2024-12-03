package com.racha.rachadev.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {
    private static final String[] WHITELIST = {
        "/",
        "/login",
        "/register",
        "/db-console/**",
        "/**",
        "/post/**",
        "/reset-password",
        "/forgot-password",
        "/error", 
        "/access-denied", 
        "/api/v1/**",
        "/change-password"
    };

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(WHITELIST).permitAll()
                .requestMatchers("/profile/**").authenticated()
                .requestMatchers("/update_photo/**").authenticated()
                .requestMatchers("/uploads/**").authenticated()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/editor/**").hasAnyRole("EDITOR", "ADMIN")
                .requestMatchers("/test/**").hasAuthority("ACCESS_ADMIN_PANEL")
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
            )
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedPage("/access-denied")
            )
            .csrf(csrf -> csrf.disable())
            .rememberMe(rememberMe -> rememberMe
                .key("uniqueAndSecret")
                .tokenValiditySeconds(86400)
                .rememberMeParameter("remember-me")
            )
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        return http.build();
    }
}

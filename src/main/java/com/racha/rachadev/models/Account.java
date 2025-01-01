package com.racha.rachadev.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "account")
public class Account {

    @Id
    private String id;

    @Email(message = "Invalid email address")
    @NotEmpty(message = "Please enter a valid email address")
    private String email;

    @NotEmpty(message = "Password missing")
    private String password;

    @NotEmpty(message = "First name missing")
    private String firstName;

    @NotEmpty(message = "Last name missing")
    private String lastName;

    private String gender;

    @Min(value = 18)
    @Max(value = 150)
    private int age;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private String imageData;

    private String role;

    private String passwordResetToken;

    private LocalDateTime passwordResetTokenExpiry;

    private Set<String> authorityIds;
}

package com.racha.rachadev.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "post")
public class Post {

    @Id
    private String id;

    @NotBlank(message = "Post title missing")
    private String title;

    @NotBlank(message = "Post body missing")
    private String body;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @DBRef
    private Account account;
}

package com.myworkspace.notesManagementServiceApp.data.model;

import lombok.Data;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document("Users")
public class User {
    private String id;
    private String username;
    private String password;
    private boolean isLogged;
    private LocalDateTime dateCreated;
    @DBRef
    private List<Note> notes = new ArrayList<>();
}

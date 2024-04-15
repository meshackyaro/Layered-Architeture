package com.myworkspace.notesManagementServiceApp.data.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document("Notes")
public class Note {
    private String id;
    private String title;
    private String content;
    private String Author;
    private LocalDate dateCreated;
}

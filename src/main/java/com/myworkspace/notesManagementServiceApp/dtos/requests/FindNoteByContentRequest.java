package com.myworkspace.notesManagementServiceApp.dtos.requests;

import lombok.Data;

@Data
public class FindNoteByContentRequest {
    private String author;
    private String content;
}

package com.myworkspace.notesManagementServiceApp.dtos.requests;

import lombok.Data;

@Data
public class FindNoteByUserRequest {
    private String author;
    private String title;
}

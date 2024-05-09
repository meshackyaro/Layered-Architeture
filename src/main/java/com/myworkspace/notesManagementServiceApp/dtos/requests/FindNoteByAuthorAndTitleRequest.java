package com.myworkspace.notesManagementServiceApp.dtos.requests;

import lombok.Data;

@Data
public class FindNoteByAuthorAndTitleRequest {
    private String author;
    private String title;
}

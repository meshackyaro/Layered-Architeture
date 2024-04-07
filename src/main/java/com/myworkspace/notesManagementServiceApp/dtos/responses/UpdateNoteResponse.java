package com.myworkspace.notesManagementServiceApp.dtos.responses;

import lombok.Data;

@Data
public class UpdateNoteResponse {
    private String author;
    private String newTitle;
    private String newContent;
    private String message;
}

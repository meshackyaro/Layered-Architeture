package com.myworkspace.notesManagementServiceApp.dtos.responses;

import lombok.Data;

@Data
public class ShareNoteResponse {
    private String id;
    private String title;
    private String content;
    private String author;
    private String message;
    private boolean isActive;
}

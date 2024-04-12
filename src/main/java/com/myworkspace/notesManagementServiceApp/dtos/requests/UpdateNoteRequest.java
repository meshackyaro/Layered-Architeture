package com.myworkspace.notesManagementServiceApp.dtos.requests;

import lombok.Data;
@Data
public class UpdateNoteRequest {
    private String author;
    private String newTitle;
    private String newContent;
    private String title;
}

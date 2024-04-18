package com.myworkspace.notesManagementServiceApp.dtos.requests;

import lombok.Data;

@Data
public class DeleteNoteRequest {
    private String author;
    private String id;
//    private String title;
}

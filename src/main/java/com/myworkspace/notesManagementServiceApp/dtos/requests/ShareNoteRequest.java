package com.myworkspace.notesManagementServiceApp.dtos.requests;

import lombok.Data;

@Data
public class ShareNoteRequest {
//    private String id;
    private String title;
//    private String content;
    private String author;
    private String shareTo;
}

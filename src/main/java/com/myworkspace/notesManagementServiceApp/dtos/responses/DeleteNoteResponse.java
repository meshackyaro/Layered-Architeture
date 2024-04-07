package com.myworkspace.notesManagementServiceApp.dtos.responses;

import lombok.Data;

@Data
public class DeleteNoteResponse {
    private String title;
    private String message;
}

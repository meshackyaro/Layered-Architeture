package com.myworkspace.notesManagementServiceApp.dtos.requests;

import lombok.Data;

@Data
public class FindAllNotesForAUserRequest {
    private String username;
}

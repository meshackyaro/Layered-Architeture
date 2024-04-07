package com.myworkspace.notesManagementServiceApp.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIResponse {
    private Object data;
    private boolean isSuccessful;
}

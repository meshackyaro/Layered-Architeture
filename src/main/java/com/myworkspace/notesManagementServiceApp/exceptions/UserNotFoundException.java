package com.myworkspace.notesManagementServiceApp.exceptions;

public class UserNotFoundException extends NoteManagerException{
    public UserNotFoundException(String message) {
        super(message);
    }
}

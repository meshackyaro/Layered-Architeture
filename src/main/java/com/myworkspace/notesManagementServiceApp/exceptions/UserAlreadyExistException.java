package com.myworkspace.notesManagementServiceApp.exceptions;

public class UserAlreadyExistException extends NoteManagerException{
    public UserAlreadyExistException(String message) {
        super(message);
    }
}

package com.myworkspace.notesManagementServiceApp.exceptions;

public class InvalidPasswordException extends NoteManagerException{
    public InvalidPasswordException(String message) {
        super(message);
    }
}

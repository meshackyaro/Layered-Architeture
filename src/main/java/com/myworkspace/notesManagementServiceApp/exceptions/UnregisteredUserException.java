package com.myworkspace.notesManagementServiceApp.exceptions;

public class UnregisteredUserException extends NoteManagerException{
    public UnregisteredUserException(String message) {
        super(message);
    }
}

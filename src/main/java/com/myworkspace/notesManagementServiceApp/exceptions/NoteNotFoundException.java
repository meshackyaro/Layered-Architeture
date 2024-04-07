package com.myworkspace.notesManagementServiceApp.exceptions;

public class NoteNotFoundException extends NoteManagerException{
    public NoteNotFoundException(String message) {
        super(message);
    }
}

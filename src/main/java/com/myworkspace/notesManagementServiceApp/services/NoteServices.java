package com.myworkspace.notesManagementServiceApp.services;

import com.myworkspace.notesManagementServiceApp.data.model.Note;
import com.myworkspace.notesManagementServiceApp.dtos.requests.CreateNoteRequest;
import com.myworkspace.notesManagementServiceApp.dtos.requests.DeleteNoteRequest;
import com.myworkspace.notesManagementServiceApp.dtos.requests.ShareNoteRequest;
import com.myworkspace.notesManagementServiceApp.dtos.requests.UpdateNoteRequest;
import com.myworkspace.notesManagementServiceApp.dtos.responses.CreateNoteResponse;
import com.myworkspace.notesManagementServiceApp.dtos.responses.DeleteNoteResponse;
import com.myworkspace.notesManagementServiceApp.dtos.responses.ShareNoteResponse;
import com.myworkspace.notesManagementServiceApp.dtos.responses.UpdateNoteResponse;
import java.util.List;

public interface NoteServices {
    CreateNoteResponse createNote(CreateNoteRequest createNoteRequest);

    UpdateNoteResponse updateNote(UpdateNoteRequest updateNoteRequest);

    Note findNoteByTitle(String title);

    DeleteNoteResponse deleteNote(DeleteNoteRequest deleteNote);

    long count();

    List<Note> findAll();

    ShareNoteResponse shareNote(ShareNoteRequest shareNoteRequest);
}

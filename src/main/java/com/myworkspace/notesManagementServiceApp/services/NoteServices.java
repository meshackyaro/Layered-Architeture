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

    Note findNoteById(String id);

    DeleteNoteResponse deleteNote(DeleteNoteRequest deleteNote);

    long count();

    List<Note> findAll();

    ShareNoteResponse shareNote(ShareNoteRequest shareNoteRequest);

    List<Note> findNotesByUser(String username);

    List<Note> findNoteByAuthorAndTitle(String username, String title);

    List<Note> findNoteByAuthorAndContent(String username, String content);

    Note findNoteBy(String username, String title);

    //    List<Note> findNoteByContent(String username, String keyword);
}

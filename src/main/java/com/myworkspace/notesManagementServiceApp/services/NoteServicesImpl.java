package com.myworkspace.notesManagementServiceApp.services;

import com.myworkspace.notesManagementServiceApp.data.model.Note;
import com.myworkspace.notesManagementServiceApp.data.repositories.NoteRepository;
import com.myworkspace.notesManagementServiceApp.dtos.requests.CreateNoteRequest;
import com.myworkspace.notesManagementServiceApp.dtos.requests.DeleteNoteRequest;
import com.myworkspace.notesManagementServiceApp.dtos.requests.ShareNoteRequest;
import com.myworkspace.notesManagementServiceApp.dtos.requests.UpdateNoteRequest;
import com.myworkspace.notesManagementServiceApp.dtos.responses.CreateNoteResponse;
import com.myworkspace.notesManagementServiceApp.dtos.responses.DeleteNoteResponse;
import com.myworkspace.notesManagementServiceApp.dtos.responses.ShareNoteResponse;
import com.myworkspace.notesManagementServiceApp.dtos.responses.UpdateNoteResponse;
import com.myworkspace.notesManagementServiceApp.exceptions.NoteNotFoundException;
import com.myworkspace.notesManagementServiceApp.exceptions.NullValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoteServicesImpl implements NoteServices {
    @Autowired
    private NoteRepository noteRepository;


    @Override
    public CreateNoteResponse createNote(CreateNoteRequest createNoteRequest) {
        validateTitleFieldFor(createNoteRequest);
        validateContentFieldFor(createNoteRequest);
        validateAuthorFor(createNoteRequest);

        Note note = new Note();
        note.setTitle(createNoteRequest.getTitle());
        note.setContent(createNoteRequest.getContent());
        note.setAuthor(createNoteRequest.getAuthor());
        note = noteRepository.save(note);

        CreateNoteResponse createNoteResponse = new CreateNoteResponse();
        createNoteResponse.setId(note.getId());
        createNoteResponse.setTitle(note.getTitle());
        createNoteResponse.setContent(note.getContent());
        createNoteResponse.setAuthor(note.getAuthor());
        createNoteResponse.setMessage("Note Created Successfully");
        return createNoteResponse;

    }

    private static void validateAuthorFor(CreateNoteRequest createNoteRequest) {
        if (createNoteRequest.getAuthor().isEmpty())
            throw new NullValueException("Author field can not be empty");
    }

    private static void validateContentFieldFor(CreateNoteRequest createNoteRequest) {
        if (createNoteRequest.getContent().isEmpty())
            throw new NullValueException("Empty note");
    }

    private static void validateTitleFieldFor(CreateNoteRequest createNoteRequest) {
        if (createNoteRequest.getTitle().isEmpty())
            throw new NullValueException("Title field can not be empty");
    }

    @Override
    public UpdateNoteResponse updateNote(UpdateNoteRequest updateRequest) {
        Note foundNote = noteRepository.findNoteById(updateRequest.getId());
        foundNote.setTitle(updateRequest.getTitle());
        foundNote.setContent(updateRequest.getContent());
        foundNote.setAuthor(updateRequest.getAuthor());
        noteRepository.save(foundNote);

        UpdateNoteResponse updateResponse = new UpdateNoteResponse();
        updateResponse.setNewTitle(updateRequest.getTitle());
        updateResponse.setNewContent(updateRequest.getContent());
        updateResponse.setAuthor(updateRequest.getAuthor());
        updateResponse.setId(updateRequest.getId());
        updateResponse.setMessage("Note updated");
        return updateResponse;
    }

    @Override
    public Note findNoteById(String title) {
        return noteRepository.findNoteById(title);
    }

    @Override
    public DeleteNoteResponse deleteNote(DeleteNoteRequest deleteNoteRequest) {
        Note foundNote = findNoteById(deleteNoteRequest.getId());
        if (foundNote == null)
            throw new NoteNotFoundException("Note not found");
        noteRepository.delete(foundNote);

        DeleteNoteResponse deleteResponse = new DeleteNoteResponse();
//        deleteResponse.setId(deleteResponse.getId());
//        deleteResponse.setAuthor(deleteNoteRequest.getAuthor());
        deleteResponse.setMessage("Deleted");
        return deleteResponse;
    }

    @Override
    public long count() {
        return noteRepository.count();
    }

    @Override
    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    @Override
    public ShareNoteResponse shareNote(ShareNoteRequest shareNoteRequest) {
        Note foundNote = findNoteById(shareNoteRequest.getTitle());

        if (foundNote == null) throw new NoteNotFoundException("Note not found");

        Note sharedNote = new Note();
        sharedNote.setAuthor(foundNote.getAuthor());
        sharedNote.setTitle(shareNoteRequest.getTitle());
        sharedNote.setContent(foundNote.getContent());
        Note savedNote = noteRepository.save(sharedNote);

        ShareNoteResponse shareNoteResponse = new ShareNoteResponse();
        shareNoteResponse.setAuthor(savedNote.getAuthor());
        shareNoteResponse.setTitle(savedNote.getTitle());
        shareNoteResponse.setContent(savedNote.getContent());
        shareNoteResponse.setMessage("Note shared successfully");
        return shareNoteResponse;
    }

    @Override
    public List<Note> findByUser(String username) {
        return noteRepository.findNoteByAuthor(username);
    }

//    @Override
//    public List<Note> findAllNotesFor(String username) {
//        return noteRepository.findNoteByAuthor(username);
//    }

}

package com.myworkspace.notesManagementServiceApp.services;

import com.myworkspace.notesManagementServiceApp.data.model.Note;
import com.myworkspace.notesManagementServiceApp.data.repositories.NoteRepository;
import com.myworkspace.notesManagementServiceApp.dtos.requests.CreateNoteRequest;
import com.myworkspace.notesManagementServiceApp.dtos.requests.DeleteNoteRequest;
import com.myworkspace.notesManagementServiceApp.dtos.requests.UpdateNoteRequest;
import com.myworkspace.notesManagementServiceApp.dtos.responses.CreateNoteResponse;
import com.myworkspace.notesManagementServiceApp.dtos.responses.DeleteNoteResponse;
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
        if (createNoteRequest.getTitle().isEmpty())
            throw new NullValueException("Title field can not be empty");
        if (createNoteRequest.getContent().isEmpty())
            throw new NullValueException("Empty note");
        if (createNoteRequest.getAuthor().isEmpty())
            throw new NullValueException("Author field can not be empty");

        Note note = new Note();
        note.setTitle(createNoteRequest.getTitle());
        note.setContent(createNoteRequest.getContent());
        note.setAuthor(createNoteRequest.getAuthor());
        noteRepository.save(note);

        CreateNoteResponse createNoteResponse = new CreateNoteResponse();
        createNoteResponse.setTitle(createNoteRequest.getTitle());
        createNoteResponse.setContent(createNoteRequest.getContent());
        createNoteResponse.setAuthor(createNoteRequest.getAuthor());
        createNoteResponse.setMessage("Note Created Successfully");
        return createNoteResponse;

    }

    @Override
    public UpdateNoteResponse updateNote(UpdateNoteRequest updateRequest) {
        Note foundNote = noteRepository.findNoteByTitle(updateRequest.getTitle());
        foundNote.setTitle(updateRequest.getNewTitle());
        foundNote.setContent(updateRequest.getNewContent());
        noteRepository.save(foundNote);

        UpdateNoteResponse updateResponse = new UpdateNoteResponse();
        updateResponse.setNewTitle(updateRequest.getNewTitle());
        updateResponse.setNewContent(updateRequest.getNewContent());
        updateResponse.setAuthor(updateRequest.getAuthor());
        updateResponse.setMessage("Note updated");
        return updateResponse;
    }

    @Override
    public Note findNoteByTitle(String title) {
        return noteRepository.findNoteByTitle(title);
    }

    @Override
    public DeleteNoteResponse deleteNote(DeleteNoteRequest deleteNoteRequest) {
        Note foundNote = findNoteByTitle(deleteNoteRequest.getTitle());
        if (foundNote == null)
            throw new NoteNotFoundException("Note not found");
        noteRepository.delete(foundNote);

        DeleteNoteResponse deleteResponse = new DeleteNoteResponse();
        deleteResponse.setAuthor(deleteNoteRequest.getAuthor());
        deleteResponse.setTitle(deleteNoteRequest.getTitle());
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

    public List<Note> findNoteByAuthor(String Author) {
        return noteRepository.findNoteByAuthor(Author);
    }

}

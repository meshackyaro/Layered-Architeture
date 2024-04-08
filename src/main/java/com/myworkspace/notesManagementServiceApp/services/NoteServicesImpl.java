package com.myworkspace.notesManagementServiceApp.services;

import com.myworkspace.notesManagementServiceApp.data.model.Note;
import com.myworkspace.notesManagementServiceApp.data.model.User;
import com.myworkspace.notesManagementServiceApp.data.repositories.NoteRepository;
import com.myworkspace.notesManagementServiceApp.dtos.requests.CreateNoteRequest;
import com.myworkspace.notesManagementServiceApp.dtos.requests.DeleteNoteRequest;
import com.myworkspace.notesManagementServiceApp.dtos.requests.UpdateNoteRequest;
import com.myworkspace.notesManagementServiceApp.dtos.responses.CreateNoteResponse;
import com.myworkspace.notesManagementServiceApp.dtos.responses.DeleteNoteResponse;
import com.myworkspace.notesManagementServiceApp.dtos.responses.UpdateNoteResponse;
import com.myworkspace.notesManagementServiceApp.exceptions.NoteNotFoundException;
import com.myworkspace.notesManagementServiceApp.exceptions.NullValueException;
import com.myworkspace.notesManagementServiceApp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoteServicesImpl implements NoteServices {
    @Autowired
    private UserServices userServices;
    @Autowired
    private NoteRepository noteRepository;

    @Override
    public CreateNoteResponse createNote(CreateNoteRequest createNoteRequest) {
        User foundUser = userServices.findUserByUsername(createNoteRequest.getAuthor());
        if (foundUser == null)
            throw new UserNotFoundException("User not found");
        if (createNoteRequest.getTitle().isEmpty())
            throw new NullValueException("Title field can not be empty");
        if (createNoteRequest.getContent().isEmpty())
            throw new NullValueException("Content field can not be empty");
        if (createNoteRequest.getAuthor().isEmpty())
            throw new NullValueException("Author field can not be empty");

        Note note = new Note();
        note.setTitle(createNoteRequest.getTitle());
        note.setContent(createNoteRequest.getContent());
        note.setAuthor(createNoteRequest.getAuthor());
        noteRepository.save(note);

        CreateNoteResponse createNoteResponse = new CreateNoteResponse();
        createNoteResponse.setMessage("Note Created Successfully");
        return createNoteResponse;

    }

    @Override
    public UpdateNoteResponse updateNote(UpdateNoteRequest updateRequest) {
        User foundUser = userServices.findUserByUsername(updateRequest.getAuthor());
        if (foundUser == null) throw new UserNotFoundException("User not found");

        Note foundNote = noteRepository.findNoteByTitle(updateRequest.getOldTitle());
        if (foundNote == null) throw new NoteNotFoundException("Note not found");
        if (findNoteByTitle(updateRequest.getOldTitle()) == null)
            throw new NullValueException("Title field can not be empty");
        if (findNoteByTitle(updateRequest.getNewTitle()) == null)
            throw new NullValueException("Title field can not be empty");
        if (findNoteByTitle(updateRequest.getNewContent()) == null)
            throw new NullValueException("Content field can not be empty");

        foundNote.setTitle(updateRequest.getNewTitle());
        foundNote.setContent(updateRequest.getNewContent());
        foundNote.setAuthor(foundUser.getUsername());
        noteRepository.save(foundNote);

        UpdateNoteResponse updateResponse = new UpdateNoteResponse();
        updateResponse.setNewTitle(updateRequest.getNewTitle());
        updateResponse.setNewContent(updateRequest.getNewContent());
        updateResponse.setAuthor(updateRequest.getAuthor());
        updateResponse.setMessage("Updated");
        return updateResponse;
    }

    @Override
    public Note findNoteByTitle(String title) {
        return noteRepository.findNoteByTitle(title);
    }

    @Override
    public DeleteNoteResponse deleteNote(DeleteNoteRequest deleteNoteRequest) {
//        if (noteRepository.findNoteByTitle(deleteNoteRequest.getNoteTitle()) != null)
//            throw new NoteDoesNotExistException("Sorry, note doesn't exist");
        //Note note = noteRepository.findNoteByTitle(deleteNoteRequest.getNoteTitle());
        Note foundNote = findNoteByTitle(deleteNoteRequest.getNoteTitle());
        if (foundNote == null)
            throw new NoteNotFoundException("Not found");
        noteRepository.delete(foundNote);

        DeleteNoteResponse deleteResponse = new DeleteNoteResponse();
        deleteResponse.setTitle(deleteNoteRequest.getNoteTitle());
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

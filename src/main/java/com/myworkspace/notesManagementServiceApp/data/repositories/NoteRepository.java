package com.myworkspace.notesManagementServiceApp.data.repositories;

import com.myworkspace.notesManagementServiceApp.data.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
    Note findNoteById(String id);

    List<Note> findNoteByAuthor(String username);

    Note findNoteBy(String author, String title);

//    List<Note> findAllNotesFor(String username);
}

package com.myworkspace.notesManagementServiceApp.data.repositories;

import com.myworkspace.notesManagementServiceApp.data.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
    Note findNoteByTitle(String title);

    List<Note> findNoteByAuthor(String author);
}

package com.myworkspace.notesManagementServiceApp.data.repositories;

import com.myworkspace.notesManagementServiceApp.data.model.Note;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class NoteRepositoryTest {
    @Autowired
    NoteRepository noteRepository;
    @Test
    public void noteRepositoryTest() {
        noteRepository.deleteAll();
        Note note = new Note();
        note = noteRepository.save(note);
        int currentNotes = noteRepository.findAll().size();
        assertEquals(currentNotes, noteRepository.count());
        assertEquals(note,noteRepository.findNoteById(note.getId()));
    }

}

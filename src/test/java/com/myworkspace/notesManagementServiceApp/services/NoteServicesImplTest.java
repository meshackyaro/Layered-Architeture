package com.myworkspace.notesManagementServiceApp.services;

import com.myworkspace.notesManagementServiceApp.data.model.Note;
import com.myworkspace.notesManagementServiceApp.data.model.User;
import com.myworkspace.notesManagementServiceApp.data.repositories.NoteRepository;
import com.myworkspace.notesManagementServiceApp.data.repositories.UserRepository;
import com.myworkspace.notesManagementServiceApp.dtos.requests.*;
import com.myworkspace.notesManagementServiceApp.dtos.responses.*;
import com.myworkspace.notesManagementServiceApp.exceptions.NoteNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NoteServicesImplTest {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private NoteServices noteServices;
    @Autowired
    private UserServices userServices;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void createNoteTest() {
        userRepository.deleteAll();
        noteRepository.deleteAll();
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("username");
        registerUserRequest.setPassword("password");
        RegistrationResponse response = userServices.register(registerUserRequest);
        long currentlyRegistered = userServices.findAll().size();
        assertEquals(currentlyRegistered, userServices.getNumberOfUsers());

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername(registerUserRequest.getUsername());
        loginRequest.setPassword(registerUserRequest.getPassword());
        LoginResponse login = userServices.login(loginRequest);
        User user = userServices.findUserByUsername(loginRequest.getUsername());
        assertTrue(user.isLogged());

        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setTitle("Title");
        createNoteRequest.setContent("Content");
        createNoteRequest.setAuthor(registerUserRequest.getUsername());
        CreateNoteResponse response1 = noteServices.createNote(createNoteRequest);
        long currentNote = noteServices.findAll().size();
        assertEquals(currentNote, noteServices.count());
        Note foundNote = noteServices.findNoteById(response1.getId());
        assertEquals("Title", foundNote.getTitle());
    }
    @Test
    public void updateNoteTest() {
        userRepository.deleteAll();
        noteRepository.deleteAll();
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("username765");
        registerUserRequest.setPassword("password765");
        RegistrationResponse response = userServices.register(registerUserRequest);
        long currentlyRegistered = userServices.findAll().size();
        assertEquals(1, userServices.getNumberOfUsers());

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername(registerUserRequest.getUsername());
        loginRequest.setPassword(registerUserRequest.getPassword());
        LoginResponse login = userServices.login(loginRequest);
        User user = userServices.findUserByUsername(loginRequest.getUsername());
        assertTrue(user.isLogged());

        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setTitle("Title");
        createNoteRequest.setContent("Content");
        createNoteRequest.setAuthor(registerUserRequest.getUsername());
        CreateNoteResponse response1 = noteServices.createNote(createNoteRequest);
        long currentNote = noteServices.findAll().size();
        assertEquals(currentNote, noteServices.count());
        Note foundNote = noteServices.findNoteById(response1.getId());
//        System.out.println(response1);
        assertEquals("Title", foundNote.getTitle());

        UpdateNoteRequest updateNoteRequest = new UpdateNoteRequest();
        updateNoteRequest.setAuthor(createNoteRequest.getAuthor());
        updateNoteRequest.setId(response1.getId());
        updateNoteRequest.setTitle("New Title");
        updateNoteRequest.setContent("New Content");

        UpdateNoteResponse response2 = noteServices.updateNote(updateNoteRequest);
        long currentNotes = noteServices.findAll().size();
        assertEquals(currentNotes, noteServices.count());
        assertEquals("New Title", response2.getNewTitle());
        assertEquals("New Content", response2.getNewContent());
//        System.out.println(response2);
    }
    @Test
    public void deleteNoteTest() {
        userRepository.deleteAll();
        noteRepository.deleteAll();
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("username76");
        registerUserRequest.setPassword("password765");
        RegistrationResponse response = userServices.register(registerUserRequest);
        long currentlyRegistered = userServices.findAll().size();
        assertEquals(currentlyRegistered, userServices.getNumberOfUsers());

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername(registerUserRequest.getUsername());
        loginRequest.setPassword(registerUserRequest.getPassword());
        LoginResponse login = userServices.login(loginRequest);
        User user = userServices.findUserByUsername(loginRequest.getUsername());
        assertTrue(user.isLogged());

        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setTitle("Title");
        createNoteRequest.setContent("Body");
        createNoteRequest.setAuthor(registerUserRequest.getUsername());
        CreateNoteResponse response1 = noteServices.createNote(createNoteRequest);
        long currentNote = noteServices.findAll().size();
        assertEquals(currentNote, noteServices.count());
        Note foundNote = noteRepository.findNoteById(response1.getId());
        assertEquals("Title", foundNote.getTitle());

        DeleteNoteRequest deleteNote = new DeleteNoteRequest();
        deleteNote.setId(response1.getId());
        deleteNote.setTitle("Title");
        deleteNote.setAuthor(createNoteRequest.getAuthor());
        DeleteNoteResponse response2 = noteServices.deleteNote(deleteNote);
        long currentNotes = noteServices.findAll().size();
        assertEquals(currentNotes, noteServices.count());
    }
    @Test
    public void deleteUncreatedNote_throwNoteNotFoundException() {
        userRepository.deleteAll();
        noteRepository.deleteAll();
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("username");
        registerUserRequest.setPassword("password");
        RegistrationResponse response = userServices.register(registerUserRequest);
        long currentlyRegistered = userServices.findAll().size();
        assertEquals(currentlyRegistered, userServices.getNumberOfUsers());

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername(registerUserRequest.getUsername());
        loginRequest.setPassword(registerUserRequest.getPassword());
        LoginResponse login = userServices.login(loginRequest);
        User user = userServices.findUserByUsername(loginRequest.getUsername());
        assertTrue(user.isLogged());

        DeleteNoteRequest deleteNote = new DeleteNoteRequest();
        deleteNote.setTitle("Title");
        deleteNote.setAuthor("username");
        assertThrows(NoteNotFoundException.class, ()-> noteServices.deleteNote(deleteNote));
    }
//    @Test
//    public void shareNoteTest() {
//        userRepository.deleteAll();
//        noteRepository.deleteAll();
//        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
//        registerUserRequest.setUsername("username");
//        registerUserRequest.setPassword("password");
//        RegistrationResponse response = userServices.register(registerUserRequest);
//        long currentlyRegistered = userServices.findAll().size();
//        assertEquals(currentlyRegistered, userServices.getNumberOfUsers());
//
//        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
//        registerUserRequest.setUsername("newUsername");
//        registerUserRequest.setPassword("password");
//        RegistrationResponse response1 = userServices.register(registerUserRequest);
//        long currentlyRegistered1 = userServices.findAll().size();
//        assertEquals(currentlyRegistered1, userServices.getNumberOfUsers());
//
//        LoginUserRequest loginRequest = new LoginUserRequest();
//        loginRequest.setUsername(registerUserRequest.getUsername());
//        loginRequest.setPassword(registerUserRequest.getPassword());
//        LoginResponse login = userServices.login(loginRequest);
//        User user = userServices.findUserByUsername(loginRequest.getUsername());
//        assertTrue(user.isLogged());
//
//        LoginUserRequest loginRequest1 = new LoginUserRequest();
//        loginRequest.setUsername(registerUserRequest.getUsername());
//        loginRequest.setPassword(registerUserRequest.getPassword());
//        LoginResponse login1 = userServices.login(loginRequest);
//        User user1 = userServices.findUserByUsername(loginRequest.getUsername());
//        assertTrue(user1.isLogged());
//
//        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
//        createNoteRequest.setTitle("Title");
//        createNoteRequest.setContent("Content");
//        createNoteRequest.setAuthor(registerUserRequest.getUsername());
//        CreateNoteResponse response2 = noteServices.createNote(createNoteRequest);
//        long currentNote = noteServices.findAll().size();
//        assertEquals(currentNote, noteServices.count());
//        Note foundNote = noteServices.findNoteById("Title");
//        assertEquals("Title", foundNote.getTitle());
//
//        ShareNoteRequest shareNoteRequest = new ShareNoteRequest();
//        shareNoteRequest.setTitle(createNoteRequest.getTitle());
//        shareNoteRequest.setContent(createNoteRequest.getContent());
//        shareNoteRequest.setAuthor(loginRequest.getUsername());
//        shareNoteRequest.setShareTo(loginRequest1.getUsername());
//        ShareNoteResponse response3 = userServices.shareNote(shareNoteRequest);
//
//        long currentNotes = noteServices.findAll().size();
//        assertEquals(currentNotes, noteServices.count());
//
//        foundNote = noteServices.findNoteById("Title");
//        assertEquals("Title", foundNote.getTitle());
//    }


}

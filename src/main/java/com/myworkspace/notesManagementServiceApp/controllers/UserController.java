package com.myworkspace.notesManagementServiceApp.controllers;

import com.myworkspace.notesManagementServiceApp.dtos.requests.*;
import com.myworkspace.notesManagementServiceApp.dtos.responses.*;
import com.myworkspace.notesManagementServiceApp.exceptions.NoteManagerException;
import com.myworkspace.notesManagementServiceApp.services.NoteServices;
import com.myworkspace.notesManagementServiceApp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/notes")
public class UserController {

    @Autowired
    private UserServices userServices;
    @Autowired
    private NoteServices noteServices;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest registerRequest) {
        try {
            RegistrationResponse response = userServices.register(registerRequest);
            return new ResponseEntity<>(new APIResponse(true, response), CREATED);
        }
        catch(NoteManagerException e) {
            return new ResponseEntity<>(new APIResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserRequest loginRequest) {
        try {
            LoginResponse response = userServices.login(loginRequest);
            return new ResponseEntity<>(new APIResponse(true, response), CREATED);
        }
        catch(NoteManagerException e) {
            return new ResponseEntity<>(new APIResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
    @PostMapping("/createNote")
    public ResponseEntity<?> createNote(@RequestBody CreateNoteRequest createNoteRequest) {
        try {
            CreateNoteResponse response = noteServices.createNote(createNoteRequest);
            return new ResponseEntity<>(new APIResponse(true, response), CREATED);
        }
        catch(NoteManagerException e) {
            return new ResponseEntity<>(new APIResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
    @PatchMapping("/updateNote")
    public ResponseEntity<?> updateNote(@RequestBody UpdateNoteRequest updateNoteRequest, String name) {
        try {
            UpdateNoteResponse response = noteServices.updateNote(updateNoteRequest);
            return new ResponseEntity<>(new APIResponse(true, response), CREATED);
        }
        catch(NoteManagerException e) {
            return new ResponseEntity<>(new APIResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
    @DeleteMapping("/deleteNote")
    public ResponseEntity<?> deleteNote(@RequestBody DeleteNoteRequest deleteNoteRequest) {
        try {
            DeleteNoteResponse response = noteServices.deleteNote(deleteNoteRequest);
            return new ResponseEntity<>(new APIResponse(true, response), CREATED);
        }
        catch(NoteManagerException e) {
            return new ResponseEntity<>(new APIResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutUserRequest logoutRequest) {
        try {
            LogoutResponse response = userServices.logout(logoutRequest);
            return new ResponseEntity<>(new APIResponse(true, response), CREATED);
        }
        catch(NoteManagerException e) {
            return new ResponseEntity<>(new APIResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
}

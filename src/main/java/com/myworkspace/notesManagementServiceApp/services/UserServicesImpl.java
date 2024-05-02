package com.myworkspace.notesManagementServiceApp.services;

import com.myworkspace.notesManagementServiceApp.data.model.Note;
import com.myworkspace.notesManagementServiceApp.data.model.User;
import com.myworkspace.notesManagementServiceApp.data.repositories.UserRepository;
import com.myworkspace.notesManagementServiceApp.dtos.requests.*;
import com.myworkspace.notesManagementServiceApp.dtos.responses.*;
import com.myworkspace.notesManagementServiceApp.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoteServices noteService;

    @Override
    public long getNumberOfUsers() {
        return userRepository.count();
    }

    @Override
    public RegistrationResponse register(RegisterUserRequest registerRequest) {
        User user = new User();
        validateUserFor(registerRequest);
        validateUsernameFor(registerRequest);
        validatePasswordFor(registerRequest);

        user.setUsername(registerRequest.getUsername().toLowerCase());
        user.setPassword(registerRequest.getPassword());
        userRepository.save(user);

        RegistrationResponse registrationResponse = new RegistrationResponse();
        registrationResponse.setUsername(registerRequest.getUsername().toLowerCase());
        registrationResponse.setMessage("Registration Successful");
        return registrationResponse;
    }

    private void validateUserFor(RegisterUserRequest registerRequest) {
        if (findUserByUsername(registerRequest.getUsername().toLowerCase()) != null)
            throw new UserAlreadyExistException("username already exist");
    }

    private static void validatePasswordFor(RegisterUserRequest registerRequest) {
        if (registerRequest.getPassword().matches("[a-zA-Z0-9]+]"))
            throw new InvalidPasswordException("Incorrect username or password format");
    }

    private static void validateUsernameFor(RegisterUserRequest registerRequest) {
        if (registerRequest.getUsername().toLowerCase().matches("[a-zA-Z0-9]+]"))
            throw new InvalidUsernameException("Incorrect username or password format");
    }

    @Override
    public LoginResponse login(LoginUserRequest loginRequest) {
        User foundUser = findUserByUsername(loginRequest.getUsername().toLowerCase());
        if (foundUser == null)
            throw new UserNotFoundException("User not found");
        if (!foundUser.getPassword().equals(loginRequest.getPassword())) throw new InvalidPasswordException("Incorrect username or password");

        foundUser.setLogged(true);
        userRepository.save(foundUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername(loginRequest.getUsername().toLowerCase());
        loginResponse.setMessage("Login Successful");
        return loginResponse;
    }

    @Override
    public LogoutResponse logout(LogoutUserRequest logoutRequest) {
        User user = findUserByUsername(logoutRequest.getUsername().toLowerCase());
        user.setLogged(false);
        userRepository.save(user);

        LogoutResponse logoutResponse = new LogoutResponse();
        logoutResponse.setUsername(logoutRequest.getUsername().toLowerCase());
        logoutResponse.setMessage("Logout Successful");
        return logoutResponse;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

//    @Override
//    public List<Note> findAllNotesFor(String username) {
//        return noteService.findAllNotesFor(username);
//    }

    @Override
    public CreateNoteResponse createNote(CreateNoteRequest createNoteRequest) {
        User foundUser = userRepository.findByUsername(createNoteRequest.getAuthor().toLowerCase());
        if (foundUser == null) throw new UserNotFoundException("User not found");
        if (!foundUser.isLogged()) throw new LoginUserException("Login to continue");

        CreateNoteResponse response = noteService.createNote(createNoteRequest);
        Note foundNote = noteService.findNoteById(response.getId());
        foundUser.getNotes().add(foundNote);
        userRepository.save(foundUser);
        return response;
    }

    @Override
    public UpdateNoteResponse updateNote(UpdateNoteRequest updateNoteRequest) {
        User foundUser = userRepository.findByUsername(updateNoteRequest.getAuthor().toLowerCase());
        if (foundUser == null) throw new UserNotFoundException("User not found");
        if (!foundUser.isLogged()) throw new LoginUserException("Login to continue");

        UpdateNoteResponse response = noteService.updateNote(updateNoteRequest);
        Note foundNote = noteService.findNoteById(updateNoteRequest.getId());
        List<Note> notes = foundUser.getNotes();
        notes.removeIf(note -> note.getId().equals(updateNoteRequest.getId()));
        notes.add(foundNote);
        foundUser.setNotes(notes);
        userRepository.save(foundUser);
        return response;
    }

    @Override
    public DeleteNoteResponse deleteNote(DeleteNoteRequest deleteNoteRequest) {
        User foundUser = userRepository.findByUsername(deleteNoteRequest.getAuthor().toLowerCase());
        if (foundUser == null) throw new UserNotFoundException("User not found");
        if (!foundUser.isLogged()) throw new LoginUserException("Login to continue");

        DeleteNoteResponse response = noteService.deleteNote(deleteNoteRequest);
        Note foundNote = noteService.findNoteById(deleteNoteRequest.getId());
        foundUser.getNotes().remove(foundNote);
        return response;
    }

//    @Override
//    public ShareNoteResponse shareNote(ShareNoteRequest shareNoteRequest) {
//
//        ShareNoteResponse response = new ShareNoteResponse();

//        Note foundNote = noteService.findNoteBy(shareNoteRequest.getAuthor(), shareNoteRequest.getTitle());
//
//        User foundReceiver = userRepository.findByUsername(shareNoteRequest.getShareTo());
//        if (foundReceiver == null) {
//            response.setActive(false);
//            response.setMessage("Receiver not found");
//        }

//        Note sharedNote = new Note();
//        sharedNote.setTitle(foundNote.getTitle());
//        sharedNote.setContent(foundNote.getContent());
//        sharedNote.setAuthor(foundReceiver.getUsername().toLowerCase());
//
//        response.setTitle(sharedNote.getTitle());
//        response.setContent(sharedNote.getContent());
//        response.setAuthor(sharedNote.getAuthor().toLowerCase());
//        response.setActive(true);
//        response.setMessage("Share Successful");
//
//        return response;
//    }

    @Override
    public List<Note> findNoteByUser(String username) {
        User foundUser = userRepository.findByUsername(username.toLowerCase());
        if (foundUser == null) throw new UserNotFoundException("User not found");
        if (!foundUser.isLogged()) throw new LoginUserException("Login to continue");

        List<Note> notes = noteService.findNotesByUser(username);
        foundUser.getNotes().add(new Note());
        foundUser.setNotes(notes);
        userRepository.save(foundUser);
        return notes;
    }

    @Override
    public  List<Note> findNoteByAuthorAndTitle(String author, String title) {
        User user = userRepository.findByUsername(author.toLowerCase());
        if (user == null) throw new UserNotFoundException("User not found");
        if (!user.isLogged()) throw new LoginUserException("Login to continue");
        List<Note> notes = noteService.findNoteByAuthorAndTitle(author,title);
        if(notes.isEmpty()) throw new NoteNotFoundException("Note not found");
        user.setNotes(notes);
        userRepository.save(user);
        return notes;
    }


}

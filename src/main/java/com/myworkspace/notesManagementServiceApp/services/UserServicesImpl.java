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

        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        userRepository.save(user);

        RegistrationResponse registrationResponse = new RegistrationResponse();
        registrationResponse.setUsername(registerRequest.getUsername());
        registrationResponse.setMessage("Registration Successful");
        return registrationResponse;
    }

    private void validateUserFor(RegisterUserRequest registerRequest) {
        if (findUserByUsername(registerRequest.getUsername()) != null)
            throw new UserAlreadyExistException("username already exist");
    }

    private static void validatePasswordFor(RegisterUserRequest registerRequest) {
        if (registerRequest.getPassword().matches("[a-zA-Z0-9]+]"))
            throw new InvalidPasswordException("Incorrect password format. Use uppercase, lowercase letters and/or digits only");
    }

    private static void validateUsernameFor(RegisterUserRequest registerRequest) {
        if (registerRequest.getUsername().matches("[a-zA-Z0-9]+]"))
            throw new InvalidUsernameException("Incorrect username format. Use uppercase, lowercase and/or digits only.");
    }

    @Override
    public LoginResponse login(LoginUserRequest loginRequest) {
        User foundUser = findUserByUsername(loginRequest.getUsername());
        if (foundUser == null)
            throw new UserNotFoundException("User not found");

        foundUser.setLogged(true);
        userRepository.save(foundUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername(loginRequest.getUsername());
        loginResponse.setMessage("Login Successful");
        return loginResponse;
    }

    @Override
    public LogoutResponse logout(LogoutUserRequest logoutRequest) {
        User user = findUserByUsername(logoutRequest.getUsername());
        user.setLogged(false);
        userRepository.save(user);

        LogoutResponse logoutResponse = new LogoutResponse();
        logoutResponse.setUsername(logoutRequest.getUsername());
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

    @Override
    public CreateNoteResponse createNote(CreateNoteRequest createNoteRequest) {
        User foundUser = userRepository.findByUsername(createNoteRequest.getAuthor());
        if (foundUser == null) throw new UserNotFoundException("User not found");
        if (!foundUser.isLogged()) throw new LoginUserException("Login to continue");
        CreateNoteResponse response = noteService.createNote(createNoteRequest);
        Note foundNote = noteService.findNoteByTitle(createNoteRequest.getTitle());
        foundUser.getNotes().add(foundNote);
        userRepository.save(foundUser);
        return response;
    }

    @Override
    public UpdateNoteResponse updateNote(UpdateNoteRequest updateNoteRequest) {
        User foundUser = userRepository.findByUsername(updateNoteRequest.getAuthor());
        if (foundUser == null) throw new UserNotFoundException("User not found");
        if (!foundUser.isLogged()) throw new LoginUserException("Login to continue");
        UpdateNoteResponse response = noteService.updateNote(updateNoteRequest);
        Note foundNote = noteService.findNoteByTitle(updateNoteRequest.getTitle());
        List<Note> notes =foundUser.getNotes();
        notes.removeIf(note -> note.getTitle().equals(updateNoteRequest.getTitle()));
        notes.add(foundNote);
        foundUser.setNotes(notes);
        userRepository.save(foundUser);
        return response;
    }

    @Override
    public DeleteNoteResponse deleteNote(DeleteNoteRequest deleteNoteRequest) {
        User foundUser = userRepository.findByUsername(deleteNoteRequest.getAuthor());
        if (foundUser == null) throw new UserNotFoundException("User not found");
        if (!foundUser.isLogged()) throw new LoginUserException("Login to continue");
        DeleteNoteResponse response = noteService.deleteNote(deleteNoteRequest);
        Note foundNote = noteService.findNoteByTitle(deleteNoteRequest.getTitle());
        foundUser.getNotes().remove(foundNote);
        return response;
    }

    @Override
    public ShareNoteResponse shareNote(ShareNoteRequest shareNoteRequest) {
        User sender = userRepository.findByUsername(shareNoteRequest.getAuthor());
        User recipient = userRepository.findByUsername(shareNoteRequest.getAuthor());

        if (sender == null || recipient == null) throw new UserNotFoundException("User not found");
        if (!sender.isLogged() || !recipient.isLogged()) throw new LoginUserException("Login to continue");

        ShareNoteResponse response = noteService.shareNote(shareNoteRequest);
        Note foundNote = noteService.findNoteByTitle(shareNoteRequest.getTitle());
        recipient.getNotes().add(foundNote);

        userRepository.save(recipient);
        return response;
    }

}

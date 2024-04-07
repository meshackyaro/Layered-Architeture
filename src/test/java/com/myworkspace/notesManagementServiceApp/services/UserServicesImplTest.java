package com.myworkspace.notesManagementServiceApp.services;

import com.myworkspace.notesManagementServiceApp.data.model.User;
import com.myworkspace.notesManagementServiceApp.data.repositories.UserRepository;
import com.myworkspace.notesManagementServiceApp.dtos.requests.LoginUserRequest;
import com.myworkspace.notesManagementServiceApp.dtos.requests.LogoutUserRequest;
import com.myworkspace.notesManagementServiceApp.dtos.requests.RegisterUserRequest;
import com.myworkspace.notesManagementServiceApp.dtos.responses.LoginResponse;
import com.myworkspace.notesManagementServiceApp.dtos.responses.LogoutResponse;
import com.myworkspace.notesManagementServiceApp.exceptions.UnregisteredUserException;
import com.myworkspace.notesManagementServiceApp.exceptions.UserAlreadyExistException;
import com.myworkspace.notesManagementServiceApp.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServicesImplTest {
    @Autowired
    private UserServices userServices;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void newUserRegistration_registerUserTest() {
        userRepository.deleteAll();
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userServices.register(registerRequest);
        long currentlyRegistered = userServices.findAll().size();
        assertEquals(currentlyRegistered, userServices.getNumberOfUsers());

    }
    @Test
    public void newUserRegistration_registeredUserIncreasesTest() {
        userRepository.deleteAll();
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userServices.register(registerRequest);
        RegisterUserRequest registerRequest1 = new RegisterUserRequest();
        registerRequest1.setUsername("username1");
        registerRequest1.setPassword("password");
        userServices.register(registerRequest1);
        long currentlyRegistered = userServices.findAll().size();
        assertEquals(currentlyRegistered, userServices.getNumberOfUsers());
    }
    @Test
    public void registerExistingUser_throwsUserAlreadyExistException() {
        userRepository.deleteAll();
        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("username");
        registerUserRequest1.setPassword("password");
        userServices.register(registerUserRequest1);
        assertEquals(1, userServices.getNumberOfUsers());

        RegisterUserRequest registerUserRequest2 = new RegisterUserRequest();
        registerUserRequest2.setUsername("username");
        registerUserRequest2.setPassword("password");
        assertThrows(UserAlreadyExistException.class,()-> userServices.register(registerUserRequest2));
    }
    @Test
    public void loginRegisteredUser_loginTest() {
        userRepository.deleteAll();
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("username765");
        registerUserRequest.setPassword("password765");
        userServices.register(registerUserRequest);
        long currentlyRegistered = userServices.findAll().size();
        assertEquals(currentlyRegistered, userServices.getNumberOfUsers());
        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername(registerUserRequest.getUsername());
        loginRequest.setPassword(registerUserRequest.getPassword());
        LoginResponse login = userServices.login(loginRequest);
        User user = userServices.findUserByUsername(loginRequest.getUsername());
        assertTrue(user.isLogged());
    }
    @Test
    public void unregisteredUserLogin_throwUnregisteredUserExceptionTest() {
        userRepository.deleteAll();
        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        assertThrows(UnregisteredUserException.class, ()-> userServices.login(loginRequest));
    }
    @Test
    public void logoutTest() {
        userRepository.deleteAll();
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("username");
        registerUserRequest.setPassword("password");
        userServices.register(registerUserRequest);
        long currentlyRegistered = userRepository.findAll().size();
        assertEquals(currentlyRegistered, userServices.getNumberOfUsers());
        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername(registerUserRequest.getUsername());
        loginRequest.setPassword(registerUserRequest.getPassword());
        LoginResponse login = userServices.login(loginRequest);
        User user = userRepository.findByUsername(loginRequest.getUsername());
        assertTrue(user.isLogged());
        LogoutUserRequest logoutRequest = new LogoutUserRequest();
        logoutRequest.setUsername("username");
        LogoutResponse logout = userServices.logout(logoutRequest);
        user = userRepository.findByUsername(loginRequest.getUsername());
        assertFalse(user.isLogged());
    }
}

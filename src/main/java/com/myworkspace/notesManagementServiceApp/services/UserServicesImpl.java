package com.myworkspace.notesManagementServiceApp.services;

import com.myworkspace.notesManagementServiceApp.data.model.User;
import com.myworkspace.notesManagementServiceApp.data.repositories.UserRepository;
import com.myworkspace.notesManagementServiceApp.dtos.requests.LoginUserRequest;
import com.myworkspace.notesManagementServiceApp.dtos.requests.LogoutUserRequest;
import com.myworkspace.notesManagementServiceApp.dtos.requests.RegisterUserRequest;
import com.myworkspace.notesManagementServiceApp.dtos.responses.RegistrationResponse;
import com.myworkspace.notesManagementServiceApp.dtos.responses.LoginResponse;
import com.myworkspace.notesManagementServiceApp.dtos.responses.LogoutResponse;
import com.myworkspace.notesManagementServiceApp.exceptions.IncorrectPasswordException;
import com.myworkspace.notesManagementServiceApp.exceptions.UnregisteredUserException;
import com.myworkspace.notesManagementServiceApp.exceptions.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    private UserRepository userRepository;

    @Override
    public long getNumberOfUsers() {
        return userRepository.count();
    }

    @Override
    public RegistrationResponse register(RegisterUserRequest registerRequest) {
        User user = new User();
        if (findUserByUsername(registerRequest.getUsername()) != null)
            throw new UserAlreadyExistException("username already exist");
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        userRepository.save(user);

        RegistrationResponse registrationResponse = new RegistrationResponse();
        registrationResponse.setUsername(registerRequest.getUsername());
        registrationResponse.setMessage("Your registration Successful");
        return registrationResponse;
    }

    @Override
    public LoginResponse login(LoginUserRequest loginRequest) {
        User foundUser = findUserByUsername(loginRequest.getUsername());
        if (foundUser == null) throw new UnregisteredUserException("You do not have an account, please signup to login");
//        User logInUser = userRepository.findUserByUsername(loginRequest.getUsername());
        if (!foundUser.getPassword().equals(loginRequest.getPassword()))  throw new IncorrectPasswordException("Incorrect password");
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

}

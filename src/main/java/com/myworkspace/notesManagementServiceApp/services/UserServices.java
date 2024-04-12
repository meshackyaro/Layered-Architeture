package com.myworkspace.notesManagementServiceApp.services;

import com.myworkspace.notesManagementServiceApp.data.model.User;
import com.myworkspace.notesManagementServiceApp.dtos.requests.LoginUserRequest;
import com.myworkspace.notesManagementServiceApp.dtos.requests.LogoutUserRequest;
import com.myworkspace.notesManagementServiceApp.dtos.requests.RegisterUserRequest;
import com.myworkspace.notesManagementServiceApp.dtos.responses.RegistrationResponse;
import com.myworkspace.notesManagementServiceApp.dtos.responses.LoginResponse;
import com.myworkspace.notesManagementServiceApp.dtos.responses.LogoutResponse;

import java.util.List;

public interface UserServices {
    long getNumberOfUsers();

    RegistrationResponse register(RegisterUserRequest registerRequest);

     LoginResponse login(LoginUserRequest loginRequest);

    LogoutResponse logout(LogoutUserRequest logoutRequest);

    List<User> findAll();

    User findUserByUsername(String username);
}

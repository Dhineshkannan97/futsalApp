package com.futsal.controller;

import com.Constants;
import com.futsal.Entity.User;
import com.futsal.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User request) {
        String response = userService.registerUser(request.getUsername(), request.getPassword());
        System.out.println(response);
        if (response.equals(Constants.USER_REGISTERED )) {
            return new ResponseEntity<>(Constants.USER_REGISTERED, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(Constants.USERNAME_EXISTS, HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        String loginResult = userService.loginUser(username, password);

        switch (loginResult) {
            case Constants.LOGIN_SUCCESSFUL:
                return new ResponseEntity<>(Constants.LOGIN_SUCCESSFUL, HttpStatus.OK);
            case Constants.INVALID_PASSWORD:
                return new ResponseEntity<>(Constants.INVALID_PASSWORD, HttpStatus.UNAUTHORIZED);
            case Constants.USER_BLOCKED_MESSAGE:
                return new ResponseEntity<>(Constants.USER_BLOCKED_MESSAGE, HttpStatus.FORBIDDEN);
            default:
                return new ResponseEntity<>(Constants.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping("/logout")
//    public ResponseEntity<String> logoutUser(@RequestBody User logoutRequest) {
//        String username = logoutRequest.getUsername();
//        if (userService.logoutUser(username)) {
//            return new ResponseEntity<>(Constants.LOGOUT_SUCCESS_MESSAGE, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(Constants.LOGOUT_FAILED_MESSAGE, HttpStatus.UNAUTHORIZED);
//        }
//    }
}

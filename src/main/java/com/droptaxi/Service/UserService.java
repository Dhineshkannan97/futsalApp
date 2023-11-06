package com.droptaxi.Service;

import com.Constants;
import com.droptaxi.Entity.User;
import com.droptaxi.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean validatePassword(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null) {
            // Get the hashed password from the user entity
            String hashedPasswordFromDatabase = user.getPassword();

            // Use BCryptPasswordEncoder to verify the password
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            return passwordEncoder.matches(password, hashedPasswordFromDatabase);
        }
        return false;
    }

    public String loginUser(String username, String password) {
        User user = getUserByUsername(username);
        System.out.println(user);
        if (user != null) {
            if (user.getStatus()) {
                if (validatePassword(username, password)) {
//                user.setStatus(true);
                    user.setUpdate_at(new Timestamp(System.currentTimeMillis()));
                    userRepository.save(user);
                    return Constants.LOGIN_SUCCESSFUL;
                } else {
                    return Constants.INVALID_PASSWORD;
                }
            } else {
                return Constants.USER_BLOCKED_MESSAGE;
            }

        } else {
            return Constants.USER_NOT_FOUND;
        }
    }

    public boolean logoutUser(String username) {
        User user = getUserByUsername(username);
        if (user != null) {
//            user.setStatus(false);
            userRepository.save(user);
            return true;
        }
        return false;
    }


    public String registerUser(String username, String password) {
        System.out.println(username + password);
        if (userRepository.existsByUsername(username)) {
            return Constants.USERNAME_EXISTS;
        }
        // Create a new user
        User user = new User(username, password);
        user.setStatus(true);
        user.setUsername(username);
        String hashedPassword = new BCryptPasswordEncoder().encode(password);
        user.setPassword(hashedPassword);
        user.setCreate_at(new Timestamp(System.currentTimeMillis()));
        // Save the user to the database
        userRepository.save(user);
        return Constants.USER_REGISTERED;
    }
}
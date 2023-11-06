package com.droptaxi.Service;

import com.droptaxi.Entity.User;
import com.droptaxi.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;


    public Boolean getUserActivationStatus(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return user.getStatus();
        }
        return null;
    }
    public String activationStatus(Long id, boolean activation) {
        try {
            Optional<User> userOptional = userRepository.findById(id);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setStatus(activation);
                user.setUpdate_at(new Timestamp(System.currentTimeMillis()));
                userRepository.save(user);

                String action = activation ? "activated" : "deactivated";
                return "User " + action + " successfully.";
            } else {
                throw new RuntimeException("User not found with id: " + id);
            }
        } catch (RuntimeException e) {
            // Handle the exception and return a custom response
            return "Error: " + e.getMessage(); // You can customize this message
        }
    }
}

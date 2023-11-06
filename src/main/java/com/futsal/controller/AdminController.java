package com.futsal.controller;

import com.futsal.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/activationStatus")
    public ResponseEntity<Boolean> getUserActivationStatus(@RequestParam("id") Long id) {
        Boolean status = adminService.getUserActivationStatus(id);
        if (status != null) {
            return new ResponseEntity<>(status, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);// dose i need to send a valid message for admin
        }
    }

    @PostMapping("/activationStatus")
    public ResponseEntity<String> updateStatus(
            @RequestParam("id") Long id,
            @RequestParam("activation") boolean activation) {
        String response = adminService.activationStatus(id, activation);
        return ResponseEntity.ok(response);
    }


}

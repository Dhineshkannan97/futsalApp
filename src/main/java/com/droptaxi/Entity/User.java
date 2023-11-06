package com.droptaxi.Entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private Boolean status; // 0 for inactive, 1 for active
    private Timestamp Create_at; // Add an isLoggedIn field
    private Timestamp Update_at;
//    private String Create_by; // Add an isLoggedIn field
//    private String Update_by;

    public User(String username, String password) {

    }
}

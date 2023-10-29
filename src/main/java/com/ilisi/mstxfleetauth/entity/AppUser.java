package com.ilisi.mstxfleetauth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Transient;

import java.util.UUID;

@Data
@AllArgsConstructor @NoArgsConstructor
public class AppUser {

    private UUID id;
    private String username;
    private String password;
    private String user_type;
    private String nom;
    private String prenom;
    private String email;
    private String phone;
}

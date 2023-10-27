package com.ilisi.mstxfleetauth.security;

import com.ilisi.mstxfleetauth.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class SecurityController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody UserDTO userDTO) {
        log.info("Received login request for username: {}", userDTO.getUsername());

        try {
            // Authentication logic
            Map<String, String> tokens = authenticationService.authenticate(userDTO);
            log.info("Authentication successful for user: {}", userDTO.getUsername());
            return tokens;
        } catch (AuthenticationException e) {
            log.error("Authentication failed for user: {}", userDTO.getUsername(), e);
            throw e; // Rethrow the exception for handling in a global exception handler, if configured.
        }
    }
}

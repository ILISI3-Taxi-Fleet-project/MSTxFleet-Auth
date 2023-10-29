package com.ilisi.mstxfleetauth.web;


import com.ilisi.mstxfleetauth.entity.AppUser;
import com.ilisi.mstxfleetauth.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;
    @PostMapping("/register")
    public boolean register(@RequestBody AppUser appUser)
    {
        return  registrationService.registerNewUser(appUser);
    }

    /*
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        String username = registrationRequest.getUsername();
        String password = registrationRequest.getPassword();

        // Perform registration using RegistrationService
        registrationService.registerUser(username, password);

        return ResponseEntity.ok("User registered successfully");
    }*/
}

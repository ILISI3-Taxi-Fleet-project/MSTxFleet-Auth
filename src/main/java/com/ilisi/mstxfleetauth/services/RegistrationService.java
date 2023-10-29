package com.ilisi.mstxfleetauth.services;


import com.ilisi.mstxfleetauth.entity.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final MongoServiceClient mongoServiceClient;
    private final PasswordEncoder passwordEncoder;

    public boolean registerNewUser(AppUser appUser)
    {
        log.info("Received Registration request for username: {}", appUser.getUsername());
        try {

            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
            mongoServiceClient.createUser(appUser);
            log.info("Registration succed for user  : "+appUser.getUsername());
            return true;
        }catch (Exception e){
            log.error("Registration FAILED !!!!!! " +e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

}

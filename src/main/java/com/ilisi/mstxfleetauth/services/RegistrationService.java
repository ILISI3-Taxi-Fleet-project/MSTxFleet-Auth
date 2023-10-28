package com.ilisi.mstxfleetauth.services;


import com.ilisi.mstxfleetauth.entity.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final MongoServiceClient mongoServiceClient;

    public boolean registerNewUser(AppUser appUser)
    {
        log.info("Received Registration request for username: {}", appUser.getUsername());
        try {
            mongoServiceClient.createUser(appUser);
            log.info("Registration succed for user  : "+appUser.getUsername());
            return true;
        }catch (Exception e){
            log.error("Registration FAILD !!!!!! ");
            return false;
        }

    }

}

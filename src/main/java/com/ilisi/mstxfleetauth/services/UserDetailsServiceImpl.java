package com.ilisi.mstxfleetauth.services;

import com.ilisi.mstxfleetauth.entity.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MongoServiceClient userServiceFreignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser appUser = userServiceFreignClient.getUserDetails(username);
        log.info("username : "+appUser.getUsername() + " password : "+appUser.getPassword());
        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .authorities(appUser.getUser_type())
                .build();
    }



}

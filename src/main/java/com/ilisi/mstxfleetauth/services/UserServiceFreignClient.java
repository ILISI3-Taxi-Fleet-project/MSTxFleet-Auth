package com.ilisi.mstxfleetauth.services;

import com.ilisi.mstxfleetauth.entity.AppUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "mongodb-service", url = "localhost:8081") // Feign client declaration
public interface UserServiceFreignClient {

    @GetMapping("/users/search/findByUsername?username={username}")
    AppUser getUserDetails(@PathVariable("username") String username);
}

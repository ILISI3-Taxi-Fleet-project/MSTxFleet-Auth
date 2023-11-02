package com.ilisi.mstxfleetauth.services;

import com.ilisi.mstxfleetauth.entity.AppUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "mongo-service", url = "${MONGO_SERVICE_URL}")
public interface MongoServiceClient {
    @PostMapping("/users")
    void createUser(@RequestBody AppUser user);


    @GetMapping("/users/search/findByUsername")
    AppUser getUserDetails(@RequestParam("username") String username);
}
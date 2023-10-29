package com.ilisi.mstxfleetauth.services;

import com.ilisi.mstxfleetauth.entity.AppUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mongo-service", url = "localhost:8081")
public interface MongoServiceClient {

    @PostMapping("/users")
    void createUser(@RequestBody AppUser user);
}
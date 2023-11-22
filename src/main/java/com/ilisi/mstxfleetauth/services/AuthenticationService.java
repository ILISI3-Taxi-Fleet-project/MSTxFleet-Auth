package com.ilisi.mstxfleetauth.services;

import com.ilisi.mstxfleetauth.entity.AppUser;
import com.ilisi.mstxfleetauth.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public Map<String, Object> authenticate(AppUser appUser) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword())
        );

        AppUser user = (AppUser) authentication.getPrincipal();
        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("access-token", accessToken);
        tokenMap.put("refresh-token", refreshToken);
        tokenMap.put("roles", user.getAuthorities());
        tokenMap.put("userId", user.getId());

        return tokenMap;
    }


}

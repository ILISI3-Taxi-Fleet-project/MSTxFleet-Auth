package com.ilisi.mstxfleetauth.services;

import com.ilisi.mstxfleetauth.dto.UserDTO;
import com.ilisi.mstxfleetauth.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public Map<String, Object> authenticate(UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("access-token", accessToken);
        tokenMap.put("refresh-token", refreshToken);
        tokenMap.put("roles", userDetails.getAuthorities());

        return tokenMap;
    }


}

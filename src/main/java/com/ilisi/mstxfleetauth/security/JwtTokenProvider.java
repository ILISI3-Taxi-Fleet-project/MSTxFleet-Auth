package com.ilisi.mstxfleetauth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private Instant instant;
    private final JwtEncoder jwtEncoder;
    public String generateAccessToken(UserDetails userDetails) {
        // Generate access token logic using userDetails and jwtProperties
        instant = Instant.now();
        JwtClaimsSet jwtClaimsSetAccessToken = JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(jwtProperties.accessTokenExpiration(), ChronoUnit.MINUTES))
                .subject(userDetails.getUsername())
                .build();
        JwtEncoderParameters jwtEncoderParametersAccToken = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS512).build(),
                jwtClaimsSetAccessToken
        );
        return jwtEncoder.encode(jwtEncoderParametersAccToken).getTokenValue();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        // Generate refresh token logic using userDetails and jwtProperties
        instant = Instant.now();
        JwtClaimsSet jwtClaimsSetRefreshToken = JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(jwtProperties.refreshTokenExpiration(), ChronoUnit.DAYS))
                .subject(userDetails.getUsername())
                .build();
        JwtEncoderParameters jwtEncoderParametersRefreshToken = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS512).build(),
                jwtClaimsSetRefreshToken
        );
        return jwtEncoder.encode(jwtEncoderParametersRefreshToken).getTokenValue();
    }

}

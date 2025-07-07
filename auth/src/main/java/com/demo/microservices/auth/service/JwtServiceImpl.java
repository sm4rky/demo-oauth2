package com.demo.microservices.auth.service;

import com.demo.microservices.auth.entity.UserPrincipal;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtServiceImpl implements JwtService {

    JwtEncoder jwtEncoder;

    @NonFinal @Value("${spring.security.jwt.issuer}")
    String issuer;

    @NonFinal @Value("${spring.security.jwt.access-token.expiration}")
    long ACCESS_TOKEN_EXPIRATION;

    @NonFinal @Value("${spring.security.jwt.refresh-token.expiration}")
    long REFRESH_TOKEN_EXPIRATION;

    @Override
    public String generateAccessToken(UserPrincipal userPrincipal) {
        Instant now = Instant.now();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .subject(userPrincipal.getId())
                .claim("id", userPrincipal.getId())
                .claim("firstName", userPrincipal.getFirstName())
                .claim("lastName", userPrincipal.getLastName())
                .claim("email", userPrincipal.getEmail())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(ACCESS_TOKEN_EXPIRATION))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims))
                .getTokenValue();
    }

    @Override
    public String generateRefreshToken(UserPrincipal userPrincipal) {
        Instant now = Instant.now();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .subject(userPrincipal.getId())
                .claim("type", "refresh")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(REFRESH_TOKEN_EXPIRATION))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims))
                .getTokenValue();
    }

    @Override
    public long getExpirySeconds() {
        return ACCESS_TOKEN_EXPIRATION;
    }
}
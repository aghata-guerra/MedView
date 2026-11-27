package com.guerra.medview.security;

import com.guerra.medview.model.Doctor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

/**
 * Issues a signed JWT (HS256) for an authenticated doctor.
 */
@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final long expirationMinutes;

    public JwtService(JwtEncoder jwtEncoder,
                      @Value("${medview.security.jwt.expiration-minutes}") long expirationMinutes) {
        this.jwtEncoder = jwtEncoder;
        this.expirationMinutes = expirationMinutes;
    }

    public String generateToken(Doctor doctor) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("medview")
                .issuedAt(now)
                .expiresAt(now.plus(Duration.ofMinutes(expirationMinutes)))
                .subject(String.valueOf(doctor.getId()))
                .claim("name", doctor.getName())
                .claim("email", doctor.getEmail())
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    public long getExpirationSeconds() {
        return Duration.ofMinutes(expirationMinutes).toSeconds();
    }
}

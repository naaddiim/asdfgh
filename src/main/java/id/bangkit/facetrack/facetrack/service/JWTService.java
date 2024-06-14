package id.bangkit.facetrack.facetrack.service;

import id.bangkit.facetrack.facetrack.config.JWTProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class JWTService {
    private final JWTProperties jwtProperties;

    public String generateToken(UserDetails userDetails, Date expirationDate, Map<String, Object> additionalClaims) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtProperties.getKey().getBytes());
        return Jwts.builder()
                .claims()
                .subject(userDetails.getUsername()) // it means email in our user model (generally a unique value)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expirationDate)
                .add(additionalClaims)
                .and()
                .signWith(secretKey)
                .compact();
    }

    public String extractEmail(String token) {
        return getAllClaims(token).getSubject();
    }

    public boolean isExpired(String token) {
        return getAllClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
    }

    public boolean isValid(String token, UserDetails userDetails) {
        String email = extractEmail(token);
        return userDetails.getUsername().equals(email) && !isExpired(token);
    }

    private Claims getAllClaims(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtProperties.getKey().getBytes());
        JwtParser parser = Jwts.parser().verifyWith(secretKey).build();
        return parser.parseSignedClaims(token).getPayload();
    }
}

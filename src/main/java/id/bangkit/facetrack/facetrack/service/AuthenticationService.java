package id.bangkit.facetrack.facetrack.service;

import id.bangkit.facetrack.facetrack.config.JWTProperties;
import id.bangkit.facetrack.facetrack.dto.request.users.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JWTService jwtService;
    private final JWTProperties jwtProperties;

    public String authentication(LoginRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));
        if (!authenticate.isAuthenticated()) {
            throw new UsernameNotFoundException("invalid user request !");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.email());
        return generateAccessToken(userDetails);
    }

    private String generateAccessToken(UserDetails userDetails) {
        return jwtService.generateToken(userDetails,
                new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration()),
                new HashMap<>());
    }
}

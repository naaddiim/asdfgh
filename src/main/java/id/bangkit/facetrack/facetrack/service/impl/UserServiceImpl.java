package id.bangkit.facetrack.facetrack.service.impl;

import id.bangkit.facetrack.facetrack.config.JWTProperties;
import id.bangkit.facetrack.facetrack.dto.RegisterUserResponse;
import id.bangkit.facetrack.facetrack.dto.UpdateUserRequest;
import id.bangkit.facetrack.facetrack.entity.User;
import id.bangkit.facetrack.facetrack.exception.EmailUnavailableException;
import id.bangkit.facetrack.facetrack.exception.UserNotFoundException;
import id.bangkit.facetrack.facetrack.repository.UserRepository;
import id.bangkit.facetrack.facetrack.service.CustomUserDetailsService;
import id.bangkit.facetrack.facetrack.service.JWTService;
import id.bangkit.facetrack.facetrack.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JWTService jwtService;
    private final JWTProperties jwtProperties;


    @Override
    public RegisterUserResponse createUser(User newUser) {
        User found = userRepository.findByEmail(newUser.getEmail());
        if (found != null) {
            throw new EmailUnavailableException("This email cannot be used");
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        User createdUser = userRepository.save(newUser);

        UserDetails userDetails = userDetailsService.loadUserByUsername(createdUser.getEmail());
        String accessToken = generateAccessToken(userDetails);
        return new RegisterUserResponse(createdUser.getUserId(), createdUser.getEmail(), accessToken);
    }

    @Override
    public User findUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("cannot find user"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(UpdateUserRequest request, int userId) {
        User updatedUser = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("cannot find user")
        );
        updatedUser.setNama(request.nama());
        updatedUser.setNoTelp(request.noTelp());
        updatedUser.setGender(request.gender());
        return userRepository.save(updatedUser);
    }

    @Override
    public User findUserByBearerToken() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();;
        return userRepository.findByEmail(email);
    }


    private String generateAccessToken(UserDetails userDetails) {
        return jwtService.generateToken(userDetails,
                new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration()),
                new HashMap<>());
    }
}
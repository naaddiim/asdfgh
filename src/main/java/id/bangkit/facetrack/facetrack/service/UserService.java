package id.bangkit.facetrack.facetrack.service;

import id.bangkit.facetrack.facetrack.dto.RegisterUserResponse;
import id.bangkit.facetrack.facetrack.dto.UpdateUserRequest;
import id.bangkit.facetrack.facetrack.entity.User;
import id.bangkit.facetrack.facetrack.exception.EmailUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface UserService {
    RegisterUserResponse createUser(User newUser);

    User findUserById(int userId);

    List<User> findAll();

    User updateUser(UpdateUserRequest request, int userId);

    User findUserByBearerToken();
}

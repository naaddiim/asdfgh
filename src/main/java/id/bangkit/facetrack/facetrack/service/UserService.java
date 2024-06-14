package id.bangkit.facetrack.facetrack.service;

import id.bangkit.facetrack.facetrack.dto.*;
import id.bangkit.facetrack.facetrack.entity.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface UserService {
    RegisterUserResponse createUser(User newUser);

    User findUserById(int userId);

    List<User> findAll();

    User updateUser(UpdateUserRequest request, int userId);

    User findUserByBearerToken();

    void sendEmail(SimpleMailMessage email);

    String forgotPassword(ForgotPasswordRequest email);


    boolean confirmOTP(ForgotPasswordRequest request);

    User changePassword(ChangePasswordRequest request);
}

package id.bangkit.facetrack.facetrack.service;

import id.bangkit.facetrack.facetrack.dto.*;
import id.bangkit.facetrack.facetrack.dto.request.users.ChangePasswordRequest;
import id.bangkit.facetrack.facetrack.dto.request.users.CreateAndLoginUserRequest;
import id.bangkit.facetrack.facetrack.dto.request.users.ForgotPasswordRequest;
import id.bangkit.facetrack.facetrack.dto.request.users.UpdateUserRequest;

import org.springframework.mail.SimpleMailMessage;

public interface UserService {
    NewUserDTO createUser(CreateAndLoginUserRequest request);

    UserDTO findUserById(int userId);

    UserDTO updateUser(UpdateUserRequest request, int userId);

    UserDTO findUserByBearerToken();

    void sendEmail(SimpleMailMessage email);

    String forgotPassword(ForgotPasswordRequest email);

    void confirmOTP(ForgotPasswordRequest request);

    UserDTO changePassword(ChangePasswordRequest request);
}

package id.bangkit.facetrack.facetrack.controller;

import id.bangkit.facetrack.facetrack.dto.*;
import id.bangkit.facetrack.facetrack.dto.request.ChangePasswordRequest;
import id.bangkit.facetrack.facetrack.dto.request.CreateAndLoginUserRequest;
import id.bangkit.facetrack.facetrack.dto.request.ForgotPasswordRequest;
import id.bangkit.facetrack.facetrack.dto.request.UpdateUserRequest;
import id.bangkit.facetrack.facetrack.dto.response.APIResponse;
import id.bangkit.facetrack.facetrack.dto.response.AuthenticationResponse;
import id.bangkit.facetrack.facetrack.dto.response.RegisterUserResponse;
import id.bangkit.facetrack.facetrack.dto.response.UserResponse;
import id.bangkit.facetrack.facetrack.entity.Role;
import id.bangkit.facetrack.facetrack.entity.User;
import id.bangkit.facetrack.facetrack.service.AuthenticationService;
import id.bangkit.facetrack.facetrack.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication", description = "Endpoint for authentication")
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Login the User", description = "Authentication to acquire bearer token")
    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
    Map<String, Object> authenticate(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A request to authenticate the user")
            @RequestBody CreateAndLoginUserRequest authRequest) {
        AuthenticationResponse authentication = authenticationService.authentication(authRequest);
        return APIResponse.generateResponse(true, "Access Token", authentication);
    }

    @Operation(summary = "Get Current User Info", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/user/current")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getUserById() {
        UserResponse userResponse = mapToUserResponse(userService.findUserByBearerToken());
        return APIResponse.generateResponse(true, "Data user berdasarkan bearer token", userResponse);
    }

    @Operation(summary = "Create User", description = "User-ID is generated and maintained by the service.")
    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A request to create user object")
            @RequestBody @Valid CreateAndLoginUserRequest request) {
        RegisterUserResponse user = userService.createUser(toModel(request));
        return APIResponse.generateResponse(true, "Berhasil membuat user", user);
    }

    @Operation(summary = "Update User after Register", description = "Updating user information")
    @PutMapping(value = "/register/update/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> updateUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A request to update user information")
            @RequestBody @Valid UpdateUserRequest request
            , @PathVariable int userId) {
        UserResponse userResponse = mapToUserResponse(userService.updateUser(request, userId));
        return APIResponse.generateResponse(true, "Berhasil update user", userResponse);
    }

    @Operation(summary = "Forgot password", description = "check the status of email that password forgotten")
    @PostMapping(value = "/forgot-password", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> forgotPassword(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "otp field is set to be null")
            @RequestBody ForgotPasswordRequest request) {
        String emailToChange = userService.forgotPassword(request);
        return APIResponse.generateResponse(true, "Email", emailToChange);
    }

    @Operation(summary = "Confirm OTP", description = "conrim that an OTP is associated with the right email")
    @PostMapping(value = "/confirm-otp", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> confirmOtp(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "request to confirm that an otp associated with the right email")
            @RequestBody ForgotPasswordRequest request) {
        boolean status = userService.confirmOTP(request);
        if (!status) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(APIResponse.generateResponse(status, "OTP Tidak tepat", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.generateResponse(status, "OTP Benar", null));
    }

    @Operation(summary = "Change New password", description = "change password to a new one")
    @PostMapping(value = "/change-password", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> changePassword(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "otp field is set to be null")
            @RequestBody ChangePasswordRequest request) {
        User user = userService.changePassword(request);
        return APIResponse.generateResponse(true, "Email", mapToUserResponse(user));
    }

    private UserResponse mapToUserResponse(User user) {
        return new UserResponse(user.getUserId(), user.getEmail(), user.getNama(), user.getGender(), user.getNoTelp(), user.getRole(), user.getPrograms());
    }

    private User toModel(CreateAndLoginUserRequest request) {
        return User.builder()
                .email(request.email())
                .password(request.password())
                .role(Role.USER)
                .build();
    }
}

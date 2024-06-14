package id.bangkit.facetrack.facetrack.controller;

import id.bangkit.facetrack.facetrack.dto.*;
import id.bangkit.facetrack.facetrack.entity.Role;
import id.bangkit.facetrack.facetrack.entity.User;
import id.bangkit.facetrack.facetrack.service.AuthenticationService;
import id.bangkit.facetrack.facetrack.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoint for authentication")
public class UserController {
    private final UserServiceImpl userServiceImpl;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    Map<String, Object> authenticate(@RequestBody CreateAndLoginUserRequest authRequest) {
        AuthenticationResponse authentication = authenticationService.authentication(authRequest);
        return APIResponse.generateResponse(true, "Access Token", authentication);
    }

    @Operation(summary = "Get Current User Info", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/user/current")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getUserById() {
        UserResponse userResponse = mapToUserResponse(userServiceImpl.findUserByBearerToken());
        return APIResponse.generateResponse(true, "Data user berdasarkan bearer token", userResponse);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> createUser(@RequestBody @Valid CreateAndLoginUserRequest request) {
        RegisterUserResponse user = userServiceImpl.createUser(toModel(request));
        return APIResponse.generateResponse(true, "Berhasil membuat user", user);
    }

    @PutMapping("/register/update/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> updateUser(
            @RequestBody @Valid UpdateUserRequest request
            , @PathVariable int userId) {
        UserResponse userResponse = mapToUserResponse(userServiceImpl.updateUser(request, userId));
        return APIResponse.generateResponse(true, "Berhasil update user", userResponse);
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

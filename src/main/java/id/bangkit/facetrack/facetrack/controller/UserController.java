package id.bangkit.facetrack.facetrack.controller;

import id.bangkit.facetrack.facetrack.dto.*;
import id.bangkit.facetrack.facetrack.dto.request.users.ChangePasswordRequest;
import id.bangkit.facetrack.facetrack.dto.request.users.CreateAndLoginUserRequest;
import id.bangkit.facetrack.facetrack.dto.request.users.ForgotPasswordRequest;
import id.bangkit.facetrack.facetrack.dto.request.users.UpdateUserRequest;
import id.bangkit.facetrack.facetrack.dto.response.APIResponse;
import id.bangkit.facetrack.facetrack.dto.response.users.AuthenticationResponse;
import id.bangkit.facetrack.facetrack.dto.response.users.ChangePasswordResponse;
import id.bangkit.facetrack.facetrack.dto.response.users.CurrentUserResponse;
import id.bangkit.facetrack.facetrack.dto.response.users.ForgotPasswordResponse;
import id.bangkit.facetrack.facetrack.dto.response.users.RegisterUserResponse;
import id.bangkit.facetrack.facetrack.dto.response.users.UpdateUserResponse;
import id.bangkit.facetrack.facetrack.service.AuthenticationService;
import id.bangkit.facetrack.facetrack.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoint for authentication")
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Login the User", description = "Authentication to acquire bearer token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access Token for user", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class)) }),
    })
    @PostMapping(value = "/login", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public AuthenticationResponse authenticate(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A request to authenticate the user") @RequestBody CreateAndLoginUserRequest authRequest) {
        String accessToken = authenticationService.authentication(authRequest);
        return new AuthenticationResponse(true, "Access Token", accessToken);
    }

    @Operation(summary = "Get Current User Info", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Current Logged in User info", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CurrentUserResponse.class)) }),
    })
    @GetMapping(value = "/user/current")
    public CurrentUserResponse getUserById() {
        UserDTO userDTO = userService.findUserByBearerToken();
        return new CurrentUserResponse(true, "Data user berdasarkan bearer token", userDTO);
    }

    @Operation(summary = "Create User", description = "User-ID is generated and maintained by the service.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created User Response", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterUserResponse.class)) }),
    })
    @PostMapping(value = "/register", consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterUserResponse createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A request to create user object") @RequestBody @Valid CreateAndLoginUserRequest request) {
        NewUserDTO newUser = userService.createUser(request);
        return new RegisterUserResponse(true, "Berhasil membuat user", newUser);
    }

    @Operation(summary = "Update User after Register", description = "Updating user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update User Response", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateUserResponse.class)) }),
    })
    @PutMapping(value = "/register/update/{userId}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public UpdateUserResponse updateUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A request to update user information") @RequestBody @Valid UpdateUserRequest request,
            @PathVariable int userId) {
        UserDTO userDTO = userService.updateUser(request, userId);
        return new UpdateUserResponse(true, "Berhasil update user", userDTO);
    }

    @Operation(summary = "Forgot password", description = "check the status of email that password forgotten")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email for forgot password", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ForgotPasswordResponse.class)) }),
    })
    @PostMapping(value = "/forgot-password", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ForgotPasswordResponse forgotPassword(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "otp field is set to be null") @RequestBody ForgotPasswordRequest request) {
        String emailToChange = userService.forgotPassword(request);
        return new ForgotPasswordResponse(true, "Email", emailToChange);
    }

    // TODO
    @Operation(summary = "Confirm OTP", description = "conrim that an OTP is associated with the right email")
    @PostMapping(value = "/confirm-otp", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, Object>> confirmOtp(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "request to confirm that an otp associated with the right email") @RequestBody ForgotPasswordRequest request) {
        boolean status = userService.confirmOTP(request);
        if (!status) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(APIResponse.generateResponse(status, "OTP Tidak tepat", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.generateResponse(status, "OTP Benar", null));
    }
    // TODO

    @Operation(summary = "Change New password", description = "change password to a new one")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Create new password after Reset", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ChangePasswordRequest.class)) }),
})
    @PostMapping(value = "/change-password", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ChangePasswordResponse changePassword(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "otp field is set to be null") @RequestBody ChangePasswordRequest request) {
        UserDTO userDTO = userService.changePassword(request);
        return new ChangePasswordResponse(true, "Email", userDTO);
    }
}

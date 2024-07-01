package id.bangkit.facetrack.facetrack.dto.response.users;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "status", "message", "email" })
public record ForgotPasswordResponse(
        boolean status,
        String message,
        String email) {

}

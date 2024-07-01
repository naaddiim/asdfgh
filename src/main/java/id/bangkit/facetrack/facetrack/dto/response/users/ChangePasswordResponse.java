package id.bangkit.facetrack.facetrack.dto.response.users;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import id.bangkit.facetrack.facetrack.dto.UserDTO;

@JsonPropertyOrder({ "status", "message", "data" })
public record ChangePasswordResponse(
        boolean status,
        String message,
        UserDTO data) {

}

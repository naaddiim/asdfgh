package id.bangkit.facetrack.facetrack.dto.response.users;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import id.bangkit.facetrack.facetrack.dto.NewUserDTO;

@JsonPropertyOrder({ "status", "message", "data" })
public record RegisterUserResponse(
        boolean status,
        String message,
        NewUserDTO data) {

}
